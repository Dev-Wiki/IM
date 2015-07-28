package com.eebbk.tlv;

public class TLVDecodeResult {

	private int frameType;

	private int dataType;

	private int tagValue;

	private int length;

	/**
	 * <p>
	 * TLV中的Value的具体值，只可能存在两种类型：
	 * </p>
	 * <p>
	 * 1.{@link #dataType}=0x20的时候是List<{@link TLVDecodeResult}>类型
	 * </p>
	 * <p>
	 * 2.{@link #dataType}=0x00的时候是byte[]类型
	 * </p>
	 */
	private Object value;
	
	/**
	 * 获取到真正的Value值
	 * 
	 * @return
	 */
	public byte[] getRealValue() {
		return (byte[]) getRealTLVDecodeResult(this).getValue();
	}

	/**
	 * 获取到真正的TLVDecodeResult解析对象
	 * 
	 * @return
	 */
	public TLVDecodeResult getRealTLVDecodeResult() {
		return getRealTLVDecodeResult(this);
	}

	private TLVDecodeResult getRealTLVDecodeResult(TLVDecodeResult result) {
		if (result.dataType == TLVEncoder.ConstructedData) {
			return getRealTLVDecodeResult((TLVDecodeResult) result.value);
		} else {
			return result;
		}
	}

	/**
	 * 根据Tag的属性获取到相应的TLVDecodeResult解析对象
	 * 
	 * @param frameType
	 * @param dataType
	 * @param tagValue
	 * @return
	 */
	public TLVDecodeResult getTLVDecodeResultByTag(int tagValue) {
		return getTLVDecodeResultByTag(tagValue, this);
	}

	private TLVDecodeResult getTLVDecodeResultByTag(int tagValue,
			TLVDecodeResult result) {
		if (result.tagValue == tagValue) {
			return result;
		} else {
			if (result.dataType == TLVEncoder.ConstructedData) {
				return getTLVDecodeResultByTag(tagValue,
						(TLVDecodeResult) result.value);
			} else {
				return null;
			}
		}
	}
	
	/**
	 * 获取int类型的值
	 * @param tagValue
	 * @return
	 */
	public int getIntValue(int tagValue) {
		byte[] result = getRealValue();
		return TLVUtils.byteArrayToInt(result);
	}
	
	/**
	 * 获取String类型的值
	 * @param tagValue
	 * @return
	 */
	public String getStringValue(int tagValue) {
		byte[] result = getRealValue();
		return new String(result);
	}

	public int getFrameType() {
		return frameType;
	}

	public void setFrameType(int frameType) {
		this.frameType = frameType;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public int getTagValue() {
		return tagValue;
	}

	public void setTagValue(int tagValue) {
		this.tagValue = tagValue;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "TLVDecodeResult [frameType=" + frameType + ", dataType="
				+ dataType + ", tagValue=" + tagValue + ", length=" + length
				+ ", value=" + value + "]";
	}

}
