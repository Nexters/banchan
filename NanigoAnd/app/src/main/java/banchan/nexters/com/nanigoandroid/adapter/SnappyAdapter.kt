package banchan.nexters.com.nanigoandroid.adapter

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
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

    lateinit var mCard: FrameLayout
    lateinit var mImageView: ImageView
    lateinit var mRootLayout: ConstraintLayout

    private val mItems = ArrayList<TestItem>()

    init{
        mItems.add(TestItem("첫번째 질문", null, VIEW_TYPE_A))
        mItems.add(TestItem("두번째 질문", null, VIEW_TYPE_B))
        mItems.add(TestItem("세번째 질문", null, VIEW_TYPE_C))
        mItems.add(TestItem("네번째 질문", null, VIEW_TYPE_D))
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
        //(holder as SnappyViewHolder).setDate(dates[position])
        /*mCard = (holder as SnappyViewHolder).getCardView()
        mCard.tag = ("mCard")
        mImageView = holder.getImageView()
        mImageView.tag = ("mImageView")
        mRootLayout = holder.getRootLayout()
        mRootLayout.tag = ("mRootLayout")*/

        val test = mItems[position]
        if (holder is AHolder) {
            (holder as AHolder).mQuestion.text = test.question
        } else if (holder is BHolder){
            (holder as BHolder).mQuestion.text = test.question
        } else if (holder is CHolder){
            (holder as CHolder).mQuestion.text = test.question
        } else {
            (holder as DHolder).mQuestion.text = test.question
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

    fun getCardView() : FrameLayout {
        return mCard
    }

    fun getImageView() : ImageView {
        return mImageView
    }

    fun getRootLayout() : ConstraintLayout {
        return mRootLayout
    }

    override fun onButtonClick() {
        val flipAnimation = FlipAnimation(mCard, mImageView)

        if (mCard.visibility == View.GONE) {
            flipAnimation.reverse()
        }
        mRootLayout.startAnimation(flipAnimation)
        notifyDataSetChanged()
    }

    fun getListener(): FlipListener {
        return this
    }

    inner class AHolder(view: View) : RecyclerView.ViewHolder(view) {

        var mCard: FrameLayout
        var mImageView: ImageView
        var mRootLayout: ConstraintLayout
        var mQuestion: TextView

        init {
            mCard = itemView.findViewById(R.id.cv_question_card)
            mImageView = itemView.findViewById(R.id.O_img)
            mRootLayout = itemView.findViewById(R.id.cl_card)
            mQuestion = itemView.findViewById(R.id.tv_question)
        }
    }

    inner class BHolder(view: View) : RecyclerView.ViewHolder(view) {

        var mCard: FrameLayout
        var mImageView: ImageView
        var mRootLayout: ConstraintLayout
        var mQuestion: TextView

        init {
            mCard = itemView.findViewById(R.id.cv_question_card)
            mImageView = itemView.findViewById(R.id.O_img)
            mRootLayout = itemView.findViewById(R.id.cl_card)
            mQuestion = itemView.findViewById(R.id.tv_question)
        }
    }

    inner class CHolder(view: View) : RecyclerView.ViewHolder(view) {

        var mCard: FrameLayout
        var mImageView: ImageView
        var mRootLayout: ConstraintLayout
        var mQuestion: TextView

        init {
            mCard = itemView.findViewById(R.id.cv_question_card)
            mImageView = itemView.findViewById(R.id.O_img)
            mRootLayout = itemView.findViewById(R.id.cl_card)
            mQuestion = itemView.findViewById(R.id.tv_question)
        }
    }

    inner class DHolder(view: View) : RecyclerView.ViewHolder(view) {

        var mCard: FrameLayout
        var mImageView: ImageView
        var mRootLayout: ConstraintLayout
        var mQuestion: TextView

        init {
            mCard = itemView.findViewById(R.id.cv_question_card)
            mImageView = itemView.findViewById(R.id.O_img)
            mRootLayout = itemView.findViewById(R.id.cl_card)
            mQuestion = itemView.findViewById(R.id.tv_question)
        }
    }
}