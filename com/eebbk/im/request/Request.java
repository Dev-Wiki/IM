package com.eebbk.im.request;

import java.lang.reflect.Field;

public abstract class Request {

	/*private int Header;
	
	public int getHeader() {
		return Header;
	}

	public void setHeader(int header) {
		Header = header;
	}*/
	
	protected static void increaseFieldValue(Class cls, int base) {
		Field[] fs = cls.getFields();
		int i = 0;
		for (Field f : fs) {
			String fieldName = f.getName();
			if (fieldName.equals("RID")) {
				continue;
			}
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
