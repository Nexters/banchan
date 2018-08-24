package banchan.nexters.com.nanigoandroid;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import banchan.nexters.com.nanigoandroid.utils.Utils;

public class ImageViewActivity extends AppCompatActivity {
	public static final String KEY_IMAGE_URL_LIST = "KEY_IMAGE_URL_LIST";

	Toolbar mToolbar;
	ViewPager mViewPager;
	RelativeLayout mView;
	RelativeLayout mRlTopLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if( getIntent().getExtras() == null )
			throw new NullPointerException("ImageView Activity need image list for show");

		ArrayList<String> list = getIntent().getExtras().getStringArrayList(KEY_IMAGE_URL_LIST);

		setContentView(R.layout.activity_image_view);

		mToolbar = (Toolbar) findViewById(R.id.toolbar);
		mViewPager =  (ViewPager) findViewById(R.id.vpImage);
		mView =  (RelativeLayout) findViewById(R.id.root);
		mRlTopLayout =  (RelativeLayout) findViewById(R.id.rlTopLayout);

		init(list);

	}

	private void init(final ArrayList<String> list) {
		//mView.setLayoutTransition(new LayoutTransition());


		mViewPager.setAdapter(new GalleryAdapter(getLayoutInflater(), list));
		mToolbar.setNavigationIcon(R.drawable.icon_arrow_left);
		mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				onBackPressed();
			}
		});


	}

	private void toggleViewer() {
		if( mRlTopLayout.getVisibility() == View.VISIBLE ) {
			mRlTopLayout.setVisibility(View.GONE);
		} else {
			mRlTopLayout.setVisibility(View.VISIBLE);
		}
	}

	class GalleryAdapter extends PagerAdapter {

		private final ArrayList<String> mItems;
		LayoutInflater mInflater;
		private final Point mSize;

		public GalleryAdapter(LayoutInflater inflater, ArrayList<String> items) {
			mItems = items;
			mInflater = inflater;
			mSize = Utils.getScreenSize(ImageViewActivity.this, false);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			Log.d("[ActImageViewer]", "Show image position : " + position + ", url : " + mItems.get(position));

			View view = mInflater.inflate(R.layout.layout_viewpager_image, null);
			ImageView img= (ImageView)view.findViewById(R.id.viewpager_image);

			//ImageView에 현재 position 번째에 해당하는 이미지를 보여주기 위한 작업
			String imageUrl = mItems.get(position);
			img.setScaleType(ImageView.ScaleType.FIT_XY);
			//img.setImageURI(Uri.parse(imageUrl));
			Picasso.get().load(imageUrl).resize(mSize.x, 0).into(img);

			final GestureDetector.SimpleOnGestureListener singleTapDetector = new GestureDetector.SimpleOnGestureListener() {
				@Override
				public boolean onSingleTapConfirmed(MotionEvent e) {
					toggleViewer();
					return super.onSingleTapConfirmed(e);
				}
			};
			final GestureDetector detector = new GestureDetector(ImageViewActivity.this, singleTapDetector);
			view.setOnTouchListener(new View.OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					return detector.onTouchEvent(event);
				}
			});

			//ViewPager에 만들어 낸 View 추가
			container.addView(view);


			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public int getCount() {
			return mItems.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
	}

}
