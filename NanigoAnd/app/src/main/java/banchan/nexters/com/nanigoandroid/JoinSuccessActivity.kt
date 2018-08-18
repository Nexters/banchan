package banchan.nexters.com.nanigoandroid

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_join_success.*

class JoinSuccessActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join_success)

        var path = Uri.parse("android.resource://"+packageName+"/"+R.raw.banchan)
        vv_joinsuccess?.setVideoURI(path)
        vv_joinsuccess.setOnPreparedListener { mp->
            mp.isLooping=true;
        }
        vv_joinsuccess.start()

        btn_joinsuccess_ok?.setOnClickListener {
            //finish()
            startActivity(Intent(this@JoinSuccessActivity, MainActivity::class.java))
        }
    }
}
