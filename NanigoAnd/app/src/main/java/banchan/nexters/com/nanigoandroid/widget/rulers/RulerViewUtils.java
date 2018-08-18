package banchan.nexters.com.nanigoandroid.widget.rulers;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Created by Ellen on 2018-07-28.
 */
final class RulerViewUtils {

    /**
     * Convert SP to pixel.
     *
     * @param context Context.
     * @param spValue Value in sp to convert.
     *
     * @return Value in pixels.
     */
    static int sp2px(@NonNull final Context context,
                     final float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}