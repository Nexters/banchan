package banchan.nexters.com.nanigoandroid.viewholder

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.RelativeLayout
import banchan.nexters.com.nanigoandroid.R
import java.util.*

class SnappyViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_question_card, parent, false)) {
    lateinit var mCard: FrameLayout
    lateinit var mImageView: ImageView
    lateinit var mRootLayout: RelativeLayout
    init {
        mCard = itemView.findViewById(R.id.cv_question_card)
        mImageView = itemView.findViewById(R.id.O_img)
        mRootLayout = itemView.findViewById(R.id.rl_root)
    }

    fun setDate(date: Date) {
        /*(itemView.findViewById<TextView>(R.id.date_text)).text = SimpleDateFormat("EEE d").format(date)
        (itemView.findViewById<TextView>(R.id.month_text)).text = SimpleDateFormat("MMM").format(date)*/
    }

    fun getCardView() : FrameLayout {
        return itemView.findViewById(R.id.cv_question_card)
    }

    fun getImageView() : ImageView {
        return itemView.findViewById(R.id.O_img)
    }

    fun getRootLayout() : RelativeLayout {
        return itemView.findViewById(R.id.rl_root)
    }


}