package com.tc.core.web.responce;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class JsCode2Session extends WeChatVO {

    @ApiModelProperty("用户唯一标识")
    private String openId;

    @ApiModelProperty("会话密钥")
    private String sessionKey;

    @JsonIgnore
    @ApiModelProperty("用户在开放平台的唯一标识符，在满足 UnionID 下发条件的情况下会返回，详见 UnionID 机制说明。")
    private String unionId;


}
