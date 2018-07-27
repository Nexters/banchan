package banchan.nexters.com.nanigoandroid.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import banchan.nexters.com.nanigoandroid.fragment.MyPageFragment
import banchan.nexters.com.nanigoandroid.fragment.SpeakerPageFragment
import banchan.nexters.com.nanigoandroid.fragment.CardPageFragment

class MainPagerAdapter(fm:FragmentManager): FragmentPagerAdapter(fm) {

    companion object {
        const val PAGE_NUMBER = 3
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when(position) {
            0 -> "확성기"
            1 -> "질문카드"
            2 -> "마이페이지"
            else -> "null"
        }
    }

    override fun getItem(position: Int): Fragment? {
        return when(position) {
            0 -> SpeakerPageFragment().newInstance()
            1 -> CardPageFragment().newInstance()
            2 -> MyPageFragment().newInstance()
            else -> null
        }
    }

    override fun getCount(): Int {
        return PAGE_NUMBER
    }
}