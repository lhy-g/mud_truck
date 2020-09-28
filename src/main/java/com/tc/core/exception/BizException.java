package com.tc.core.exception;

import com.tc.core.constant.ResEnum;

import lombok.Data;

@Data
public class BizException extends RuntimeException {

    private Integer code;

    public BizException end(ResEnum resEnum) {
        this.code = resEnum.getCode();
        return this;
    }

    public BizException(String msg) {
        super(msg);
        this.code = ResEnum.BAD_REQUEST.getCode();
    }

    public BizException(ResEnum resEnum) {
        super(resEnum.getMsg());
        this.code = resEnum.getCode();
    }
}
