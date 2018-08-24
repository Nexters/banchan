package banchan.nexters.com.nanigoandroid

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.View
import banchan.nexters.com.nanigoandroid.adapter.MainPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity(){


    private val TAG = "TAG : MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        main_tablayout.addTab(main_tablayout.newTab().setIcon(R.drawable.selector_tab_speaker))
        main_tablayout.addTab(main_tablayout.newTab().setIcon(R.drawable.selector_tab_logo))
        main_tablayout.addTab(main_tablayout.newTab().setIcon(R.drawable.selector_tab_mypage))
        main_tablayout.tabGravity = TabLayout.GRAVITY_FILL
        for (i in 0 until main_tablayout.tabCount) {
            val tab = main_tablayout.getTabAt(i)
            if (tab != null) {
                when(i) {
                    0 -> tab.setCustomView(R.layout.layout_tab_speaker)
                    1 -> tab.setCustomView(R.layout.layout_tab_view)
                    2 -> tab.setCustomView(R.layout.layout_tab_mypage)
                }
            }
        }

        main_viewpager.adapter = MainPagerAdapter(supportFragmentManager)
        main_viewpager.currentItem = 1

        main_viewpager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(main_tablayout))
        main_tablayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener {
            override fun onTabReselected(tab: TabLayout.Tab?) {

            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {

            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                main_viewpager.currentItem = tab!!.position
            }
        })

        main_viewpager.setPageTransformer(false, NoPageTransformer())

    }

    inner class NoPageTransformer : ViewPager.PageTransformer {
        override fun transformPage(view: View, position: Float) {
            if(position <= -1.0F || position >= 1.0F) {
                view.translationX = view.width * position
                view.alpha = 0.0F
            } else if( position == 0.0F ) {
                view.translationX = view.width * position
                view.alpha = 1.0F
            } else {
                // position is between -1.0F & 0.0F OR 0.0F & 1.0F
                view.translationX = view.width * -position
                view.alpha = 1.0F - Math.abs(position)
            }
        }
    }

}
