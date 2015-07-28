package com.eebbk.test.client;

import java.util.UUID;

import junit.framework.TestCase;

import org.junit.Test;

import com.eebbk.im.ClientManager;
import com.eebbk.im.request.RegistRequest;

public class ClientManagerTest extends TestCase{

	@Test
	public void testSendRegist() throws Exception {
		ClientManager cm =new ClientManager();
		RegistRequest registInfo = new RegistRequest();
		registInfo.setRID(1314);
		registInfo.setAppKey("appkey");
		registInfo.setBasebandVersion("basebandVersion");
		registInfo.setBuildNumber("buildNumber");
		registInfo.setDeviceId(UUID.randomUUID().toString().replaceAll("-", ""));
		registInfo.setImei("imei");
		registInfo.setImsi("imsi");
		registInfo.setMac("mac");
		registInfo.setModelNumber("modelNumber");
		registInfo.setPlatform(10);
		registInfo.setSysName("sysName");
//		registInfo.setPkgName("pkgName");
//		registInfo.setSdkVerison("sdkVerison");
//		registInfo.setResolution("resolution");
//		registInfo.setSysVersion("sysVersion");
		cm.sendRegist(registInfo);
	}
}
