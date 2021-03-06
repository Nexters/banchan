package banchan.nexters.com.nanigoandroid.fragment

import android.content.Intent
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
import android.widget.Toast
import banchan.nexters.com.nanigoandroid.AnswerActivity
import banchan.nexters.com.nanigoandroid.MyApplication
import banchan.nexters.com.nanigoandroid.R
import banchan.nexters.com.nanigoandroid.adapter.SnappyAdapter
import banchan.nexters.com.nanigoandroid.adapter.SnappyAdapter.Companion.VIEW_TYPE_A
import banchan.nexters.com.nanigoandroid.adapter.SnappyAdapter.Companion.VIEW_TYPE_B
import banchan.nexters.com.nanigoandroid.adapter.SnappyAdapter.Companion.VIEW_TYPE_C
import banchan.nexters.com.nanigoandroid.adapter.SnappyAdapter.Companion.VIEW_TYPE_D
import banchan.nexters.com.nanigoandroid.data.*
import banchan.nexters.com.nanigoandroid.http.APIUtil
import banchan.nexters.com.nanigoandroid.listener.FlipListener
import banchan.nexters.com.nanigoandroid.utils.IsOnline
import banchan.nexters.com.nanigoandroid.utils.PreferenceManager
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class QuestionCardFragment: Fragment(){
    private val TAG = "QuestionCardFragment"

    lateinit var mSnappyView: RecyclerView
    lateinit var mBtnX: ImageView
    lateinit var mBtnO: ImageView
    private var mAdapter: SnappyAdapter? = null
    lateinit var mFlipListener: FlipListener

    lateinit var colors: IntArray
    lateinit var userId: String
    private val itemCount = 10
    private var mPosition = 0
    private var requestCount = 0

    private val service = APIUtil.getService()
    private var mCardList: MutableList<QuestionCard> = mutableListOf()
    private var myCellHeight: Int? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?{
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_question_card, container, false)

        mSnappyView = view.findViewById<RecyclerView>(R.id.rv_question_card)


        mBtnO = view.findViewById(R.id.btn_answer_o)
        mBtnO.setOnClickListener {
            // 결과 api 연동
            postVoteResult("A")
        }
        mBtnX = view.findViewById(R.id.btn_answer_x)
        mBtnX.setOnClickListener {
            // 결과 api 연동
            postVoteResult("B")
        }



        init()
        setup()
        //reload()

        return view
    }

    fun newInstance() : QuestionCardFragment {
        val args: Bundle = Bundle()
        val frag = QuestionCardFragment()
        frag.arguments = args
        return frag
    }

    private fun init() {

        userId = PreferenceManager.getInstance(activity).userId
        userId = if(userId.isNullOrEmpty()) { 0.toString() } else { userId }

        mCardList.add(addLastCard(R.string.last_card))
    }

    private fun setup() {
        getCardList(0)

        mSnappyView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        mSnappyView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                val offset = mSnappyView.computeVerticalScrollOffset()
                if(myCellHeight==null) {
                    myCellHeight = mSnappyView.getChildAt(0).measuredHeight
                }
                if (offset % myCellHeight!! == 0) {
                    mPosition = offset / myCellHeight!!
                    mSnappyView.setBackgroundColor(colors[mPosition%5])
                    val viewType = mAdapter!!.getItemViewType(mPosition)
                    if(viewType == VIEW_TYPE_A || viewType == VIEW_TYPE_B) {
                        Picasso.get().load(R.drawable.o_btn).fit().centerCrop().into(mBtnO)
                        Picasso.get().load(R.drawable.x_btn).fit().centerCrop().into(mBtnX)
                    } else if(viewType == VIEW_TYPE_C || viewType == VIEW_TYPE_D) {
                        Picasso.get().load(R.drawable.a_btn).fit().centerCrop().into(mBtnO)
                        Picasso.get().load(R.drawable.b_btn).fit().centerCrop().into(mBtnX)
                    }
                    //Toast.makeText(context, "mPosition : $mPosition", Toast.LENGTH_SHORT).show()
                    Log.i(TAG, "Item Position : $mPosition")

                    val currentItem = mCardList.get(mPosition)
                    if(mPosition == mCardList.size-2) {
                        getCardList(currentItem.order)
                    }
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

    fun addLastCard(stringId: Int): QuestionCard {
        val detail = CardDetailData(resources.getString(stringId))
        val lastCard = QuestionCard(-1, 0, " ", "A", "UNDECIDED", -1, detail, Vote(0, 0, 0), 0, Tag(false, false, false))
        //mItemList.add(mItemList.size, lastCard)
        return lastCard
    }

    private fun getCardList(lastOrder: Int) {
        IsOnline.onlineCheck(activity!!.applicationContext, IsOnline.onlineCallback {
            MyApplication.get().progressON(activity)
            service.getCardList(userId, lastOrder.toString(), itemCount.toString()).enqueue(object : Callback<CardList> {
                override fun onResponse(call: Call<CardList>?, response: Response<CardList>?) {
                    if(response!!.isSuccessful) {
                        val result = response.body()
                        if(result != null) {
                            if (result.type == "SUCCESS") {

                                if(result.data.size > 0) {
                                    //질문 카드가 존재하는 경우
                                    val newList: MutableList<QuestionCard> = result.data
                                    newList.add(newList.size, addLastCard(R.string.last_card))
                                    if (mCardList.size > 0 && lastOrder != 0) {
                                        mCardList.removeAt(mCardList.size - 1)
                                        mCardList.addAll(newList)
                                    } else {
                                        mAdapter = null
                                        mCardList.clear()
                                        mCardList.addAll(newList)
                                        mPosition = 0
                                    }
                                } else {
                                    //모든 카드를 답변한 경우, 표시할 질문 카드가 없는 경우
                                    mAdapter = null
                                    mCardList.clear()
                                    mCardList.add(addLastCard(R.string.last_card))
                                    mPosition = 0
                                }

                                if (mAdapter == null) {
                                    mAdapter = SnappyAdapter(mCardList)
                                    mSnappyView.adapter = mAdapter
                                    mFlipListener = mAdapter!!.getListener()
                                } else {
                                    mAdapter!!.setItemList(mCardList)
                                    mAdapter!!.notifyItemRangeChanged(mPosition, mCardList.size)
                                }
                                //Toast.makeText(context, resources.getString(R.string.get_list_success), Toast.LENGTH_LONG).show()
                                Log.i(TAG, "getCardList :" + resources.getString(R.string.get_list_success))


                                MyApplication.get().progressOFF()
                            } else {
                                if(result.reason == "QuestionNotFound") {
                                    if(requestCount < 3) {
                                        mBtnO.isClickable = false
                                        mBtnX.isClickable = false
                                        Handler().postDelayed({
                                            MyApplication.get().progressON(activity)
                                            Toast.makeText(context, resources.getString(R.string.data_empty), Toast.LENGTH_SHORT).show()
                                            getCardList(0)
                                            mBtnO.isClickable = true
                                            mBtnX.isClickable = true
                                        }, 2000)
                                        requestCount++
                                    } else {
                                        Toast.makeText(context, resources.getString(R.string.last_card), Toast.LENGTH_SHORT).show()
                                    }
                                    MyApplication.get().progressOFF()

                                } else {
                                    Toast.makeText(context, resources.getString(R.string.get_list_fail), Toast.LENGTH_SHORT).show()
                                }
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

    private fun postVoteResult(answer: String) {

        val currentItem = mCardList.get(mPosition)
        val voteDto = VoteCard(answer, currentItem.id, currentItem.tag.random, userId.toInt())
        service.voteCard(voteDto).enqueue(object : Callback<JsonObject> {
            override fun onResponse(call: Call<JsonObject>?, response: Response<JsonObject>?) {
                try {
                    if(response!!.isSuccessful) {
                        val result = response.body().toString()
                        val data = JSONObject(result)
                        if(data.getString("type") == "SUCCESS") {
                            Log.i(TAG, resources.getString(R.string.vote_card_success))
                            mFlipListener.onButtonClick(mSnappyView, answer, mPosition)


                            //결과 선택한 카드는 리스트에서 제외
                            Handler().postDelayed({
                                if(mPosition == mCardList.size-2) {
                                    getCardList(0)
                                    mAdapter!!.notifyDataSetChanged()
                                } else {
                                    mCardList.removeAt(mPosition)
                                    mAdapter!!.removeItemAtPosition(mPosition)
                                    mAdapter!!.notifyItemRemoved(mPosition)
                                    mAdapter!!.notifyItemRangeChanged(mPosition, mCardList.size)
                                }
                                val intent = Intent(context, AnswerActivity::class.java)
                                intent.putExtra("QUESTIONID", currentItem.id)
                                startActivity(intent)
//                                startActivity(Intent(context, AnswerActivity::class.java))
                            }, 500)

                        } else {
                            Log.i(TAG, resources.getString(R.string.vote_card_fail))
                        }
                    } else {
                        val data = JSONObject(response.errorBody()!!.string())
                        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show()

                        Log.e("oooo", data.toString())
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onFailure(call: Call<JsonObject>?, t: Throwable?) {
                Log.e("onFailure", call.toString())
            }

        })
    }


}