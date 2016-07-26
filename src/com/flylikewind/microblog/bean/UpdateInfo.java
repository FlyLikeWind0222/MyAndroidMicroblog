package com.flylikewind.microblog.bean;

public class UpdateInfo {

	private String version;
	private String des;
	private String apkUrl;

	public String getVersion() {
		return version;
	}

	public void setVersion(String versiion) {
		this.version = versiion;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

	public String getApkUrl() {
		return apkUrl;
	}

	public void setApkUrl(String apkUrl) {
		this.apkUrl = apkUrl;
	}

	@Override
	public String toString() {
		return "UpdataBean [versiion=" + version + ", des=" + des
				+ ", apkUrl=" + apkUrl + "]";
	}
}
