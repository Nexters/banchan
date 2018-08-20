package banchan.nexters.com.nanigoandroid.widget;

import android.app.Activity;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatDialog;

import com.airbnb.lottie.LottieAnimationView;

import banchan.nexters.com.nanigoandroid.R;


/**
 * HOW TO USE
 * new Loading().progressOn(Activity.this)
 */
public class Loading {

    /**
     * Progress Dialog
     */
    AppCompatDialog progressDialog=null;


    public  void progressON(Activity activity) {
        if (activity == null) {
            return;
        }
        if (progressDialog != null && progressDialog.isShowing()) {
//            progressSET(message);
        } else {
            progressDialog = new AppCompatDialog(activity);
            progressDialog.setCancelable(false);
            progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            progressDialog.setContentView(R.layout.loading);
            progressDialog.show();
        }

        final LottieAnimationView lottieAnimationView = (LottieAnimationView) progressDialog.findViewById(R.id.progress_lottie);
        lottieAnimationView.playAnimation();


//        TextView tv_progress_message = (TextView) progressDialog.findViewById(R.id.progress_message);
//        if (!TextUtils.isEmpty(message)) {
//            tv_progress_message.setText(message);
//        }
    }

    public  void progressOFF() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog=null;
        }
    }
}
