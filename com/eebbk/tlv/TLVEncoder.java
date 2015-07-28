package com.eebbk.tlv;


/**
 * TLV编码实现
 * 
 * @author Administrator
 * 
 */
public class TLVEncoder {

	/**
	 * 基本数据类型
	 */
	public static final int PrimitiveFrame = 0x00;

	/**
	 * 私有类型
	 */
	public static final int PrivateFrame = 0x40;

	/**
	 * 基本类型数据编码
	 */
	public static final int PrimitiveData = 0x00;

	/**
	 * TLV类型数据编码
	 */
	public static final int ConstructedData = 0x20;

	/**
	 * TLV格式编码
	 * 
	 * @param frameType
	 * @param dataType
	 * @param tagValue
	 * @param value
	 * @return
	 */
	public static TLVEncodeResult encode(int frameType, int dataType,
			int tagValue, byte[] value) {
		byte[] tagBytes = encodeTag(frameType, dataType, tagValue);
//		System.out.println("tag:"+new BigInteger(1, tagBytes).toString(2));

		byte[] lengthBytes = encodeLength(value.length);
//		System.out.println("length:"+new BigInteger(1, lengthBytes).toString(2));
		
		TLVEncodeResult result = new TLVEncodeResult();
		result.setTagBytes(tagBytes);
		result.setTagSize(tagBytes.length);
		result.setLengthBytes(lengthBytes);
		result.setLengthSize(lengthBytes.length);
		result.setValueBytes(value);
		result.setValueSize(value.length);
		return result;
	}

	/**
	 * TLV格式编码
	 * 
	 * @param frameType
	 * @param dataType
	 * @param tagValue
	 * @param value
	 * @return
	 */
	public static TLVEncodeResult encode(int frameType, int dataType,
			int tagValue, String value) {
		return encode(frameType, dataType, tagValue, value.getBytes());
	}
	
	public static TLVEncodeResult encode(int frameType, int dataType,
			int tagValue, int value) {
		return encode(frameType, dataType, tagValue, TLVUtils.intToByteArray(value));
	}

	/**
	 * <p>
	 * 生成 Tag ByteArray
	 * </p>
	 * <p>
	 * <b>其中 tagValue <= 2097151，超过之后编码的结果是错误的</b>
	 * </p>
	 * 
	 * @param tagValue
	 *            Tag 值，即协议中定义的交易类型 或 基本数据类型
	 * @param frameType
	 *            TLV类型，Tag首字节最左两bit为00：基本类型，01：私有类型(自定义类型)
	 * @param dataType
	 *            数据类型，Tag首字节第5位为0：基本数据类型，1：结构类型(TLV类型，即TLV的V为一个TLV结构)
	 * @return Tag ByteArray
	 */
	public static byte[] encodeTag(int frameType, int dataType, int tagValue) {
		int rawTag = frameType | dataType | tagValue;
		int digit = 0;
		if (tagValue >= 0x1f) {
			rawTag = frameType | dataType | 0x9f;
			digit = (int) computeTagDigit(tagValue);
			rawTag <<= 8 * digit;
			for (int i = digit - 1; i > 0; i--) {
				rawTag |= ((tagValue >> i * 7 & 0x7f) | 0x80) << i * 8;
			}
			rawTag |= tagValue & 0x7f;
		}
		return intToByteArray(rawTag, digit);
	}

	/**
	 * int转成字节数组
	 * 
	 * @param i
	 * @return
	 */
	private static byte[] intToByteArray(int value, int digit) {
		byte[] result = new byte[digit + 1];
		// 由高位到低位
		int length = result.length;
		for (int i = 0; i < length; i++) {
			result[i] = (byte) ((value >> (8 * (digit - i))) & 0xff);
		}
		return result;
	}
	
	/**
	 * 对数计算换底公式
	 * 
	 * @param value
	 * @param base
	 * @return
	 */
	public static double log(double value, double base) {
		return Math.log(value) / Math.log(base);
	}

	/**
	 * 计算Tag字节数,推导出来的计算公式
	 * 
	 * @param value
	 * @return
	 */
	private static double computeTagDigit(double value) {
		if (value < 0x1f) {
			throw new IllegalArgumentException(
					"the tag value must not less than 31.");
		}
		return Math.ceil(log(value + 1, 128));
	}

	/**
	 * 生成Length的byte数组
	 * 
	 * @param length
	 * @return
	 */
	public static byte[] encodeLength(int length) {
		if (length < 0) {
			throw new IllegalArgumentException(
					"the length must not less than 0.");
		}
		if (length < 128) {
			byte[] lengthBytes = new byte[1];
			lengthBytes[0] = (byte) length;
			return lengthBytes;
		} else {
			int digit = (int) computeLengthDigit(length);
			byte[] lengthBytes = new byte[digit + 1];
			lengthBytes[0] = (byte) (0x80 | digit);
			for (int i = 1; i < digit + 1; i++) {
				lengthBytes[i] = (byte) (length >> 8 * (digit - i));
			}
			return lengthBytes;
		}
	}

	/**
	 * 计算length的字节数,推导出来的计算公式
	 * 
	 * @param length
	 * @return
	 */
	private static double computeLengthDigit(int length) {
		if (length < 128) {
			throw new IllegalArgumentException(
					"the length must not less than 128.");
		}
		return Math.ceil(log(length + 1, 256));
	}
}
