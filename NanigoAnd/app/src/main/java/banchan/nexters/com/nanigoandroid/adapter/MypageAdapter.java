package banchan.nexters.com.nanigoandroid.adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.List;

import banchan.nexters.com.nanigoandroid.MyApplication;
import banchan.nexters.com.nanigoandroid.R;
import banchan.nexters.com.nanigoandroid.data.QuestionCard;
import banchan.nexters.com.nanigoandroid.data.Reviews;
import banchan.nexters.com.nanigoandroid.data.ReviewsList;
import banchan.nexters.com.nanigoandroid.data.VoteCard;
import banchan.nexters.com.nanigoandroid.http.APIService;
import banchan.nexters.com.nanigoandroid.http.APIUtil;
import banchan.nexters.com.nanigoandroid.utils.ImageUtil;
import banchan.nexters.com.nanigoandroid.utils.IsOnline;
import banchan.nexters.com.nanigoandroid.utils.PreferenceManager;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Ellen on 2018-08-15.
 */

public class MypageAdapter extends RecyclerView.Adapter<MypageAdapter.ViewHolder> {


    class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row

        //set background and text color
        private TextView tv_username;
        private ImageView iv_view_count;
        private TextView tv_view_count;
        private ImageView iv_comment_count;
        private TextView tv_comment_count;

        //question content
        private TextView tv_question;
        private ImageView iv_question_img;
        private TextView tv_txt_a;
        private ImageView iv_answer_a_img;
        private TextView tv_txt_b;
        private ImageView iv_answer_b_img;

        //invisible
        private ImageView iv_question_report;
        private ImageView iv_badge1;
        private ImageView iv_badge2;
        private ImageView iv_badge3;

        //        popup view
//        private LinearLayout ll_mypage_popup;
//        private TextView tv_mypage_popup_txt;
//        private ImageView ic_mypage_popup_a;
//        private ImageView ic_mypage_popup_b;

        public ViewHolder(View convertView) {
            super(convertView);

            tv_username = (TextView) convertView.findViewById(R.id.tv_username);
            iv_view_count = (ImageView) convertView.findViewById(R.id.iv_view_count);
            tv_view_count = (TextView) convertView.findViewById(R.id.tv_view_count);
            iv_comment_count = (ImageView) convertView.findViewById(R.id.iv_comment_count);
            tv_comment_count = (TextView) convertView.findViewById(R.id.tv_comment_count);
            tv_question = (TextView) convertView.findViewById(R.id.tv_question);
            iv_question_img = (ImageView) convertView.findViewById(R.id.iv_question_img);
            tv_txt_a = (TextView) convertView.findViewById(R.id.tv_txt_a);
            iv_answer_a_img = (ImageView) convertView.findViewById(R.id.iv_answer_a_img);
            tv_txt_b = (TextView) convertView.findViewById(R.id.tv_txt_b);
            iv_answer_b_img = (ImageView) convertView.findViewById(R.id.iv_answer_b_img);

            iv_question_report = (ImageView) convertView.findViewById(R.id.iv_question_report);
            iv_badge1 = (ImageView) convertView.findViewById(R.id.iv_badge1);
            iv_badge2 = (ImageView) convertView.findViewById(R.id.iv_badge2);
            iv_badge3 = (ImageView) convertView.findViewById(R.id.iv_badge3);


        }
    }

    private List<QuestionCard> questionCardList;
    private String tab;
    private Context context;
private Activity activity;
    public MypageAdapter(List<QuestionCard> list, String tab, Context context, Activity activity) {
        questionCardList = list;
        this.tab = tab;
        this.context = context;
        this.activity = activity;
    }

    @Override
    public MypageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View mView;
        switch (viewType) {
            case 0:
                mView = inflater.inflate(R.layout.item_question_card, parent, false);
                break;
            case 1:
                mView = inflater.inflate(R.layout.item_question_card_b, parent, false);
                break;
            case 2:
                mView = inflater.inflate(R.layout.item_question_card_c, parent, false);
                break;
            case 3:
                mView = inflater.inflate(R.layout.item_question_card_d, parent, false);
                break;
            default:
                mView = inflater.inflate(R.layout.item_question_card, parent, false);

                break;
        }
        ViewHolder viewHolder = new ViewHolder(mView);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final MypageAdapter.ViewHolder h, final int position) {
        final QuestionCard card = questionCardList.get(position);

        /**
         * set vsible gone
         */
        h.iv_question_report.setVisibility(View.GONE);
        h.iv_badge1.setVisibility(View.GONE);
        h.iv_badge2.setVisibility(View.GONE);
        h.iv_badge3.setVisibility(View.GONE);

        if (tab.equals("Q")) {
            h.tv_username.setText("결과 확정하기");
            h.tv_username.setTextColor(Color.WHITE);
            h.tv_username.setBackgroundResource(R.drawable.bg_round_grey_mypage_confirm);

            h.tv_username.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setView(R.layout.layout_my_decision);

//                    final Dialog dialog = new Dialog(context);
//                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                    View dialogLayout = inflater.inflate(R.layout.layout_my_decision, null);

//                    dialog.setContentView(dialogLayout);

                  final  AlertDialog dialog = builder.show();

                    TextView tv_mypage_popup_txt = (TextView) dialog.findViewById(R.id.tv_mypage_popup_txt);
                    ImageView ic_mypage_popup_a = (ImageView) dialog.findViewById(R.id.ic_mypage_popup_a);
                    ImageView ic_mypage_popup_b = (ImageView) dialog.findViewById(R.id.ic_mypage_popup_b);

                    tv_mypage_popup_txt.setText(PreferenceManager.getInstance(context).getUserName() + "님은\n어떤 결정을 내렸나요?");

                    if(card.getDecision().equals("UNDECIDED")){

                        ic_mypage_popup_a.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                vote("A", card.getId(), new mCallback() {
                                    @Override
                                    public void onSuccess() {
                                        Toast.makeText(context, "성공했어요", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }

                                    @Override
                                    public void onFailure() {
                                        Toast.makeText(context, "실패했어요", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });
                        ic_mypage_popup_b.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                vote("B", card.getId(), new mCallback() {
                                    @Override
                                    public void onSuccess() {
                                        Toast.makeText(context, "성공했어요", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();                                }

                                    @Override
                                    public void onFailure() {
                                        Toast.makeText(context, "실패했어요", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                    }
                                });
                            }
                        });

                    }else if(card.getDecision().equals("A")){
                        ic_mypage_popup_b.setVisibility(View.GONE);
                    }else if(card.getDecision().equals("B")){
                        ic_mypage_popup_a.setVisibility(View.GONE);

                    }




                }
            });
        } else if (tab.equals("A")) {
            h.tv_username.setText(card.getUsername());
        }

        h.iv_view_count.setImageResource(R.drawable.ic_view_count_dark);
        h.tv_view_count.setText(card.getVote().getTotal() + "");
        h.iv_comment_count.setImageResource(R.drawable.ic_comment_count_dark);
        h.tv_comment_count.setText(card.getReview() + "");

        switch (card.getType()) {
            case "A":
                h.tv_question.setText(card.getDetail().getTXT_Q());

                break;
            case "B":
                h.tv_question.setText(card.getDetail().getTXT_Q());
                Picasso.get().load(ImageUtil.Companion.getBaseURL() + card.getDetail().getIMG_Q()).fit().centerCrop().into(h.iv_question_img);

                break;
            case "C":
                h.tv_question.setText(card.getDetail().getTXT_Q());
                h.tv_txt_a.setText(card.getDetail().getTXT_A());
                h.tv_txt_b.setText(card.getDetail().getTXT_B());
                break;
            case "D":
                h.tv_question.setText(card.getDetail().getTXT_Q());
                Picasso.get().load(ImageUtil.Companion.getBaseURL() + card.getDetail().getIMG_Q()).fit().centerCrop().into(h.iv_question_img);
                Picasso.get().load(ImageUtil.Companion.getBaseURL() + card.getDetail().getIMG_A()).fit().centerCrop().into(h.iv_answer_a_img);
                Picasso.get().load(ImageUtil.Companion.getBaseURL() + card.getDetail().getIMG_B()).fit().centerCrop().into(h.iv_answer_b_img);
                h.tv_txt_a.setText(card.getDetail().getTXT_A());
                h.tv_txt_b.setText(card.getDetail().getTXT_B());
                break;

        }


    }

    @Override
    public int getItemCount() {
        return questionCardList.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (questionCardList.get(position).getType()) {
            case "A":
                return 0;
            case "B":
                return 1;
            case "C":
                return 2;
            case "D":
                return 3;
            default:
                return 0;
        }

    }

    private static APIService service = APIUtil.getService();


    private void vote(final String answer, final int questionId, final mCallback callback) {

        IsOnline.onlineCheck(context, new IsOnline.onlineCallback() {
            @Override
            public void onSuccess() {

                VoteCard card = new VoteCard(answer, questionId, false, Integer.parseInt(PreferenceManager.getInstance(context).getUserId()));
                service.voteCard(card).enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, retrofit2.Response<JsonObject> response) {

                        try {

                            if (response.isSuccessful()) {
                                String result = response.body().toString();
                                JSONObject data = new JSONObject(result);


                                if (data.getString("type").equals("SUCCESS")) {
//                                    String userId = data.getJSONObject("data").getString("id");

//                                    Toast.makeText(context, "성공했어요", Toast.LENGTH_SHORT).show();
                                    callback.onSuccess();

                                } else {
//                                    Toast.makeText(applicationContext, "fail", Toast.LENGTH_SHORT).show();

                                    callback.onFailure();
                                }
                            } else {
//end respone error
                                JSONObject data = new JSONObject(response.errorBody().string());
//                                Toast.makeText(applicationContext, "error", Toast.LENGTH_SHORT).show();
                                callback.onFailure();


                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        //request fail(not found, time out, etc...)
//                        Toast.makeText(applicationContext, "onFailure", Toast.LENGTH_SHORT).show();
                        callback.onFailure();

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
