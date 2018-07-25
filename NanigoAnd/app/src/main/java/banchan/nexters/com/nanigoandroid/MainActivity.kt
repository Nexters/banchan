package banchan.nexters.com.nanigoandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        btn_hello.text = "Hi! banchan"
        btn_hello.setOnClickListener { Toast.makeText(this@MainActivity, "btn_hello Click", Toast.LENGTH_SHORT).show() }
    }
}
