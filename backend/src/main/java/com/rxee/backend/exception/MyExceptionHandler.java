package com.rxee.backend.exception;

import com.rxee.backend.vo.ResultVo;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResultVo handle(ServiceException se){
        return ResultVo.fail(se.getCode(), se.getMessage());
    }
}
