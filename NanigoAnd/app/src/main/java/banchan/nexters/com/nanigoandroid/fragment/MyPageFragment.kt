package banchan.nexters.com.nanigoandroid.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import banchan.nexters.com.nanigoandroid.R

class MyPageFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card_page, container, false)
    }

    fun newInstance() : MyPageFragment {
        val args: Bundle = Bundle()
        val frag = MyPageFragment()
        frag.arguments = args
        return frag
    }
}