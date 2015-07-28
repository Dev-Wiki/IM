package com.eebbk.tlv;

public class TLVUtils {

	/**
	 * 字节数组转int,适合转高位在前低位在后的byte[]
	 * 
	 * @param tagBytes
	 * @return
	 */
	public static int byteArrayToInt(byte[] tagBytes) {
		int value = 0;
		int length = tagBytes.length;
		for (int i = 0; i < length; i++) {
			value |= (tagBytes[i] & 0xFF) << 8 * (length - 1 - i);
		}
		return value;
	}
	
	/**
	 * int转byte[]，高位在前低位在后
	 * @param value
	 * @return
	 */
	public static byte[] intToByteArray(int value) {
		int length = 4;
		if(value < Math.pow(2, 8)) {
			length = 1;
		} else if(value < Math.pow(2, 16)) {
			length = 2;
		} else if(value < Math.pow(2, 24)) {
			length = 3;
		} else if(value <= Integer.MAX_VALUE) {
			length = 4;
		}
		byte[] result = new byte[length];
		// 由高位到低位
		for (int i = 0; i < length; i++) {
			result[i] = (byte) ((value >> (8 * (length - 1 - i))) & 0xff);
		}
		return result;
	}
}
