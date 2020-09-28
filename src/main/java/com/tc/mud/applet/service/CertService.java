package com.tc.mud.applet.service;

import java.util.Map;

/***
 * 二维码 
 * @author Admin
 *
 */
public interface CertService {
	
	/**
	 * 签名
	 * @param obj    签字内容
	 * @param uniond 签字人unionID
	 * @return
	 * @throws Exception
	 */
	String sign(Object obj,String userId)throws Exception;
	
	/**
	 * 验证签名
	 * @param obj    要验证的签字内容
	 * @param uniond 签字人userId
	 * @param sign   签字人的签名
	 * @return
	 * @throws Exception
	 */
	void verify(Object obj,String userId,String sign)throws Exception;
	
	
	
	/**
	 * 证书验证
	 * @param data
	 * @return    签字人
	 * @throws Exception
	 */
	 
	boolean certVerify(String data) throws Exception;
	
	/**
	 * 请求证书验证
	 * @param data
	 * @return    签字人
	 * @throws Exception
	 */
	 
	void requestCertVerify(String data) throws Exception;
	
	/***
	 * data字符串转换成 map对象
	 * @param str
	 * @return
	 */
	Map<String,String> dataStringToMap(String data);
}
