package com.eebbk.im.response;

import com.eebbk.im.Command;

public class RegistResponse extends Response {
	
	public final int COMMAND = Command.REGIST_RESPONSE; 

	public static class TagValue {
		public static int RID = 0;
		public static int responseCode;
		public static int registId;
		public static int password;
	}
	
	static {
		increaseFieldValue(TagValue.class, 10);
	}
	
	private int RID;
	
	private int responseCode;
	
	private int registId;
	
	private String password;

	public int getRID() {
		return RID;
	}

	public void setRID(int rID) {
		RID = rID;
	}

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public int getRegistId() {
		return registId;
	}

	public void setRegistId(int registId) {
		this.registId = registId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
