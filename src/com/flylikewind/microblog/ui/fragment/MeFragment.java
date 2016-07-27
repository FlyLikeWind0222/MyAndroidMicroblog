package com.flylikewind.microblog.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.flylikewind.microblog.R;
import com.flylikewind.microblog.ui.activity.WBDemoMainActivity;

@SuppressLint("NewApi")
public class MeFragment extends Fragment {
	private Button weiboApi;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View newsLayout = inflater.inflate(R.layout.main_tab_04, container,
				false);
		weiboApi = (Button) newsLayout.findViewById(R.id.tab_setting_weiboapi);
		weiboApi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),
						WBDemoMainActivity.class);
				startActivity(intent);
			}
		});
		return newsLayout;
	}

}
