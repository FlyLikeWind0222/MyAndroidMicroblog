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

import com.flylikewind.microblog.R;
import com.flylikewind.microblog.adapter.HomeListviewAdapter;
import com.flylikewind.microblog.ui.xlistview.XListView;
import com.flylikewind.microblog.ui.xlistview.XListView.IXListViewListener;
import com.sina.weibo.sdk.openapi.models.Status;

@SuppressLint("NewApi")
public class MainTab01_01 extends Fragment implements IXListViewListener {

	public int pageCount = 50;
	private XListView tabHomeListview;
	private HomeListviewAdapter adapter;
	private ImageView radar;
	private static MainTab01_01 mTab01;
	private View mainTab01;
	private Activity homeActivity;
	private Handler mHandler;

	public MainTab01_01() {
	}

	public static MainTab01_01 getInstance() {
		if (mTab01 == null) {
			synchronized (MainTab01_01.class) {
				if (mTab01 == null) {
					mTab01 = new MainTab01_01();
				}
			}
		}
		return mTab01;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainTab01 = inflater.inflate(R.layout.main_tab_01, container, false);
		return mainTab01;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		homeActivity = getActivity();
		radar = (ImageView) mainTab01
				.findViewById(R.id.navigationbar_icon_radar);
		adapter = new HomeListviewAdapter(homeActivity);
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
		if (adapter.statuses != null) {
			return adapter.statuses.statusList;
		}
		return null;
	}

	public void onLoad() {
		tabHomeListview.stopRefresh();
		tabHomeListview.stopLoadMore();
		tabHomeListview.setRefreshTime("刚刚");
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
				ArrayList<Status> weiboLists = adapter.statuses.statusList;
				String id = weiboLists.get(weiboLists.size() - 1).id;
				adapter.isLoadMore = true;
				adapter.getdata(0L, Long.parseLong(id) - 1, pageCount, 1,
						false, 0, false);
			}
		}, 2000);
	}
}