package com.tc.mud.applet.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("泥票信息")
public class MudTicketInfoDTO {

	@ApiModelProperty(value = "订单ID",hidden = true)
	private String orderId;
	@ApiModelProperty(value = "代理ID",hidden = true)
	private String agentId;
	@ApiModelProperty(value = "泥票ID",hidden = true)
	private String ticketId;
	@ApiModelProperty(value = "泥场ID")
	private String yardId;
	@ApiModelProperty(value = "发布公司ID",hidden = true)
	private String companyId;
	@ApiModelProperty(value = "发行老板ID",hidden = true)
	private String bossId;
	@ApiModelProperty(value = "泥票名称" )
	private String ticketName;
	@ApiModelProperty(value = "发布公司名称")
	private String companyName;
	@ApiModelProperty(value = "开工日期")
	private String startDate;
	@ApiModelProperty(value = "上班时间")
	private String startTime;
	@ApiModelProperty(value = "下班时间")
	private String endTime;
	@ApiModelProperty(value = "价格")
	private String price;
	@ApiModelProperty(value = "车次")
	private String count;
	@ApiModelProperty(value = "时间戳",hidden = true)
	private String timeStamp;
	@ApiModelProperty(value = "签字",hidden = true)
	private String sign;
	@ApiModelProperty(value = "容量")
	private String capacity;
	@ApiModelProperty(value = "图片编号(泥票背景图)")
	private String imageNum;
	@ApiModelProperty(value = "类型(tail:泥尾票,head:泥头票)")
	private String type;
	@ApiModelProperty(value = "泥头票状态[0:未发布;1:发布;2:下架;3:暂停;4:结束]")
	private String status;
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
