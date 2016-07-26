package com.flylikewind.microblog.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.flylikewind.microblog.R;
import com.flylikewind.microblog.ui.fragment.MainTab01;
import com.flylikewind.microblog.util.AccessTokenKeeper;
import com.flylikewind.microblog.util.Constants;
import com.flylikewind.microblog.util.MyTextUtils;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.openapi.StatusesAPI;
import com.sina.weibo.sdk.openapi.models.ErrorInfo;
import com.sina.weibo.sdk.openapi.models.Status;
import com.sina.weibo.sdk.openapi.models.StatusList;
import com.sina.weibo.sdk.utils.LogUtil;

public class HomeListviewAdapter extends BaseAdapter {
	private static final String TAG = "HomeListviewAdapter";
	/** 当前 Token 信息 */
	private Oauth2AccessToken mAccessToken;
	/** 用于获取微博信息流等操作的API */
	private StatusesAPI mStatusesAPI;
	private Activity activity;
	// 微博数据
	public StatusList statuses;
	private LayoutInflater mInflater;
	// 是否加载更多的标志
	public boolean isLoadMore = false;
	private ImageLoader mImageLoader;
	private int mImageThumbSize;
	private int mImageThumbSpacing;

	public HomeListviewAdapter(Activity activity) {
		this.activity = activity;
		RequestQueue queue = Volley.newRequestQueue(activity);
		mImageLoader = new ImageLoader(queue, new BitmapCache());
		mImageThumbSize = activity.getResources().getDimensionPixelSize(
				R.dimen.image_thumbnail_size);
		mImageThumbSpacing = activity.getResources().getDimensionPixelSize(
				R.dimen.image_thumbnail_spacing);

		mInflater = LayoutInflater.from(activity);
		// 获取当前已保存过的 Token
		mAccessToken = AccessTokenKeeper.readAccessToken(activity);
		// 对statusAPI实例化
		mStatusesAPI = new StatusesAPI(activity, Constants.APP_KEY,
				mAccessToken);
		getdata(0L, 0L, MainTab01.getInstance().pageCount, 1, false, 0, false);
	}

	public void getdata(long since_id, long max_id, int count, int page,
			boolean base_app, int featureType, boolean trim_user) {
		// 获取最新微博数据并封装
		if (mAccessToken != null && mAccessToken.isSessionValid()) {
			mStatusesAPI.friendsTimeline(since_id, max_id, count, page,
					base_app, featureType, trim_user, mListener);
		}
	}

	public void clear() {
		if (statuses != null) {
			this.statuses.statusList.clear();
		}
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return statuses == null ? 0 : statuses.statusList.size();
	}

	@Override
	public Object getItem(int position) {
		return statuses == null ? 0 : statuses.statusList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return Long.parseLong(statuses == null ? "0" : statuses.statusList
				.get(position).id);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view;
		final ViewHolder viewHolder;

		/*
		 * convertView将之前加载好的布局进行缓存，所以此处判断convertView是否为空：不为空则已有缓存，直接使用缓存的内容；为空，
		 * 则重新创建对象，提高效率
		 */
		if (convertView == null) {
			view = mInflater.inflate(R.layout.home_listview_item, null);
			viewHolder = new ViewHolder();
			viewHolder.home_listview_user_head_imageview = (NetworkImageView) view
					.findViewById(R.id.home_listview_user_head_imageview);
			viewHolder.home_listview_username = (TextView) view
					.findViewById(R.id.home_listview_username);
			viewHolder.home_listview_time = (TextView) view
					.findViewById(R.id.home_listview_time);
			viewHolder.home_listview_from = (TextView) view
					.findViewById(R.id.home_listview_from);
			viewHolder.home_listview_body_message = (TextView) view
					.findViewById(R.id.home_listview_body_message);
			viewHolder.home_listview_gridview = (GridView) view
					.findViewById(R.id.home_listview_gridview);
			// 将viewHolder存储在view中
			view.setTag(viewHolder);
		} else {
			view = convertView;
			// 重新获取viewHolder
			viewHolder = (ViewHolder) view.getTag();
		}

		/*
		 * 设置各组件需要显示的微博信息
		 */
		Status status = statuses.statusList.get(position);
		// 设置微博头像
		String imgUrl = status.user.profile_image_url;
		viewHolder.home_listview_user_head_imageview
				.setDefaultImageResId(R.drawable.logo);
		viewHolder.home_listview_user_head_imageview
				.setErrorImageResId(R.drawable.logo);
		viewHolder.home_listview_user_head_imageview.setImageUrl(imgUrl,
				mImageLoader);
		// 设置用户昵称
		viewHolder.home_listview_username.setText(status.user.screen_name);
		// 设置微博创建时间
		viewHolder.home_listview_time.setText(MyTextUtils
				.formatTime(status.created_at));
		// 设置微博来源
		viewHolder.home_listview_from.setText(MyTextUtils
				.getFromText(status.source));
		// 设置微博信息内容
		viewHolder.home_listview_body_message.setText(status.text);
		// 设置微博配图
		ArrayList<String> picUrls = status.pic_urls;
		if (picUrls != null && picUrls.size() > 0) {
			final PhotoWallAdapter photoWallAdapter = new PhotoWallAdapter(
					activity, 0, picUrls.toArray(new String[1]),
					viewHolder.home_listview_gridview);
			viewHolder.home_listview_gridview.setAdapter(photoWallAdapter);
			viewHolder.home_listview_gridview.getViewTreeObserver()
					.addOnGlobalLayoutListener(
							new ViewTreeObserver.OnGlobalLayoutListener() {
								@SuppressWarnings("deprecation")
								@Override
								public void onGlobalLayout() {
									final int numColumns = (int) Math.floor(viewHolder.home_listview_gridview
											.getWidth()
											/ (mImageThumbSize + mImageThumbSpacing));
									if (numColumns > 0) {
										int columnWidth = (viewHolder.home_listview_gridview
												.getWidth() / numColumns)
												- mImageThumbSpacing;
										photoWallAdapter
												.setItemHeight(columnWidth);
										viewHolder.home_listview_gridview
												.getViewTreeObserver()
												.removeGlobalOnLayoutListener(
														this);
									}
								}
							});
			photoWallAdapter.fluchCache();
		}

		return view;
	}

	/**
	 * 内部类： 每次在getView()方法中还是会调用View的findViewById()方法来获取一次控件的实例，
	 * 此时借助ViewHolder来对这部分性能进行优化，用于对控件实例进行缓存。
	 * 
	 * @author FlyLikeWind
	 * 
	 */
	class ViewHolder {
		NetworkImageView home_listview_user_head_imageview;
		TextView home_listview_username;
		TextView home_listview_time;
		TextView home_listview_from;
		TextView home_listview_body_message;
		GridView home_listview_gridview;
	}

	/**
	 * 微博 OpenAPI 回调接口。
	 */
	private RequestListener mListener = new RequestListener() {
		@Override
		public void onComplete(String response) {
			if (!TextUtils.isEmpty(response)) {
				LogUtil.i(TAG, response);
				if (response.startsWith("{\"statuses\"")) {
					// 如果是加载更多的返回，应该保留原来微博集合的数据
					if (isLoadMore) {
						// 调用 StatusList#parse 解析字符串成微博列表对象
						statuses.statusList
								.addAll(StatusList.parse(response).statusList);
						isLoadMore = false;
					} else {
						// 调用 StatusList#parse 解析字符串成微博列表对象
						statuses = StatusList.parse(response);
					}

					if (statuses != null && statuses.total_number > 0) {
						Toast.makeText(activity,
								"获取微博信息流成功, 条数: " + statuses.statusList.size(),
								Toast.LENGTH_LONG).show();
						notifyDataSetChanged();
						MainTab01.getInstance().onLoad();
					}
				} else if (response.startsWith("{\"created_at\"")) {
					// 调用 Status#parse 解析字符串成微博对象
					Status status = Status.parse(response);
					Toast.makeText(activity, "发送一送微博成功, id = " + status.id,
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(activity, response, Toast.LENGTH_LONG)
							.show();
				}
			}
		}

		@Override
		public void onWeiboException(WeiboException e) {
			LogUtil.e(TAG, e.getMessage());
			ErrorInfo info = ErrorInfo.parse(e.getMessage());
			Toast.makeText(activity, info.toString(), Toast.LENGTH_LONG).show();
		}
	};

	/**
	 * 使用LruCache来缓存图片
	 */
	public class BitmapCache implements ImageCache {
		private LruCache<String, Bitmap> mCache;

		public BitmapCache() {
			// 获取应用程序最大可用内存
			int maxMemory = (int) Runtime.getRuntime().maxMemory();
			int cacheSize = maxMemory / 8;
			mCache = new LruCache<String, Bitmap>(cacheSize) {
				@Override
				protected int sizeOf(String key, Bitmap bitmap) {
					return bitmap.getRowBytes() * bitmap.getHeight();
				}
			};
		}

		@Override
		public Bitmap getBitmap(String url) {
			return mCache.get(url);
		}

		@Override
		public void putBitmap(String url, Bitmap bitmap) {
			mCache.put(url, bitmap);
		}
	}
}
