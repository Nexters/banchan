package banchan.nexters.com.nanigoandroid

import android.content.DialogInterface
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import banchan.nexters.com.nanigoandroid.utils.ImageUtil
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

    private var img_q: String? = null
    private var img_a: String? = null
    private var img_b: String? = null


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

        btn_question_upload.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when( v?.id ?: -1) {
            R.id.btn_qi_upload -> makeDialog(IMG_TYPE_Q)
            R.id.btn_qa_type -> makeDialog(IMG_TYPE_A)
            R.id.btn_qb_type -> makeDialog(IMG_TYPE_B)
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
            R.id.btn_question_upload -> uploadQuestion()
        }
    }

    private fun changeTextCount(count: Int) : String {
        val pattern = getString(R.string.question_text_count)
        val to = String.format(pattern, count)
        return to
    }

    // 카메라 띄우기(갤러리, 파일 경로) - 크롭 - 파일로 다운
    private fun getPicture(flag : String) {
        RxPaparazzo.single(this)
                .crop() //편집기능
                .size(SmallSize()) //해상도 조절
                .usingGallery()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    // 파일 경로
                    response -> Log.i(TAG, "파일 경로 : "+ response.data().file.toString())
                    convertFileByte(flag, response.data().file)
                }
    }

    private fun takePicture(flag : String) {
        RxPaparazzo.single(this)
                .crop() //편집기능
                .size(SmallSize()) //해상도 조절
                .usingCamera()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    // 파일 경로
                    response -> Log.i(TAG, "파일 경로 : "+ response.data().file.toString())
                    convertFileByte(flag, response.data().file)
                }
    }

    private fun makeDialog(flag : String) {
        val dialog = AlertDialog.Builder(this@CardUploadActivity)
        dialog.setView(R.layout.dialog_textview)
                .setCancelable(false)
                .setPositiveButton("사진촬영", DialogInterface.OnClickListener { dialog, id ->
                    Log.v("알림", "다이얼로그 > 사진촬영 선택")
                    // 사진 촬영 클릭
                    takePicture(flag)
                }).setNegativeButton("앨범선택", DialogInterface.OnClickListener { dialogInterface, id ->
                    Log.v("알림", "다이얼로그 > 앨범선택 선택")

                    //앨범에서 선택
                    getPicture(flag)
                }).setNeutralButton("취소   ", DialogInterface.OnClickListener { dialog, id ->
                    Log.v("알림", "다이얼로그 > 취소 선택")

                    // 취소 클릭. dialog 닫기.
                    dialog.cancel()
                })

        val alert = dialog.create()
        alert.setOnShowListener {
            alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.nanigoPink))
            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.nanigoPink))
            alert.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(resources.getColor(R.color.nanigoPink))
        }

        alert.show()

    }


    fun convertFileByte(flag: String, file: File) {
        when(flag) {
            IMG_TYPE_Q -> img_q = ImageUtil().bitmap2ByteArray(file)
            IMG_TYPE_A -> img_a = ImageUtil().bitmap2ByteArray(file)
            IMG_TYPE_B -> img_b = ImageUtil().bitmap2ByteArray(file)
        }
        setThumb(flag, file.path)
    }

    fun setThumb(flag: String, path: String) {
        when(flag) {
            IMG_TYPE_Q -> {
                rl_qi_wrap.visibility = View.GONE
                iv_q_image.visibility = View.VISIBLE
                Picasso.get().load("file://"+path).config(Bitmap.Config.RGB_565).fit().centerCrop().into(iv_q_image)
            }
            IMG_TYPE_A -> {
                iv_qa_camera.visibility = View.GONE
                iv_type_a_img.visibility = View.VISIBLE
                Picasso.get().load("file://"+path).config(Bitmap.Config.RGB_565).fit().centerCrop().into(iv_type_a_img)
                tv_a_mark.setTextColor(resources.getColor(R.color.image_A_mark))
            }
            IMG_TYPE_B -> {
                iv_qb_camera.visibility = View.GONE
                iv_type_b_img.visibility = View.VISIBLE
                Picasso.get().load("file://"+path).config(Bitmap.Config.RGB_565).fit().centerCrop().into(iv_type_b_img)
                tv_b_mark.setTextColor(resources.getColor(R.color.image_B_mark))
            }
        }
    }

    fun uploadQuestion() {
        /**
        * {
            "detail": {
                "TXT_Q": "얘들아 어떤 사진이 더 잘 생겼어?",
                "TXT_A": "셀카",
                "TXT_B": "SM 사진",
                "IMG_A": "~~~~~~~~~~~~",
                "IMG_B": "------------"
            },
            "type": "A",
            "userId": 43
        }
        * */

        val txt_q = et_question.text
        val txt_a = tv_qa_type_txt.text
        val txt_b = tv_qb_type_txt.text



    }

}
