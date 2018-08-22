package banchan.nexters.com.nanigoandroid.listener

import android.view.View
import java.text.FieldPosition

/**
 * Created by XNOTE on 2018-08-18.
 */
interface FlipListener {
    fun onButtonClick(v: View, isO: Boolean, position: Int)
}