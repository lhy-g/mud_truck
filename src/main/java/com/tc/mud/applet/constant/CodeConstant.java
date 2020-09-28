package com.tc.mud.applet.constant;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 
 * 
 * @author Admin
 *
 */

@Data
@ApiModel("二维码相关常量")
public class CodeConstant {

	@ApiModelProperty(value = "证书")
	public static final String CERT = "cert";
	
	
	@ApiModelProperty(value = "证书名称")
	public static final String CERT_NAME = "n";

	@ApiModelProperty(value = "证书内容")
	public static final String CONTENT = "c";
	
	@ApiModelProperty(value = "老板ID")
	public static final String BOSS_ID = "b";
	
	@ApiModelProperty(value = "老板发行时间戳")
	public static final String TIME_STAMP_1 = "d";
	
	@ApiModelProperty(value = "申请人/签字人[UnionID]")
	public static final String APPLICANT = "a";
	
	@ApiModelProperty(value = "申请/签字时间[时间戳]")
	public static final String TIME_STAMP = "t";
	
	@ApiModelProperty(value = "申请人签名")
	public static final String SIGN = "s";
	
	@ApiModelProperty(value = "成员类型/授权类型")
	public static final String TYPE = "y";
	
	@ApiModelProperty(value = "状态(0:无效,1:有效)")
	public static final String STATUS = "u";
	
	@ApiModelProperty(value = "公钥")
	public static final String PUBLICKEY = "p";
	
	@ApiModelProperty(value = "成员ID")
	public static final String MEMBER_ID = "m";
	 
	@ApiModelProperty(value = "消费者ID/购买者")
	public static final String CONSUMER_ID = "o";
	
	@ApiModelProperty(value = "促销劵二维码数据前缀")
	public static final String URL = "https://n.3p3.top?data=";
	
	//String str = "https://maotaiapi.lcpower.cn?number=";
	
	@ApiModelProperty(value = "用户二维码前缀")
	public static final String URL_USER = "https://p.3p3.top/user?data=";
	
	
	
	
//	@ApiModelProperty(value="签名")
//	private static final String SIGN = "s";
//	
//	
//	@ApiModelProperty(value="签名")
//	private static final String SIGN = "s";
//	
	
	@ApiModelProperty(value = "证书名称")
	private String n;
	
	@ApiModelProperty(value = "证书内容")
	private String c;
	
	@ApiModelProperty(value = "时间戳（发行时间）")
	private String d;
	
	@ApiModelProperty(value = "老板UnionID")
	private String b;
	
	@ApiModelProperty(value = "时间戳（代理人发行时间）")
	private String t;
	
	@ApiModelProperty(value = "申请人UnionID")
	private String a;
	
	@ApiModelProperty(value = "申请人签名")
	private String s;
}
