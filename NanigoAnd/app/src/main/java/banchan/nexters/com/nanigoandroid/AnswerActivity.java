package banchan.nexters.com.nanigoandroid;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.LinearLayout;
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

public class AnswerActivity extends AppCompatActivity {
    private APIService service = APIUtil.getService();
    private SwipeRefreshLayout swipe_answer_reviews;

    int questionId = 52;
    RecyclerView rv_answer_reviews_list;
    private ReviewsAdapter adapter;


    private boolean isLoading = false;
    private int currentPage = 0;

    private List<ReviewsList> reviewsLists;
    private LinearLayout ll_answer_reviews_empty;

    private EditText et_answer_review_input;
    private TextView btn_answer_reviews_submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);


//        swipe_answer_reviews = findViewById(R.id.swipe_answer_reviews);
//        swipe_answer_reviews.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                currentPage = 0;
//                reviewsList(currentPage);
//                swipe_answer_reviews.setRefreshing(false);
//            }
//        });

        initView();
        initialize();
        reviewsList(currentPage);

        btn_answer_reviews_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!et_answer_review_input.getText().toString().equals("")){
                    //submit reviews
                }
            }
        });

    }

    private void initView(){
        et_answer_review_input = (EditText)findViewById(R.id.et_answer_review_input);
        ll_answer_reviews_empty = (LinearLayout) findViewById(R.id.ll_answer_reviews_empty);
        rv_answer_reviews_list = (RecyclerView) findViewById(R.id.rv_answer_reviews_list);

        btn_answer_reviews_submit = (TextView) findViewById(R.id.btn_answer_reviews_submit);

    }
    private void initialize(){
        et_answer_review_input.setFocusable(false);

        rv_answer_reviews_list.setHasFixedSize(true);
        rv_answer_reviews_list.setNestedScrollingEnabled(false);



    }

    private void reviewsList(final int page) {

        IsOnline.onlineCheck(getApplicationContext(), new IsOnline.onlineCallback() {
            @Override
            public void onSuccess() {
//                int lastReviewId = 10000;
//                if (page != 0) {
//                    lastReviewId = page;
//                }
                service.reviewList(questionId + "", "0" + "").enqueue(new Callback<ReviewsData>() {
                    @Override
                    public void onResponse(Call<ReviewsData> call, retrofit2.Response<ReviewsData> response) {
                        rv_answer_reviews_list.setVisibility(View.VISIBLE);
                        ll_answer_reviews_empty.setVisibility(View.GONE);
                        isLoading = false;
                        if (response.body().getType().equals("SUCCESS")) {
                            if (response.body().getData().size() == 0) {
                                if (currentPage == 0) {
                                    ll_answer_reviews_empty.setVisibility(View.VISIBLE);
                                    rv_answer_reviews_list.setVisibility(View.GONE);
                                }
                                currentPage = 0;

                            } else {
                                ll_answer_reviews_empty.setVisibility(View.GONE);
                                rv_answer_reviews_list.setVisibility(View.VISIBLE);
                                if (currentPage != 0) {
                                    reviewsLists.addAll(response.body().getData());
                                    adapter.notifyItemRangeInserted(adapter.getItemCount(), reviewsLists.size() - 1);
                                } else {
                                    reviewsLists = response.body().getData();
                                    adapter = new ReviewsAdapter(reviewsLists);
                                    rv_answer_reviews_list.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//                                    rv_answer_reviews_list.addItemDecoration(new SimpleDividerItemDecoration(getApplicationContext()));

//                            ItemClickSupport.addTo(rvTransHist).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//                                @Override
//                                public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                                    startActivityForResult(new Intent(Intent.ACTION_VIEW, Uri.parse("tndnpartner://detail?orderNo=" + transHistLists.get(position).getOrderNo())), 0);
//                                }
//                            });
                                    rv_answer_reviews_list.setAdapter(adapter);
                                }

                                //pagination
                                rv_answer_reviews_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                    @Override
                                    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                        super.onScrollStateChanged(recyclerView, newState);
                                        if (!rv_answer_reviews_list.canScrollVertically(1)) {
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