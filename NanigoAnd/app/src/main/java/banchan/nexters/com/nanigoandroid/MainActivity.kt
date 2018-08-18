package banchan.nexters.com.nanigoandroid

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import banchan.nexters.com.nanigoandroid.adapter.MainPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity(), View.OnClickListener {

    private val TAG = "TAG : MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_viewpager.adapter = MainPagerAdapter(supportFragmentManager)
        main_viewpager.currentItem = 1


        btn_tabs_speaker.setOnClickListener(this)
        btn_tabs_main.setOnClickListener(this)
        btn_tabs_mypage.setOnClickListener(this)
        //rl_main_tabs.setupWithViewPager(main_viewpager)

    }

    override fun onClick(v: View?) {
        when( v?.id ?: -1) {
            R.id.btn_tabs_speaker -> main_viewpager.setCurrentItem(0, true)
            R.id.btn_tabs_main -> main_viewpager.setCurrentItem(1, true)
            R.id.btn_tabs_mypage -> main_viewpager.setCurrentItem(2, true)
        }
    }
}
