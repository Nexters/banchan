package banchan.nexters.com.nanigoandroid.adapter

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import banchan.nexters.com.nanigoandroid.R
import banchan.nexters.com.nanigoandroid.animation.FlipAnimation
import banchan.nexters.com.nanigoandroid.data.QuestionCard
import banchan.nexters.com.nanigoandroid.listener.FlipListener
import banchan.nexters.com.nanigoandroid.utils.ImageUtil
import com.squareup.picasso.Picasso


class SnappyAdapter(val mItemList: List<QuestionCard>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), FlipListener {

    val VIEW_TYPE_A = 0
    val VIEW_TYPE_B = 1
    val VIEW_TYPE_C = 3
    val VIEW_TYPE_D = 4

    lateinit var colors: IntArray

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
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

        (holder as CardHolder).mCard.tag = "mCard"
        (holder as CardHolder).mResult_O.tag = "mResult_O"
        (holder as CardHolder).mResult_X.tag = "mResult_X"
        (holder as CardHolder).mRootLayout.tag = "mRootLayout"
        (holder as CardHolder).mUsername.text = item.username
        (holder as CardHolder).mViewCount.text = item.vote.total.toString()
        (holder as CardHolder).mCommentCount.text = item.review.toString()
        (holder as CardHolder).mBadgeNew.visibility = if(item.tag.new) { View.VISIBLE } else { View.GONE }
        (holder as CardHolder).mBadgeFirst.visibility = if(item.tag.first) { View.VISIBLE } else { View.GONE }
        (holder as CardHolder).mBadgeRandom.visibility = if(item.tag.random) { View.VISIBLE } else { View.GONE }
        (holder as CardHolder).mQmark.setTextColor(colors[position%5])



        if (holder is AHolder) {
            (holder as AHolder).mQuestion.text = itemDetail.TXT_Q
        } else if (holder is BHolder){
            (holder as BHolder).mQuestion.text = itemDetail.TXT_Q
            if(itemDetail.IMG_Q!!.isNotEmpty()) {
                Picasso.get().load(ImageUtil.baseURL + itemDetail.IMG_Q).fit().centerCrop().into((holder as BHolder).mImageQ)
            }
        } else if (holder is CHolder){
            (holder as CHolder).mQuestion.text = itemDetail.TXT_Q
            (holder as CHolder).mTextA.text = itemDetail.TXT_A
            (holder as CHolder).mTextB.text = itemDetail.TXT_B
            if(itemDetail.IMG_A!!.isNotEmpty()) {
                Picasso.get().load(ImageUtil.baseURL + itemDetail.IMG_A).fit().centerCrop().into((holder as CHolder).mImageA)
            }
            if(itemDetail.IMG_B!!.isNotEmpty()) {
                Picasso.get().load(ImageUtil.baseURL + itemDetail.IMG_B).fit().centerCrop().into((holder as CHolder).mImageB)
            }
        } else {
            (holder as DHolder).mQuestion.text = itemDetail.TXT_Q
            if(itemDetail.IMG_Q!!.isNotEmpty()) {
                Picasso.get().load(ImageUtil.baseURL + itemDetail.IMG_Q).fit().centerCrop().into((holder as DHolder).mImageQ)
            }
            (holder as DHolder).mTextA.text = itemDetail.TXT_A
            (holder as DHolder).mTextB.text = itemDetail.TXT_B
            if(itemDetail.IMG_A!!.isNotEmpty()) {
                Picasso.get().load(ImageUtil.baseURL + itemDetail.IMG_A).fit().centerCrop().into((holder as DHolder).mImageA)
            }
            if(itemDetail.IMG_B!!.isNotEmpty()) {
                Picasso.get().load(ImageUtil.baseURL + itemDetail.IMG_B).fit().centerCrop().into((holder as DHolder).mImageB)
            }
        }

        /*//현재 목록 상 마지막 셀을 요청하면
        if (position == mItemList.size - 1 && !isMore) {
            cur_page++
            isMore = true
            //더보기 더 가져오기
            showLoading(true)
            getImageQuery()
        }*/

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


    override fun onButtonClick(v: View, isO: Boolean, position: Int) {
        val rootLayout = v.findViewWithTag<RelativeLayout>("mRootLayout")
        val result_o = v.findViewWithTag<ImageView>("mResult_O")
        val result_x = v.findViewWithTag<ImageView>("mResult_X")
        val card = v.findViewWithTag<ConstraintLayout>("mCard")

        if(isO) {
            val flipAnimation = FlipAnimation(card, result_o)
            rootLayout.startAnimation(flipAnimation)
        } else {
            val flipAnimation = FlipAnimation(card, result_x)
            rootLayout.startAnimation(flipAnimation)
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
}