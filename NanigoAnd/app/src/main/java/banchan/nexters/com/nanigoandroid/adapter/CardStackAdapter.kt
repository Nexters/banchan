package banchan.nexters.com.nanigoandroid.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import banchan.nexters.com.nanigoandroid.R
import banchan.nexters.com.nanigoandroid.viewholder.CardStackItem
import java.util.*

class CardStackAdapter: RecyclerView.Adapter<CardStackItem>() {
    private val items = ArrayList(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardStackItem {
        return CardStackItem(
                LayoutInflater.from(parent.context).inflate(R.layout.dummy_card_item, parent, false))
    }

    override fun onBindViewHolder(holder: CardStackItem, position: Int) {
        holder.bind(items[position].toInt())
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItems(): List<Int> {
        return items
    }

    fun removeTopItem() {
        items.removeAt(0)
        notifyDataSetChanged()
    }
}