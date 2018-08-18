package banchan.nexters.com.nanigoandroid.adapter

import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import banchan.nexters.com.nanigoandroid.animation.FlipAnimation
import banchan.nexters.com.nanigoandroid.listener.FlipListener
import banchan.nexters.com.nanigoandroid.viewholder.SnappyViewHolder
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class SnappyAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>(), FlipListener {

    lateinit var mCard: FrameLayout
    lateinit var mImageView: ImageView
    lateinit var mRootLayout: ConstraintLayout



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SnappyViewHolder(parent!!)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //(holder as SnappyViewHolder).setDate(dates[position])
        mCard = (holder as SnappyViewHolder).getCardView()
        mImageView = (holder as SnappyViewHolder).getImageView()
        mRootLayout = (holder as SnappyViewHolder).getRootLayout()

    }

    private var startDate: Date? = null
    private var endDate: Date? = null

    private var dates: MutableList<Date>

    init {
        val formatter = SimpleDateFormat("yyyy-MM-dd")

        try {
            startDate = formatter.parse("2017-01-01")
        } catch (ex: ParseException) {
        }

        try {
            endDate = formatter.parse("2018-12-31")
        } catch (ex: ParseException) {
        }

        val start = Calendar.getInstance()
        start.time = startDate

        val end = Calendar.getInstance()
        end.time = endDate

        dates = ArrayList()
        var date = start.time
        while (start.before(end)) {
            dates.add(date)
            start.add(Calendar.DATE, 1)
            date = start.time
        }
    }

    override fun getItemCount(): Int {
        return dates.size
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


}