package com.flylikewind.microblog.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

public class MyTextUtils {

	/**
	 * 格式化时间
	 * 
	 * @param time
	 *            Tue May 31 17:46:55 +0800 2011
	 * @return 刚刚/--分钟前/--小时前/--天前
	 */
	public static String formatTime(String time) {
		try {
			// Tue May 31 17:46:55 +0800 2011
			// "EEE MMM dd HH:mm:ss zzz yyyy"
			SimpleDateFormat sdf = new SimpleDateFormat(
					"EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
			Date date = sdf.parse(time);
			return converTime(date.getTime() / 1000);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * 时间格式处理
	 * 
	 * @param timestamp
	 *            时间转化成秒的数值
	 * @return 刚刚/--分钟前/--小时前/--天前
	 */
	public static String converTime(long timestamp) {
		long currentSeconds = System.currentTimeMillis() / 1000;
		long timeGap = currentSeconds - timestamp;// 与现在时间相差秒数
		String timeStr = null;
		if (timeGap > 24 * 60 * 60) {// 1天以上
			timeStr = timeGap / (24 * 60 * 60) + "天前";
		} else if (timeGap > 60 * 60) {// 1小时-24小时
			timeStr = timeGap / (60 * 60) + "小时前";
		} else if (timeGap > 60) {// 1分钟-59分钟
			timeStr = timeGap / 60 + "分钟前";
		} else {// 1秒钟-59秒钟
			timeStr = "刚刚";
		}
		return timeStr;
	}

	/**
	 * 获取超链接字符串里的内容
	 * 
	 * @param weiboSource
	 * @return
	 */
	public static String getFromText(String weiboSource) {
		if (weiboSource != null && weiboSource.length() > 0) {
			int start = weiboSource.indexOf(">");
			int end = weiboSource.lastIndexOf("<");
			weiboSource = weiboSource.substring(start + 1, end);
			return "来自 " + weiboSource;
		}
		return "";
	}

	/**
	 * 微博列表评论、转发、赞的TextView文字设置
	 * 
	 * @param view
	 * @param num
	 */
	public static void setText(TextView view, int num) {
		if (num != 0) {
			view.setText(num + "");
		}
	}

	public static SpannableString signContent(Context context, String text) {
		/**
		 * 在Android里提供了许多个性化TextView内容的工具类， 使用这些类可以代替常规String。
		 * android.text.Spanned android.text.SpannableString
		 * android.text.SpannableStringBuilder
		 * 
		 * 由于Spannable等类最终都实现了CharSequence接口，
		 * 所以可以直接把SpannableString和SpannableStringBuilder通过TextView
		 * .setText()设置给TextView。
		 */

		/*
		 * 实例化一个Spannable对象
		 */
		SpannableString spannableString = new SpannableString(text);
		// 通过setSpan()方法可以用来定义不同的样式内容
		// 设置字体
		/*
		 * 配合String工具类定位
		 */
		// 判断文本中是否有两个“#”
		signTxet(spannableString, text);
		// 判断文本中是否有“@”
		signAtText(spannableString, text);
		/*
		 * 利用正则表达式匹配定位
		 */
		Map<String, Integer> map = getHttpPostion(text);
		if (map != null && map.size() > 0) {
			spannableString.setSpan(new ForegroundColorSpan(Color.BLUE),
					map.get("start"), map.get("end"),
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

			// 设置字体大小
			spannableString.setSpan(new RelativeSizeSpan((float) 1.1),
					map.get("start"), map.get("end"),
					Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		}
		// 标记文字中表情图片位置
		signPicture(context, spannableString, text);

		return spannableString;
	}

	/**
	 * 获取文字中地址的位置
	 * 
	 * @param text
	 * @return
	 */
	private static Map<String, Integer> getHttpPostion(String text) {
		Map<String, Integer> map = new HashMap<String, Integer>();
		// Pattern pattern = Pattern.compile("http:.*");
		// http://t.cn/Rtw2bKR
		Pattern pattern = Pattern.compile("^(http://|ftp://|https://|www){0,1}"
				+ "[^\u4e00-\u9fa5\\s]*?\\.(com|net|cn|me|tw|fr)"
				+ "[^\u4e00-\u9fa5\\s]*$");
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			map.put("start", matcher.start());
			map.put("end", matcher.end());
		} else {
			pattern = Pattern.compile("(http://t.cn/){0,1}[a-zA-Z\\d]+");
			matcher = pattern.matcher(text);
			if (matcher.find()) {
				map.put("start", matcher.start());
				map.put("end", matcher.end());
			}
		}
		return map;
	}

	/**
	 * 标记需要替换成图片的文本
	 * 
	 * @param context
	 * @param spannableString
	 * @param text
	 * @return
	 */
	private static void signPicture(Context context,
			SpannableString spannableString, String text) {
		int picStart = text.indexOf("[");
		int picEnd = text.indexOf("]");
		int newPicStart = picStart;
		int newPicEnd = picEnd;
		String newText = text;
		while (newPicStart != -1 && newPicEnd != -1) {
			// 图片替换相应的文字
			int picId = PictureUtil.getPic(newText.substring(newPicStart + 1,
					newPicEnd));
			if (picId != 0) {
				Drawable drawable = context.getResources().getDrawable(picId);
				drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
						drawable.getIntrinsicHeight());
				spannableString.setSpan(new ImageSpan(drawable), picStart,
						picEnd + 1, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
			} else {
				spannableString.setSpan(new ForegroundColorSpan(Color.BLUE),
						picStart, picEnd + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}

			// 截取还未进行图片化的文字，循环处理
			newText = newText.substring(newPicEnd + 1);
			newPicStart = newText.indexOf("[");
			newPicEnd = newText.indexOf("]");
			picStart += newPicStart + 1;
			picEnd += newPicEnd + 1;
		}
	}

	private static void signAtText(SpannableString spannableString, String text) {
		int picStart = text.indexOf("@");
		int picEnd = text.indexOf(" ");
		int newPicStart = picStart;
		int newPicEnd = picEnd;
		String newText = text;
		while (newPicStart != -1) {
			// 19 25 30 34 38
			// 强力推荐！！全程无尿点[阴险][赞]@文章同學 @包贝尔 @宋佳 @焦俊艳@朱亚文
			if (picEnd > picStart) {
				spannableString.setSpan(new ForegroundColorSpan(Color.BLUE),
						picStart, picEnd, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
			}

			// 截取还未进行图片化的文字，循环处理
			if (newPicEnd < newText.length()) {
				newText = newText.substring(newPicEnd + 1);
				newPicStart = newText.indexOf("@");
				newPicEnd = newText.indexOf(" ");
				picStart = picEnd + newPicStart + 1;
				if (newPicEnd == -1) {
					newPicEnd = newText.indexOf(":");
					if (newPicEnd == -1) {
						newPicEnd = newText.length() - 1;
						picEnd = text.length();
					}
				} else {
					picEnd += newPicEnd + 1;
				}
			}
		}
	}

	/**
	 * 标记需要变色的文本##
	 * 
	 * @param context
	 * @param spannableString
	 * @param text
	 */
	private static void signTxet(SpannableString spannableString, String text) {
		int txtStart = text.indexOf("#");
		int txtEnd = txtStart + text.substring(txtStart + 1).indexOf("#");
		int newTxtStart = txtStart;
		int newTxtEnd = txtEnd;
		String newText = text;
		while (newTxtStart != -1 && newTxtEnd != -1) {
			spannableString.setSpan(new ForegroundColorSpan(Color.BLUE),
					txtStart, txtEnd + 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

			// 截取还未进行图片化的文字，循环处理
			if (newTxtEnd < newText.length() - 1) {
				newText = newText.substring(newTxtEnd + 2);
				newTxtStart = newText.indexOf("#");
				newTxtEnd = newTxtStart
						+ newText.substring(newTxtStart + 1).indexOf("#");
				txtStart += newTxtStart + 1;
				txtEnd += newTxtEnd + 2;
			}
		}
	}
}
