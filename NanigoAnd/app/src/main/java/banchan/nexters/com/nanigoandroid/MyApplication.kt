package banchan.nexters.com.nanigoandroid

import android.app.Application
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo


class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        RxPaparazzo.register(this)
    }
}