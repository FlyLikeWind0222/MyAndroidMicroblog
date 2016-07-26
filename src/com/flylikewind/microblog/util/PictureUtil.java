package com.flylikewind.microblog.util;

import java.util.HashMap;
import java.util.Map;

import com.flylikewind.microblog.R;

public class PictureUtil {

	private static Map<String, Integer> pictures = new HashMap<String, Integer>();

	private static void init() {
		pictures.put("可爱", R.drawable.bba_org);
		pictures.put("好爱哦", R.drawable.lxh_haoaio);
		pictures.put("赞", R.drawable.face329);
		pictures.put("good", R.drawable.face100);
		pictures.put("阴险", R.drawable.face105);
		pictures.put("doge", R.drawable.zhh_org);
		pictures.put("挤眼", R.drawable.face290);
		pictures.put("心", R.drawable.hearta_org);
		pictures.put("亲亲", R.drawable.qq_org);
		pictures.put("哈哈", R.drawable.laugh);
		pictures.put("泪", R.drawable.sada_org);
		pictures.put("睡", R.drawable.sleepa_org);
		pictures.put("作揖", R.drawable.lxh_qiuguanzhu);
		pictures.put("笑cry", R.drawable.lxh_xiaohaha);
		pictures.put("鲜花", R.drawable.lxh_meigui);
		pictures.put("爱你", R.drawable.lovea_org);
		pictures.put("偷笑", R.drawable.heia_org);
	}

	public static int getPic(String key) {
		if (pictures.size() <= 0) {
			init();
		}
		if (pictures.get(key) == null) {
			return 0;
		} else {
			return pictures.get(key);
		}
	}
}
