package com.tc.core.web.responce;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WeChatVO {

    @ApiModelProperty("错误码")
    private Integer errCode;

    @ApiModelProperty("错误信息")
    private String errMsg;
}
