package com.tc.mud.applet.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("泥票申请泥尾预约信息")
public class MudTruckApplyTailDTO {
	@ApiModelProperty(value = "泥尾票ID",hidden = true)
	private String tailId;
	
	@ApiModelProperty(value = "泥尾票ID")
	private String tailTime;
	
	@ApiModelProperty(value = "泥尾票ID")
	private String tailStatus;
	
	@ApiModelProperty(value = "泥尾票项目名称")
	private String itemName;
	
	@ApiModelProperty(value = "泥尾地址")
	private String address;
	
	@ApiModelProperty(value = "开放时间")
	private String openingTime;
	
}
