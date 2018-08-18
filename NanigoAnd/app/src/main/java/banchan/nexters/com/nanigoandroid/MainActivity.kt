package banchan.nexters.com.nanigoandroid

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.util.Log
import banchan.nexters.com.nanigoandroid.adapter.MainPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*



class MainActivity : AppCompatActivity() {

    private val TAG = "TAG : MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_viewpager.adapter = MainPagerAdapter(supportFragmentManager)
        main_viewpager.currentItem = 1
        main_viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                Log.i(TAG, "onPageScrollStateChanged() :$state")

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                if(position == 1) {
                    main_viewpager.disableTouches()
                } else {
                    main_viewpager.enableTouches()
                }
            }

            override fun onPageSelected(position: Int) {
                Log.i(TAG, "onPageSelected() :$position")
                if(position == 1) {
                    main_viewpager.disableTouches()
                } else {
                    main_viewpager.enableTouches()
                }
            }

        })

        main_tabs.setupWithViewPager(main_viewpager)

    }


}
