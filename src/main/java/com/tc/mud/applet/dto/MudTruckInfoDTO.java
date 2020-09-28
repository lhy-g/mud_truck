package com.tc.mud.applet.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("泥土信息[每车]")
public class MudTruckInfoDTO {

	@ApiModelProperty(value = "泥票ID")
	private String mudId;
	@ApiModelProperty(value = "运输员ID")
	private String driverId;
	
	@ApiModelProperty(value = "泥头票ID")
	private String headId;
	@ApiModelProperty(value = "放行员ID")
	private String releaseId;
	@ApiModelProperty(value = "放行时间戳")
	private String releaseTime;
	@ApiModelProperty(value = "放行员签字")
	private String releaseSign;
	
	@ApiModelProperty(value = "泥尾票ID")
	private String tailId;
	@ApiModelProperty(value = "收纳员ID")
	private String receiverId;
	@ApiModelProperty(value = "收纳员时间戳")
	private String receiveTime;
	@ApiModelProperty(value = "收纳员签字")
	private String receiveSign;
	
	@ApiModelProperty(value = "泥尾工作开始时间")
	private String tailStartTime;
	@ApiModelProperty(value = "泥尾工作结束开始")
	private String tailEndTime;
	@ApiModelProperty(value = "泥票状态[0:等待装车;1:运输中;2:等待卸车;3:结束]")
	private String status;
	
//	@ApiModelProperty(value = "泥头排队(等待装车数量)")
//	private String load;
//	@ApiModelProperty(value = "泥尾排队(等待卸车数量)")
//	private String unload;
}
