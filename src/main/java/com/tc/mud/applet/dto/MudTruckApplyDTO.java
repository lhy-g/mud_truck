package com.tc.mud.applet.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("泥票预约信息")
public class MudTruckApplyDTO {
	
	@ApiModelProperty(value = "预约日期")
	private String date;
	
	@ApiModelProperty(value = "预约用户")
	private String userId;
	
	@ApiModelProperty(value = "泥头票ID")
	private String headId;
	
	@ApiModelProperty(value = "泥头预约进场时间")
	private String headTime;
	
	@ApiModelProperty(value = "泥尾票ID")
	private String tailId;
	
	@ApiModelProperty(value = "泥尾预约进场时间")
	private String tailTime;
	
//	@ApiModelProperty(value = "泥头票ID")
//	private String headStatus;
//	
//	@ApiModelProperty(value = "泥头票项目名称")
//	private String itemName;
//	
//	@ApiModelProperty(value = "泥头地址")
//	private String address;
//	
//	@ApiModelProperty(value = "开放时间")
//	private String openingTime;
	
	
	
	 
	
	
}
