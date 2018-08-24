package banchan.nexters.com.nanigoandroid;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;

public class WelcomeActivity extends AppCompatActivity {

    private String name = "";
    private Animation reavel, fade;

    private TextView tv_welcome_name;
    private ImageView iv_left_star;
    private ImageView iv_right_star;
    private View view_welcome_name_line;
    private TextView btn_welcome_go;
    private LottieAnimationView lottie_welcome;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);


         name = getIntent().getStringExtra("NAME");
        if (name == null) name = "NANIGO";

        initView();

        reavel = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in_reavel);
        fade = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);


        lottie_welcome.setImageAssetsFolder("images/");

        tv_welcome_name.setText(name + " 님");
        iv_left_star.startAnimation(reavel);
        iv_right_star.startAnimation(reavel);
        view_welcome_name_line.startAnimation(fade);
        tv_welcome_name.startAnimation(fade);

        btn_welcome_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        });


    }

    private void initView() {
        tv_welcome_name = (TextView) findViewById(R.id.tv_welcome_name);
        iv_left_star = (ImageView) findViewById(R.id.iv_left_star);
        iv_right_star = (ImageView) findViewById(R.id.iv_right_star);
        view_welcome_name_line = (View) findViewById(R.id.view_welcome_name_line);
        btn_welcome_go = (TextView) findViewById(R.id.btn_welcome_go);
        lottie_welcome = (LottieAnimationView) findViewById(R.id.lottie_welcome);
    }
}
