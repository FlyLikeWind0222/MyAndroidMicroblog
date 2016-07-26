package com.flylikewind.microblog.ui.activity;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.flylikewind.microblog.R;
import com.flylikewind.microblog.bean.UpdateInfo;
import com.flylikewind.microblog.service.UpdateInfoService;
import com.flylikewind.microblog.util.AccessTokenKeeper;
import com.flylikewind.microblog.util.Constants;
import com.flylikewind.microblog.util.DownLoadFileTask;
import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

public class SplashActivity extends Activity {
	private static final String TAG = "SplashActivity";
	private TextView tv_splash_version;
	private RelativeLayout ll_splash_main;
	private UpdateInfo info;
	private ProgressDialog pd;
	private String versiontext;
	private AuthInfo mAuthInfo;
	/** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
	private SsoHandler mSsoHandler;
	/** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能 */
	private Oauth2AccessToken mAccessToken;
	@SuppressLint("HandlerLeak")
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			// 判断服务器版本号 和客户端的版本号 是否相同
			if (isNeedUpdate(versiontext)) {
				Log.i(TAG, "弹出来升级对话框");
				showUpdataDialog();
			}
		}

	};

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 取消标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.splash);
		// 创建微博实例
		// mWeiboAuth = new WeiboAuth(this, Constants.APP_KEY,
		// Constants.REDIRECT_URL, Constants.SCOPE);
		// 快速授权时，请不要传入 SCOPE，否则可能会授权不成功
		mAuthInfo = new AuthInfo(this, Constants.APP_KEY,
				Constants.REDIRECT_URL, Constants.SCOPE);
		mSsoHandler = new SsoHandler(SplashActivity.this, mAuthInfo);
		pd = new ProgressDialog(this);
		pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		pd.setMessage("正在下载...");
		ll_splash_main = (RelativeLayout) this.findViewById(R.id.rl_splash_bg);
		tv_splash_version = (TextView) this
				.findViewById(R.id.tv_splash_version);
		versiontext = getVersion();
		// 让当前的activity延时两秒钟 检查更新
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(2000);
					handler.sendEmptyMessage(0);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		tv_splash_version.setText(versiontext);
		AlphaAnimation aa = new AlphaAnimation(0.0f, 1.0f);
		aa.setDuration(2000);
		ll_splash_main.startAnimation(aa);

		// 动画结束后跳转到StartActivity，关闭LogoActivity
		// aa.setAnimationListener(new AnimationAdapter() {
		// @Override
		// public void onAnimationEnd(Animation animation) {
		// mSsoHandler.authorize(new AuthListener());
		// }
		// });

		// 完成窗体的全屏显示 // 取消掉状态栏
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

	}

	/**
	 * 升级的对话框
	 */
	private void showUpdataDialog() {
		AlertDialog.Builder buider = new Builder(this);
		buider.setIcon(R.drawable.logo);
		buider.setTitle("升级提醒");
		buider.setMessage(info.getDes());
		buider.setCancelable(false); // 让用户不能取消对话框
		buider.setPositiveButton("确定", new OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {

				Log.i(TAG, "下载apk文件" + info.getApkUrl());
				if (Environment.getExternalStorageState().equals(
						Environment.MEDIA_MOUNTED)) {
					DownLoadFileThreadTask task = new DownLoadFileThreadTask(
							info.getApkUrl(), Environment
									.getExternalStorageDirectory().getPath()
									+ "/new.apk");
					pd.show();
					new Thread(task).start();

				} else {
					Toast.makeText(getApplicationContext(), "sd卡不可用",
							Toast.LENGTH_SHORT).show();
					loadLoginPage();
				}

			}
		});
		buider.setNegativeButton("取消", new OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				Log.i(TAG, "用户取消进入程序主界面");
				loadLoginPage();
			}
		});

		buider.create().show();

	}

	private class DownLoadFileThreadTask implements Runnable {
		private String path; // 服务器路径
		private String filepath; // 本地文件路径

		public DownLoadFileThreadTask(String path, String filepath) {
			this.path = path;
			this.filepath = filepath;
		}

		public void run() {
			try {
				File file = DownLoadFileTask.getFile(path, filepath, pd);
				Log.i(TAG, "下载成功");
				pd.dismiss();
				install(file);
			} catch (Exception e) {
				e.printStackTrace();
				Toast.makeText(getApplicationContext(), "下载文件失败",
						Toast.LENGTH_SHORT).show();
				pd.dismiss();
				loadLoginPage();

			}

		}

	}

	/**
	 * 
	 * @param versiontext
	 *            当前客户端的版本号信息
	 * @return 是否需要更新
	 */
	private boolean isNeedUpdate(String versiontext) {
		UpdateInfoService service = new UpdateInfoService(this);
		try {
			info = service.getUpdataInfo(R.string.updateurl);
			String version = info.getVersion();
			if (versiontext.equals(version)) {
				Log.i(TAG, "版本相同,无需升级, 进入主界面");
				loadLoginPage();
				return false;
			} else {
				Log.i(TAG, "版本不同,需要升级");
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Toast.makeText(this, "获取更新信息异常", Toast.LENGTH_SHORT).show();
			Log.i(TAG, "获取更新信息异常, 进入主界面");
			loadLoginPage();
			return false;
		}

	}

	/**
	 * 获取当前应用程序的版本号
	 * 
	 * @return
	 */
	private String getVersion() {
		try {
			PackageManager manager = getPackageManager();
			PackageInfo info = manager.getPackageInfo(getPackageName(), 0);
			return info.versionName;
		} catch (Exception e) {

			e.printStackTrace();
			return "版本号未知";
		}
	}

	/**
	 * 加载登录页面
	 */
	private void loadLoginPage() {
		// 关闭开始界面
		finish();

		// 从 SharedPreferences 中读取上次已保存好 AccessToken 等信息，
		// 第一次启动本应用，AccessToken 不可用
		mAccessToken = AccessTokenKeeper.readAccessToken(this);
		if (mAccessToken.isSessionValid()) {
			loadMainUI();
		} else {
			mSsoHandler.authorize(new AuthListener());
		}

	}

	/**
	 * 加载主页面
	 */
	private void loadMainUI() {
		Intent intent = new Intent(this, FragmentMainActivity.class);
		startActivity(intent);
		// 指定界面切换的动画
		overridePendingTransition(R.anim.tran_enter, R.anim.alpha_exit);
		finish(); // 把当前activity从任务栈里面移除
	}

	/**
	 * 安装apk
	 * 
	 * @param file
	 */
	private void install(File file) {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.fromFile(file),
				"application/vnd.android.package-archive");
		finish();
		startActivity(intent);
	}

	/**
	 * 当 SSO 授权 Activity 退出时，该函数被调用。
	 * 
	 * @see {@link Activity#onActivityResult}
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// SSO 授权回调
		// 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	/**
	 * 微博认证授权回调类。 1. SSO 授权时，需要在 {@link #onActivityResult} 中调用
	 * {@link SsoHandler#authorizeCallBack} 后， 该回调才会被执行。 2. 非 SSO
	 * 授权时，当授权结束后，该回调就会被执行。 当授权成功后，请保存该 access_token、expires_in、uid 等信息到
	 * SharedPreferences 中。
	 */
	class AuthListener implements WeiboAuthListener {
		@Override
		public void onComplete(Bundle values) {
			// 从 Bundle 中解析 Token
			mAccessToken = Oauth2AccessToken.parseAccessToken(values);
			// 从这里获取用户输入的 电话号码信息
			@SuppressWarnings("unused")
			String phoneNum = mAccessToken.getPhoneNum();
			if (mAccessToken.isSessionValid()) {
				// 显示 Token
				// updateTokenView(false);

				// 保存 Token 到 SharedPreferences
				AccessTokenKeeper.writeAccessToken(SplashActivity.this,
						mAccessToken);
				Toast.makeText(SplashActivity.this,
						R.string.weibosdk_demo_toast_auth_success,
						Toast.LENGTH_SHORT).show();
			} else {
				// 以下几种情况，您会收到 Code：
				// 1. 当您未在平台上注册的应用程序的包名与签名时；
				// 2. 当您注册的应用程序包名与签名不正确时；
				// 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
				String code = values.getString("code");
				String message = getString(R.string.weibosdk_demo_toast_auth_failed);
				if (!TextUtils.isEmpty(code)) {
					message = message + "\nObtained the code: " + code;
				}
				Toast.makeText(SplashActivity.this, message, Toast.LENGTH_LONG)
						.show();
			}

			loadMainUI();
		}

		@Override
		public void onCancel() {
			Toast.makeText(SplashActivity.this,
					R.string.weibosdk_demo_toast_auth_canceled,
					Toast.LENGTH_LONG).show();
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(SplashActivity.this,
					"Auth exception : " + e.getMessage(), Toast.LENGTH_LONG)
					.show();
		}
	}

}
