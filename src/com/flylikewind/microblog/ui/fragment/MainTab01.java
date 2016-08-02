package com.flylikewind.microblog.ui.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.flylikewind.microblog.CustomApplication;
import com.flylikewind.microblog.R;
import com.flylikewind.microblog.adapter.HomeListviewAdapter2;
import com.flylikewind.microblog.ui.xlistview.XListView;
import com.flylikewind.microblog.ui.xlistview.XListView.IXListViewListener;
import com.flylikewind.microblog.util.AccessTokenKeeper;
import com.flylikewind.microblog.util.Constants;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.sina.weibo.sdk.net.openapi.RefreshTokenApi;
import com.sina.weibo.sdk.openapi.models.Status;

@SuppressLint("NewApi")
public class MainTab01 extends Fragment implements IXListViewListener {

	public int pageCount = 20;
	private XListView tabHomeListview;
	private HomeListviewAdapter2 adapter;
	private ImageView radar;
	private static MainTab01 mTab01;
	private View mainTab01;
	private Activity homeActivity;
	private Handler mHandler;

	public MainTab01() {
	}

	public static MainTab01 getInstance() {
		if (mTab01 == null) {
			synchronized (MainTab01.class) {
				if (mTab01 == null) {
					mTab01 = new MainTab01();
				}
			}
		}
		return mTab01;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// 刷新token
		// refreshTokenRequest();

		mainTab01 = inflater.inflate(R.layout.main_tab_01, container, false);
		return mainTab01;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		homeActivity = getActivity();
		radar = (ImageView) mainTab01
				.findViewById(R.id.navigationbar_icon_radar);
		adapter = new HomeListviewAdapter2(homeActivity);
		tabHomeListview = (XListView) mainTab01
				.findViewById(R.id.tab_home_listview);
		tabHomeListview.setPullLoadEnable(true);
		tabHomeListview.setAdapter(adapter);
		tabHomeListview.setXListViewListener(this);
		mHandler = new Handler();
		radar.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				adapter.notifyDataSetChanged();
			}
		});
	}

	public List<Status> objects(int currentpage) {
		adapter.getdata(0L, 0L, 10, 1, false, 0, false);
		if (CustomApplication.statuses != null) {
			return CustomApplication.statuses.statusList;
		}
		return null;
	}

	public void onLoad() {
		if (tabHomeListview != null) {
			tabHomeListview.stopRefresh();
			tabHomeListview.stopLoadMore();
			tabHomeListview.setRefreshTime("刚刚");
		}
	}

	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				adapter.getdata(0L, 0L, pageCount, 1, false, 0, false);
			}
		}, 2000);
	}

	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				ArrayList<Status> weiboLists = CustomApplication.statuses.statusList;
				String id = weiboLists.get(weiboLists.size() - 1).id;
				adapter.isLoadMore = true;
				adapter.getdata(0L, Long.parseLong(id) - 1, pageCount, 1,
						false, 0, false);
			}
		}, 2000);
	}

	/**
	 * 刷新token信息
	 */
	@SuppressWarnings("unused")
	private void refreshTokenRequest() {
		Oauth2AccessToken token = AccessTokenKeeper
				.readAccessToken(homeActivity);
		RefreshTokenApi.create(homeActivity).refreshToken(Constants.APP_KEY,
				token.getRefreshToken(), new RequestListener() {

					@Override
					public void onWeiboException(WeiboException arg0) {
						Toast.makeText(homeActivity,
								"RefreshToken Result : " + arg0.getMessage(),
								Toast.LENGTH_LONG).show();

					}

					@Override
					public void onComplete(String arg0) {
						Toast.makeText(homeActivity,
								"RefreshToken Result : " + arg0,
								Toast.LENGTH_LONG).show();
					}
				});
	}
}
