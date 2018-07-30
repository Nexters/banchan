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

        val pagerAdapter = MainPagerAdapter(supportFragmentManager)
        val mViewpager = findViewById<CustomViewPager>(R.id.main_viewpager)
        mViewpager.adapter = pagerAdapter
        mViewpager.currentItem = 1

        mViewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                Log.i(TAG, "onPageScrollStateChanged() :$state")

            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

            override fun onPageSelected(position: Int) {
                Log.i(TAG, "onPageSelected() :$position")
                if(position == 1) {
                    mViewpager.disableTouches()
                } else {
                    mViewpager.enableTouches()
                }
            }

        })

        main_tabs.setupWithViewPager(mViewpager)

    }


}
