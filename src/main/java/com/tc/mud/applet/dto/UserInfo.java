package com.tc.mud.applet.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("用户信息")
public class UserInfo {
	
	 
	@ApiModelProperty(value = "用户unionId")
	private String unionId;
	/*
	 * userId =  unionId.substring(20)
	 */
	@ApiModelProperty(value = "用户Id")
	private String userId;
	
	@ApiModelProperty(value = "用户昵称")
	private String nickname;
	
	@ApiModelProperty(value = "用户手机")
	private String phone;
	
	@ApiModelProperty(value = "用户头像Url")
	private String headUrl;
	
	@ApiModelProperty(value = "用户状态")
	private String status;
	
	@ApiModelProperty(value = "用户类型")
	private String type;
	
//	@ApiModelProperty(value = "用户公钥")
//	private String publicKey;
//	
//	@ApiModelProperty(value = "用户二维码名片url")
//	private String RqCodeUrl;

}
