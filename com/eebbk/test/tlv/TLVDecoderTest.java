package com.eebbk.test.tlv;

import java.math.BigInteger;
import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.eebbk.tlv.TLVDecodeResult;
import com.eebbk.tlv.TLVDecoder;
import com.eebbk.tlv.TLVEncoder;
import com.eebbk.tlv.TLVObject;
import com.eebbk.tlv.TLVUtils;

public class TLVDecoderTest {

	private String data = "tvl_test: [frameType=64, dataType=32, tagValue=1314, length=19, value=TLVDecodeResult [frameType=0, dataType=32, tagValue=31, length=16, value=TLVDecodeResult [frameType=64, dataType=32, tagValue=127, length=13, value=TLVDecodeResult [frameType=0, dataType=0, tagValue=201006, length=8, value=[B@29b9ab6c]]]]";

	private byte[] encodeResult;

	@Before
	public void setUp() {
		/*
		 * encodeResult = TLVEncoder.encodeTLV( 0x40, 0x20, 1314,
		 * TLVEncoder.encodeTLV( 0x00, 0x20, 31, TLVEncoder.encodeTLV( 0x40,
		 * 0x20, 127, TLVEncoder.encodeTLV(0x00, 0x00, 201006, data)
		 * .toByteArray()).toByteArray()) .toByteArray()).toByteArray();
		 */
		byte[] b1 = TLVEncoder.encode(0x40, 0x00, 1311, "hello_tlv_test")
				.toByteArray();
		byte[] b2 = TLVEncoder.encode(
				0x40,
				0x20,
				1312,
				TLVEncoder.encode(0x40, 0x00, 1216, "hello_tlv_test2")
						.toByteArray()).toByteArray();
		byte[] b3 = new byte[b1.length + b2.length];
		System.arraycopy(b1, 0, b3, 0, b1.length);
		System.arraycopy(b2, 0, b3, b1.length, b2.length);
		byte[] b4 = TLVEncoder.encode(
				0x40,
				0x20,
				1314,
				b3).toByteArray();
		byte[] b5 = TLVEncoder.encode(
				0x40,
				0x20,
				1315,
				b3).toByteArray();
		byte[] b6 = new byte[b4.length + b5.length];
		System.arraycopy(b4, 0, b6, 0, b4.length);
		System.arraycopy(b5, 0, b6, b4.length, b5.length);
		encodeResult = TLVEncoder.encode(0x40, 0x20, 0, b6)
				.toByteArray();
	}

	@Test
	public void testDecodeFrameType() {
		byte[] bs = TLVEncoder.encodeTag(0x40, 0x20, 31);
		int frameType = TLVDecoder.decodeFrameType(bs);
		System.out.println(new BigInteger(1, bs).toString(2));
		System.out.println("frameType:" + TLVDecoder.decodeFrameType(bs));
		Assert.assertEquals(0x40, frameType);
	}

	@Test
	public void testByteArrayToInt() {

	}

	@Test
	public void testDecodeLength() {
		boolean b = true;
		for (int i = 0; i < 2097152; i++) {
			int length = TLVDecoder.decodeLength(TLVEncoder.encodeLength(i));
			if (length != i) {
				b = false;
				System.err.println("length:" + length + " i:" + i);
				break;
			}
		}
		Assert.assertEquals(true, b);
		/*
		 * int length = TLVDecoder.decodeLength(TLVEncoder.encodeLength(1200));
		 * System.out.println("length:"+length); Assert.assertEquals(1200,
		 * length);
		 */
	}

	@Test
	public void testDecodeDataType() {
		int dataType = TLVDecoder.decodeDataType(TLVEncoder.encodeTag(0x40,
				0x20, 31));
		System.out.println("dataType:" + dataType);
		Assert.assertEquals(0x20, dataType);
	}

	@Test
	public void testDecodeTagValue1() {
		int max = -1;
		boolean b = true;
		for (int i = 0; i < Integer.MAX_VALUE; i++) {
			int value = TLVDecoder.decodeTagValue(TLVEncoder.encodeTag(0x40,
					0x20, i));
			if (value == i) {
				// System.out.println("after decode:" + value +
				// " before decode:" + i);
			} else {
				max = i;
				b = false;
				System.err.println("after decode:" + value + " before decode:"
						+ i);
				break;
			}
		}
		System.err.println("max:" + max);
		Assert.assertEquals(true, b);
	}

	@Test
	public void testDecodeTagValue2() {
		int value = TLVDecoder.decodeTagValue(TLVEncoder.encodeTag(0x40, 0x20,
				2097152));
		System.out.println("after decode:" + value + " before decode:"
				+ 2097152);
		Assert.assertEquals(2097152, value);
	}

	@Test
	public void testGetTagBytesSize() {
		int length = TLVDecoder.getTagBytesSize(TLVEncoder.encodeTag(0x40,
				0x20, 2));
		System.out.println("length:" + length);
	}

	@Test
	public void testEncodeTLVAndDecodeTLV() {
		TLVObject tlvData = new TLVObject();
//		tlvData.put(1, 1);
//		tlvData.put(11, new TLVObject().put(1, "001").put(3, "002").put(5, "003"));
		tlvData.put(
				11,
				new TLVObject().put(100,
						new TLVObject().put(1000, 10000).put(1001, 10010)).put(
						101, new TLVObject().put(1111, 1010).put(2222, 2020)));
		TLVDecodeResult decodeResult = TLVDecoder.decode(tlvData.toByteArray());
		System.out.println(decodeResult);
	}

	@Test
	public void test() {
		
	}
}
