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
import banchan.nexters.com.nanigoandroid.item.TestItem
import banchan.nexters.com.nanigoandroid.listener.FlipListener
import java.util.*


class SnappyAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), FlipListener {


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

        val test = mItems[position]
        if (holder is AHolder) {
            (holder as AHolder).mQuestion.text = test.question
            (holder as AHolder).mCard.tag = "mCard"
            (holder as AHolder).mImageView.tag = "mImageView"
            (holder as AHolder).mRootLayout.tag = "mRootLayout"
        } else if (holder is BHolder){
            (holder as BHolder).mQuestion.text = test.question
            (holder as BHolder).mCard.tag = "mCard"
            (holder as BHolder).mImageView.tag = "mImageView"
            (holder as BHolder).mRootLayout.tag = "mRootLayout"
        } else if (holder is CHolder){
            (holder as CHolder).mQuestion.text = test.question
            (holder as CHolder).mCard.tag = "mCard"
            (holder as CHolder).mImageView.tag = "mImageView"
            (holder as CHolder).mRootLayout.tag = "mRootLayout"
        } else {
            (holder as DHolder).mQuestion.text = test.question
            (holder as DHolder).mCard.tag = "mCard"
            (holder as DHolder).mImageView.tag = "mImageView"
            (holder as DHolder).mRootLayout.tag = "mRootLayout"
        }

    }

    override fun getItemViewType(position: Int): Int {
        return if (mItems.get(position).item_viewtype == VIEW_TYPE_A) {
            VIEW_TYPE_A
        } else if (mItems.get(position).item_viewtype == VIEW_TYPE_B){
            VIEW_TYPE_B
        } else if (mItems.get(position).item_viewtype == VIEW_TYPE_C){
            VIEW_TYPE_C
        } else {
            VIEW_TYPE_D
        }
    }

    override fun getItemCount(): Int {
        return mItems.size
    }


    override fun onButtonClick(v: View) {
        val rootLayout = v.findViewWithTag<RelativeLayout>("mRootLayout")
        val result = v.findViewWithTag<ImageView>("mImageView")
        val card = v.findViewWithTag<ConstraintLayout>("mCard")

        val flipAnimation = FlipAnimation(card, result)

        /*if (card.visibility == View.GONE) {
            flipAnimation.reverse()
        }*/
        rootLayout.startAnimation(flipAnimation)
    }

    fun getListener(): FlipListener {
        return this
    }

    inner class AHolder(view: View) : RecyclerView.ViewHolder(view) {

        var mCard: ConstraintLayout
        var mImageView: ImageView
        var mRootLayout: RelativeLayout
        var mQuestion: TextView

        init {
            mCard = itemView.findViewById(R.id.cl_card)
            mImageView = itemView.findViewById(R.id.O_img)
            mRootLayout = itemView.findViewById(R.id.rl_root)
            mQuestion = itemView.findViewById(R.id.tv_question)
        }
    }

    inner class BHolder(view: View) : RecyclerView.ViewHolder(view) {

        var mCard: ConstraintLayout
        var mImageView: ImageView
        var mRootLayout: RelativeLayout
        var mQuestion: TextView

        init {
            mCard = itemView.findViewById(R.id.cl_card)
            mImageView = itemView.findViewById(R.id.O_img)
            mRootLayout = itemView.findViewById(R.id.rl_root)
            mQuestion = itemView.findViewById(R.id.tv_question)
        }
    }

    inner class CHolder(view: View) : RecyclerView.ViewHolder(view) {

        var mCard: ConstraintLayout
        var mImageView: ImageView
        var mRootLayout: RelativeLayout
        var mQuestion: TextView

        init {
            mCard = itemView.findViewById(R.id.cl_card)
            mImageView = itemView.findViewById(R.id.O_img)
            mRootLayout = itemView.findViewById(R.id.rl_root)
            mQuestion = itemView.findViewById(R.id.tv_question)
        }
    }

    inner class DHolder(view: View) : RecyclerView.ViewHolder(view) {

        var mCard: ConstraintLayout
        var mImageView: ImageView
        var mRootLayout: RelativeLayout
        var mQuestion: TextView

        init {
            mCard = itemView.findViewById(R.id.cl_card)
            mImageView = itemView.findViewById(R.id.O_img)
            mRootLayout = itemView.findViewById(R.id.rl_root)
            mQuestion = itemView.findViewById(R.id.tv_question)
        }
    }
}