package com.tc.core.handler;

import com.tc.core.exception.BizException;
import com.tc.core.web.responce.R;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
public class BaseExceptionHandler {

    @ExceptionHandler(value = BizException.class)
    public R handleFebsException(BizException e) {
        log.error("业务错误", e);
        R r = new R();
        r.setCode(e.getCode());
        r.setMsg(e.getMessage());
        return r;
    }
}
