package banchan.nexters.com.nanigoandroid.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import banchan.nexters.com.nanigoandroid.R;

/**
 * Created by YounDitt on 2015-10-17.
 */
public class IsOnline {
    public  static void onlineCheck(Context context, onlineCallback callback) {
        ConnectivityManager manager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if ((wifi != null && wifi.isConnected()) || (mobile != null && mobile.isConnected())) {
            callback.onSuccess();
        } else {
            Toast.makeText(context, context.getResources().getString(R.string.err_internet), Toast.LENGTH_SHORT);
        }
    }

    public  interface onlineCallback {
        void onSuccess();
    }
}
