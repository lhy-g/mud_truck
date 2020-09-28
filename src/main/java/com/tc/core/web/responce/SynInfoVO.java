package com.tc.core.web.responce;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

@ApiModel("登录视图")
@Builder
@Data
public class SynInfoVO {

    @ApiModelProperty("凭证")
    private String token;
}
