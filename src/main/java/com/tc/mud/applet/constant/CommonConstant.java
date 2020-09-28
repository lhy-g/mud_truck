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
@ApiModel("常用常量")
public class CommonConstant {

	@ApiModelProperty(value = "老板")
	public static final String MEMBER_BOSS = "boss";
	
	@ApiModelProperty(value = "代理人")
	public static final String MEMBER_AGENT = "agent";
	
	@ApiModelProperty(value = "销售员")
	public static final String MEMBER_SELLER = "seller";
	
	@ApiModelProperty(value = "消费者")
	public static final String MEMBER_CONSUMER = "consumer";
	
	@ApiModelProperty(value = "消费者")
	public static final String IMAGE_NUM = "imageNum";
	
	@ApiModelProperty(value = "redis:交易")
	public static final String TRAN = "tran:";

}
