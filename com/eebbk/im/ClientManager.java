package com.eebbk.im;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;

import com.eebbk.im.request.RegistRequest;
import com.eebbk.tlv.TLVDecodeResult;
import com.eebbk.tlv.TLVDecoder;
import com.eebbk.tlv.TLVObject;

public class ClientManager {

	public void sendRegist (RegistRequest registInfo) throws Exception {
		TLVObject tlvObj = new TLVObject();
		tlvObj.put(registInfo.COMMAND, formatData(RegistRequest.TagValue.class, registInfo));
		System.out.println("send_size:"+tlvObj.size());
		System.out.println("send_byte_string:"+tlvObj.toBinaryString());
		System.out.println("解析:"+TLVDecoder.decode(tlvObj.toByteArray()));
		try {
			Socket socket = new Socket("172.28.10.38", 1883);
			System.out.println("socket连接成功...");
			OutputStream os = socket.getOutputStream();
			os.write(tlvObj.toByteArray());
			os.flush();
			System.out.println("请求发送成功...");
			InputStream is = socket.getInputStream();
			byte[] buf = new byte[10];
			int length = 0;
			StringBuffer sb = new StringBuffer();
			while((length = is.read(buf)) != -1) {
				System.out.println("length:"+length);
				sb.append(new String(buf, 0, length));
				if(is.available() == 0) {
					TLVDecodeResult result = TLVDecoder.decode(sb.toString().getBytes());
					System.out.println("返回结果:"+result.toString());
				}
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private TLVObject formatData(Class tagValueCls, Object targetObj) throws Exception {
		Field[] tagValueFields = tagValueCls.getFields();
		Field[] targetFields = targetObj.getClass().getDeclaredFields();
		TLVObject tlvObj = new TLVObject();
		for (Field tagValueField : tagValueFields) {
			String tagValueFieldName = tagValueField.getName();
			for (Field targetField : targetFields) {
				String targetFieldName = targetField.getName();
				if (tagValueFieldName.equals(targetFieldName)) {
					targetField.setAccessible(true);
					Object valueObj = targetField.get(targetObj);
					if (valueObj != null) {
						if (String.class.isInstance(valueObj)) {
							tlvObj.put(tagValueField.getInt(tagValueCls), valueObj.toString());
//							System.out.println("targetFieldName:"+targetFieldName+" value:"+valueObj.toString());
						} else if (Integer.class.isInstance(valueObj)) {
							tlvObj.put(tagValueField.getInt(tagValueCls), (int) valueObj);
//							System.out.println("targetFieldName:"+targetFieldName+" value:"+valueObj);
						} else {
							System.err.println("error...:"+"targetFieldName:"+targetFieldName
									+" value:"+targetField.get(targetObj)
									+"type:"+valueObj.getClass());
						}
					} else {
						System.err.println("null...:" + targetFieldName);
					}
				}
			}
			
		}
		return tlvObj;
	}
}
