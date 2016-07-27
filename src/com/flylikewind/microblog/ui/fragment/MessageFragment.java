package com.flylikewind.microblog.ui.fragment;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flylikewind.microblog.R;

@SuppressLint("NewApi")
public class MessageFragment extends Fragment{

	private static MessageFragment mTab02;
	private View mainTab02;

	public MessageFragment() {
	}

	public static MessageFragment getInstance() {
		if (mTab02 == null) {
			synchronized (MessageFragment.class) {
				if (mTab02 == null) {
					mTab02 = new MessageFragment();
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
		
	}

}
