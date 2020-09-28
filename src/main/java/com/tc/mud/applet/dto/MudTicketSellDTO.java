package com.tc.mud.applet.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("泥票信息")
public class MudTicketSellDTO {

	@ApiModelProperty(value = "订单ID",hidden = true)
	private String orderId;
	@ApiModelProperty(value = "代理ID",hidden = true)
	private String agentId;
	@ApiModelProperty(value = "泥票ID",hidden = true)
	private String ticketId;
	@ApiModelProperty(value = "泥场ID")
	private String yardId;
	@ApiModelProperty(value = "购买人ID",hidden = true)
	private String buyerId;
	@ApiModelProperty(value = "购买司机Id",hidden = true)
	private String driverId;
	@ApiModelProperty(value = "出售人ID")
	private String sellerId;

	@ApiModelProperty(value = "出售人签字")
	private String sign;
	@ApiModelProperty(value = "时间戳")
	private String timeStamp;
//	@ApiModelProperty(value = "购买公司ID",hidden = true)
//	private String companyId;
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
