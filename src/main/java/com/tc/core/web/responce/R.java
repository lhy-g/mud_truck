package com.tc.core.web.responce;

import com.tc.core.constant.ResEnum;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("响应体")
public class R<T> {

    @ApiModelProperty("代码")
    private Integer code;

    @ApiModelProperty("消息")
    private String msg;

    @ApiModelProperty("数据")
    private T data;

    public Integer getCode() {
        return code;
    }

    public R<T> setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public R<T> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public T getData() {
        return data;
    }

    public R<T> setData(T data) {
        this.data = data;
        return this;
    }

    public static R success() {
        R r = new R();
        r.setCode(ResEnum.SUCCESS.getCode());
        r.setMsg(ResEnum.SUCCESS.getMsg());
        return r;
    }

    public static R fail() {
        R r = new R();
        r.setCode(ResEnum.BAD_REQUEST.getCode());
        r.setMsg(ResEnum.BAD_REQUEST.getMsg());
        return r;
    }

    public static R fail(ResEnum resEnum) {
        R r = new R();
        r.setCode(resEnum.getCode());
        r.setMsg(resEnum.getMsg());
        return r;
    }
}
