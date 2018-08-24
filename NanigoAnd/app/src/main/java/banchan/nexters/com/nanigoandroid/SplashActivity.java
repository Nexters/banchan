package banchan.nexters.com.nanigoandroid;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.util.ArrayList;

import banchan.nexters.com.nanigoandroid.utils.PreferenceManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
//        startActivity(new Intent(this, OnBoardActivity.class));

        /* SPLASH_DISPLAY_LENGTH 뒤에 메뉴 액티비티를 실행시키고 종료한다.*/
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* 메뉴액티비티를 실행하고 로딩화면을 죽인다.*/

                PermissionListener permissionlistener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {


                        if (PreferenceManager.getInstance(getApplicationContext()).getOnboard()) {
                            startActivity(new Intent(SplashActivity.this, OnBoardActivity.class));
                            finish();
                        } else {
                            if (PreferenceManager.getInstance(getApplicationContext()).getUserId().equals("")) {
                                startActivity(new Intent(SplashActivity.this, JoinActivity.class));
                                finish();
                                overridePendingTransition(R.anim.fade_out,R.anim.fade_in);
                            } else {
                                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                finish();
                            }
                        }
                    }//success

                    @Override
                    public void onPermissionDenied(ArrayList<String> deniedPermissions) {
                        finish();
                    }

                };
                TedPermission.with(SplashActivity.this)
                        .setPermissionListener(permissionlistener)
                        .setRationaleMessage("서비스 사용을 위해서는 다음 권한이 필요합니다")
                        .setDeniedMessage("권한 거부시 사용이 제한됩니다.\n [설정] > [권한] 에서 권한을 허용해주세요")
                        .setPermissions(Manifest.permission.READ_PHONE_STATE, Manifest.permission.CALL_PHONE)
                        .check();

            }
        }, 1500);


    }
}
