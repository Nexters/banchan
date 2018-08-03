package banchan.nexters.com.nanigoandroid.adapter

import android.animation.Animator
import android.animation.ObjectAnimator
import android.content.Context
import android.support.constraint.ConstraintLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.FrameLayout
import android.widget.ImageView
import banchan.nexters.com.nanigoandroid.R
import banchan.nexters.com.nanigoandroid.viewholder.TouristSpot
import com.markjmind.propose.Propose
import com.markjmind.propose.listener.JwAnimatorListener

class TouristSpotCardAdapter(mContext: Context): ArrayAdapter<TouristSpot>(mContext, 0) {
    override fun getView(position: Int, contentView: View?, parent: ViewGroup): View {
        var contentView = contentView
        val holder: ViewHolder

        if (contentView == null) {
            val inflater = LayoutInflater.from(context)
            contentView = inflater.inflate(R.layout.item_question_card, parent, false)
            holder = ViewHolder(contentView!!)
            contentView.tag = holder
        } else {
            holder = contentView.tag as ViewHolder
        }

        val spot = getItem(position)

        /** Layout init  */
        holder.flip_lyt!!.cameraDistance = Propose.getCameraDistanceX(context) * 2

        /** create animator  */
        val frontRight = ObjectAnimator.ofFloat(holder.flip_lyt, View.ROTATION_Y, 0F, 90F)
        frontRight.duration = 500
        val frontLeft = ObjectAnimator.ofFloat(holder.flip_lyt, View.ROTATION_Y, 0F, -90F)
        frontLeft.duration = 500
        /** "duration" use to onClick  */
        val backRight = ObjectAnimator.ofFloat(holder.flip_lyt, View.ROTATION_Y, -90F, 0F)
        backRight.duration = 500
        val backLeft = ObjectAnimator.ofFloat(holder.flip_lyt, View.ROTATION_Y, 90F, 0F)
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
        holder.flip_lyt!!.setOnTouchListener(propose)
        /** set touch listener  */


        /** set AnimatorListener for flip  */
        frontRight.addListener(object : JwAnimatorListener() {
            override fun onStart(arg0: Animator) {}
            override fun onEnd(arg0: Animator) {
                holder.cl_front!!.visibility = View.INVISIBLE
                holder.X_img!!.visibility = View.VISIBLE
                holder.flip_lyt!!.setBackgroundResource(R.drawable.shape_alpha1)
            }

            override fun onReverseStart(arg0: Animator) {
                holder.cl_front!!.visibility = View.VISIBLE
                holder.X_img!!.visibility = View.INVISIBLE
                holder.flip_lyt!!.setBackgroundResource(R.drawable.shape_alpha1)
            }

            override fun onReverseEnd(arg0: Animator) {}
        })

        frontLeft.addListener(object : JwAnimatorListener() {
            override fun onStart(arg0: Animator) {}
            override fun onEnd(arg0: Animator) {
                holder.cl_front!!.visibility = View.INVISIBLE
                holder.O_img!!.visibility = View.VISIBLE
                holder.flip_lyt!!.setBackgroundResource(R.drawable.shape_alpha1)
            }

            override fun onReverseStart(arg0: Animator) {
                holder.cl_front!!.visibility = View.VISIBLE
                holder.O_img!!.visibility = View.INVISIBLE
                holder.flip_lyt!!.setBackgroundResource(R.drawable.shape_alpha1)
            }

            override fun onReverseEnd(arg0: Animator) {}
        })



        return contentView
    }

    private class ViewHolder(view: View) {
        var flip_lyt: FrameLayout
        var cl_front: ConstraintLayout
        var O_img: ImageView
        var X_img: ImageView

        init {
            flip_lyt = view.findViewById(R.id.flip_lyt)
            cl_front = view.findViewById(R.id.cl_front)
            O_img = view.findViewById(R.id.O_img)
            X_img = view.findViewById(R.id.X_img)
        }
    }
}