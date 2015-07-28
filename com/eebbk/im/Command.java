package com.eebbk.im;

import java.lang.reflect.Field;

public class Command {

	public static int REGIST_REQUEST = 0; //注册
	public static int REGIST_RESPONSE = 1; //注册响应
	
	public static int LOGIN_REQUEST = 2; //登录
	public static int LOGIN_RESPONSE = 3; //登录响应
	
	public static int HEART_BEAT_REQUEST = 4; //心跳
	public static int HEART_BEAT_RESPONSE = 5; //心跳响应
	
	public static int MESSAGE_REQUEST = 6; //同步消息
	public static int MESSAGE_RESPONSE = 7; //同步消息响应
	
	public static int SYNCINFORM = 8; // 同步通知
	
	public static int SYNC_REQUEST = 9; //同步请求
	public static int SYNC_RESPONSE = 10; //同步请求响应
	
	public static int SYNC_FINISH = 11; //同步请求响应完成
	public static int SYNC_FINISH_ANSWER_BACK = 12; //同步请求响应完成应答
	
	public static int TRANSPOND_REQUEST = 13; //数据包转发
	public static int TRANSPOND_RESPONSE = 14; //数据包转发响应
	
	/*static {
		increaseFieldValue(Command.class, 0);
	}*/
	
	private static void increaseFieldValue(Class cls, int base) {
		Field[] fs = cls.getFields();
		int i = 0;
		for (Field f : fs) {
			try {
				f.setInt(cls, base + i);
				i++;
//				System.out.println(f.getName()+":"+f.getInt(cls));
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}
}
