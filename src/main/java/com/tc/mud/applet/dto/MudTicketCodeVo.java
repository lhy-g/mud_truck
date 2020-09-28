package com.tc.mud.applet.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("泥票图片")
public class MudTicketCodeVo {

	@ApiModelProperty(value = "订单ID",hidden = true)
	private String orderId;
	@ApiModelProperty(value = "代理ID",hidden = true)
	private String agentId;
	@ApiModelProperty(value = "发行老板ID",hidden = true)
	private String bossId;
	
	@ApiModelProperty(value = "时间戳",hidden = true)
	private String timeStamp;
	
	
	@ApiModelProperty(value = "泥票ID",hidden = true)
	private String ticketId;
	@ApiModelProperty(value = "泥票名称" )
	private String ticketName;
	@ApiModelProperty(value = "图片编号(泥票背景图)")
	private String imageNum;
 
 
//	@ApiModelProperty(value = "装车所在区")
//	private String area;
//	@ApiModelProperty(value = "装车所在地址")
//	private String address;
//	@ApiModelProperty(value = "中标通知书")
//	private String permit1;
//	@ApiModelProperty(value = "开工许可证")
//	private String permit2;
//	@ApiModelProperty(value = "进场通知书")
//	private String permit3;
//	@ApiModelProperty(value = "运输路线报批")
//	private String permit4;
//	@ApiModelProperty(value = "管理人员报备")
//	private String permit5;

	
}
