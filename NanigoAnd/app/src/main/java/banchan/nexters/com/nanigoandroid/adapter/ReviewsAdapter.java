package banchan.nexters.com.nanigoandroid.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

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

import static android.app.AlertDialog.THEME_TRADITIONAL;

/**
 * Created by Ellen on 2018-08-15.
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {


    class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        private RelativeLayout item_reviews_layout_color;
        private LinearLayout item_reviews_layout_visible;
        private TextView item_reviews_report;

        private ImageView item_reviews_icon;
        private TextView item_reviews_name;
        private TextView item_reviews_time;
        private ImageView item_reviews_more;

        private TextView item_reviews_comment;
        private EditText item_reviews_comment_edit;
        private TextView item_reviews_comment_submit;
        private ViewSwitcher switcher;

        public ViewHolder(View convertView) {
            super(convertView);

            item_reviews_layout_color = (RelativeLayout) convertView.findViewById(R.id.item_reviews_layout_color);
            item_reviews_layout_visible = (LinearLayout) convertView.findViewById(R.id.item_reviews_layout_visible);
            item_reviews_report = (TextView) convertView.findViewById(R.id.item_reviews_report);

            item_reviews_icon = (ImageView) convertView.findViewById(R.id.item_reviews_icon);
            item_reviews_name = (TextView) convertView.findViewById(R.id.item_reviews_name);
            item_reviews_time = (TextView) convertView.findViewById(R.id.item_reviews_time);
            item_reviews_more = (ImageView) convertView.findViewById(R.id.item_reviews_more);

            item_reviews_comment = (TextView) convertView.findViewById(R.id.item_reviews_comment);
            item_reviews_comment_submit = (TextView) convertView.findViewById(R.id.item_reviews_comment_submit);
            item_reviews_comment_edit = (EditText) convertView.findViewById(R.id.item_reviews_comment_edit);

            switcher = (ViewSwitcher) convertView.findViewById(R.id.item_reviews_view_switcher);


        }
    }

    private List<ReviewsList> reviewsLists;
    private Context activityContext;
    private Context applicationContext;
    private Activity activity;
    private String questionType;

    public ReviewsAdapter(List<ReviewsList> list, String questionType, Context activityContext, Context applicationContext) {
        reviewsLists = list;
        this.questionType = questionType;
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
    public void onBindViewHolder(final ReviewsAdapter.ViewHolder viewHolder, final int position) {
        final ReviewsList reviewsList = reviewsLists.get(position);
        if (reviewsList.getReportState() == 1) {
            viewHolder.item_reviews_layout_color.setBackgroundColor(applicationContext.getResources().getColor(R.color.bg_reviews_report));
            viewHolder.item_reviews_layout_visible.setVisibility(View.INVISIBLE);
            viewHolder.item_reviews_report.setVisibility(View.VISIBLE);
        } else {
            viewHolder.item_reviews_layout_color.setBackgroundColor(applicationContext.getResources().getColor(R.color.bg_reviews));
            viewHolder.item_reviews_layout_visible.setVisibility(View.VISIBLE);
            viewHolder.item_reviews_report.setVisibility(View.INVISIBLE);
        }

        switch (reviewsList.getAnswer()) {
            case 0:
                viewHolder.item_reviews_icon.setImageResource(R.drawable.ic_me_review);
                break;
            case 1:
                if (questionType.equals("A") || questionType.equals("B")) {
                    //ox
                    viewHolder.item_reviews_icon.setImageResource(R.drawable.ic_o_review);
                } else {
                    //ab
                    viewHolder.item_reviews_icon.setImageResource(R.drawable.ic_a_review);
                }
                break;
            case 2:
                if (questionType.equals("A") || questionType.equals("B")) {
                    //ox
                    viewHolder.item_reviews_icon.setImageResource(R.drawable.ic_x_review);
                } else {
                    //ab
                    viewHolder.item_reviews_icon.setImageResource(R.drawable.ic_b_review);
                }
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
                if (PreferenceManager.getInstance(applicationContext).getUserId().equals(reviewsList.getUesrId() + "")) {
                    // AlertDialog 셋팅
                    alert.setPositiveButton("신고", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, int id) {
                            reportReviews(reviewsList.getId(),new mCallback() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(applicationContext, "신고가 완료되었습니다!", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }

                                @Override
                                public void onFailure() {
                                    Toast.makeText(applicationContext, applicationContext.getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                        }
                    }).setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, int id) {
                            deleteReviews(reviewsList.getId(), new mCallback() {
                                @Override
                                public void onSuccess() {
                                    Toast.makeText(applicationContext, "삭제가 완료되었습니다!", Toast.LENGTH_SHORT).show();
                                    deleteItem(position);
                                    dialog.dismiss();
                                }

                                @Override
                                public void onFailure() {
                                    Toast.makeText(applicationContext, applicationContext.getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });
                        }
                    });
                } else {
                    // AlertDialog 셋팅
                    alert.setPositiveButton("수정", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            viewHolder.switcher.showNext(); //or switcher.showPrevious();
                            viewHolder.item_reviews_comment_edit.setText(viewHolder.item_reviews_comment.getText().toString());


                        }
                    }).setNegativeButton("삭제", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, int id) {
                            deleteReviews(reviewsList.getId(), new mCallback() {
                                @Override
                                public void onSuccess() {
                                    deleteItem(position);
                                    Toast.makeText(applicationContext, "삭제가 완료되었습니다!  ", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }

                                @Override
                                public void onFailure() {
                                    Toast.makeText(applicationContext, applicationContext.getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                }
                            });

                        }
                    });
                }


                // 다이얼로그 생성
                AlertDialog alertDialog = alert.create();
                alertDialog.show();

            }
        });


        viewHolder.item_reviews_comment_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Reviews review = new Reviews();
                review.setContent(viewHolder.item_reviews_comment_edit.getText().toString());
                review.setQuestionId(reviewsList.getQuestionId());
                review.setUserId(reviewsList.getUserId());

                updateReviews(review, new mCallback() {
                    @Override
                    public void onSuccess() {
                        viewHolder.switcher.showPrevious(); //or switcher.showPrevious();
                        viewHolder.item_reviews_comment.setText(viewHolder.item_reviews_comment_edit.getText().toString());
                        Toast.makeText(applicationContext, "수정이 완료되었습니다!  ", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure() {
                        viewHolder.switcher.showPrevious(); //or switcher.showPrevious();
                        viewHolder.item_reviews_comment_edit.setText("");
                        Toast.makeText(applicationContext, applicationContext.getResources().getString(R.string.failed), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return reviewsLists.size();
    }

    private void deleteItem(int position) {
        reviewsLists.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, reviewsLists.size());
//        holder.itemView.setVisibility(View.GONE);
    }



    private static APIService service = APIUtil.getService();


    private void updateReviews(final Reviews review, final mCallback callback) {

        IsOnline.onlineCheck(applicationContext, new IsOnline.onlineCallback() {
            @Override
            public void onSuccess() {
//                new Loading().progressON(activity);

                service.updateReviews(review).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {

                        try {

                            if (response.isSuccessful()) {
                                String result = response.body().toString();
                                JSONObject data = new JSONObject(result);


                                if (data.getString("type").equals("SUCCESS")) {
//                                    String userId = data.getJSONObject("data").getString("id");

//                                    Toast.makeText(applicationContext, "성공  ", Toast.LENGTH_SHORT).show();
                                    callback.onSuccess();

                                } else {
//                                    Toast.makeText(applicationContext, "fail", Toast.LENGTH_SHORT).show();
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


    private void reportReviews(final int reviewId, final mCallback callback) {

        IsOnline.onlineCheck(applicationContext, new IsOnline.onlineCallback() {
            @Override
            public void onSuccess() {
//                new Loading().progressON(activity);

                Reviews review = new Reviews();
                review.setReviewId(reviewId);
                review.setUserId(Integer.parseInt(PreferenceManager.getInstance(applicationContext).getUserId()));
                service.reportReviews(review).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {

                        try {

                            if (response.isSuccessful()) {
                                String result = response.body().toString();
                                JSONObject data = new JSONObject(result);


                                if (data.getString("type").equals("SUCCESS")) {
//                                    String userId = data.getJSONObject("data").getString("id");

//                                    Toast.makeText(applicationContext, "성공  ", Toast.LENGTH_SHORT).show();
                                    callback.onSuccess();

                                } else {
//                                    Toast.makeText(applicationContext, "fail", Toast.LENGTH_SHORT).show();
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
//                                    Toast.makeText(applicationContext, "성공  ", Toast.LENGTH_SHORT).show();
                                } else {
                                    callback.onFailure();
//                                    Toast.makeText(applicationContext, "fail", Toast.LENGTH_SHORT).show();

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
