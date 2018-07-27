package banchan.nexters.com.nanigoandroid.viewholder

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import banchan.nexters.com.nanigoandroid.R

class CardStackItem(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    private var textView: TextView? = itemView?.findViewById(R.id.card_text)

    fun bind(i: Int) {
        textView!!.text = i.toString()
    }
}