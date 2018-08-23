package banchan.nexters.com.nanigoandroid.fragment

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import banchan.nexters.com.nanigoandroid.MyApplication
import banchan.nexters.com.nanigoandroid.R
import banchan.nexters.com.nanigoandroid.adapter.SnappyAdapter
import banchan.nexters.com.nanigoandroid.data.CardList
import banchan.nexters.com.nanigoandroid.data.QuestionCard
import banchan.nexters.com.nanigoandroid.data.VoteCard
import banchan.nexters.com.nanigoandroid.http.APIUtil
import banchan.nexters.com.nanigoandroid.listener.FlipListener
import banchan.nexters.com.nanigoandroid.utils.IsOnline
import com.google.gson.JsonObject
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class QuestionCardFragment: Fragment(){
    lateinit var mProgressBar: ProgressBar
    lateinit var mSnappyView: RecyclerView
    lateinit var mBtnX: ImageView
    lateinit var mBtnO: ImageView
    private var mAdapter: SnappyAdapter? = null
    lateinit var mFlipListener: FlipListener

    lateinit var colors: IntArray
    private val userId: String? = null//PreferenceManager.getInstance(activity).userId
    private var lastOrder = 0
    private var itemCount = 10
    private var mPosition = 0

    private val service = APIUtil.getService()
    private var mCardList: MutableList<QuestionCard> = mutableListOf()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?{
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_question_card, container, false)

        mProgressBar = view.findViewById(R.id.activity_main_progress_bar)
        mSnappyView = view.findViewById<RecyclerView>(R.id.rv_question_card)


        mBtnX = view.findViewById(R.id.btn_answer_x)
        mBtnX.setOnClickListener {
            mFlipListener.onButtonClick(mSnappyView, false, mPosition)
            // 결과 api 연동
        }
        mBtnO = view.findViewById(R.id.btn_answer_o)
        mBtnO.setOnClickListener {
            mFlipListener.onButtonClick(mSnappyView, true, mPosition)
            // 결과 api 연동
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
        getCardList()

        mSnappyView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        mSnappyView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val offset = mSnappyView.computeVerticalScrollOffset()

                val myCellHeight = mSnappyView.getChildAt(0).measuredHeight
                if (offset % myCellHeight == 0) {
                    mPosition = offset / myCellHeight
                    mSnappyView.setBackgroundColor(colors[mPosition%5])
                    Toast.makeText(context, "mPosition : $mPosition", Toast.LENGTH_SHORT).show()
                }

            }
        })

        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(mSnappyView)

        val typeArray = context!!.resources.obtainTypedArray(R.array.main_background_colors)
        colors = IntArray(typeArray.length())
        for (i in 0 until typeArray.length()) {
            colors[i] = typeArray.getColor(i, 0)
        }
        typeArray.recycle()
        mSnappyView.setBackgroundColor(colors[0])

    }

    private fun getCardList() {
        IsOnline.onlineCheck(activity!!.applicationContext, IsOnline.onlineCallback {
            MyApplication.get().progressON(activity)
            service.getCardList(if(userId.isNullOrEmpty()) { 1004.toString() } else { userId }, lastOrder.toString(), itemCount.toString()).enqueue(object : Callback<CardList> {
                override fun onResponse(call: Call<CardList>?, response: Response<CardList>?) {
                    if(response!!.isSuccessful) {
                        val result = response.body()
                        if(result != null) {
                            if (result.type == "SUCCESS") {
                                if(result.data.isNotEmpty()) {
                                    mCardList.clear()
                                    mCardList.addAll(result.data)

                                    if(mAdapter == null) {
                                        mAdapter = SnappyAdapter(mCardList)
                                        mSnappyView.adapter = mAdapter
                                        mFlipListener = mAdapter!!.getListener()
                                    } else {
                                        mAdapter!!.notifyDataSetChanged()
                                    }
                                    goneLoadingProgress()
                                    Toast.makeText(context, resources.getString(R.string.get_list_success), Toast.LENGTH_LONG).show()
                                } else {
                                    Toast.makeText(context, resources.getString(R.string.data_empty), Toast.LENGTH_SHORT).show()
                                }

                                MyApplication.get().progressOFF()
                            } else {
                                Toast.makeText(context, resources.getString(R.string.get_list_fail), Toast.LENGTH_SHORT).show()
                                MyApplication.get().progressOFF()
                            }
                        }
                    } else {
                        val data = JSONObject(response.errorBody()!!.string())
                        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()

                        Log.e("oooo", data.toString())
                    }
                }

                override fun onFailure(call: Call<CardList>?, t: Throwable?) {
                    Log.e("onFailure", call.toString())
                }
            })
        })
    }

//    private fun postVoteResult(answer: String) {
//
//
//        val voteDto = VoteCard(answer, )
//        service.voteCard(cardDto).enqueue(object : Callback<JsonObject> {
//            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
//                try {
//                    if(response!!.isSuccessful) {
//                        val result = response.body().toString()
//                        val data = JSONObject(result)
//                        if(data.getString("type") == "SUCCESS") {
//                            Toast.makeText(applicationContext, resources.getString(R.string.upload_success), Toast.LENGTH_SHORT).show()
//                            finish()
//                        } else {
//                            Toast.makeText(applicationContext, resources.getString(R.string.upload_fail), Toast.LENGTH_SHORT).show()
//                        }
//                    } else {
//                        val data = JSONObject(response.errorBody()!!.string())
//                        Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show()
//
//                        Log.e("oooo", data.toString())
//                    }
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            }
//
//            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
//                Log.e("onFailure", call.toString())
//            }
//
//        })
//    }

    private fun paginate() {
        //mRecyclerView.setPaginationReserved()
        //mAdapter!!.addAll(createTouristSpots())
        mAdapter!!.notifyDataSetChanged()
    }


    private fun reload() {
        mSnappyView.visibility = View.GONE
        viewLoadingProgress()
        Handler().postDelayed({
//            mAdapter = SnappyAdapter()
//            mSnappyView.setAdapter(mAdapter)
            goneLoadingProgress()
            mSnappyView.visibility = View.VISIBLE

        }, 1000)
    }

    private fun viewLoadingProgress() {
        mProgressBar.visibility = View.VISIBLE
    }

    private fun goneLoadingProgress() {
        mProgressBar.visibility = View.GONE
    }


}