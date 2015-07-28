package com.eebbk.im.request;

import com.eebbk.im.Command;


public class RegistRequest extends Request {

	public final int COMMAND = Command.REGIST_REQUEST;
	
	public static class TagValue {
		public static int RID = 0;
		public static int platform; //10
		public static int appKey; //11 
		public static int pkgName; //12
		public static int deviceId; //13
		public static int sdkVerison; //14
		public static int sysName; //15
		public static int sysVersion; //16
		public static int imei; //17
		public static int imsi; //18
		public static int mac; //19
		public static int modelNumber; //20
		public static int basebandVersion; //21
		public static int buildNumber; //22
		public static int resolution; //23
	}
	
	static {
		increaseFieldValue(TagValue.class, 10);
	}
	
	private int RID;
	
	private int platform; // 平台类型
	
	private String appKey; // app标识
	
	private String pkgName; // app包名
	
	private String deviceId; // 设备唯一识别码
	
	private String sdkVerison; // 系统sdk版本号
	
	private String sysName; // 系统名
	
	private String sysVersion; // 系统版本
	
	private String imei; // 移动设备国际身份码
	
	private String imsi; // 国际移动用户识别码
	
	private String mac; // 硬件地址
	
	private String modelNumber; // 固件版本号
	
	private String basebandVersion; // 基带版本号
	
	private String buildNumber; // 编译号
	
	private String resolution; // 分辨率
	
	public int getRID() {
		return RID;
	}

	public void setRID(int rID) {
		RID = rID;
	}

	public int getPlatform() {
		return platform;
	}

	public void setPlatform(int platform) {
		this.platform = platform;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getPkgName() {
		return pkgName;
	}

	public void setPkgName(String pkgName) {
		this.pkgName = pkgName;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getSdkVerison() {
		return sdkVerison;
	}

	public void setSdkVerison(String sdkVerison) {
		this.sdkVerison = sdkVerison;
	}

	public String getSysName() {
		return sysName;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public String getSysVersion() {
		return sysVersion;
	}

	public void setSysVersion(String sysVersion) {
		this.sysVersion = sysVersion;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getImsi() {
		return imsi;
	}

	public void setImsi(String imsi) {
		this.imsi = imsi;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getModelNumber() {
		return modelNumber;
	}

	public void setModelNumber(String modelNumber) {
		this.modelNumber = modelNumber;
	}

	public String getBasebandVersion() {
		return basebandVersion;
	}

	public void setBasebandVersion(String basebandVersion) {
		this.basebandVersion = basebandVersion;
	}

	public String getBuildNumber() {
		return buildNumber;
	}

	public void setBuildNumber(String buildNumber) {
		this.buildNumber = buildNumber;
	}

	public String getResolution() {
		return resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public static void main(String[] args) {
		new RegistRequest();
	}

}
