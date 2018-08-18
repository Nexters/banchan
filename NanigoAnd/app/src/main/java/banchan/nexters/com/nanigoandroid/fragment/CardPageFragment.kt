package banchan.nexters.com.nanigoandroid.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import banchan.nexters.com.nanigoandroid.R


class CardPageFragment : Fragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_card_page, container, false)
        /*mRecyclerView = view.findViewById(R.id.nanigo_recycler_view)
        mRecyclerView.layoutManager = SwipeableLayoutManager().setAngle(10)
                .setAnimationDuratuion(450)
                .setMaxShowCount(2)
                .setScaleGap(0.1f)
                .setTransYGap(0)
        mRecyclerView.adapter = mAdapter*/
        return view
    }

    fun newInstance() : CardPageFragment {
        val args: Bundle = Bundle()
        val frag = CardPageFragment()
        frag.arguments = args



        return frag
    }


}
