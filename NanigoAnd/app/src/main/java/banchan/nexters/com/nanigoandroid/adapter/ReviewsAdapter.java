package banchan.nexters.com.nanigoandroid.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;

import banchan.nexters.com.nanigoandroid.JoinActivity;
import banchan.nexters.com.nanigoandroid.R;
import banchan.nexters.com.nanigoandroid.data.Reviews;
import banchan.nexters.com.nanigoandroid.data.ReviewsList;
import banchan.nexters.com.nanigoandroid.data.User;
import banchan.nexters.com.nanigoandroid.data.Username;
import banchan.nexters.com.nanigoandroid.http.APIService;
import banchan.nexters.com.nanigoandroid.http.APIUtil;
import banchan.nexters.com.nanigoandroid.utils.IsOnline;
import banchan.nexters.com.nanigoandroid.utils.PreferenceManager;
import banchan.nexters.com.nanigoandroid.utils.Utils;
import banchan.nexters.com.nanigoandroid.widget.Loading;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Ellen on 2018-08-15.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {


    class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        private ImageView item_reviews_icon;
        private TextView item_reviews_name;
        private TextView item_reviews_time;
        private ImageView item_reviews_more;
        private TextView item_reviews_comment;

        public ViewHolder(View convertView) {
            super(convertView);

            item_reviews_icon = (ImageView) convertView.findViewById(R.id.item_reviews_icon);
            item_reviews_name = (TextView) convertView.findViewById(R.id.item_reviews_name);
            item_reviews_time = (TextView) convertView.findViewById(R.id.item_reviews_time);
            item_reviews_more = (ImageView) convertView.findViewById(R.id.item_reviews_more);
            item_reviews_comment = (TextView) convertView.findViewById(R.id.item_reviews_comment);

        }
    }

    private List<ReviewsList> reviewsLists;
    private Context activityContext;
    private Context applicationContext;
    private Activity activity;

    public ReviewsAdapter(List<ReviewsList> list, Context activityContext, Context applicationContext) {
        reviewsLists = list;
        this.activityContext = activityContext;
        this.applicationContext = applicationContext;
        activity = (Activity) activityContext;
    }

    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View mView = inflater.inflate(R.layout.item_reviews_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(mView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ReviewsAdapter.ViewHolder viewHolder, int position) {
        final ReviewsList reviewsList = reviewsLists.get(position);
        switch (reviewsList.getAnswer()) {
            case 0:
                viewHolder.item_reviews_icon.setImageResource(R.drawable.ic_me_review);
                break;
            case 1:
                viewHolder.item_reviews_icon.setImageResource(R.drawable.ic_a_review);
                break;
            case 2:
                viewHolder.item_reviews_icon.setImageResource(R.drawable.ic_b_review);
                break;
            default:
                viewHolder.item_reviews_icon.setImageResource(R.drawable.ic_me_review);

                break;
        }

        viewHolder.item_reviews_name.setText(reviewsList.getUserName());
        viewHolder.item_reviews_time.setText(reviewsList.getCreatedAt());
        viewHolder.item_reviews_comment.setText(reviewsList.getContent());

        viewHolder.item_reviews_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(activityContext);
                if ( PreferenceManager.getInstance(applicationContext).getUserId().equals(reviewsList.getUesrId()+"")) {
                    // AlertDialog 셋팅
                    alert.setCancelable(false).setPositiveButton("신고", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            reportReviews(new mCallback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onFailure() {

                                }
                            });
                        }
                    }).setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            deleteReviews(reviewsList.getId(), new mCallback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onFailure() {

                                }
                            });
                            // 다이얼로그를 취소한다
                            dialog.cancel();
                        }
                    });
                } else {
                    // AlertDialog 셋팅
                    alert.setCancelable(false).setPositiveButton("수정", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            updateReviews(new mCallback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onFailure() {

                                }
                            });
                        }
                    }).setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            deleteReviews(reviewsList.getId(), new mCallback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onFailure() {

                                }
                            });
                            // 다이얼로그를 취소한다
                            dialog.cancel();
                        }
                    });
                }


                // 다이얼로그 생성
//                AlertDialog alertDialog = alert.create();
//                alertDialog.show();


            }
        });
    }

    @Override
    public int getItemCount() {
        return reviewsLists.size();
    }


    private static APIService service = APIUtil.getService();


    private void updateReviews(final mCallback callback) {

        IsOnline.onlineCheck(applicationContext, new IsOnline.onlineCallback() {
            @Override
            public void onSuccess() {
//                new Loading().progressON(activity);

                Reviews review = new Reviews();
                review.setContent("");
                review.setQuestionId(0);
                review.setUserId(0);
                service.updateReviews(review).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {

                        try {

                            if (response.isSuccessful()) {
                                String result = response.body().toString();
                                JSONObject data = new JSONObject(result);


                                if (data.getString("type").equals("SUCCESS")) {
//                                    String userId = data.getJSONObject("data").getString("id");

                                    Toast.makeText(applicationContext, "성공  ", Toast.LENGTH_SHORT).show();
                                    callback.onSuccess();

                                } else {
                                    Toast.makeText(applicationContext, "fail", Toast.LENGTH_SHORT).show();
                                    callback.onFailure();


                                }
                            } else {
//end respone error
                                JSONObject data = new JSONObject(response.errorBody().string());
                                Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show();
                                callback.onFailure();


                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        new Loading().progressOFF();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        //request fail(not found, time out, etc...)
                        Toast.makeText(applicationContext, "onFailure", Toast.LENGTH_SHORT).show();
                        new Loading().progressOFF();
                        callback.onFailure();

                    }
                });
            }
        });

    }


    private void reportReviews(final mCallback callback) {

        IsOnline.onlineCheck(applicationContext, new IsOnline.onlineCallback() {
            @Override
            public void onSuccess() {
//                new Loading().progressON(activity);

                Reviews review = new Reviews();
                review.setReviewId(0);
                review.setUserId(0);
                service.reportReviews(review).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {

                        try {

                            if (response.isSuccessful()) {
                                String result = response.body().toString();
                                JSONObject data = new JSONObject(result);


                                if (data.getString("type").equals("SUCCESS")) {
//                                    String userId = data.getJSONObject("data").getString("id");

                                    Toast.makeText(applicationContext, "성공  ", Toast.LENGTH_SHORT).show();
                                    callback.onSuccess();

                                } else {
                                    Toast.makeText(applicationContext, "fail", Toast.LENGTH_SHORT).show();
                                    callback.onFailure();

                                }
                            } else {
//end respone error
                                JSONObject data = new JSONObject(response.errorBody().string());
                                Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show();
                                callback.onFailure();

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            callback.onFailure();

                        }
                        new Loading().progressOFF();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        //request fail(not found, time out, etc...)
                        Toast.makeText(applicationContext, "onFailure", Toast.LENGTH_SHORT).show();
                        new Loading().progressOFF();
                        callback.onFailure();

                    }
                });
            }
        });

    }


    private void deleteReviews(final int reviewId, final mCallback callback) {

        IsOnline.onlineCheck(applicationContext, new IsOnline.onlineCallback() {
            @Override
            public void onSuccess() {
//                new Loading().progressON(activity);

                service.deleteReviews(reviewId + "").enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {

                        try {

                            if (response.isSuccessful()) {
                                String result = response.body().toString();
                                JSONObject data = new JSONObject(result);


                                if (data.getString("type").equals("SUCCESS")) {
//                                    String userId = data.getJSONObject("data").getString("id");
                                    callback.onSuccess();
                                    Toast.makeText(applicationContext, "성공  ", Toast.LENGTH_SHORT).show();
                                } else {
                                    callback.onFailure();
                                    Toast.makeText(applicationContext, "fail", Toast.LENGTH_SHORT).show();

                                }
                            } else {
//end respone error
                                JSONObject data = new JSONObject(response.errorBody().string());
                                Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show();
                                callback.onFailure();

                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            callback.onFailure();

                        }
                        new Loading().progressOFF();
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        //request fail(not found, time out, etc...)
                        Toast.makeText(applicationContext, "onFailure", Toast.LENGTH_SHORT).show();
                        callback.onFailure();

                        new Loading().progressOFF();
                    }
                });
            }
        });

    }

    private interface mCallback {
        void onSuccess();

        void onFailure();
    }
}
