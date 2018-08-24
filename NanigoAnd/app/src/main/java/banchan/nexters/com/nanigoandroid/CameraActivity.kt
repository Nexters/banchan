package banchan.nexters.com.nanigoandroid

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo
import com.miguelbcr.ui.rx_paparazzo2.entities.size.SmallSize
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers



class CameraActivity : AppCompatActivity() {
    private val TAG = "CameraActivity"

    companion object {
        val INTENT_KEY = "INTENT_KEY"
        val INTENT_FLAG = "INTENT_FLAG"
        val KEY_CAMERA = 0
        val KEY_GALLERY = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_camera)


        val extra = intent.getIntExtra(INTENT_KEY, 0)
        val flag = intent.getStringExtra(INTENT_FLAG)

        if(extra == KEY_CAMERA) {
            takePicture(flag)
        } else if (extra == KEY_GALLERY) {
            getPicture(flag)
        }

    }

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
                    val returnIntent = Intent()
                    returnIntent.putExtra("file", response.data().file.toString())
                    returnIntent.putExtra(CameraActivity.INTENT_FLAG, flag)
                    setResult(Activity.RESULT_OK, returnIntent)
                    finish()

                    //convertFileByte(flag, response.data().file)
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
                    val returnIntent = Intent()
                    returnIntent.putExtra("file", response.data().file.toString())
                    returnIntent.putExtra(CameraActivity.INTENT_FLAG, flag)
                    setResult(Activity.RESULT_OK, returnIntent)
                    finish()
                    //convertFileByte(flag, response.data().file)
                }
    }

}
