package com.flylikewind.microblog.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flylikewind.microblog.R;

@SuppressLint("NewApi")
public class MainTab02 extends Fragment /* implements IXListViewListener */{

	// public int pageCount = 50;
	// private XListView tabHomeListview;
	// private HomeListviewAdapter2 adapter;
	// private ImageView radar;
	private static MainTab02 mTab02;
	private View mainTab02;

	// private Activity homeActivity;
	// private Handler mHandler;

	public MainTab02() {
	}

	public static MainTab02 getInstance() {
		if (mTab02 == null) {
			synchronized (MainTab02.class) {
				if (mTab02 == null) {
					mTab02 = new MainTab02();
				}
			}
		}
		return mTab02;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mainTab02 = inflater.inflate(R.layout.main_tab_02, container, false);
		return mainTab02;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// homeActivity = getActivity();
		// radar = (ImageView) mainTab02
		// .findViewById(R.id.navigationbar_icon_radar);
		// adapter = new HomeListviewAdapter2(homeActivity);
		// tabHomeListview = (XListView) mainTab02
		// .findViewById(R.id.tab_home_listview);
		// tabHomeListview.setPullLoadEnable(true);
		// tabHomeListview.setAdapter(adapter);
		// tabHomeListview.setXListViewListener(this);
		// mHandler = new Handler();
		// radar.setOnClickListener(new OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// adapter.notifyDataSetChanged();
		// }
		// });
	}

	// public List<Status> objects(int currentpage) {
	// adapter.getdata(0L, 0L, 10, 1, false, 0, false);
	// if (adapter.statuses != null) {
	// return adapter.statuses.statusList;
	// }
	// return null;
	// }
	//
	// public void onLoad() {
	// tabHomeListview.stopRefresh();
	// tabHomeListview.stopLoadMore();
	// tabHomeListview.setRefreshTime("刚刚");
	// }
	//
	// @Override
	// public void onRefresh() {
	// mHandler.postDelayed(new Runnable() {
	// @Override
	// public void run() {
	// adapter.getdata(0L, 0L, pageCount, 1, false, 0, false);
	// }
	// }, 2000);
	// }
	//
	// @Override
	// public void onLoadMore() {
	// mHandler.postDelayed(new Runnable() {
	// @Override
	// public void run() {
	// ArrayList<Status> weiboLists = adapter.statuses.statusList;
	// String id = weiboLists.get(weiboLists.size() - 1).id;
	// adapter.isLoadMore = true;
	// adapter.getdata(0L, Long.parseLong(id) - 1, pageCount, 1,
	// false, 0, false);
	// }
	// }, 2000);
	// }
}
