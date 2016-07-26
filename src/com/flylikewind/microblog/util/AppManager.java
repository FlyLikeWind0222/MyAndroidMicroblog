package com.flylikewind.microblog.util;

import android.content.SharedPreferences;
import android.text.TextUtils;

import com.flylikewind.microblog.CustomApplication;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;
import com.sina.weibo.sdk.openapi.models.User;

/**
 * 提供一些基础的操作,主要是应用所需的一些基础数据
 * 
 * @author zjt
 * 
 * @date 2016-1-11上午9:43:48
 */
public class AppManager {

	// 单例
	private static AppManager instance = new AppManager();

	private AppManager() {
	}

	public static AppManager getInstance() {
		return instance;
	}

	/*
	 * 应用所需基础数据（登录成功后,从服务器获取）
	 */
	// private static List<Quarter> quarterCache = null; // 职能
	// private static List<NormalDiploma> normalDiplomaCache = null; // 证书
	// private static List<SepicalDiploma> sepicalDiplomaCache = null; // 专业 证书
	// private static ConstantCache constantCache = null; // 静态字典数据

	/*
	 * 是否第一次使用app
	 */
	public boolean isFirstLaunch() {
		return TextUtils.isEmpty(CustomApplication.getSpUtil().getString(
				ConstantUtil.IS_FIRST_LAUNCH, ""));
	}

	//
	/*
	 * 用户是否登录
	 */
	public boolean isLogin() {
		String loginUsername = CustomApplication.getSpUtil().getString(
				ConstantUtil.LOGIN_USERNAME, "");
		String loginPassword = CustomApplication.getSpUtil().getString(
				ConstantUtil.LOGIN_PASSWORD, "");
		return (!TextUtils.isEmpty(loginUsername) && !TextUtils
				.isEmpty(loginPassword));
	}

	/*
	 * 用户是否第一次注册
	 */
	public boolean isFirstRegister() {
		return TextUtils.isEmpty(CustomApplication.getSpUtil().getString(
				ConstantUtil.IS_FIRST_REGISETER, ""));
	}

	/*
	 * 登录成功后的sessionId，存储在客户端cookie中； 所有请求都需加上此令牌,如果与登录令牌不一致,说明会话失效,需重新登录
	 */
	public String getSessionId() {
		return CustomApplication.getSpUtil().getString(ConstantUtil.JSESSIONID,
				"");
	}

	/*
	 * 登录成功后将数据保存到本地sqlite（职能信息、静态字典、证书、用户信息等...）
	 */
	// public void saveLoginInfoToDb(QuarterCacheResult quarterCacheResult,
	// User user) {
	//
	// try {
	//
	// clearLoginInfoFromDb(); // 先清除之前的数据,避免重复
	// DbUtils dbUtil = CustomApplication.getDbUtils();
	//
	// if (user != null) {
	// user.setId(UUID.randomUUID().toString());
	// dbUtil.save(user);
	// }
	//
	// if (user.getPreferenceList() != null) {
	// user.getPreferenceList().setUserId(user.getId());
	// dbUtil.save(user.getPreferenceList());
	// }
	//
	// if (quarterCacheResult != null) {
	//
	// // 保存职能
	// if (quarterCacheResult.getQuarterCache() != null
	// && quarterCacheResult.getQuarterCache().size() > 0) {
	// for (Quarter quarter : quarterCacheResult.getQuarterCache()) {
	//
	// // 1,先保存职能
	// quarter.setId(UUID.randomUUID().toString());
	// dbUtil.save(quarter);
	//
	// // 2,再保存职能子类别,需要关联parentId
	// for (QuarterSubType subType : quarter.getSubType()) {
	// subType.setParentId(quarter.getId());
	// subType.setpId(UUID.randomUUID().toString());
	// }
	//
	// dbUtil.saveAll(quarter.getSubType());
	// }
	// }
	//
	// // 保存通用证书 - 层级关系,需保存parentId
	// if (quarterCacheResult.getNormalDiplomaCache() != null
	// && quarterCacheResult.getNormalDiplomaCache().size() > 0) {
	// for (NormalDiploma diploma : quarterCacheResult
	// .getNormalDiplomaCache()) {
	//
	// // 1,先保存证书
	// diploma.setId(UUID.randomUUID().toString());
	// dbUtil.save(diploma);
	//
	// // 2,保存证书子类别,需关联parentId
	// for (NormalDiplomaSubType subType : diploma
	// .getSubType()) {
	// subType.setParentId(diploma.getId());
	// }
	// dbUtil.saveAll(diploma.getSubType());
	//
	// }
	// }
	//
	// // 保存专用证书 - 层级关系,需保存parentId
	// if (quarterCacheResult.getProDiplomaCache() != null
	// && quarterCacheResult.getProDiplomaCache().size() > 0) {
	//
	// for (SepicalDiploma diploma : quarterCacheResult
	// .getProDiplomaCache()) {
	//
	// // 1,先保存证书
	// diploma.setId(UUID.randomUUID().toString());
	// dbUtil.save(diploma);
	//
	// // 2,保存证书子类别,需关联parentId
	// for (SepicalDiplomaSubtype subType : diploma
	// .getSubType()) {
	// subType.setParentId(diploma.getId());
	// }
	// dbUtil.saveAll(diploma.getSubType());
	//
	// }
	//
	// }
	//
	// // 保存基础数据,主要是一些配置类的静态字典
	// dbUtil.saveAll(quarterCacheResult.getConstantCache()
	// .getStandard());
	// dbUtil.saveAll(quarterCacheResult.getConstantCache()
	// .getVendor());
	// dbUtil.saveAll(quarterCacheResult.getConstantCache()
	// .getPositionProperty());
	// dbUtil.saveAll(quarterCacheResult.getConstantCache()
	// .getCompanyProperty());
	// dbUtil.saveAll(quarterCacheResult.getConstantCache()
	// .getMonthlySalary());
	// dbUtil.saveAll(quarterCacheResult.getConstantCache()
	// .getPositionLabel());
	// dbUtil.saveAll(quarterCacheResult.getConstantCache()
	// .getMarketType());
	// dbUtil.saveAll(quarterCacheResult.getConstantCache()
	// .getEducationBackground());
	// dbUtil.saveAll(quarterCacheResult.getConstantCache()
	// .getWorkYear());
	// dbUtil.saveAll(quarterCacheResult.getConstantCache()
	// .getWorkStatus());
	// dbUtil.saveAll(quarterCacheResult.getConstantCache()
	// .getIdType());
	// dbUtil.saveAll(quarterCacheResult.getConstantCache().getSex());
	// dbUtil.saveAll(quarterCacheResult.getConstantCache()
	// .getMessageClassify());
	// dbUtil.saveAll(quarterCacheResult.getConstantCache()
	// .getCompanyScale());
	//
	// // 通过全局变量保存,避免每次都查询数据库
	// quarterCache = quarterCacheResult.getQuarterCache(); // 职能
	// normalDiplomaCache = quarterCacheResult.getNormalDiplomaCache(); // 证书
	// constantCache = quarterCacheResult.getConstantCache(); // 常量缓存（即静态字典数据）
	// sepicalDiplomaCache = quarterCacheResult.getProDiplomaCache();
	//
	// }
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// }

	/*
	 * 清除登录后保存的信息（职能信息、静态字典、证书、用户信息等...）
	 */
	// public void clearLoginInfoFromDb() {
	//
	// try {
	//
	// DbUtils dbUtil = CustomApplication.getDbUtils();
	//
	// // 用户相关
	// dbUtil.deleteAll(User.class);
	// dbUtil.deleteAll(Preference.class);
	//
	// // 清除职能
	// dbUtil.deleteAll(Quarter.class);
	// dbUtil.deleteAll(QuarterSubType.class);
	//
	// // 清除证书
	// dbUtil.deleteAll(NormalDiploma.class);
	// dbUtil.deleteAll(NormalDiplomaSubType.class);
	//
	// // 清除静态字典
	// dbUtil.deleteAll(StaticDictionary.class);
	//
	// // 删除专用证书
	// dbUtil.deleteAll(SepicalDiploma.class);
	// dbUtil.deleteAll(SepicalDiplomaSubtype.class);
	//
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	//
	// CustomApplication.getSpUtil().edit()
	// .putString(ConstantUtil.LOGIN_PASSWORD, "").commit();
	// }

	/*
	 * 得到当前登录用户
	 */
	public User getCurrentUser() {

		DbUtils dbUtils = CustomApplication.getDbUtils();
		SharedPreferences spUtil = CustomApplication.getSpUtil();

		User currentUser = null;

		try {
			currentUser = dbUtils
					.findFirst(Selector.from(User.class).where(
							"mUserId",
							"=",
							spUtil.getString(ConstantUtil.CURRENT_USER_ID,
									spUtil.getString(
											ConstantUtil.CURRENT_USER_ID, ""))));
		} catch (DbException e) {
			e.printStackTrace();
		}

		if (currentUser == null) {
			currentUser = new User();
		}

		return currentUser;

	}

	/*
	 * 获取职能
	 */
	// public List<Quarter> getQuarterCache() {
	//
	// // 不必每次都查询数据库
	// if (quarterCache != null) {
	// return quarterCache;
	// }
	//
	// DbUtils dbUtils = CustomApplication.getDbUtils();
	// try {
	// quarterCache = dbUtils.findAll(Quarter.class);
	// } catch (DbException e) {
	// e.printStackTrace();
	// }
	// return quarterCache;
	//
	// }

	/*
	 * 获取证书
	 */
	// public List<NormalDiploma> getNormalDiplomaCache() {
	//
	// // 不必每次都查询数据库
	// if (normalDiplomaCache != null) {
	// return normalDiplomaCache;
	// }
	//
	// DbUtils dbUtils = CustomApplication.getDbUtils();
	// try {
	// normalDiplomaCache = dbUtils.findAll(NormalDiploma.class);
	// } catch (DbException e) {
	// e.printStackTrace();
	// }
	//
	// return normalDiplomaCache;
	//
	// }

	/*
	 * 获取专业
	 */
	// public List<SepicalDiploma> getSepicalDiplomaCache() {
	//
	// // 不必每次都查询数据库
	// if (sepicalDiplomaCache != null) {
	// return sepicalDiplomaCache;
	// }
	//
	// DbUtils dbUtils = CustomApplication.getDbUtils();
	// try {
	// sepicalDiplomaCache = dbUtils.findAll(SepicalDiploma.class);
	// } catch (DbException e) {
	// e.printStackTrace();
	// }
	//
	// return sepicalDiplomaCache;
	//
	// }

	/*
	 * 获取常量缓存数据（即数据字典）
	 */
	// public ConstantCache getConstsantCahce() {
	//
	// // 不必每次都查询数据库
	// if (constantCache != null) {
	// return constantCache;
	// }
	//
	// constantCache = new ConstantCache();
	// DbUtils dbUtils = CustomApplication.getDbUtils();
	// try {
	//
	// List<StaticDictionary> data = dbUtils.findAll(Selector.from(
	// StaticDictionary.class).where("typeKey", "=",
	// ConstantUtil.DICT_TYPE_STANDARD));
	// constantCache.setStandard(data);
	//
	// data = dbUtils.findAll(Selector.from(StaticDictionary.class).where(
	// "typeKey", "=", ConstantUtil.DICT_TYPE_VENDOR));
	// constantCache.setVendor(data);
	//
	// data = dbUtils.findAll(Selector.from(StaticDictionary.class).where(
	// "typeKey", "=", ConstantUtil.DICT_TYPE_POSITIONPROPERTY));
	// constantCache.setPositionProperty(data);
	//
	// data = dbUtils.findAll(Selector.from(StaticDictionary.class).where(
	// "typeKey", "=", ConstantUtil.DICT_TYPE_COMPANYPROPERTY));
	// constantCache.setCompanyProperty(data);
	//
	// data = dbUtils.findAll(Selector.from(StaticDictionary.class).where(
	// "typeKey", "=", ConstantUtil.DICT_TYPE_MONTHLYSALARY));
	// constantCache.setMonthlySalary(data);
	//
	// data = dbUtils.findAll(Selector.from(StaticDictionary.class).where(
	// "typeKey", "=", ConstantUtil.DICT_TYPE_POSITIONLABEL));
	// constantCache.setPositionLabel(data);
	//
	// data = dbUtils.findAll(Selector.from(StaticDictionary.class).where(
	// "typeKey", "=", ConstantUtil.DICT_TYPE_MARKETTYPE));
	// constantCache.setMarketType(data);
	//
	// data = dbUtils.findAll(Selector.from(StaticDictionary.class).where(
	// "typeKey", "=", ConstantUtil.DICT_TYPE_EDUCATION));
	// constantCache.setEducationBackground(data);
	//
	// data = dbUtils.findAll(Selector.from(StaticDictionary.class).where(
	// "typeKey", "=", ConstantUtil.DICT_TYPE_WORKYEAR));
	// constantCache.setWorkYear(data);
	//
	// data = dbUtils.findAll(Selector.from(StaticDictionary.class).where(
	// "typeKey", "=", ConstantUtil.DICT_TYPE_WORKSTATUS));
	// constantCache.setWorkStatus(data);
	//
	// data = dbUtils.findAll(Selector.from(StaticDictionary.class).where(
	// "typeKey", "=", ConstantUtil.DICT_TYPE_IDTYPE));
	// constantCache.setIdType(data);
	//
	// data = dbUtils.findAll(Selector.from(StaticDictionary.class).where(
	// "typeKey", "=", ConstantUtil.DICT_TYPE_SEX));
	// constantCache.setSex(data);
	//
	// data = dbUtils.findAll(Selector.from(StaticDictionary.class).where(
	// "typeKey", "=", ConstantUtil.DICT_TYPE_MESSAGE_CLASSIFY));
	// constantCache.setMessageClassify(data);
	//
	// data = dbUtils.findAll(Selector.from(StaticDictionary.class).where(
	// "typeKey", "=", ConstantUtil.DICT_TYPE_COMPANY_SCALE));
	// constantCache.setCompanyScale(data);
	//
	// } catch (DbException e) {
	// e.printStackTrace();
	// }
	//
	// return constantCache;
	//
	// }

	/**
	 * 获取静态字典的value，主要用于控件显示
	 * 
	 * @param dictionaryType
	 *            ：字典类型
	 * @param key
	 *            ：传入key
	 * @return
	 */
	// public String getStaticDictionaryValue(String dictionaryType, String key)
	// {
	//
	// if (!TextUtils.isEmpty(key)) {
	//
	// List<StaticDictionary> list = null;
	//
	// ConstantCache constantCache = getConstsantCahce();
	// if (constantCache != null) {
	//
	// if (ConstantUtil.DICT_TYPE_STANDARD
	// .equalsIgnoreCase(dictionaryType)) {
	// list = constantCache.getStandard();
	// } else if (ConstantUtil.DICT_TYPE_VENDOR
	// .equalsIgnoreCase(dictionaryType)) {
	// list = constantCache.getVendor();
	// } else if (ConstantUtil.DICT_TYPE_POSITIONPROPERTY
	// .equalsIgnoreCase(dictionaryType)) {
	// list = constantCache.getPositionProperty();
	// } else if (ConstantUtil.DICT_TYPE_COMPANYPROPERTY
	// .equalsIgnoreCase(dictionaryType)) {
	// list = constantCache.getCompanyProperty();
	// } else if (ConstantUtil.DICT_TYPE_MONTHLYSALARY
	// .equalsIgnoreCase(dictionaryType)) {
	// list = constantCache.getMonthlySalary();
	// } else if (ConstantUtil.DICT_TYPE_POSITIONLABEL
	// .equalsIgnoreCase(dictionaryType)) {
	// list = constantCache.getPositionLabel();
	// } else if (ConstantUtil.DICT_TYPE_MARKETTYPE
	// .equalsIgnoreCase(dictionaryType)) {
	// list = constantCache.getMarketType();
	// } else if (ConstantUtil.DICT_TYPE_EDUCATION
	// .equalsIgnoreCase(dictionaryType)) {
	// list = constantCache.getEducationBackground();
	// } else if (ConstantUtil.DICT_TYPE_WORKYEAR
	// .equalsIgnoreCase(dictionaryType)) {
	// list = constantCache.getWorkYear();
	// } else if (ConstantUtil.DICT_TYPE_WORKSTATUS
	// .equalsIgnoreCase(dictionaryType)) {
	// list = constantCache.getWorkStatus();
	// } else if (ConstantUtil.DICT_TYPE_IDTYPE
	// .equalsIgnoreCase(dictionaryType)) {
	// list = constantCache.getIdType();
	// } else if (ConstantUtil.DICT_TYPE_SEX
	// .equalsIgnoreCase(dictionaryType)) {
	// list = constantCache.getSex();
	// } else if (ConstantUtil.DICT_TYPE_MESSAGE_CLASSIFY
	// .equalsIgnoreCase(dictionaryType)) {
	// list = constantCache.getMessageClassify();
	// } else if (ConstantUtil.DICT_TYPE_COMPANY_SCALE
	// .equalsIgnoreCase(dictionaryType)) {
	// list = constantCache.getCompanyScale();
	// }
	//
	// }
	//
	// if (list != null && list.size() > 0) {
	//
	// for (StaticDictionary dict : list) {
	//
	// if (key.equalsIgnoreCase(dict.getDicKey())) {
	// return dict.getDicValue();
	// }
	//
	// }
	//
	// }
	//
	// }
	//
	// return "";
	// }

}
