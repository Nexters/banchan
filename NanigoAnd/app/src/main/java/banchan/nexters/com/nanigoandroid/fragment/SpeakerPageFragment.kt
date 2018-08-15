package banchan.nexters.com.nanigoandroid.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.ButtonBarLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import banchan.nexters.com.nanigoandroid.CardUploadActivity
import banchan.nexters.com.nanigoandroid.MainActivity
import banchan.nexters.com.nanigoandroid.R

class SpeakerPageFragment: Fragment() {
    private lateinit var btn_question_go: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_card_page, container, false)
        btn_question_go = view.findViewById<Button>(R.id.btn_question_go)

        btn_question_go.setOnClickListener { startActivity(Intent(context, CardUploadActivity::class.java)) }

        return view
    }

    fun newInstance() : SpeakerPageFragment {
        val args: Bundle = Bundle()
        val frag = SpeakerPageFragment()
        frag.arguments = args
        return frag
    }
}