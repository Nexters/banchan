package banchan.nexters.com.nanigoandroid

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v7.app.AppCompatActivity
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
        for (i in 0 until main_tablayout.getTabCount()) {
            val tab = main_tablayout.getTabAt(i)
            if (tab != null) tab.setCustomView(R.layout.layout_tab_view)
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



    }

}
