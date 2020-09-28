package com.tc.core.exception;

import com.tc.core.constant.ResEnum;

import lombok.Data;
/**
 * 自定义异常
 * @author Admin
 *
 */
@Data
public class BaseException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -2600079017193131299L;
	private Integer code;

    public BaseException end(ResEnum resEnum) {
        this.code = resEnum.getCode();
        return this;
    }

    public BaseException(String msg) {
        super(msg);
        this.code = ResEnum.BAD_REQUEST.getCode();
    }

    public BaseException(ResEnum resEnum) {
        super(resEnum.getMsg());
        this.code = resEnum.getCode();
    }
}
