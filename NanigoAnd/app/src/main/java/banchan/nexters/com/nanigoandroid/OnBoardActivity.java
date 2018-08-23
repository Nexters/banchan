package banchan.nexters.com.nanigoandroid;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;

import banchan.nexters.com.nanigoandroid.adapter.OnBoardAdapter;
import banchan.nexters.com.nanigoandroid.fragment.OnBoard01Fragment;
import banchan.nexters.com.nanigoandroid.fragment.OnBoard02Fragment;
import banchan.nexters.com.nanigoandroid.fragment.OnBoard03Fragment;
import banchan.nexters.com.nanigoandroid.utils.PreferenceManager;

public class OnBoardActivity extends AppCompatActivity {
    private PageIndicatorView indicator;
    private ViewPager pager;

    int MAX_PAGE = 3;

    Fragment cur_fragment = new Fragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);

        initView();

        pager.setAdapter(new OnBoardAdapter(getSupportFragmentManager()));
        indicator.setViewPager(pager);

        findViewById(R.id.btn_onboard_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.getInstance(getApplicationContext()).setOnboard(false);
                startActivity(new Intent(OnBoardActivity.this, JoinActivity.class));
                finish();
            }
        });

        findViewById(R.id.btn_onboard_around).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void initView() {
        pager = findViewById(R.id.pager_onboard);
        indicator = findViewById(R.id.indicator_onboard);
    }


    public class OnBoardAdapter extends FragmentPagerAdapter {

        public OnBoardAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position < 0 || MAX_PAGE <= position) return null;
            switch (position) {
                case 0:
                    cur_fragment = new OnBoard01Fragment();
                    break;
                case 1:
                    cur_fragment = new OnBoard02Fragment();
                    break;
                case 2:
                    cur_fragment = new OnBoard03Fragment();
                    break;
            }
            return cur_fragment;
        }

        @Override
        public int getCount() {
            return MAX_PAGE;
        }
    }
}
