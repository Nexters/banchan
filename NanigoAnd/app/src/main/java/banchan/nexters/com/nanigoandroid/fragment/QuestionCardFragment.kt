package banchan.nexters.com.nanigoandroid.fragment

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import banchan.nexters.com.nanigoandroid.R
import banchan.nexters.com.nanigoandroid.adapter.TouristSpotCardAdapter
import banchan.nexters.com.nanigoandroid.viewholder.TouristSpot
import com.yuyakaido.android.cardstackview.CardStackView
import com.yuyakaido.android.cardstackview.SwipeDirection
import java.util.*

class QuestionCardFragment: Fragment() {
    lateinit var mProgressBar: ProgressBar
    lateinit var mCardStackView: CardStackView
    private var mAdapter: TouristSpotCardAdapter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_question_card, container, false)
        mProgressBar = view.findViewById(R.id.activity_main_progress_bar)
        mCardStackView = view.findViewById(R.id.activity_main_card_stack_view)
        setup()
        reload()

        return view
    }

    fun newInstance() : QuestionCardFragment {
        val args: Bundle = Bundle()
        val frag = QuestionCardFragment()
        frag.arguments = args
        return frag
    }

    private fun setup() {

        mCardStackView.setCardEventListener(object : CardStackView.CardEventListener {
            override fun onCardDragging(percentX: Float, percentY: Float) {
                Log.d("CardStackView", "onCardDragging")
            }

            override fun onCardSwiped(direction: SwipeDirection) {
                Log.d("CardStackView", "onCardSwiped: " + direction.toString())
                Log.d("CardStackView", "topIndex: " + mCardStackView.topIndex)
                if (mCardStackView.topIndex == mAdapter!!.count - 5) {
                    Log.d("CardStackView", "Paginate: " + mCardStackView.topIndex)
                    paginate()
                }
            }

            override fun onCardReversed() {
                Log.d("CardStackView", "onCardReversed")
            }

            override fun onCardMovedToOrigin() {
                Log.d("CardStackView", "onCardMovedToOrigin")
            }

            override fun onCardClicked(index: Int) {
                Log.d("CardStackView", "onCardClicked: $index")
            }
        })
    }

    private fun paginate() {
        mCardStackView.setPaginationReserved()
        mAdapter!!.addAll(createTouristSpots())
        mAdapter!!.notifyDataSetChanged()
    }

    private fun createTouristSpots(): List<TouristSpot> {
        val spots = ArrayList<TouristSpot>()
        spots.add(TouristSpot("Yasaka Shrine", "Kyoto", "https://source.unsplash.com/Xq1ntWruZQI/600x800"))
        spots.add(TouristSpot("Fushimi Inari Shrine", "Kyoto", "https://source.unsplash.com/NYyCqdBOKwc/600x800"))
        spots.add(TouristSpot("Bamboo Forest", "Kyoto", "https://source.unsplash.com/buF62ewDLcQ/600x800"))
        spots.add(TouristSpot("Brooklyn Bridge", "New York", "https://source.unsplash.com/THozNzxEP3g/600x800"))
        spots.add(TouristSpot("Empire State Building", "New York", "https://source.unsplash.com/USrZRcRS2Lw/600x800"))
        spots.add(TouristSpot("The statue of Liberty", "New York", "https://source.unsplash.com/PeFk7fzxTdk/600x800"))
        spots.add(TouristSpot("Louvre Museum", "Paris", "https://source.unsplash.com/LrMWHKqilUw/600x800"))
        spots.add(TouristSpot("Eiffel Tower", "Paris", "https://source.unsplash.com/HN-5Z6AmxrM/600x800"))
        spots.add(TouristSpot("Big Ben", "London", "https://source.unsplash.com/CdVAUADdqEc/600x800"))
        spots.add(TouristSpot("Great Wall of China", "China", "https://source.unsplash.com/AWh9C-QjhE4/600x800"))
        return spots
    }

    private fun createTouristSpotCardAdapter(): TouristSpotCardAdapter {
        val adapter = TouristSpotCardAdapter(this.activity)
        adapter.addAll(createTouristSpots())
        return adapter
    }

    private fun reload() {
        mCardStackView.setVisibility(View.GONE)
        mProgressBar.setVisibility(View.VISIBLE)
        Handler().postDelayed({
            mAdapter = createTouristSpotCardAdapter()
            mCardStackView.setAdapter(mAdapter)
            mCardStackView.setVisibility(View.VISIBLE)
            mProgressBar.setVisibility(View.GONE)
        }, 1000)
    }

    private fun reverse() {
        mCardStackView.reverse()
    }
}