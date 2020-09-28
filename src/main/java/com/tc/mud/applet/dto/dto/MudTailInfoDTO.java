package com.tc.mud.applet.dto.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("泥尾票信息")
public class MudTailInfoDTO {

	@ApiModelProperty(value = "泥尾票ID",hidden = true)
	private String tailId;
	@ApiModelProperty(value = "发布公司ID")
	private String companyId;
	@ApiModelProperty(value = "发布公司名称")
	private String companyName;
	@ApiModelProperty(value = "装车所在区")
	private String area;
	@ApiModelProperty(value = "装车所在地址")
	private String address;
	@ApiModelProperty(value = "开始日期")
	private String startDate;
	@ApiModelProperty(value = "价格")
	private String price;
	@ApiModelProperty(value = "许可证")
	private String permit1;
	@ApiModelProperty(value = "进场土方（土石方协议+地勘地质报告+泥土样本）报备")
	private String permit2;
	@ApiModelProperty(value = "进场驾驶证报备")
	private String permit3;
	@ApiModelProperty(value = "码头进港车辆行驶证报备")
	private String permit4;
	@ApiModelProperty(value = "中标通知书")
	private String permit5;
	@ApiModelProperty(value = "进场通知书")
	private String permit6;
	@ApiModelProperty(value = "泥尾票状态[0:未发布;1:发布;2:下架]")
	private String status;
//	@ApiModelProperty(value = "工作状态[0:未开工;1:开工;2:暂停;3:下班;4:删除]")
//	private String workStatus;
}
