package com.tc.mud.applet.dto.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("泥尾票交易信息")
public class MudTailTranDTO {

	@ApiModelProperty(value = "泥尾票ID",hidden = true)
	private String tailId;
	@ApiModelProperty(value = "购买公司ID")
	private String companyId;
	@ApiModelProperty(value = "购买公司名称")
	private String companyName;
//	@ApiModelProperty(value = "泥尾票状态[0:未发布;1:发布;2:下架]")
//	private String status;
//	@ApiModelProperty(value = "工作状态[0:未开工;1:开工;2:暂停;3:下班;4:删除]")
//	private String workStatus;
}
