package banchan.nexters.com.nanigoandroid.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import banchan.nexters.com.nanigoandroid.AnswerActivity;
import banchan.nexters.com.nanigoandroid.MyApplication;
import banchan.nexters.com.nanigoandroid.R;
import banchan.nexters.com.nanigoandroid.adapter.MypageAdapter;
import banchan.nexters.com.nanigoandroid.data.CardList;
import banchan.nexters.com.nanigoandroid.data.QuestionCard;
import banchan.nexters.com.nanigoandroid.http.APIService;
import banchan.nexters.com.nanigoandroid.http.APIUtil;
import banchan.nexters.com.nanigoandroid.utils.IsOnline;
import banchan.nexters.com.nanigoandroid.utils.ItemClickSupport;
import banchan.nexters.com.nanigoandroid.utils.PreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;

public class MyPageFragment extends Fragment implements View.OnClickListener {


    private TextView tv_mypage_q_selected;
    private TextView tv_mypage_q_deselected;
    private TextView tv_mypage_a_selected;
    private TextView tv_mypage_a_deselected;

    private TextView tv_mypage_user;

    private LinearLayout ll_mypage_empty;

    private ImageView iv_mypage_arrow;

    private static APIService service = APIUtil.getService();

    private RecyclerView rv_mypage_list;
    private MypageAdapter adapter;

    private boolean isLoading = false;
    private int currentPage = 0;

    View v;

    private List<QuestionCard> cardLists;

    private String tab;

    public MyPageFragment() {

    }

    public static MyPageFragment newInstance() {
        Bundle args = new Bundle();

        MyPageFragment fragment = new MyPageFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = (View) inflater.inflate(R.layout.fragment_my_page, container, false);

        initView();
        tv_mypage_q_selected.setOnClickListener(this);
        tv_mypage_q_deselected.setOnClickListener(this);
        tv_mypage_a_selected.setOnClickListener(this);
        tv_mypage_a_deselected.setOnClickListener(this);

        tv_mypage_user.setText(PreferenceManager.getInstance(getActivity().getApplicationContext()).getUserName()+"님");
        if (tab.equals("A")) {
            mVotes(currentPage);
        } else {
            mQuestions(currentPage);
        }

        return v;
    }

    private void initView() {

        tv_mypage_q_selected = (TextView) v.findViewById(R.id.tv_mypage_q_selected);
        tv_mypage_q_deselected = (TextView) v.findViewById(R.id.tv_mypage_q_deselected);
        tv_mypage_a_selected = (TextView) v.findViewById(R.id.tv_mypage_a_selected);
        tv_mypage_a_deselected = (TextView) v.findViewById(R.id.tv_mypage_a_deselected);

        tv_mypage_user = (TextView) v.findViewById(R.id.tv_mypage_user);
        rv_mypage_list = (RecyclerView) v.findViewById(R.id.rv_mypage_list);
        ll_mypage_empty = (LinearLayout) v.findViewById(R.id.ll_mypage_empty);
        iv_mypage_arrow = (ImageView) v.findViewById(R.id.iv_mypage_arrow);

        tab = "Q";
    }


    private void mQuestions(final int page) {

        IsOnline.onlineCheck(getActivity().getApplicationContext(), new IsOnline.onlineCallback() {
            @Override
            public void onSuccess() {
                MyApplication.Companion.get().progressON(getActivity());

                /**
                 * userId, lastOrder, count
                 */
                String userId = PreferenceManager.getInstance(getActivity().getApplicationContext()).getUserId();

                service.mQuestions(userId, page + "", "5").enqueue(new Callback<CardList>() {
                    @Override
                    public void onResponse(Call<CardList> call, retrofit2.Response<CardList> response) {

                        rv_mypage_list.setVisibility(View.VISIBLE);
                        ll_mypage_empty.setVisibility(View.GONE);
                        isLoading = false;
                        if (response.isSuccessful() && response.body().getType().equals("SUCCESS")) {

                            rv_mypage_list.setVisibility(View.VISIBLE);
                            ll_mypage_empty.setVisibility(View.GONE);
                            if (currentPage != 0) {
                                cardLists.addAll(response.body().getData());
                                adapter.notifyItemRangeInserted(adapter.getItemCount(), cardLists.size() - 1);
                            } else {
                                cardLists = response.body().getData();
                                adapter = new MypageAdapter(cardLists, tab, getActivity().getApplicationContext(),getActivity());
                                rv_mypage_list.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                                ItemClickSupport.addTo(rv_mypage_list).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                                    @Override
                                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                                        Intent intent = new Intent(getActivity(), AnswerActivity.class);
                                        intent.putExtra("QUESTIONID", cardLists.get(position).getId());
                                        startActivity(intent);
                                        getActivity().overridePendingTransition(0, R.anim.fade_in_reavel);

                                    }
                                });
                                rv_mypage_list.setAdapter(adapter);
                            }
                            rv_mypage_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);
                                    if (!rv_mypage_list.canScrollVertically(1)) {
                                        // 최하단
                                        // if direction -1 is 최상단
                                        if (!isLoading) {
//                                            currentPage = cardLists.get(cardLists.size() - 1).getId();
                                            currentPage++;
                                            mQuestions(currentPage);
                                            isLoading = true;
                                        }
                                    }
                                }

                                @Override
                                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);
                                }
                            });
                        } else {
                            if (response.body().getReason().equals("QuestionNotFound")) {
                                if (currentPage == 0) {
                                    ll_mypage_empty.setVisibility(View.VISIBLE);
                                    rv_mypage_list.setVisibility(View.GONE);
                                }
//                                currentPage = 0;

                            } else {
                                isLoading = false;
                                currentPage = 0;
                                Toast.makeText(getActivity().getApplicationContext(), "TYPE IS FAIL", Toast.LENGTH_SHORT).show();
                            }

                        }


                        MyApplication.Companion.get().progressOFF();
                    }

                    @Override
                    public void onFailure(Call<CardList> call, Throwable t) {
                        //request fail(not found, time out, etc...)
                        isLoading = false;
                        currentPage = 0;
                        Toast.makeText(getActivity().getApplicationContext(), "onFailure", Toast.LENGTH_SHORT).show();
                        MyApplication.Companion.get().progressOFF();

                    }
                });
            }
        });

    }

    private void mVotes(final int page) {

        IsOnline.onlineCheck(getActivity().getApplicationContext(), new IsOnline.onlineCallback() {
            @Override
            public void onSuccess() {
                MyApplication.Companion.get().progressON(getActivity());

                /**
                 * userId, lastOrder, count
                 */
                String userId = PreferenceManager.getInstance(getActivity().getApplicationContext()).getUserId();

                service.mVotes(userId, page + "", "5").enqueue(new Callback<CardList>() {
                    @Override
                    public void onResponse(Call<CardList> call, retrofit2.Response<CardList> response) {

                        rv_mypage_list.setVisibility(View.VISIBLE);
                        ll_mypage_empty.setVisibility(View.GONE);
                        isLoading = false;
                        if (response.isSuccessful() && response.body().getType().equals("SUCCESS")) {

                            rv_mypage_list.setVisibility(View.VISIBLE);
                            ll_mypage_empty.setVisibility(View.GONE);
                            if (currentPage != 0) {
                                cardLists.addAll(response.body().getData());
                                adapter.notifyItemRangeInserted(adapter.getItemCount(), cardLists.size() - 1);
                            } else {
                                cardLists = response.body().getData();
                                adapter = new MypageAdapter(cardLists, tab, getActivity().getApplicationContext(),getActivity());
                                rv_mypage_list.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
                                rv_mypage_list.setAdapter(adapter);
                            }
                            rv_mypage_list.addOnScrollListener(new RecyclerView.OnScrollListener() {
                                @Override
                                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                                    super.onScrollStateChanged(recyclerView, newState);
                                    if (!rv_mypage_list.canScrollVertically(1)) {
                                        // 최하단
                                        // if direction -1 is 최상단
                                        if (!isLoading) {
//                                            currentPage = cardLists.get(cardLists.size() - 1).getId();
                                            currentPage++;
                                            mVotes(currentPage);
                                            isLoading = true;
                                        }
                                    }
                                }

                                @Override
                                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                                    super.onScrolled(recyclerView, dx, dy);
                                }
                            });
                        } else {
                            if (response.body().getReason().equals("QuestionNotFound")) {
                                if (currentPage == 0) {
                                    ll_mypage_empty.setVisibility(View.VISIBLE);
                                    rv_mypage_list.setVisibility(View.GONE);
                                }
//                                currentPage = 0;

                            } else {
                                isLoading = false;
                                currentPage = 0;
                                Toast.makeText(getActivity().getApplicationContext(), "TYPE IS FAIL", Toast.LENGTH_SHORT).show();
                            }

                        }


                        MyApplication.Companion.get().progressOFF();
                    }

                    @Override
                    public void onFailure(Call<CardList> call, Throwable t) {
                        //request fail(not found, time out, etc...)
                        isLoading = false;
                        currentPage = 0;
                        Toast.makeText(getActivity().getApplicationContext(), "onFailure", Toast.LENGTH_SHORT).show();
                        MyApplication.Companion.get().progressOFF();

                    }
                });
            }
        });

    }

    @Override
    public void onClick(View v) {

        if (tab.equals("Q")) {
            if (v.getId() == R.id.tv_mypage_a_deselected) {
                tab = "A";
                tv_mypage_q_selected.setVisibility(View.GONE);
                tv_mypage_q_deselected.setVisibility(View.VISIBLE);
                tv_mypage_a_selected.setVisibility(View.VISIBLE);
                tv_mypage_a_deselected.setVisibility(View.GONE);
                currentPage = 0;
                mVotes(currentPage);
                cardLists.clear();
            }
        } else {
            tab = "A";
            if (v.getId() == R.id.tv_mypage_q_deselected) {
                tab = "Q";
                tv_mypage_q_selected.setVisibility(View.VISIBLE);
                tv_mypage_q_deselected.setVisibility(View.GONE);
                tv_mypage_a_selected.setVisibility(View.GONE);
                tv_mypage_a_deselected.setVisibility(View.VISIBLE);
                currentPage = 0;
                mQuestions(currentPage);
                cardLists.clear();
            }
        }

    }

    private Boolean isVisible = false;

    @Override
    public void onStart() {
        super.onStart();
        if (isVisible ){
            init();
        }
    }
    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisible = isVisibleToUser;
        if ( isVisible) {
            init();
        }
    }

    private void init(){
        if(tab.equals("A")){
            currentPage = 0;
            mVotes(currentPage);
            cardLists.clear();
        }else if(tab.equals("B")){
            currentPage = 0;
            mQuestions(currentPage);
            cardLists.clear();
        }
    }
}