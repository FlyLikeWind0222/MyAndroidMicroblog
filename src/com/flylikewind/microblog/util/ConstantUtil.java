package com.flylikewind.microblog.util;

/**
 * 常量工具
 * 
 * @author zjt
 * 
 * @date 2016-1-10上午9:42:18
 */
public interface ConstantUtil {

	// 是否第一次启动
	String IS_FIRST_LAUNCH = "IS_FIRST_LAUNCH";

	// 是否第一次注册
	String IS_FIRST_REGISETER = "is_first_register";

	// sharedpreferences filename
	String CONFIG = "config";

	/*
	 * 登录用户名和密码,通过其判断是否登录
	 */
	String LOGIN_USERNAME = "LOGIN_USERNAME";
	String LOGIN_PASSWORD = "LOGIN_PASSWORD";
	String REMEBER_USERNAME = "REMEBER_USERNAME";

	/*
	 * 验证手机号码界面类型
	 */
	Integer VALIDATE_PHONE_TYPE_FIND_PWD = 0; // 找回密码
	Integer VALIDATE_PHONE_TYPE_REGISTER = 1; // 注册

	/*
	 * 用户账号类型
	 */
	String USER_TYPE = "user_type";
	String USER_TYPE_JOBSEEKER = "jobseeker"; // 求职者
	String USER_TYPE_HR_ADMIN = "hr-admin"; // hr管理
	String USER_TYPE_HR_NORMAL = "hr-nomal"; // hr子账号

	/*
	 * 会话相关
	 */
	String JSESSIONID = "JSESSIONID"; // sessionId
	String CURRENT_USER_ID = "CURRENT_USER_ID"; // 当前登录用户userId
	String ERROR_CODE_LOGIN_TIMEOUT = "01A0088888"; // 登录超时错误码

	/* 极光推送相关 * */
	String JPUSH_OPEN = "jpush_open"; // 极光推送是否开启

	/*
	 * 静态字典类型
	 */
	String DICT_TYPE_STANDARD = "standard"; // 制式
	String DICT_TYPE_VENDOR = "vendor"; // 厂家
	String DICT_TYPE_POSITIONPROPERTY = "positionProperty"; // 职位性质
	String DICT_TYPE_COMPANYPROPERTY = "companyProperty"; // 公司性质
	String DICT_TYPE_MONTHLYSALARY = "monthlySalary"; // 月薪
	String DICT_TYPE_POSITIONLABEL = "positionLabel"; // 职位卖点
	String DICT_TYPE_MARKETTYPE = "marketType"; // 集市分类
	String DICT_TYPE_EDUCATION = "educationBackground"; // 学历
	String DICT_TYPE_WORKYEAR = "workYear"; // 工作年限
	String DICT_TYPE_WORKSTATUS = "workStatus"; // 工作状态s
	String DICT_TYPE_IDTYPE = "idType"; // 用户身份
	String DICT_TYPE_SEX = "sex"; // 性别
	String DICT_TYPE_MESSAGE_CLASSIFY = "messageClassify"; // 消息类别
	String DICT_TYPE_COMPANY_SCALE = "companyScale"; // 公司规模

	/*
	 * 选择搜索条件类型 - 回调
	 */
	String CHOOSE_SEARCH_TYPE_CITY = "CHOOSE_SEARCH_TYPE_CITY"; // 地区
	String CHOOSE_SEARCH_TYPE_CITY_TWO = "CHOOSE_SEARCH_TYPE_CITY_TWO"; // 地区
	String CHOOSE_SEARCH_TYPE_QUARTER = "CHOOSE_SEARCH_TYPE_QUARTER"; // 职能
	String CHOOSE_SEARCH_TYPE_DIPLOMA = "CHOOSE_SEARCH_TYPE_DIPLOMA"; // 证书
	String CHOOSE_SEARCH_TYPE_REALEASE_TIME = "CHOOSE_SEARCH_TYPE_REALEASE_TIME"; // 发布时间
	String CHOOSE_SEARCH_TYPE_ACCEPT_GRADUATE = "CHOOSE_SEARCH_TYPE_ACCEPT_GRADUATE"; // 是否接受应届生
	String CHOOSE_SEARCH_TYPE_VALID_TIME = "CHOOSE_SEARCH_TYPE_VALID_TIME"; // 有效时间
	String SEARCH_RESULT_RESULT_REINPUT = "SEARCH_RESULT_RESULT_REINPUT"; // 在搜索结果界面,点击
	String CHOOSE_COMMON_DIPLOMA = "CHOOSE_COMMON_DIPLOMA"; // 普通证书

	/*
	 * 求职者 （区分简历被阅读还是被收藏）
	 */
	String JS_ME_RESUME_BEREAD = "JS_ME_RESUME_BEREAD";
	String JS_ME_RESUME_BECOLLECT = "JS_ME_RESUME_BECOLLECT";

	/*
	 * 求职者 （应聘记录or收藏职位）
	 */
	String JS_ME_POSITION_APPLY = "JS_ME_POSITION_APPLY";
	String JS_ME_POSITION_COLLECT = "JS_ME_POSITION_COLLECT";

	// 扫描二维码
	String SCAN_QRCODE = "scanQrcode";

	// 简历完成程度
	String PERFECTDEGREE = "PERFECTDEGREE";

}
