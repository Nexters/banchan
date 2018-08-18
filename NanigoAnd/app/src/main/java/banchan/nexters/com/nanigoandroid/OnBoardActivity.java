package banchan.nexters.com.nanigoandroid;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.rd.PageIndicatorView;

import java.util.ArrayList;
import java.util.List;

import banchan.nexters.com.nanigoandroid.adapter.OnBoardAdapter;

public class OnBoardActivity extends AppCompatActivity {
    private PageIndicatorView indicator;
    private ViewPager pager;
    private OnBoardAdapter adapter;
    ArrayList<Integer> idx_images;

    int MAX_PAGE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_board);

        initView();
        onBoardPager();
    }

    private void initView() {
        pager = findViewById(R.id.pager_onboard);
        indicator = findViewById(R.id.indicator_onboard);

    }

    private void onBoardPager(){
        int[] mImages = {
                R.raw.data,
                R.raw.data1,
                R.raw.data
        };

        adapter = new OnBoardAdapter(getApplicationContext(), mImages);
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);
    }
}
