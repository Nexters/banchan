package banchan.nexters.com.nanigoandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import banchan.nexters.com.nanigoandroid.adapter.ReviewsAdapter;
import banchan.nexters.com.nanigoandroid.data.ReviewsData;
import banchan.nexters.com.nanigoandroid.data.ReviewsList;
import banchan.nexters.com.nanigoandroid.http.APIService;
import banchan.nexters.com.nanigoandroid.http.APIUtil;
import banchan.nexters.com.nanigoandroid.utils.IsOnline;
import banchan.nexters.com.nanigoandroid.utils.SimpleDividerItemDecoration;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Ellen on 2018-08-15.
 */

public class ConvertActivity extends AppCompatActivity {
    private APIService service = APIUtil.getService();
    private SwipeRefreshLayout swipe_reviews_refresh;

    int questionId = 2;
    RecyclerView rvReviews;
    private ReviewsAdapter adapter;


    private boolean isLoading = false;
    private int currentPage = 0;

    private List<ReviewsList> reviewsLists;
    private TextView tv_reviews_empty;
    TextView anim_test;
    Animation animation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_test);

        anim_test = (TextView) findViewById(R.id.anim_test);

        animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shrink_up);
        anim_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anim_test.startAnimation(animation);

            }
        });
        tv_reviews_empty = (TextView) findViewById(R.id.tv_reviews_empty);
        rvReviews = (RecyclerView) findViewById(R.id.rv_reviews);
        rvReviews.setHasFixedSize(true);
        findViewById(R.id.btn_review_refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reviewsList(currentPage);
            }
        });
        swipe_reviews_refresh = findViewById(R.id.swipe_reviews_refresh);
        swipe_reviews_refresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 0;
                reviewsList(currentPage);
                swipe_reviews_refresh.setRefreshing(false);
            }
        });

    }

    private void reviewsList(final int page) {

        IsOnline.onlineCheck(getApplicationContext(), new IsOnline.onlineCallback() {
            @Override
            public void onSuccess() {
                int lastReviewId = 10000;
                if (page != 0) {
                    lastReviewId = page;
                }
                service.reviewList(questionId + "", lastReviewId + "").enqueue(new Callback<ReviewsData>() {
                    @Override
                    public void onResponse(Call<ReviewsData> call, retrofit2.Response<ReviewsData> response) {
                        rvReviews.setVisibility(View.VISIBLE);
                        tv_reviews_empty.setVisibility(View.GONE);
                        isLoading = false;
                        if (response.body().getType().equals("SUCCESS")) {
                            if (response.body().getData().size() == 0) {
                                if (currentPage == 0) {
                                    tv_reviews_empty.setVisibility(View.VISIBLE);
                                    rvReviews.setVisibility(View.GONE);
                                }
                                currentPage = 0;

                            } else {
                                tv_reviews_empty.setVisibility(View.GONE);
                                rvReviews.setVisibility(View.VISIBLE);
                                if (currentPage != 0) {
                                    reviewsLists.addAll(response.body().getData());
                                    adapter.notifyItemRangeInserted(adapter.getItemCount(), reviewsLists.size() - 1);
                                } else {
                                    reviewsLists = response.body().getData();
                                    adapter = new ReviewsAdapter(reviewsLists);
                                    rvReviews.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                    rvReviews.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));

//                            ItemClickSupport.addTo(rvTransHist).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//                                @Override
//                                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                                    startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse("tndnpartner://detail?orderNo=" + transHistLists.get(position).getOrderNo())), 0);
//                                }
//                            });
                                    rvReviews.setAdapter(adapter);
                                }

                                //pagination
                                rvReviews.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                    @Override
                                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                        super.onScrollStateChanged(recyclerView, newState);
                                        if (!rvReviews.canScrollVertically(1)) {
                                            // 최하단
                                            // if direction -1 is 최상단
                                            if (!isLoading) {
                                                currentPage = reviewsLists.get(reviewsLists.size() - 1).getId();
                                                reviewsList(currentPage);
                                                isLoading = true;
                                            }
                                        }
                                    }

                                    @Override
                                    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                        super.onScrolled(recyclerView, dx, dy);
                                    }
                                });
                            }//end else list is empty

                        } else {
                            isLoading = false;
                            currentPage = 0;
                            Toast.makeText(getApplicationContext(), "TYPE IS FAIL", Toast.LENGTH_SHORT).show();

                        }//end else type is fail
                    }

                    @Override
                    public void onFailure(Call<ReviewsData> call, Throwable t) {
                        //request fail(not found, time out, etc...)
                        isLoading = false;
                        currentPage = 0;
                        Toast.makeText(getApplicationContext(), "onFailure", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}
