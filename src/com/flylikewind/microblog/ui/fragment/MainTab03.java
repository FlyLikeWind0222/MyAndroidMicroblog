package com.flylikewind.microblog.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flylikewind.microblog.R;

@SuppressLint("NewApi")
public class MainTab03 extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View newsLayout = inflater.inflate(R.layout.main_tab_03, container,
				false);
		return newsLayout;
	}

}
