package banchan.nexters.com.nanigoandroid

import android.app.Activity
import android.app.Application
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatDialog
import android.view.View
import com.airbnb.lottie.LottieAnimationView
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        RxPaparazzo.register(this)
    }

    init {
        instance = this
    }

    companion object {
        private var instance: MyApplication? = null

        fun get(): MyApplication {
            return instance!!
        }
    }


//    class MyApplication private constructor(){
//        // 외부에서 static 형태로 접근 가능
//        companion object {
//            fun getInstance() = MyApplication()
//        }
//    }


    /**
     * Progress Dialog
     * HOW TO USE
     * MyApplication.Companion.get().progressON(JoinActivity.this);
     * MyApplication.Companion.get().progressOFF();
     */
    internal var progressDialog: AppCompatDialog? = null


    fun progressON(activity: Activity?) {
        if (activity == null) {
            return
        }
        if (progressDialog != null && progressDialog!!.isShowing) {
            //            progressSET(message);
        } else {
            progressDialog = AppCompatDialog(activity)
            progressDialog!!.setCancelable(false)
            progressDialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            progressDialog!!.setContentView(R.layout.loading)
            progressDialog!!.show()
        }

        val lottieAnimationView = progressDialog!!.findViewById<View>(R.id.progress_lottie) as LottieAnimationView?
        lottieAnimationView!!.playAnimation()


    }

    fun progressOFF() {
        if (progressDialog != null && progressDialog!!.isShowing) {
            progressDialog!!.dismiss()
            progressDialog = null
        }
    }
}