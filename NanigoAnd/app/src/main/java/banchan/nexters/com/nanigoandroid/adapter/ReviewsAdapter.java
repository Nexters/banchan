package banchan.nexters.com.nanigoandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import banchan.nexters.com.nanigoandroid.R;
import banchan.nexters.com.nanigoandroid.data.ReviewsList;

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


    public ReviewsAdapter(List<ReviewsList> list) {
        reviewsLists = list;
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
        ReviewsList reviewsList = reviewsLists.get(position);
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
    }

    @Override
    public int getItemCount() {
        return reviewsLists.size();
    }


}
