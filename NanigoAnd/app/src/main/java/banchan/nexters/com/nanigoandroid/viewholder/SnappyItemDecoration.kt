package banchan.nexters.com.nanigoandroid.viewholder

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View
import com.dant.centersnapreyclerview.SnappingRecyclerView


class SnappyItemDecoration(@param:SnappingRecyclerView.OrientationMode @field:SnappingRecyclerView.OrientationMode private val orientation: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.getChildAdapterPosition(view) == 0) {
            if (orientation == SnappingRecyclerView.VERTICAL) {
                outRect.top = parent.height / 2 - view.getHeight() / 2
            } else {
                outRect.left = parent.width / 2 - view.getWidth() / 2
            }
        } else if (parent.getChildAdapterPosition(view) == parent.adapter.itemCount - 1) {
            if (orientation == SnappingRecyclerView.VERTICAL) {
                outRect.bottom = parent.height / 2 - view.getHeight() / 2
            } else {
                outRect.right = parent.width / 2 - view.getWidth() / 2
            }
        }
    }
}