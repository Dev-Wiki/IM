package com.eebbk.test.tlv;

public class TLVTest {

	public static double log(double value, double base) {
		return Math.log(value) / Math.log(base);
	}

	public static byte[] parseTag(int tagValue, int frameType, int dataType) {
		int size = 1;
		int rawTag = frameType | dataType | tagValue;
		if (tagValue < 0x1F) {
			// 1 byte tag
			rawTag = frameType | dataType | tagValue;
		} else {
			// mutli byte tag
			rawTag = frameType | dataType | 0x1F;
			if (tagValue < 0x80) {
				rawTag <<= 8;
				rawTag |= tagValue & 0x7F;
			} else if (tagValue < 0x3FFF) {
				rawTag <<= 16;
				rawTag |= (((tagValue & 0x3FFF) >> 7 & 0x7F) | 0x80) << 8;
				rawTag |= ((tagValue & 0x3FFF) & 0x7F);
			} else if (tagValue < 0x3FFFF) {
				rawTag <<= 24;
				rawTag |= (((tagValue & 0x3FFFF) >> 14 & 0x7F) | 0x80) << 16;
				rawTag |= (((tagValue & 0x3FFFF) >> 7 & 0x7F) | 0x80) << 8;
				rawTag |= ((tagValue & 0x3FFFF) & 0x7F);
			}
		}
		return intToByteArray(rawTag);
	}

	public static byte[] intToByteArray(int i) {
		byte[] result = new byte[4];
		// 由高位到低位
		result[0] = (byte) ((i >> 24) & 0xFF);
		result[1] = (byte) ((i >> 16) & 0xFF);
		result[2] = (byte) ((i >> 8) & 0xFF);
		result[3] = (byte) (i & 0xFF);// 11111100000001111111101111111
		return result;
	}

	public static byte[] parseLength(int length) {
		if (length < 0) {
			throw new IllegalArgumentException();
		} else
		// 短形式
		if (length < 128) {
			byte[] actual = new byte[1];
			actual[0] = (byte) length;
			return actual;
		} else
		// 长形式
		if (length < 256) {
			byte[] actual = new byte[2];
			actual[0] = (byte) 0x81;
			actual[1] = (byte) length;
			return actual;
		} else if (length < 65536) {
			byte[] actual = new byte[3];
			actual[0] = (byte) 0x82;
			actual[1] = (byte) (length >> 8);
			actual[2] = (byte) length;
			return actual;
		} else if (length < 16777126) {
			byte[] actual = new byte[4];
			actual[0] = (byte) 0x83;
			actual[1] = (byte) (length >> 16);
			actual[2] = (byte) (length >> 8);
			actual[3] = (byte) length;
			return actual;
		} else {
			byte[] actual = new byte[5];
			actual[0] = (byte) 0x84;
			actual[1] = (byte) (length >> 24);
			actual[2] = (byte) (length >> 16);
			actual[3] = (byte) (length >> 8);
			actual[4] = (byte) length;
			return actual;
		}
	}
}
