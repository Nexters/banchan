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
import banchan.nexters.com.nanigoandroid.item.TestItem
import banchan.nexters.com.nanigoandroid.listener.FlipListener
import banchan.nexters.com.nanigoandroid.utils.ImageUtil
import com.squareup.picasso.Picasso
import java.util.*


class SnappyAdapter(val mItemList: List<QuestionCard>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), FlipListener {


    val VIEW_TYPE_A = 0
    val VIEW_TYPE_B = 1
    val VIEW_TYPE_C = 3
    val VIEW_TYPE_D = 4

    private val mItems = ArrayList<TestItem>()

    init{
        mItems.add(TestItem("첫번째 질문", null, VIEW_TYPE_A))
        mItems.add(TestItem("두번째 질문", null, VIEW_TYPE_B))
        mItems.add(TestItem("세번째 질문", null, VIEW_TYPE_C))
        mItems.add(TestItem("전 남친한테 새벽에 연락왔는데 답장 해볼까?가나다라마바사아자차카타파하하하하하하하하하", null, VIEW_TYPE_D))
        mItems.add(TestItem("다섯번쨰 질문", null, VIEW_TYPE_A))
        mItems.add(TestItem("여섯번째 질문", null, VIEW_TYPE_B))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

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
        if (holder is AHolder) {
            (holder as AHolder).mQuestion.text = itemDetail.TXT_Q
            (holder as AHolder).mCard.tag = "mCard"
            (holder as AHolder).mResult_O.tag = "mResult_O"
            (holder as AHolder).mResult_X.tag = "mResult_X"
            (holder as AHolder).mRootLayout.tag = "mRootLayout"
        } else if (holder is BHolder){
            (holder as BHolder).mQuestion.text = itemDetail.TXT_Q
            if(itemDetail.IMG_Q!!.isNotEmpty()) {
                Picasso.get().load(ImageUtil.baseURL + itemDetail.IMG_Q).fit().centerCrop().into((holder as BHolder).mImageQ)
            }
            (holder as BHolder).mCard.tag = "mCard"
            (holder as BHolder).mResult_O.tag = "mResult_O"
            (holder as BHolder).mResult_X.tag = "mResult_X"
            (holder as BHolder).mRootLayout.tag = "mRootLayout"
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
            (holder as CHolder).mCard.tag = "mCard"
            (holder as CHolder).mResult_O.tag = "mResult_O"
            (holder as CHolder).mResult_X.tag = "mResult_X"
            (holder as CHolder).mRootLayout.tag = "mRootLayout"
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
            (holder as DHolder).mCard.tag = "mCard"
            (holder as DHolder).mResult_O.tag = "mResult_O"
            (holder as DHolder).mResult_X.tag = "mResult_X"
            (holder as DHolder).mRootLayout.tag = "mRootLayout"
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


    override fun onButtonClick(v: View, isO: Boolean) {
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

    inner class AHolder(view: View) : RecyclerView.ViewHolder(view) {

        var mCard: ConstraintLayout
        var mResult_O: ImageView
        var mResult_X: ImageView
        var mRootLayout: RelativeLayout
        var mQuestion: TextView

        init {
            mCard = view.findViewById(R.id.cl_card)
            mResult_O = view.findViewById(R.id.O_img)
            mResult_X = view.findViewById(R.id.X_img)
            mRootLayout = view.findViewById(R.id.rl_root)
            mQuestion = view.findViewById(R.id.tv_question)
        }
    }

    inner class BHolder(view: View) : RecyclerView.ViewHolder(view) {

        var mCard: ConstraintLayout
        var mResult_O: ImageView
        var mResult_X: ImageView
        var mRootLayout: RelativeLayout
        var mQuestion: TextView
        var mImageQ: ImageView

        init {
            mCard = itemView.findViewById(R.id.cl_card)
            mResult_O = itemView.findViewById(R.id.O_img)
            mResult_X = itemView.findViewById(R.id.X_img)
            mRootLayout = itemView.findViewById(R.id.rl_root)
            mQuestion = itemView.findViewById(R.id.tv_question)
            mImageQ = view.findViewById(R.id.iv_question_img)
        }
    }

    inner class CHolder(view: View) : RecyclerView.ViewHolder(view) {

        var mCard: ConstraintLayout
        var mResult_O: ImageView
        var mResult_X: ImageView
        var mRootLayout: RelativeLayout
        var mQuestion: TextView
        var mTextA: TextView
        var mTextB: TextView
        var mImageA: ImageView
        var mImageB: ImageView

        init {
            mCard = view.findViewById(R.id.cl_card)
            mResult_O = view.findViewById(R.id.O_img)
            mResult_X = view.findViewById(R.id.X_img)
            mRootLayout = view.findViewById(R.id.rl_root)
            mQuestion = view.findViewById(R.id.tv_question)
            mTextA = view.findViewById(R.id.tv_txt_a)
            mTextB = view.findViewById(R.id.tv_txt_b)
            mImageA = view.findViewById(R.id.iv_answer_a_img)
            mImageB = view.findViewById(R.id.iv_answer_b_img)
        }
    }

    inner class DHolder(view: View) : RecyclerView.ViewHolder(view) {

        var mCard: ConstraintLayout
        var mResult_O: ImageView
        var mResult_X: ImageView
        var mRootLayout: RelativeLayout
        var mQuestion: TextView
        var mTextA: TextView
        var mTextB: TextView
        var mImageQ: ImageView
        var mImageA: ImageView
        var mImageB: ImageView

        init {
            mCard = itemView.findViewById(R.id.cl_card)
            mResult_O = itemView.findViewById(R.id.O_img)
            mResult_X = itemView.findViewById(R.id.X_img)
            mRootLayout = itemView.findViewById(R.id.rl_root)
            mQuestion = itemView.findViewById(R.id.tv_question)
            mTextA = view.findViewById(R.id.tv_txt_a)
            mTextB = view.findViewById(R.id.tv_txt_b)
            mImageQ = view.findViewById(R.id.iv_question_img)
            mImageA = view.findViewById(R.id.iv_answer_a_img)
            mImageB = view.findViewById(R.id.iv_answer_b_img)
        }
    }
}