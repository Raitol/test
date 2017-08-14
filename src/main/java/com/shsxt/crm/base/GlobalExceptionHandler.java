package com.shsxt.crm.base;

import com.shsxt.crm.exception.ParamException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler extends BaseController {
    @ExceptionHandler(value = ParamException.class)
    @ResponseBody
    public ResultInfo handlerParamException(ParamException e) {
        return failure(e);
    }

}
