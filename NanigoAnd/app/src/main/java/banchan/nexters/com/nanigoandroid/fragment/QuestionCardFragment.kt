package banchan.nexters.com.nanigoandroid.fragment

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import banchan.nexters.com.nanigoandroid.R
import banchan.nexters.com.nanigoandroid.adapter.SnappyAdapter
import banchan.nexters.com.nanigoandroid.listener.FlipListener




class QuestionCardFragment: Fragment(){
    lateinit var mProgressBar: ProgressBar
    lateinit var mSnappyView: RecyclerView
    lateinit var mBtnX: Button
    lateinit var mBtnO: Button
    private var mAdapter: SnappyAdapter? = null
    lateinit var mFlipListener: FlipListener

    lateinit var colors: IntArray


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?{
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_question_card, container, false)

        mProgressBar = view.findViewById(R.id.activity_main_progress_bar)
        mSnappyView = view.findViewById(R.id.rv_question_card)
        mBtnX = view.findViewById(R.id.btn_answer_x)
        mBtnO = view.findViewById(R.id.btn_answer_o)
        mBtnX.setOnClickListener {
            flipCard()
        }

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
        mSnappyView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mAdapter = SnappyAdapter()
        mSnappyView.adapter = mAdapter
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(mSnappyView)
        mFlipListener = mAdapter!!.getListener()

        val typeArray = context!!.resources.obtainTypedArray(R.array.main_background_colors)
        colors = IntArray(typeArray.length())
        for (i in 0 until typeArray.length()) {
            colors[i] = typeArray.getColor(i, 0)
        }
        typeArray.recycle()

        mSnappyView.setBackgroundColor(colors[0])
    }

    private fun paginate() {
        //mRecyclerView.setPaginationReserved()
        //mAdapter!!.addAll(createTouristSpots())
        mAdapter!!.notifyDataSetChanged()
    }


    private fun reload() {
        mSnappyView.setVisibility(View.GONE)
        mProgressBar.setVisibility(View.VISIBLE)
        Handler().postDelayed({
//            mAdapter = SnappyAdapter()
//            mSnappyView.setAdapter(mAdapter)
            mSnappyView.setVisibility(View.VISIBLE)
            mProgressBar.setVisibility(View.GONE)
        }, 1000)
    }

    private fun flipCard() {
        mFlipListener.onButtonClick()
        mAdapter!!.notifyDataSetChanged()
    }


}