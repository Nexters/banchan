package banchan.nexters.com.nanigoandroid

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import banchan.nexters.com.nanigoandroid.data.CardDetailData
import banchan.nexters.com.nanigoandroid.data.UploadCardData
import banchan.nexters.com.nanigoandroid.http.APIUtil
import banchan.nexters.com.nanigoandroid.utils.ImageUtil
import banchan.nexters.com.nanigoandroid.utils.IsOnline
import banchan.nexters.com.nanigoandroid.utils.PreferenceManager
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_card_upload.*
import kotlinx.android.synthetic.main.layout_toolbar_question_upload.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CardUploadActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = "CardUploadActivity"

    companion object {
        val IMG_TYPE_Q = "IMG_TYPE_Q"
        val IMG_TYPE_A = "IMG_TYPE_A"
        val IMG_TYPE_B = "IMG_TYPE_B"
    }


    private var img_q: String = ""
    private var img_a: String = ""
    private var img_b: String = ""

    private var imm: InputMethodManager? = null


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

        //바깥영역 터치 시 키보드 창 닫힘
        imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //imm.hideSoftInputFromWindow(et_question.getWindowToken(), 0)


        //사진 업로드
        btn_qi_upload.setOnClickListener(this)
        btn_qa_type.setOnClickListener(this)
        btn_qb_type.setOnClickListener(this)

        btn_q_type_ox.setOnClickListener(this)
        btn_q_type_ab.setOnClickListener(this)
        toolbar_exit.setOnClickListener(this)

        btn_question_upload.setOnClickListener(this)
        rl.setOnClickListener(this)
    }

    private fun hideKeyboard() {
        var view = getCurrentFocus()
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(this)
        }
        imm!!.hideSoftInputFromWindow(view.getWindowToken(), 0)
    }


    override fun onClick(v: View?) {
        hideKeyboard()
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
            R.id.rl -> {}
        }
    }

    private fun changeTextCount(count: Int) : String {
        val pattern = getString(R.string.question_text_count)
        val to = String.format(pattern, count)
        return to
    }

    // 카메라 띄우기(갤러리, 파일 경로) - 크롭 - 파일로 다운
    private fun getPicture(flag : String) {
        val intent = Intent(this@CardUploadActivity, CameraActivity::class.java)
        intent.putExtra(CameraActivity.INTENT_KEY, CameraActivity.KEY_GALLERY)
        intent.putExtra(CameraActivity.INTENT_FLAG, flag)
        startActivityForResult(intent, 1)

/*

        RxPaparazzo.single(camera)
                .crop() //편집기능
                .size(SmallSize()) //해상도 조절
                .usingGallery()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    // 파일 경로
                    response -> Log.i(TAG, "파일 경로 : "+ response.data().file.toString())
                    convertFileByte(flag, response.data().file)
                }*/
    }

    private fun takePicture(flag : String) {
        val intent = Intent(this@CardUploadActivity, CameraActivity::class.java)
        intent.putExtra(CameraActivity.INTENT_KEY, CameraActivity.KEY_CAMERA)
        intent.putExtra(CameraActivity.INTENT_FLAG, flag)
        startActivityForResult(intent, 1)

        /*val camera = CameraActivity()
        RxPaparazzo.single(camera)
                .crop() //편집기능
                .size(SmallSize()) //해상도 조절
                .usingCamera()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    // 파일 경로
                    response -> Log.i(TAG, "파일 경로 : "+ response.data().file.toString())
                    convertFileByte(flag, response.data().file)
                }*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                val file = data!!.getStringExtra("file")
                val flag = data!!.getStringExtra(CameraActivity.INTENT_FLAG)
                convertFileByte(flag, file)
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //만약 반환값이 없을 경우의 코드를 여기에 작성하세요.
            }
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


    fun convertFileByte(flag: String, file: String) {
        when(flag) {
            IMG_TYPE_Q -> img_q = ImageUtil().bitmap2ByteArray(file)
            IMG_TYPE_A -> img_a = ImageUtil().bitmap2ByteArray(file)
            IMG_TYPE_B -> img_b = ImageUtil().bitmap2ByteArray(file)
        }
        setThumb(flag, file)
    }

    fun setThumb(flag: String, path: String) {
        when(flag) {
            IMG_TYPE_Q -> {
                rl_qi_wrap.visibility = View.GONE
                iv_q_image.visibility = View.VISIBLE
                Picasso.get().load("file://$path").fit().centerCrop().into(iv_q_image)
            }
            IMG_TYPE_A -> {
                iv_qa_camera.visibility = View.GONE
                iv_type_a_img.visibility = View.VISIBLE
                Picasso.get().load("file://$path").fit().centerCrop().into(iv_type_a_img)
                tv_a_mark.setTextColor(resources.getColor(R.color.image_A_mark))
            }
            IMG_TYPE_B -> {
                iv_qb_camera.visibility = View.GONE
                iv_type_b_img.visibility = View.VISIBLE
                Picasso.get().load("file://$path").fit().centerCrop().into(iv_type_b_img)
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

        val service = APIUtil.getService()

        IsOnline.onlineCheck(applicationContext, IsOnline.onlineCallback {
            val txt_q = if(et_question.text.toString().isEmpty()) { " " } else { et_question.text.toString() }
            val txt_a = if(tv_qa_type_txt.text.toString().isEmpty()) { " " } else { tv_qa_type_txt.text.toString() }
            val txt_b = if(tv_qb_type_txt.text.toString().isEmpty()) { " " } else { tv_qb_type_txt.text.toString() }
            val card_type = if(img_q.isBlank()) {
                if( txt_a.isBlank() && txt_b.isBlank()) {
                    "A"
                } else {
                    "C"
                }
            } else {
                if( txt_a.isBlank() && txt_b.isBlank()) {
                    "B"
                } else {
                    "D"
                }
            }

            val userId = PreferenceManager.getInstance(applicationContext).userId
            val userIdInt: Int? = if(!userId.isNullOrEmpty()) { userId.toInt() } else { 1004 }
            val cardDetailDto = CardDetailData(txt_q, txt_a, txt_b, img_q, img_a, img_b)
            val cardDto = UploadCardData(cardDetailDto, card_type, userIdInt!!)
            service.uploadCard(cardDto).enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                    try {
                        if(response!!.isSuccessful) {
                            val result = response.body().toString()
                            val data = JSONObject(result)
                            if(data.getString("type") == "SUCCESS") {
                                Toast.makeText(applicationContext, resources.getString(R.string.upload_success), Toast.LENGTH_SHORT).show()
                                finish()
                            } else {
                                Toast.makeText(applicationContext, resources.getString(R.string.upload_fail), Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            val data = JSONObject(response.errorBody()!!.string())
                            Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show()

                            Log.e("oooo", data.toString())
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }

                override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                    Log.e("onFailure", call.toString())
                }

            })
        })

    }

}
