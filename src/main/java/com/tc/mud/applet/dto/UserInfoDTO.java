package com.tc.mud.applet.dto;

 

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户信息")
 
public class UserInfoDTO {
	@ApiModelProperty(value = "用户unionId")
	private String unionId;
	@ApiModelProperty(value = "用户ID")
	private String userId;
	@ApiModelProperty(value = "用户昵称")
	private String niceName;
	@ApiModelProperty(value = "用户手机")
	private String phone;
	@ApiModelProperty(value = "用户头像Url")
	private String headUrl;
	@ApiModelProperty(value = "用户状态")
	private String status;
	@ApiModelProperty(value = "用户类型")
	private String type;
}
