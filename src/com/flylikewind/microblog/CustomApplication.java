package com.flylikewind.microblog;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import cn.jpush.android.api.JPushInterface;

import com.flylikewind.microblog.util.ConstantUtil;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.DbUtils;
import com.sina.weibo.sdk.openapi.models.StatusList;
import com.umeng.analytics.MobclickAgent;

/**
 * 自定义的应用程序管理,主要提供一些全局操作
 * 
 * @author zjt
 * 
 * @date 2016-1-11上午9:52:11
 */
public class CustomApplication extends Application {

	private static Context context; // 全局的上下文对象
	private static DbUtils dbUtil = null; // 全局的数据库操作工具类
	private static BitmapUtils bitmapUtils = null; // 图片加载工具类
	private static SharedPreferences spUtil = null; // sharedPreferences工具类
	// 微博数据
	public static StatusList statuses;

	@Override
	public void onCreate() {
		super.onCreate();
		if (!isInited()) {
			init();

			// 初始化极光推送
			boolean isJpushOpen = getSpUtil().getBoolean(
					ConstantUtil.JPUSH_OPEN, true);
			if (isJpushOpen) {
				initJPush();
			}

			// 初始化并登录云通讯平台
			// if (AppManager.getInstance().isLogin()) {
			// new Thread(new Runnable() {
			// @Override
			// public void run() {
			// try {
			// User user = AppManager.getInstance()
			// .getCurrentUser();
			// String nickName = "", portrait = "";
			// if ("jobseeker".equals(user.getIdType())) {
			// nickName = user.getRealName();
			// portrait = user.getPortrait();
			// } else if ("hr-admin".equals(user.getIdType())
			// || "hr-nomal".equals(user.getIdType())) {
			// nickName = user.getName();
			// portrait = user.getLogo();
			// }
			// ECKManager.getInstance().init(context,
			// user.getIMUuid(), user.getIdType(),
			// nickName, user.getMobileNo(), portrait,
			// new OnECLoginListener() {
			//
			// @Override
			// public void onLoginSuccess() {
			// }
			//
			// @Override
			// public void onLoginFail() {
			// spUtil.edit()
			// .putString(
			// ConstantUtil.LOGIN_PASSWORD,
			// "").commit();
			// }
			// });
			// } catch (Exception e) {
			// e.printStackTrace();
			// }
			// }
			// }).start();
			// }
		}
	}

	/**
	 * 判断应用数据是否已经被设置过，Application的onCreate会执行多次，避免全局数据设置多次（每次启动服务会执行一次onCreate）
	 * 
	 * @return boolean 如果因为启动服务而执行onCreate返回true说明之前已经初始化,否则返回false
	 */
	private synchronized boolean isInited() {
		int pid = android.os.Process.myPid();
		String processName = "";
		ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningAppProcessInfo> ls = am.getRunningAppProcesses();
		for (RunningAppProcessInfo info : ls) {
			if (pid == info.pid) {
				processName = info.processName;
				break;
			}
		}
		if (TextUtils.isEmpty(processName)
				|| !processName.equalsIgnoreCase(getPackageName())) {
			return true;
		}
		return false;
	}

	/*
	 * 初始化操作
	 */
	private void init() {
		context = this;

		dbUtil = DbUtils.create(context);
		spUtil = getSharedPreferences(ConstantUtil.CONFIG, Context.MODE_PRIVATE);
		bitmapUtils = new BitmapUtils(context);
		bitmapUtils.configDiskCacheEnabled(true);
		bitmapUtils.configMemoryCacheEnabled(true);

		// 友盟统计数据使用
		MobclickAgent.setDebugMode(true);
		MobclickAgent.openActivityDurationTrack(false);
	}

	/*
	 * 初始化极光推送
	 */
	private void initJPush() {
		JPushInterface.setDebugMode(true);
		JPushInterface.init(this);
	}

	/*
	 * 用于在程序的任意位置获取全局的上下文对象
	 */
	public static Context getAppContext() {
		return context;
	}

	/*
	 * 获取数据库操作工具
	 */
	public static DbUtils getDbUtils() {
		return dbUtil;
	}

	/*
	 * 获取图片加载工具
	 */
	public static BitmapUtils getBitmapUtils() {
		return bitmapUtils;
	}

	/*
	 * 存储工具
	 */
	public static SharedPreferences getSpUtil() {
		return spUtil;
	}

}
