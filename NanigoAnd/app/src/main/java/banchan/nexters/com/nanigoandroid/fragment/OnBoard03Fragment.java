package banchan.nexters.com.nanigoandroid.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;

import banchan.nexters.com.nanigoandroid.R;

public class OnBoard03Fragment extends Fragment {
    private LottieAnimationView lottie_onboard_03;
    private  Boolean isStarted =false;
    private Boolean isVisible = false;
    private Boolean isPlayed = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = (View) inflater.inflate(R.layout.layout_onboard_pager_03, container, false);
        lottie_onboard_03 = (LottieAnimationView) v.findViewById(R.id.lottie_onboard_03);
        lottie_onboard_03.setImageAssetsFolder("images/");
        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
        isStarted = true;
        if (isVisible && isStarted) {
            setAnimation();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        isStarted = false;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if (isStarted && isVisible) {
            setAnimation();
        }
    }

    private void setAnimation() {
        if(!isPlayed){
            lottie_onboard_03.playAnimation();
            isPlayed=true;
        }

    }
}