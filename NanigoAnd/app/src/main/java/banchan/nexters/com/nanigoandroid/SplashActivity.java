package banchan.nexters.com.nanigoandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import banchan.nexters.com.nanigoandroid.utils.PreferenceManager;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startActivity(new Intent(this,MainActivity.class));

//        if(PreferenceManager.getInstance(getApplicationContext()).getOnboard()){
//            startActivity(new Intent(this,OnBoardActivity.class));
//            finish();
//        }else{
//            if(PreferenceManager.getInstance(getApplicationContext()).getUserId().equals("")){
//                startActivity(new Intent(this,JoinActivity.class));
//                finish();
//            } else {
//                startActivity(new Intent(this,MainActivity.class));
//                finish();
//            }
//        }
    }
}
