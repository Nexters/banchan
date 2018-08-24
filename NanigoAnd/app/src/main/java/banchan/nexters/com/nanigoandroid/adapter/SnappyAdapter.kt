package banchan.nexters.com.nanigoandroid.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Handler
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import banchan.nexters.com.nanigoandroid.ImageViewActivity
import banchan.nexters.com.nanigoandroid.R
import banchan.nexters.com.nanigoandroid.animation.FlipAnimation
import banchan.nexters.com.nanigoandroid.data.QuestionCard
import banchan.nexters.com.nanigoandroid.data.ReportCard
import banchan.nexters.com.nanigoandroid.http.APIUtil
import banchan.nexters.com.nanigoandroid.listener.FlipListener
import banchan.nexters.com.nanigoandroid.utils.ImageUtil
import banchan.nexters.com.nanigoandroid.utils.IsOnline
import banchan.nexters.com.nanigoandroid.utils.PreferenceManager
import com.google.gson.JsonObject
import com.squareup.picasso.Picasso
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import java.util.*


class SnappyAdapter(var mItemList: MutableList<QuestionCard>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), FlipListener {

    companion object {
        val VIEW_TYPE_A = 0
        val VIEW_TYPE_B = 1
        val VIEW_TYPE_C = 3
        val VIEW_TYPE_D = 4
    }

    init {
        mItemList.removeAt(mItemList.size-1)

    }
    var mContext: Context? = null

    private val service = APIUtil.getService()
    lateinit var colors: IntArray

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        mContext = parent.context
        val typeArray = parent.context!!.resources.obtainTypedArray(R.array.main_background_colors)
        colors = IntArray(typeArray.length())
        for (i in 0 until typeArray.length()) {
            colors[i] = typeArray.getColor(i, 0)
        }
        typeArray.recycle()


        return if (viewType == VIEW_TYPE_A) {
            AHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_question_card, parent, false))
        } else if (viewType == VIEW_TYPE_B){
            BHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_question_card_b, parent, false))
        } else if (viewType == VIEW_TYPE_C) {
            CHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_question_card_c, parent, false))
        } else {
            DHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_question_card_d, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = mItemList.get(position)
        val itemDetail = item.detail

        //val test = mItems[position]

        (holder as CardHolder).mCard.tag = "mCard$position"
        (holder as CardHolder).mResult_O.tag = "mResult_O$position"
        (holder as CardHolder).mResult_X.tag = "mResult_X$position"
        (holder as CardHolder).mRootLayout.tag = "mRootLayout$position"
        (holder as CardHolder).mUsername.text = item.username
        (holder as CardHolder).mViewCount.text = item.vote.total.toString()
        (holder as CardHolder).mCommentCount.text = item.review.toString()
        (holder as CardHolder).mBadgeNew.visibility = if(item.tag.new) { View.VISIBLE } else { View.GONE }
        (holder as CardHolder).mBadgeFirst.visibility = if(item.tag.first) { View.VISIBLE } else { View.GONE }
        (holder as CardHolder).mBadgeRandom.visibility = if(item.tag.random) { View.VISIBLE } else { View.GONE }
        (holder as CardHolder).mQmark.setTextColor(colors[position%5])

        if(item.id == -1 && item.order == 0) {
            (holder as CardHolder).mBottom.visibility = View.INVISIBLE

        } else {
            (holder as CardHolder).mBottom.visibility = View.VISIBLE
        }

        (holder as CardHolder).mReport.setOnClickListener(View.OnClickListener {
            val alert = AlertDialog.Builder(mContext)
            if (PreferenceManager.getInstance(mContext).userId != item.userId.toString()) {
                // AlertDialog 셋팅
                alert.setPositiveButton("신고") { dialog, id ->
                    reportCards(item.id, object : mCallback {
                        override fun onSuccess() {
                            Toast.makeText(mContext, "신고가 완료되었습니다!", Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                        }

                        override fun onFailure() {
                            Toast.makeText(mContext, mContext!!.resources.getString(R.string.failed), Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                        }
                    })
                }
            }

            // 다이얼로그 생성
            val alertDialog = alert.create()
            alertDialog.show()
        })

        val imageList = ArrayList<String>()


        if (holder is AHolder) {
            (holder as AHolder).mQuestion.text = itemDetail.TXT_Q
        } else if (holder is BHolder){
            (holder as BHolder).mQuestion.text = itemDetail.TXT_Q
            if(itemDetail.IMG_Q.isNotBlank()) {
                val url = ImageUtil.baseURL + itemDetail.IMG_Q
                Picasso.get().load(url).fit().centerCrop().into((holder as BHolder).mImageQ)
                imageList.add(url)
            } else {
                Picasso.get().load(R.drawable.default_pattern).fit().centerCrop().into((holder as BHolder).mImageQ)
            }
        } else if (holder is CHolder){
            (holder as CHolder).mQuestion.text = itemDetail.TXT_Q
            (holder as CHolder).mTextA.text = itemDetail.TXT_A
            (holder as CHolder).mTextB.text = itemDetail.TXT_B
            if(itemDetail.IMG_A.isNotBlank()) {
                val url = ImageUtil.baseURL + itemDetail.IMG_A
                Picasso.get().load(url).fit().centerCrop().into((holder as CHolder).mImageA)
                imageList.add(url)
            } else {
                Picasso.get().load(R.drawable.default_pattern).fit().centerCrop().into((holder as CHolder).mImageA)
            }
            if(itemDetail.IMG_B.isNotBlank()) {
                val url = ImageUtil.baseURL + itemDetail.IMG_B
                Picasso.get().load(url).fit().centerCrop().into((holder as CHolder).mImageB)
                imageList.add(url)
            } else {
                Picasso.get().load(R.drawable.default_pattern).fit().centerCrop().into((holder as CHolder).mImageB)
            }
        } else {
            (holder as DHolder).mQuestion.text = itemDetail.TXT_Q
            if(itemDetail.IMG_Q.isNotBlank()) {
                val url = ImageUtil.baseURL + itemDetail.IMG_Q
                Picasso.get().load(url).fit().centerCrop().into((holder as DHolder).mImageQ)
                imageList.add(url)
            } else {
                Picasso.get().load(R.drawable.default_pattern).fit().centerCrop().into((holder as DHolder).mImageQ)
            }
            (holder as DHolder).mTextA.text = itemDetail.TXT_A
            (holder as DHolder).mTextB.text = itemDetail.TXT_B
            if(itemDetail.IMG_A.isNotBlank()) {
                val url = ImageUtil.baseURL + itemDetail.IMG_A
                Picasso.get().load(url).fit().centerCrop().into((holder as DHolder).mImageA)
                imageList.add(url)
            } else {
                Picasso.get().load(R.drawable.default_pattern).fit().centerCrop().into((holder as DHolder).mImageA)
            }
            if(itemDetail.IMG_B.isNotBlank()) {
                val url = ImageUtil.baseURL + itemDetail.IMG_B
                Picasso.get().load(url).fit().centerCrop().into((holder as DHolder).mImageB)
                imageList.add(url)
            } else {
                Picasso.get().load(R.drawable.default_pattern).fit().centerCrop().into((holder as DHolder).mImageB)
            }
        }

        (holder as CardHolder).mCard.setOnClickListener {
            if(imageList.size > 0) {
                val intent = Intent(mContext, ImageViewActivity::class.java)
                intent.putStringArrayListExtra(ImageViewActivity.KEY_IMAGE_URL_LIST, imageList)
                mContext!!.startActivity(intent)
            }
        }

    }

    private fun reportCards(questionId: Int, callback: mCallback) {

        IsOnline.onlineCheck(mContext) {
            val userId = Integer.parseInt(PreferenceManager.getInstance(mContext).userId)
            val review = ReportCard(questionId, userId)
            service.reportCard(review).enqueue(object : Callback<JsonObject> {
                override fun onResponse(call: Call<JsonObject>, response: retrofit2.Response<JsonObject>) {

                    try {

                        if (response.isSuccessful) {
                            val result = response.body()!!.toString()
                            val data = JSONObject(result)


                            if (data.getString("type") == "SUCCESS") {
                                //                                    String userId = data.getJSONObject("data").getString("id");

                                //                                    Toast.makeText(applicationContext, "성공  ", Toast.LENGTH_SHORT).show();
                                callback.onSuccess()

                            } else {
                                //                                    Toast.makeText(applicationContext, "fail", Toast.LENGTH_SHORT).show();
                                callback.onFailure()

                            }
                        } else {
                            //end respone error
                            val data = JSONObject(response.errorBody()!!.string())
                            Toast.makeText(mContext, "error", Toast.LENGTH_SHORT).show()
                            callback.onFailure()

                        }

                    } catch (e: Exception) {
                        e.printStackTrace()
                        callback.onFailure()

                    }

                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                    //request fail(not found, time out, etc...)
                    Toast.makeText(mContext, "onFailure", Toast.LENGTH_SHORT).show()
                    callback.onFailure()

                }
            })
        }

    }

    override fun getItemViewType(position: Int): Int {

        val item = mItemList.get(position)

        return when(item.type) {
            "A" -> VIEW_TYPE_A
            "B" -> VIEW_TYPE_B
            "C" -> VIEW_TYPE_C
            "D" -> VIEW_TYPE_D
            else -> {
                0
            }
        }

    }

    override fun getItemCount(): Int {
        return mItemList.size
    }

    fun removeItemAtPosition(position: Int) {
        mItemList.removeAt(position)
    }

    fun setItemList(list: MutableList<QuestionCard>) {
        mItemList = list
    }



    override fun onButtonClick(v: View, answer: String, position: Int) {
        val rootLayout = v.findViewWithTag<RelativeLayout>("mRootLayout$position")
        val result_o = v.findViewWithTag<ImageView>("mResult_O$position")
        val result_x = v.findViewWithTag<ImageView>("mResult_X$position")
        val card = v.findViewWithTag<ConstraintLayout>("mCard$position")

        if(answer == "A") {
            val flipAnimation = FlipAnimation(card, result_o)
            rootLayout.startAnimation(flipAnimation)
            Handler().postDelayed({
                card.visibility = View.VISIBLE
                result_o.visibility = View.GONE
            }, 1000)
        } else {
            val flipAnimation = FlipAnimation(card, result_x)
            rootLayout.startAnimation(flipAnimation)
            Handler().postDelayed({
                card.visibility = View.VISIBLE
                result_x.visibility = View.GONE
            }, 1000)
        }


    }

    fun getListener(): FlipListener {
        return this
    }

    open inner class CardHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mCard: ConstraintLayout
        var mResult_O: ImageView
        var mResult_X: ImageView
        var mRootLayout: RelativeLayout
        var mUsername: TextView
        var mViewCount: TextView
        var mCommentCount: TextView
        var mBadgeNew: ImageView
        var mBadgeFirst: ImageView
        var mBadgeRandom: ImageView
        var mQmark: TextView
        var mReport: ImageView
        var mBottom: ConstraintLayout

        init {
            mCard = view.findViewById(R.id.cl_card)
            mResult_O = view.findViewById(R.id.O_img)
            mResult_X = view.findViewById(R.id.X_img)
            mRootLayout = view.findViewById(R.id.rl_root)
            mUsername = view.findViewById(R.id.tv_username)
            mViewCount = view.findViewById(R.id.tv_view_count)
            mCommentCount = view.findViewById(R.id.tv_comment_count)
            mBadgeNew = view.findViewById(R.id.iv_badge1)
            mBadgeFirst = view.findViewById(R.id.iv_badge2)
            mBadgeRandom = view.findViewById(R.id.iv_badge3)
            mQmark = view.findViewById(R.id.tv_Q)
            mReport = view.findViewById(R.id.iv_question_report)
            mBottom = view.findViewById(R.id.rl_sub_info)
        }
    }

    inner class AHolder(view: View) : CardHolder(view) {

        var mQuestion: TextView

        init {
            mQuestion = view.findViewById(R.id.tv_question)
        }
    }

    inner class BHolder(view: View) : CardHolder(view) {

        var mQuestion: TextView
        var mImageQ: ImageView

        init {
            mQuestion = view.findViewById(R.id.tv_question)
            mImageQ = view.findViewById(R.id.iv_question_img)
        }
    }

    inner class CHolder(view: View) : CardHolder(view) {

        var mQuestion: TextView
        var mTextA: TextView
        var mTextB: TextView
        var mImageA: ImageView
        var mImageB: ImageView

        init {
            mQuestion = view.findViewById(R.id.tv_question)
            mTextA = view.findViewById(R.id.tv_txt_a)
            mTextB = view.findViewById(R.id.tv_txt_b)
            mImageA = view.findViewById(R.id.iv_answer_a_img)
            mImageB = view.findViewById(R.id.iv_answer_b_img)
        }
    }

    inner class DHolder(view: View) : CardHolder(view) {

        var mQuestion: TextView
        var mTextA: TextView
        var mTextB: TextView
        var mImageQ: ImageView
        var mImageA: ImageView
        var mImageB: ImageView

        init {
            mQuestion = view.findViewById(R.id.tv_question)
            mTextA = view.findViewById(R.id.tv_txt_a)
            mTextB = view.findViewById(R.id.tv_txt_b)
            mImageQ = view.findViewById(R.id.iv_question_img)
            mImageA = view.findViewById(R.id.iv_answer_a_img)
            mImageB = view.findViewById(R.id.iv_answer_b_img)
        }
    }


    private interface mCallback {
        fun onSuccess()

        fun onFailure()
    }
}