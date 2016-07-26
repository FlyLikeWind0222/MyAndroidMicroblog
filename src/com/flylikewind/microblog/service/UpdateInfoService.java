package com.flylikewind.microblog.service;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.util.Xml;

import com.flylikewind.microblog.bean.UpdateInfo;
import com.flylikewind.microblog.util.UpdateInfoParser;

public class UpdateInfoService {

	private Context context;

	public UpdateInfoService(Context context) {
		this.context = context;
	}

	/**
	 * 
	 * @param urlid
	 *            服务器路径string对应的id
	 * @return 更新的信息
	 */
	public UpdateInfo getUpdataInfo(int urlid) throws Exception {
		String path = context.getResources().getString(urlid);
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(2000);
		conn.setRequestMethod("GET");
		InputStream is = conn.getInputStream();
		return UpdateInfoParser.getUpdataInfo(is);
	}

	/**
	 * 获取服务器上的最新的版本信息
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public UpdateInfo getUpdateInfo(String path) throws Exception {
		URL url = new URL(path);
		HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setConnectTimeout(5000);
		conn.setRequestMethod("GET");
		if (conn.getResponseCode() == 200) {
			InputStream is = conn.getInputStream();
			return parserUpdateInfo(is);
		}
		return null;
	}

	/**
	 * 解析服务器的流信息为UpdateInfo对象
	 * 
	 * @param is
	 * @return
	 * @throws Exception
	 */
	private UpdateInfo parserUpdateInfo(InputStream is) throws Exception {
		UpdateInfo updateInfo = null;
		XmlPullParser parser = Xml.newPullParser();
		parser.setInput(is, "UTF-8");
		int eventType = parser.getEventType();
		while (eventType != XmlPullParser.END_DOCUMENT) {
			switch (eventType) {
			case XmlPullParser.START_TAG:
				if ("updateinfo".equals(parser.getName())) {
					updateInfo = new UpdateInfo();
				} else if ("version".equals(parser.getName())) {
					String version = parser.nextText();
					updateInfo.setVersion(version);
				} else if ("url".equals(parser.getName())) {
					String url = parser.nextText();
					updateInfo.setApkUrl(url);
				} else if ("description".equals(parser.getName())) {
					String description = parser.nextText();
					updateInfo.setDes(description);
				}
				break;

			default:
				break;
			}
			eventType = parser.next();
		}
		return updateInfo;
	}
}
