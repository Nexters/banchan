package banchan.nexters.com.nanigoandroid.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.VideoView;

import com.airbnb.lottie.LottieAnimationView;

import banchan.nexters.com.nanigoandroid.R;

public class OnBoard01Fragment extends Fragment {
    private LottieAnimationView lottie_onboard_01;
    private Boolean isStarted = false;
    private Boolean isVisible = false;
    private Boolean isPlayed = false;

//    VideoView vv_onboard_01;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v=(View)inflater.inflate(R.layout.layout_onboard_pager_01,container,false);
         lottie_onboard_01 = (LottieAnimationView)v.findViewById(R.id.lottie_onboard_01);
        lottie_onboard_01.setImageAssetsFolder("images/");
//        vv_onboard_01 = (VideoView)v.findViewById(R.id.vv_onboard_01);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        isStarted = true;
        if (isVisible && isStarted){
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

    private void setAnimation(){
        if(!isPlayed){
            lottie_onboard_01.playAnimation();
//            String uriPath = "android.resource://" + getActivity().getPackageName() + "/" + R.raw.nanigo_onboarding_01;
//
//            vv_onboard_01.setVideoURI(Uri.parse(uriPath));
//            vv_onboard_01.requestFocus();
//                    vv_onboard_01.start();
            isPlayed=true;
        }
    }
}