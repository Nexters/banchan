package banchan.nexters.com.nanigoandroid.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.airbnb.lottie.LottieAnimationView;

import java.util.ArrayList;
import java.util.List;

public class OnBoardAdapter extends PagerAdapter {
    Context mContext;
    LayoutInflater mLayoutInflater;
    int[] mImages;

    public OnBoardAdapter(Context context, int[] mImages) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.mImages = mImages;
    }

    @Override
    public int getCount() {
        return mImages.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((ImageView) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        LottieAnimationView imageView = new LottieAnimationView(mContext);
        imageView.setImageAssetsFolder("images/");
        imageView.setAnimation(this.mImages[position]);
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