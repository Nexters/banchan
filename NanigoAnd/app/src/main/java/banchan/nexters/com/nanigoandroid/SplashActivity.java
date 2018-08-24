package banchan.nexters.com.nanigoandroid;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

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
            }
        }, 1500);


    }
}
