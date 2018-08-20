package banchan.nexters.com.nanigoandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo
import com.miguelbcr.ui.rx_paparazzo2.entities.size.SmallSize
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_card_upload.*
import kotlinx.android.synthetic.main.layout_toolbar_question_upload.*
import java.io.File


class CardUploadActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = "CardUploadActivity"

    private val IMG_TYPE_Q = "IMG_TYPE_Q"
    private val IMG_TYPE_A = "IMG_TYPE_A"
    private val IMG_TYPE_B = "IMG_TYPE_B"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card_upload)

        tv_q_text_count.text = changeTextCount(0)
        et_question.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                tv_q_text_count.text = changeTextCount(count)
            }

            override fun afterTextChanged(s: Editable) {

            }
        })

        //사진 업로드
        btn_qi_upload.setOnClickListener(this)
        btn_qa_type.setOnClickListener(this)
        btn_qb_type.setOnClickListener(this)

        btn_q_type_ox.setOnClickListener(this)
        btn_q_type_ab.setOnClickListener(this)
        toolbar_exit.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when( v?.id ?: -1) {
            R.id.btn_qi_upload -> getPicture(IMG_TYPE_Q)
            R.id.btn_qa_type -> getPicture(IMG_TYPE_A)
            R.id.btn_qb_type -> getPicture(IMG_TYPE_B)
            R.id.btn_q_type_ox -> {
                btn_q_type_ox.isSelected = !btn_q_type_ox.isSelected
                btn_q_type_ab.isSelected = false
                if(btn_q_type_ab.isSelected) { rl_q_type_ab.visibility = View.VISIBLE } else { rl_q_type_ab.visibility = View.GONE }
            }
            R.id.btn_q_type_ab -> {
                btn_q_type_ab.isSelected = !btn_q_type_ab.isSelected
                btn_q_type_ox.isSelected = false
                if(btn_q_type_ab.isSelected) { rl_q_type_ab.visibility = View.VISIBLE } else { rl_q_type_ab.visibility = View.GONE }
            }
            R.id.toolbar_exit -> finish()
        }
    }

    private fun changeTextCount(count: Int) : String {
        val pattern = getString(R.string.question_text_count)
        val to = String.format(pattern, count)
        return to
    }

    // 카메라 띄우기(갤러리, 파일 경로) - 크롭 - 파일로 다운 - 업로드 - 다운로드 URL 획득
    private fun getPicture(flag : String) {
        RxPaparazzo.single(this)
                .crop() //편집기능
                .size(SmallSize()) //해상도 조절
                .usingCamera()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    // 파일 경로
                    response -> Log.i(TAG, "파일 경로 : "+ response.data().file.toString())
                    uploadFile(flag, response.data().file)
                }
    }

    // 입력원이 파일 -> 업로드
    fun uploadFile(flag: String, file: File) {
        Log.i(TAG, "uploadFile : "+ file.toString())
        setThumb(flag, "https://s3.ap-northeast-2.amazonaws.com/nanigo-deploy/img/47IMG_A.jpeg")
        /*val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference // (root)/
        // 파일 uri 객체
        val uri = Uri.fromFile(file)
        // 업로드할 파일의 경로
        val uRef = storageRef.child("thumb/${uri.lastPathSegment}")
        uRef.putFile(uri)
                .continueWithTask { task ->
                    // 다운로드 URL 요청
                    uRef.downloadUrl
                }
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        Log.i(TAG, "업로드 결과 : "+ task.result.toString())
                        Toast.makeText(this@SignInActivity, "업로드 되었습니다.", Toast.LENGTH_SHORT).show()
                        setThumb(task.result.toString())
                    } else {
                        Toast.makeText(this@SignInActivity, "업로드 실패 : " + task.result, Toast.LENGTH_SHORT).show()
                    }
                }*/

        /*val path = file.toString() // Test1_4::class.java!!.getResource("che2.jpg").getPath()

        val image = Picasso.get().load(path).get() //ImageIO.read(File(path))
        val lnth = image.byteCount
        val dst = ByteBuffer.allocate(lnth)
        image.copyPixelsToBuffer(dst)
        val barray = dst.array()
        Log.i(TAG, "uploadFile : "+ Base64.encode(barray, 0).toString())*/
    }

    fun setThumb(flag: String, url: String) {
        when(flag) {
            IMG_TYPE_Q -> {
                rl_qi_wrap.visibility = View.GONE
                iv_q_image.visibility = View.VISIBLE
                Picasso.get().load(url).into(iv_q_image)
            }
            IMG_TYPE_A -> {
                iv_qa_camera.visibility = View.GONE
                iv_type_a_img.visibility = View.VISIBLE
                Picasso.get().load(url).into(iv_type_a_img)
                tv_a_mark.setTextColor(resources.getColor(R.color.image_A_mark))
            }
            IMG_TYPE_B -> {
                iv_qb_camera.visibility = View.GONE
                iv_type_b_img.visibility = View.VISIBLE
                Picasso.get().load(url).into(iv_type_b_img)
                tv_b_mark.setTextColor(resources.getColor(R.color.image_B_mark))
            }
        }
    }


    /*fun uploadFile(file: File) {

        val imageByte: ByteArray

        val path = Test1_4::class.java!!.getResource("che2.jpg").getPath()

        val image = ImageIO.read(File(path))
        val baos = ByteArrayOutputStream()
        ImageIO.write(image, "jpg", baos)
        baos.flush()

        imageByte = baos.toByteArray()
        System.out.println(Base64.getEncoder().encodeToString(imageByte))

    }*/

}
