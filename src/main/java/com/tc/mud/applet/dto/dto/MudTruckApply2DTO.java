package com.tc.mud.applet.dto.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("泥票申请")
public class MudTruckApply2DTO {
	@ApiModelProperty(value = "用户Id")
	private String userId;
	
	@ApiModelProperty(value = "申请日期")
	private String date;
	
	@ApiModelProperty(value = "泥头票ID")
	private String headId;
	
	@ApiModelProperty(value = "泥头票预约时间")
	private String headTime;
	
	@ApiModelProperty(value = "泥头项目名称")
	private String headItemName;
	
	@ApiModelProperty(value = "泥头项目地址")
	private String headAddress;
	
	@ApiModelProperty(value = "泥头开工时间")
	private String headStartTime;
	
	@ApiModelProperty(value = "泥头收工时间")
	private String headEndTime;
	
	@ApiModelProperty(value = "泥头排队时间")
	private String headQueuingTime;
	
	@ApiModelProperty(value = "泥头状态")
	private String headOpenStatus;
	
	@ApiModelProperty(value = "预计行驶时间")
	private String travelTime;
	
	@ApiModelProperty(value = "泥尾票ID")
	private String tailId;
	
	@ApiModelProperty(value = "泥尾项目名称")
	private String tailItemName;
	
	@ApiModelProperty(value = "泥尾项目地址")
	private String tailAddress;
	
	@ApiModelProperty(value = "泥尾开工时间")
	private String tailStartTime;
	
	@ApiModelProperty(value = "泥尾收工时间")
	private String tailEndTime;
	
	@ApiModelProperty(value = "泥尾票预约时间")
	private String tailTime;
	
}
