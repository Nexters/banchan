package banchan.nexters.com.nanigoandroid

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class SpeakerPageFragment: Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card_page, container, false)
    }

    fun newInstance() :SpeakerPageFragment {
        val args: Bundle = Bundle()
        val frag = SpeakerPageFragment()
        frag.arguments = args
        return frag
    }
}