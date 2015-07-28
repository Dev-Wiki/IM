package com.eebbk.tlv;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class TLVObject {
	
	private ByteArrayOutputStream baos;

	public TLVObject() {
		baos = new ByteArrayOutputStream();
	}
	
	public TLVObject put(int tagValue, int value) {
		writeValue(tagValue, TLVUtils.intToByteArray(value));
		return this;
	}
	
	public TLVObject put(int tagValue, String value) {
		writeValue(tagValue, value.getBytes());
		return this;
	}
	
	public TLVObject put(int tagValue, byte[] value) {
		writeValue(tagValue, value);
		return this;
	}
	
	public TLVObject put(int tagValue, TLVObject tlvObject) {
		writeTLV(tagValue, tlvObject);
		return this;
	}
	
	private void writeValue(int tagValue, byte[] value) {
		TLVEncodeResult result = TLVEncoder.encode(0x00, 0x00, tagValue, value);
		try {
			baos.write(result.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void writeTLV(int tagValue, TLVObject tlvObject) {
		if (tlvObject.size() > 0) {
			TLVEncodeResult result = TLVEncoder.encode(0x00, 0x20, tagValue, tlvObject.toByteArray());
			try {
				baos.write(result.toByteArray());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*public int getInt(int tagValue) {
		List<TLVDecodeResult> list = TLVDecoder.decodeTLV(baos.toByteArray());
		int value = -1;
		for (TLVDecodeResult result : list) {
			if(result.getTagValue() == tagValue) {
				value = result.getIntValue(tagValue);
				break;
			}
		}
		return value;
	}*/
	
	/*public String getString(int tagValue) {
		List<TLVDecodeResult> list = TLVDecoder.decodeTLV(baos.toByteArray());
		String value = null;
		for (TLVDecodeResult result : list) {
			if(result.getTagValue() == tagValue) {
				value = result.getStringValue(tagValue);
				break;
			}
		}
		return value;
	}*/
	
	/*public TLVDecodeResult get(int tagValue) {
		List<TLVDecodeResult> list = TLVDecoder.decodeTLV(baos.toByteArray());
		TLVDecodeResult result = null;
		for (TLVDecodeResult r : list) {
			if(r.getTagValue() == tagValue) {
				result = r;
				break;
			}
		}
		return result;
	}*/
	
	public int size() {
		return baos.size();
	}
	
	public byte[] toByteArray() {
		return baos.toByteArray();
	}
	
	public String toBinaryString() {
		return new BigInteger(1, baos.toByteArray()).toString(2);
	}
	
	@Override
	public String toString() {
		return TLVDecoder.decode(baos.toByteArray()).toString();
	}
}
