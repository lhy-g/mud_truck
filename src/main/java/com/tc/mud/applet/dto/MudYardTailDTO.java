package com.tc.mud.applet.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("泥尾场")
public class MudYardTailDTO {

	@ApiModelProperty(value = "泥场ID")
	private String yardId;
	@ApiModelProperty(value = "泥场名称" )
	private String yardName;
	@ApiModelProperty(value = "公司ID",hidden = true)
	private String companyId;
	@ApiModelProperty(value = "泥场地址")
	private String address;
	@ApiModelProperty(value = "开工许可证")
	private String permit1;
	@ApiModelProperty(value = "进场土方（土石方协议+地勘地质报告+泥土样本）报备")
	private String permit2;
	@ApiModelProperty(value = "进场驾驶证报备")
	private String permit3;
	@ApiModelProperty(value = "进场车辆行驶证报备")
	private String permit4;
	@ApiModelProperty(value = "中标通知书")
	private String permit5;
	@ApiModelProperty(value = "进场通知书")
	private String permit6;
	@ApiModelProperty(value = "泥场认证状态[0:申请;1:通过;2:未通过]")
	private String status;
//	@ApiModelProperty(value = "工作状态[0:未开工;1:开工;2:暂停;3:下班;4:删除]")
//	private String workStatus;
}
