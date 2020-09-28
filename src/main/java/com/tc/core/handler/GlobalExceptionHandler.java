package com.tc.core.handler;

 
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tc.core.constant.ResEnum;
import com.tc.core.exception.BaseException;
import com.tc.core.exception.BizException;
import com.tc.core.web.responce.R;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import java.util.List;
import java.util.Set;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 请求body为空
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public R handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return R.fail().setMsg(e.getMessage());
       // return R.fail(e.getMessage());
    }
    
    /**
     * 统一处理请求参数校验(实体对象传参)
     * 
     * @param e BindException
     * @return R
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public R handleIllegalArgumentException(IllegalArgumentException e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return R.fail().setMsg(e.getMessage());
    }
    
    @ExceptionHandler(BizException.class)
    public R handleBizException(BizException e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return R.fail().setMsg(e.getMessage());
    }
    
    @ExceptionHandler(BaseException.class)
    public R handleBaseException(BaseException e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return R.fail().setMsg(e.getMessage());
    }
    
    /**
     * 统一处理请求参数校验(实体对象传参)
     *
     * @param e BindException
     * @return R
     */
    @ExceptionHandler(BindException.class)
    public R handleBindException(BindException e) {
        log.error(ExceptionUtils.getStackTrace(e));
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String errMsg = getFieldErrorsMsg(fieldErrors);
        return R.fail().setMsg(errMsg);
    }

    /**
     * 统一处理请求参数校验(普通传参)
     *
     * @param e ConstraintViolationException
     * @return R
     */
    @ExceptionHandler(value = ConstraintViolationException.class)
    public R handleConstraintViolationException(ConstraintViolationException e) {
        log.error(ExceptionUtils.getStackTrace(e));
        StringBuilder message = new StringBuilder();
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        for (ConstraintViolation<?> violation : violations) {
            Path path = violation.getPropertyPath();
            String[] pathArr = StringUtils.splitByWholeSeparatorPreserveAllTokens(path.toString(), ".");
            message.append(pathArr[1]).append(violation.getMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return R.fail().setMsg(message.toString());
    }

    /**
     * 入参校验@Valid异常处理,添加此处理器后,在方法入参出就不需要写BindResult errors了,会自动处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error(ExceptionUtils.getStackTrace(e));
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        String errMsg = getFieldErrorsMsg(fieldErrors);
        return R.fail().setMsg(errMsg);
    }

    /**
     * NullPointerException的e.getMessage()可能会为null
     *
     * @param e
     * @return
     */
    @ExceptionHandler(value = NullPointerException.class)
    public R handleNullPointerExceptionn(NullPointerException e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return R.fail(ResEnum.UNKNOWN_ERROR).setMsg("参数错误");
       // return R.fail(500, ExceptionUtils.getMessage(e));
    }

    @ExceptionHandler(value = RuntimeException.class)
    public R handleRuntimeException(RuntimeException e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return R.fail(ResEnum.UNKNOWN_ERROR).setMsg(e.getMessage());
     //   return R.fail(500, e.getMessage());
    }

    @ExceptionHandler(value = Exception.class)
    public R handleException(Exception e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return R.fail(ResEnum.UNKNOWN_ERROR).setMsg(e.getMessage());
      //  return R.fail(500, e.getMessage());
    }

    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable e) {
        log.error(ExceptionUtils.getStackTrace(e));
        return R.fail(ResEnum.UNKNOWN_ERROR).setMsg(e.getMessage());
      //  return R.fail(500, e.getMessage());
    }

    private String getFieldErrorsMsg(List<FieldError> fieldErrors) {
        StringBuilder message = new StringBuilder();
        for (FieldError error : fieldErrors) {
            message.append(error.getField()).append(error.getDefaultMessage()).append(",");
        }
        message = new StringBuilder(message.substring(0, message.length() - 1));
        return message.toString();
    }

}
