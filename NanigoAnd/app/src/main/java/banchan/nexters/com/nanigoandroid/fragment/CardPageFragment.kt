package banchan.nexters.com.nanigoandroid.fragment


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import banchan.nexters.com.nanigoandroid.R
import banchan.nexters.com.nanigoandroid.adapter.CardStackAdapter
import swipeable.com.layoutmanager.OnItemSwiped
import swipeable.com.layoutmanager.SwipeableLayoutManager
import swipeable.com.layoutmanager.SwipeableTouchHelperCallback
import swipeable.com.layoutmanager.touchelper.ItemTouchHelper


class CardPageFragment : Fragment() {

    private val mAdapter = CardStackAdapter()
    lateinit var mRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_card_page, container, false)
        mRecyclerView = view.findViewById(R.id.nanigo_recycler_view)

        newInstance()
        return view
    }

    fun newInstance() : CardPageFragment {
        val args: Bundle = Bundle()
        val frag = CardPageFragment()
        frag.arguments = args

        val swipeableTouchHelperCallback = object : SwipeableTouchHelperCallback(object : OnItemSwiped {
            override fun onItemSwiped() {
                //mAdapter.removeTopItem()
            }

            override fun onItemSwipedLeft() {
                Log.e("SWIPE", "LEFT")
            }

            override fun onItemSwipedRight() {
                Log.e("SWIPE", "RIGHT")
            }
        }) {
        }



        val itemTouchHelper = ItemTouchHelper(swipeableTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(mRecyclerView)
        mRecyclerView.layoutManager = SwipeableLayoutManager().setAngle(10)
                .setAnimationDuratuion(450)
                .setMaxShowCount(3)
                .setScaleGap(0.1f)
                .setTransYGap(0)
        mRecyclerView.adapter = mAdapter



        return frag
    }


}
