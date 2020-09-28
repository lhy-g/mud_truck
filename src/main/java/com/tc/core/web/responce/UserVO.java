package com.tc.core.web.responce;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@ApiModel("用户视图")
@Data
public class UserVO {

    @ApiModelProperty("ID")
    private Long id;

    @ApiModelProperty("昵称")
    private String nickname;

    @ApiModelProperty("手机号")
    private String phone;
    
    @ApiModelProperty("用户unionId")
    private String unionId;
    
    @ApiModelProperty("用户Id")
    private String userId;
    
    @ApiModelProperty("用户类型")
    private String type;
    
    @ApiModelProperty("用户所在公司Id")
    private String companyId;
    
    @ApiModelProperty("头像链接")
    private String headPortraitLink;

    @ApiModelProperty("授权数")
    private String authorizeCount;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;
}
