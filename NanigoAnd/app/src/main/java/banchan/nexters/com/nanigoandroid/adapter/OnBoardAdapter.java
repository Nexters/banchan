package banchan.nexters.com.nanigoandroid.adapter;

import android.content.Context;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

public class OnBoardAdapter extends PagerAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    String[] lotties;



    public OnBoardAdapter(Context context, String[] lotties) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.lotties = lotties;
    }

    @Override
    public int getCount() {
        return lotties.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        LottieAnimationView imageView = new LottieAnimationView(mContext);
        imageView.setImageAssetsFolder("images/");
        imageView.setAnimation(this.lotties[position]);
        imageView.setMaxWidth(5);
        imageView.playAnimation();
//        imageView.loop(true);

        ((ViewPager) container).addView(imageView, 0);
        return imageView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((ImageView) object);
    }
}