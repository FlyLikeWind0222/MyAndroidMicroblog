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
import com.flylikewind.microblog.ui.fragment.DiscoveryFragment;
import com.flylikewind.microblog.ui.fragment.HomeFragment;
import com.flylikewind.microblog.ui.fragment.MeFragment;
import com.flylikewind.microblog.ui.fragment.MessageFragment;

public class FragmentMainActivity extends Activity implements OnClickListener {
	private HomeFragment mTab01;
	private MessageFragment mTab02;
	private DiscoveryFragment mTab03;
	private MeFragment mTab04;

	/**
	 * 底部四个按钮
	 */
	private LinearLayout mTabBtnHome, mTabBtnMessage, mTabBtnDiscovery,
			mTabBtnMe;
	private ImageButton btnHome, btnMessage, btnDiscovery, btnMe;
	private TextView txtHome, txtMessage, txtDiscovery, txtMe;
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
		mTabBtnMessage = (LinearLayout) findViewById(R.id.id_tab_bottom_message);
		mTabBtnDiscovery = (LinearLayout) findViewById(R.id.id_tab_bottom_discovery);
		mTabBtnMe = (LinearLayout) findViewById(R.id.id_tab_bottom_me);
		btnHome = (ImageButton) mTabBtnHome
				.findViewById(R.id.btn_tab_bottom_home);
		btnMessage = (ImageButton) mTabBtnMessage
				.findViewById(R.id.btn_tab_bottom_message);
		btnDiscovery = (ImageButton) mTabBtnDiscovery
				.findViewById(R.id.btn_tab_bottom_discovery);
		btnMe = (ImageButton) mTabBtnMe.findViewById(R.id.btn_tab_bottom_me);
		txtHome = (TextView) mTabBtnHome.findViewById(R.id.txt_tab_bottom_home);
		txtMessage = (TextView) mTabBtnMessage
				.findViewById(R.id.txt_tab_bottom_message);
		txtDiscovery = (TextView) mTabBtnDiscovery
				.findViewById(R.id.txt_tab_bottom_discovery);
		txtMe = (TextView) mTabBtnMe.findViewById(R.id.txt_tab_bottom_me);

		mTabBtnHome.setOnClickListener(this);
		mTabBtnMessage.setOnClickListener(this);
		mTabBtnDiscovery.setOnClickListener(this);
		mTabBtnMe.setOnClickListener(this);
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
			btnHome.setImageResource(R.drawable.tabbar_home_highlighted);
			txtHome.setTextColor(getResources().getColor(R.color.font_orange));
			if (mTab01 == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				mTab01 = HomeFragment.getInstance();
				transaction.add(R.id.id_content, mTab01);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(mTab01);
			}
			break;
		case 1:
			// 当点击了消息tab时，改变控件的图片和文字颜色
			btnMessage
					.setImageResource(R.drawable.tabbar_message_center_highlighted);
			txtMessage.setTextColor(getResources()
					.getColor(R.color.font_orange));
			if (mTab02 == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				mTab02 = new MessageFragment();
				transaction.add(R.id.id_content, mTab02);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(mTab02);
			}
			break;
		case 2:
			// 当点击了动态tab时，改变控件的图片和文字颜色
			btnDiscovery
					.setImageResource(R.drawable.tabbar_discover_highlighted);
			txtDiscovery.setTextColor(getResources().getColor(
					R.color.font_orange));
			if (mTab03 == null) {
				// 如果NewsFragment为空，则创建一个并添加到界面上
				mTab03 = new DiscoveryFragment();
				transaction.add(R.id.id_content, mTab03);
			} else {
				// 如果NewsFragment不为空，则直接将它显示出来
				transaction.show(mTab03);
			}
			break;
		case 3:
			// 当点击了设置tab时，改变控件的图片和文字颜色
			btnMe.setImageResource(R.drawable.tabbar_profile_highlighted);
			txtMe.setTextColor(getResources().getColor(R.color.font_orange));
			if (mTab04 == null) {
				// 如果SettingFragment为空，则创建一个并添加到界面上
				mTab04 = new MeFragment();
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
		btnHome.setImageResource(R.drawable.tabbar_home);
		txtHome.setTextColor(getResources().getColor(R.color.font_light_gray));
		btnMessage.setImageResource(R.drawable.tabbar_message_center);
		txtMessage.setTextColor(getResources()
				.getColor(R.color.font_light_gray));
		btnDiscovery.setImageResource(R.drawable.tabbar_discover);
		txtDiscovery.setTextColor(getResources().getColor(
				R.color.font_light_gray));
		btnMe.setImageResource(R.drawable.tabbar_profile);
		txtMe.setTextColor(getResources().getColor(R.color.font_light_gray));
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
