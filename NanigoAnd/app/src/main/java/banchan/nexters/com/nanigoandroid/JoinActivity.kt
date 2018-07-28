package banchan.nexters.com.nanigoandroid

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_join.*

class JoinActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)
        btn_join_ok.setOnClickListener{
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("nanigo://joinsuccess")))
        }
    }
}
