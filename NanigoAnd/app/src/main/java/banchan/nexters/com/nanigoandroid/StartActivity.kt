package banchan.nexters.com.nanigoandroid

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        var path = Uri.parse("android.resource://"+packageName+"/"+R.raw.banchan)
        vv_start?.setVideoURI(path)
        vv_start.setOnPreparedListener { mp->
            mp.isLooping=true;
        }
        vv_start.start()

        btn_start_go.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW,Uri.parse("nanigo://join")))
        }

    }
}
