package banchan.nexters.com.nanigoandroid.viewholder

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import banchan.nexters.com.nanigoandroid.R
import com.markjmind.propose.Propose
import com.markjmind.propose.listener.JwAnimatorListener

class CardStackItem(itemView: View?) : RecyclerView.ViewHolder(itemView) {
    private var textView: TextView? = itemView?.findViewById(R.id.card_text)
    private var flip_lyt: FrameLayout? = itemView?.findViewById(R.id.flip_lyt)
    private var front_img: ImageView? = itemView?.findViewById(R.id.front_img)
    private var O_img: ImageView? = itemView?.findViewById(R.id.O_img)
    private var X_img: ImageView? = itemView?.findViewById(R.id.X_img)

    fun bind(i: Int) {
        textView!!.text = i.toString()
    }

    fun setFlipMotion(context: Context) {
        /** Layout init  */
        flip_lyt!!.cameraDistance = Propose.getCameraDistanceX(context) * 2

        /** create animator  */
        val frontRight = ObjectAnimator.ofFloat(flip_lyt, View.ROTATION_Y, 0F, 90F)
        frontRight.duration = 500
        val frontLeft = ObjectAnimator.ofFloat(flip_lyt, View.ROTATION_Y, 0F, -90F)
        frontLeft.duration = 500
        /** "duration" use to onClick  */
        val backRight = ObjectAnimator.ofFloat(flip_lyt, View.ROTATION_Y, -90F, 0F)
        backRight.duration = 500
        val backLeft = ObjectAnimator.ofFloat(flip_lyt, View.ROTATION_Y, 90F, 0F)
        backLeft.duration = 500
        /** "duration" use to onClick  */

        /** Propose create  */
        val propose = Propose(context)
        propose.motionRight.play(frontRight).next(backRight)
        propose.motionLeft.play(frontLeft).next(backLeft)
        /** set right move Animator  */
        propose.motionRight.motionDistance = 200 * Propose.getDensity(context)
        propose.motionLeft.motionDistance = 200 * Propose.getDensity(context)
        /** set Drag Distance  */
        flip_lyt!!.setOnTouchListener(propose)
        /** set touch listener  */


        /** set AnimatorListener for flip  */
        frontRight.addListener(object : JwAnimatorListener() {
            override fun onStart(arg0: Animator) {}
            override fun onEnd(arg0: Animator) {
                front_img!!.visibility = View.INVISIBLE
                X_img!!.visibility = View.VISIBLE
                flip_lyt!!.setBackgroundResource(R.drawable.shape_alpha1)
            }

            override fun onReverseStart(arg0: Animator) {
                front_img!!.visibility = View.VISIBLE
                X_img!!.visibility = View.INVISIBLE
                flip_lyt!!.setBackgroundResource(R.drawable.shape_alpha1)
            }

            override fun onReverseEnd(arg0: Animator) {}
        })

        frontLeft.addListener(object : JwAnimatorListener() {
            override fun onStart(arg0: Animator) {}
            override fun onEnd(arg0: Animator) {
                front_img!!.visibility = View.INVISIBLE
                O_img!!.visibility = View.VISIBLE
                flip_lyt!!.setBackgroundResource(R.drawable.shape_alpha1)
            }

            override fun onReverseStart(arg0: Animator) {
                front_img!!.visibility = View.VISIBLE
                O_img!!.visibility = View.INVISIBLE
                flip_lyt!!.setBackgroundResource(R.drawable.shape_alpha1)
            }

            override fun onReverseEnd(arg0: Animator) {}
        })
/*
        val selectX = ObjectAnimator.ofFloat(flip_lyt, View.ROTATION_Y, 0F, 90F)
        selectX.duration = 500
        btnX.setOnClickListener {
            selectX.start()
            //propose.motionRight.play(frontRight).next(backRight)
        }
        val selectO = ObjectAnimator.ofFloat(flip_lyt, View.ROTATION_Y, 0F, -90F)
        selectO.duration = 500
        btnO.setOnClickListener {
            selectO.start()
            //propose.motionLeft.play(frontLeft).next(backLeft)
        }*/



    }

}