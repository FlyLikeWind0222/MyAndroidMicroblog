package com.flylikewind.microblog.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flylikewind.microblog.R;
import com.flylikewind.microblog.ui.fragment.MainTab01;
import com.flylikewind.microblog.ui.fragment.MainTab02;
import com.flylikewind.microblog.ui.fragment.MainTab03;
import com.flylikewind.microblog.ui.fragment.MainTab04;

public class FragmentMainActivity extends Activity implements OnClickListener {
	private MainTab01 mTab01;
	private MainTab02 mTab02;
	private MainTab03 mTab03;
	private MainTab04 mTab04;

	/**
	 * 底部四个按钮
	 */
	private LinearLayout mTabBtnHome, mTabBtnFrd, mTabBtnAddress,
			mTabBtnSettings;
	private ImageButton btnWeixin, btnFrd, btnAddress, btnSettings;
	private TextView txtWeixin, txtFrd, txtAddress, txtSettings;
	/**
	 * 用于对Fragment进行管理
	 */
	private FragmentManager fragmentManager;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_main);
		initViews();
		fragmentManager = getFragmentManager();
		setTabSelection(0);
	}

	private void initViews() {

		mTabBtnHome = (LinearLayout) findViewById(R.id.id_tab_bottom_home);
		mTabBtnFrd = (LinearLayout) findViewById(R.id.id_tab_bottom_message);
		mTabBtnAddress = (LinearLayout) findViewById(R.id.id_tab_bottom_discovery);
		mTabBtnSettings = (LinearLayout) findViewById(R.id.id_tab_bottom_me);
		btnWeixin = (ImageButton) mTabBtnHome
				.findViewById(R.id.btn_tab_bottom_home);
		btnFrd = (ImageButton) mTabBtnFrd
				.findViewById(R.id.btn_tab_bottom_message);
		btnAddress = (ImageButton) mTabBtnAddress
				.findViewById(R.id.btn_tab_bottom_discovery);
		btnSettings = (ImageButton) mTabBtnSettings
				.findViewById(R.id.btn_tab_bottom_me);
		txtWeixin = (TextView) mTabBtnHome
				.findViewById(R.id.txt_tab_bottom_home);
		txtFrd = (TextView) mTabBtnFrd
				.findViewById(R.id.txt_tab_bottom_message);
		txtAddress = (TextView) mTabBtnAddress
				.findViewById(R.id.txt_tab_bottom_discovery);
		txtSettings = (TextView) mTabBtnSettings
				.findViewById(R.id.txt_tab_bottom_me);

		mTabBtnHome.setOnClickListener(this);
		mTabBtnFrd.setOnClickListener(this);
		mTabBtnAddress.setOnClickListener(this);
		mTabBtnSettings.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.id_tab_bottom_home:
			setTabSelection(0);
			break;
		case R.id.id_tab_bottom_message:
			setTabSelection(1);
			break;
		case R.id.id_tab_bottom_discovery:
			setTabSelection(2);
			break;
		case R.id.id_tab_bottom_me:
			setTabSelection(3);
			break;

		default:
			break;
		}
	}

	/**
	 * 根据传入的index参数来设置选中的tab页。
	 * 
	 */
	@SuppressLint("NewApi")
	private void setTabSelection(int index) {
		// 重置按钮
		resetBtn();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
		case 0:
			// 当点击了消息tab时，改变控件的图片和文字颜色
			btnWeixin.setImageResource(R.drawable.tabbar_home_highlighted);
			txtWeixin
					.setTextColor(getResources().getColor(R.color.font_orange));
			if (mTab01 == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				mTab01 = MainTab01.getInstance();
				transaction.add(R.id.id_content, mTab01);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(mTab01);
			}
			break;
		case 1:
			// 当点击了消息tab时，改变控件的图片和文字颜色
			btnFrd.setImageResource(R.drawable.tabbar_message_center_highlighted);
			txtFrd.setTextColor(getResources().getColor(R.color.font_orange));
			if (mTab02 == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				mTab02 = new MainTab02();
				transaction.add(R.id.id_content, mTab02);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(mTab02);
			}
			break;
		case 2:
			// 当点击了动态tab时，改变控件的图片和文字颜色
			btnAddress.setImageResource(R.drawable.tabbar_discover_highlighted);
			txtAddress.setTextColor(getResources()
					.getColor(R.color.font_orange));
			if (mTab03 == null) {
				// 如果NewsFragment为空，则创建一个并添加到界面上
				mTab03 = new MainTab03();
				transaction.add(R.id.id_content, mTab03);
			} else {
				// 如果NewsFragment不为空，则直接将它显示出来
				transaction.show(mTab03);
			}
			break;
		case 3:
			// 当点击了设置tab时，改变控件的图片和文字颜色
			btnSettings.setImageResource(R.drawable.tabbar_profile_highlighted);
			txtSettings.setTextColor(getResources().getColor(
					R.color.font_orange));
			if (mTab04 == null) {
				// 如果SettingFragment为空，则创建一个并添加到界面上
				mTab04 = new MainTab04();
				transaction.add(R.id.id_content, mTab04);
			} else {
				// 如果SettingFragment不为空，则直接将它显示出来
				transaction.show(mTab04);
			}
			break;
		}
		transaction.commit();
	}

	/**
	 * 清除掉所有的选中状态。
	 */
	private void resetBtn() {
		btnWeixin.setImageResource(R.drawable.tabbar_home);
		txtWeixin
				.setTextColor(getResources().getColor(R.color.font_light_gray));
		btnFrd.setImageResource(R.drawable.tabbar_message_center);
		txtFrd.setTextColor(getResources().getColor(R.color.font_light_gray));
		btnAddress.setImageResource(R.drawable.tabbar_discover);
		txtAddress.setTextColor(getResources()
				.getColor(R.color.font_light_gray));
		btnSettings.setImageResource(R.drawable.tabbar_profile);
		txtSettings.setTextColor(getResources().getColor(
				R.color.font_light_gray));
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	@SuppressLint("NewApi")
	private void hideFragments(FragmentTransaction transaction) {
		if (mTab01 != null) {
			transaction.hide(mTab01);
		}
		if (mTab02 != null) {
			transaction.hide(mTab02);
		}
		if (mTab03 != null) {
			transaction.hide(mTab03);
		}
		if (mTab04 != null) {
			transaction.hide(mTab04);
		}
	}

}
