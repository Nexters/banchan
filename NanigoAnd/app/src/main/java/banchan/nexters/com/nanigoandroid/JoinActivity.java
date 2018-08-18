package banchan.nexters.com.nanigoandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import banchan.nexters.com.nanigoandroid.adapter.ReviewsAdapter;
import banchan.nexters.com.nanigoandroid.data.NameData;
import banchan.nexters.com.nanigoandroid.data.NameList;
import banchan.nexters.com.nanigoandroid.data.ReviewsData;
import banchan.nexters.com.nanigoandroid.http.APIService;
import banchan.nexters.com.nanigoandroid.http.APIUtil;
import banchan.nexters.com.nanigoandroid.utils.IsOnline;
import banchan.nexters.com.nanigoandroid.utils.SimpleDividerItemDecoration;
import banchan.nexters.com.nanigoandroid.widget.rulers.RulerValuePicker;
import banchan.nexters.com.nanigoandroid.widget.rulers.RulerValuePickerListener;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Ellen on 2018-08-18.
 */

public class JoinActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_join_name_pre;
    private TextView tv_join_name_post;
    private ImageView iv_join_name_refresh;

    private TextView tv_join_male;
    private TextView tv_join_female;
    private TextView tv_join_none;


    private RulerValuePicker picker_join_age;
    private TextView tv_join_age;
    private TextView btn_join_ok;


    private APIService service;


    private String prefix="";
    private String postfix="";
    private String gender="";
    private String age="";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);
/**
 * ToolBar START
 */
        TextView toolbar_txt = (TextView) findViewById(R.id.toolbar_txt);
        toolbar_txt.setText("회원가입");
        ImageView toolbar_exit = (ImageView) findViewById(R.id.toolbar_exit);
/**
 * ToolBar END
 */

        service = APIUtil.getService();

        initView();
        userName();
        tv_join_name_pre.setSelected(true);
        tv_join_name_post.setSelected(true);
        tv_join_male.setOnClickListener(this);
        tv_join_female.setOnClickListener(this);
        tv_join_none.setOnClickListener(this);
        iv_join_name_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userName();
            }
        });

        btn_join_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(btn_join_ok.isEnabled()){
//                    startActivity(new Intent(JoinActivity.this,));
                }else{
                    Toast.makeText(getApplicationContext(),"선택해주세요!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        picker_join_age.setIndicatorHeight(0.3f, 0.3f);
        picker_join_age.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int selectedValue) {
                tv_join_age.setText(selectedValue + "살");
                age = selectedValue + "";
                btnEnable();
//                Toast.makeText(getApplicationContext(),selectedValue+"",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onIntermediateValueChange(int selectedValue) {

            }
        });


    }

    private void initView() {
        tv_join_name_pre = (TextView) findViewById(R.id.tv_join_name_pre);
        tv_join_name_post = (TextView) findViewById(R.id.tv_join_name_post);
        iv_join_name_refresh = (ImageView) findViewById(R.id.iv_join_name_refresh);

        tv_join_male = (TextView) findViewById(R.id.tv_join_male);
        tv_join_female = (TextView) findViewById(R.id.tv_join_female);
        tv_join_none = (TextView) findViewById(R.id.tv_join_none);

        tv_join_age = (TextView) findViewById(R.id.tv_join_age);
        btn_join_ok = (TextView) findViewById(R.id.btn_join_ok);

        picker_join_age = (RulerValuePicker) findViewById(R.id.picker_join_age);

    }


    private void userName() {

        IsOnline.onlineCheck(getApplicationContext(), new IsOnline.onlineCallback() {
            @Override
            public void onSuccess() {
                service.userName().enqueue(new Callback<NameData>() {
                    @Override
                    public void onResponse(Call<NameData> call, retrofit2.Response<NameData> response) {
                        if (response.body().getType().equals("SUCCESS")) {

                            for (NameList name : response.body().getData()) {
                                switch (name.getType()) {
                                    case "O":
                                        //명사
                                        tv_join_name_post.setText(name.getWord());
                                        postfix = name.getId() + "";
                                        btnEnable();
                                        break;
                                    case "R":
                                        //형용사
                                        tv_join_name_pre.setText(name.getWord());
                                        prefix = name.getId() + "";
                                        btnEnable();
                                        break;
                                    default:
                                        tv_join_name_post.setText("?");
                                        tv_join_name_pre.setText("?");
                                        break;
                                }
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "ERROR : " + response.body().getReason(), Toast.LENGTH_SHORT).show();

                        }
                    }

                    @Override
                    public void onFailure(Call<NameData> call, Throwable t) {
                        //request fail(not found, time out, etc...)
                        Toast.makeText(getApplicationContext(), "onFailure", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_join_male:
                initGender();
                tv_join_male.setSelected(true);
                gender = "m";
                btnEnable();
                break;
            case R.id.tv_join_female:
                initGender();
                tv_join_female.setSelected(true);
                gender = "f";
                btnEnable();
                break;
            case R.id.tv_join_none:
                initGender();
                tv_join_none.setSelected(true);
                gender = "n";
                btnEnable();
                break;
        }
    }

    private void initGender() {
        tv_join_male.setSelected(false);
        tv_join_female.setSelected(false);
        tv_join_none.setSelected(false);
    }

    private void btnEnable() {
        if (!age.equals("") && !age.equals("0") && !age.equals("100")
//                && !prefix.equals("")&& !prefix.equals("?")
//                && !postfix.equals("")&& !postfix.equals("?")
                && !gender.equals("") && !gender.equals("?")) {
            btn_join_ok.setSelected(true);
        } else {
            btn_join_ok.setSelected(false);
        }
    }
}
