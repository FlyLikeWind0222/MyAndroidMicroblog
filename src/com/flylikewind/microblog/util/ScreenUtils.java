package com.flylikewind.microblog.util;

import android.app.Activity;
import android.util.DisplayMetrics;

public class ScreenUtils {

	/**
	 * 手机屏幕宽度
	 */
	public static int screenWidth = 0;
	/**
	 * 手机屏幕高度
	 */
	public static int screenHeight = 0;
	public static int widthDpi = 0;
	public static int heightDpi = 0;

	/**
	 * 初始化，以便获取屏幕大小
	 * 
	 * @param activity
	 */
	private static void init(Activity activity) {
		// 通过WindowManager获取
		DisplayMetrics dm = new DisplayMetrics();
		dm = activity.getResources().getDisplayMetrics();
		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;
		widthDpi = (int) (screenWidth / dm.density + 0.5f);
		heightDpi = (int) (screenHeight / dm.density + 0.5f);
	}

	/**
	 * 返回屏幕宽度和高度中数值小的一个
	 * 
	 * @return
	 */
	public static int getSmallerSize(Activity activity) {
		if (screenWidth == 0 || screenHeight == 0)
			init(activity);
		return screenWidth > screenHeight ? screenHeight : screenWidth;
	}
}
