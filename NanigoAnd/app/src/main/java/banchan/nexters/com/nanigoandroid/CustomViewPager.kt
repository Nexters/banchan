package banchan.nexters.com.nanigoandroid

import android.content.Context
import android.support.v4.view.MotionEventCompat
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.MotionEvent

class CustomViewPager : ViewPager{
    private var enable = true

    constructor(mContext: Context) : super(mContext) {
        enableTouches()
    }
    constructor(mContext: Context, attrs: AttributeSet) : super(mContext, attrs) {
        val a = context.obtainStyledAttributes(attrs, R.styleable.MyViewPager)
        try {
            enable = a.getBoolean(R.styleable.MyViewPager_swipeable, true)
        } finally {
            a.recycle()
        }
    }


    override fun onTouchEvent(ev: MotionEvent): Boolean {
        return if (enable) {
            super.onTouchEvent(ev)
        } else {
            MotionEventCompat.getActionMasked(ev) != MotionEvent.ACTION_MOVE && super.onTouchEvent(ev)
        }
    }

    override fun onInterceptTouchEvent(ev: MotionEvent): Boolean {
        if (enable) {
            return super.onInterceptTouchEvent(ev)
        } else {
            if (MotionEventCompat.getActionMasked(ev) == MotionEvent.ACTION_MOVE) {
                // ignore move action
            } else {
                if (super.onInterceptTouchEvent(ev)) {
                    return super.onTouchEvent(ev)
                }
            }
            return false
        }
    }

    fun enableTouches() {
        enable = true
    }

    fun disableTouches() {
        enable = false
    }
}