package banchan.nexters.com.nanigoandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import banchan.nexters.com.nanigoandroid.adapter.MainPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_viewpager.adapter = MainPagerAdapter(supportFragmentManager)
        main_tabs.setupWithViewPager(main_viewpager)

    }
}
