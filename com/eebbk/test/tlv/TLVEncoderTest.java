package com.eebbk.test.tlv;

import java.lang.reflect.Method;
import java.math.BigInteger;

import junit.framework.Assert;

import org.junit.Test;

import com.eebbk.tlv.TLVDecodeResult;
import com.eebbk.tlv.TLVDecoder;
import com.eebbk.tlv.TLVEncodeResult;
import com.eebbk.tlv.TLVEncoder;
import com.eebbk.tlv.TLVObject;

public class TLVEncoderTest {

	@Test
	public void testEncodeTag() {
		String s1 = new BigInteger(1, TLVEncoder.encodeTag(0x00, 0x20, 0))
				.toString(2);
		System.out.println("s1:" + s1);
		String s2 = new BigInteger(1, TLVTest.parseTag(0, 0x40, 0x20))
				.toString(2);
		System.out.println("s2:" + s2);
//		Assert.assertEquals(s1, s2);
	}

	@Test
	public void testEncodeLength() {
		String s1 = new BigInteger(1, TLVEncoder.encodeLength(120000))
				.toString(2);
		System.out.println(s1);
		String s2 = new BigInteger(1, TLVTest.parseLength(120000)).toString(2);
		System.out.println(s2);
		Assert.assertEquals(s1, s2);
	}

	@Test
	public void testComputeTagDigit() {
		try {
			Method m = TLVEncoder.class.getDeclaredMethod("computeTagDigit",
					double.class);
			m.setAccessible(true);
			double result = (double) m.invoke(TLVEncoder.class, 2097152);
			System.out.println(result);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	@Test
	public void testComputeLengthDigit() {
		try {
			Method m = TLVEncoder.class.getDeclaredMethod("computeLengthDigit",
					int.class);
			m.setAccessible(true);
			double result = (double) m.invoke(TLVEncoder.class, 120000);
			System.out.println(result);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	@Test
	public void testEncodeTLV() {
		String data = "decodeResult:TLVDecodeResult [frameType=64, dataType=32, tagValue=1314, length=19, value=TLVDecodeResult [frameType=0, dataType=32, tagValue=31, length=16, value=TLVDecodeResult [frameType=64, dataType=32, tagValue=127, length=13, value=TLVDecodeResult [frameType=0, dataType=0, tagValue=201006, length=8, value=[B@29b9ab6c]]]]tvl_test";
//		System.out.println(data.length());
		// String data = "tlv_test";
		TLVEncodeResult encodeResult = TLVEncoder.encode(0x00, 0x20, 2,
				TLVEncoder.encode(0x00, 0x00, 1,
						2).toByteArray());
		TLVObject tlvObj = new TLVObject();
		tlvObj.put(2, new TLVObject().put(1, 2));
		byte[] b = encodeResult.toByteArray();
		String s1 = new BigInteger(1, b).toString(2);
		System.out.println("s1::" + s1);
		System.out.println("s2::"+tlvObj.toBinaryString());
	}
}
