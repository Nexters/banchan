package banchan.nexters.com.nanigoandroid.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        private TextView item_reviews_list_date;
        private TextView item_reviews_list_content;
        private TextView item_reviews_list_user;

        public ViewHolder(View convertView) {
            super(convertView);

            item_reviews_list_date = (TextView) convertView.findViewById(R.id.item_reviews_list_date);
            item_reviews_list_content = (TextView) convertView.findViewById(R.id.item_reviews_list_content);
            item_reviews_list_user = (TextView) convertView.findViewById(R.id.item_reviews_list_user);
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
        viewHolder.item_reviews_list_date.setText(reviewsList.getCreatedAt());
        viewHolder.item_reviews_list_content.setText(reviewsList.getContent());
        viewHolder.item_reviews_list_user.setText("유저:"+reviewsList.getUesrId());
    }

    @Override
    public int getItemCount() {
        return reviewsLists.size();
    }


}
