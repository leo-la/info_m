package com.tfswx.exception;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;

/**
 * 统一异常处理器
 *
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 处理自定义的业务异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value = BaseException.class)
    @ResponseBody
    public ResultBody bizExceptionHandler(HttpServletRequest req, BaseException e){
        logger.error("发生业务异常！原因是：{}",e.getErrorMsg());
        return ResultBody.error(e.getErrorCode(),e.getErrorMsg());
    }

    /**
     * 处理空指针的异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =NullPointerException.class)
    @ResponseBody
    public ResultBody nullExceptionHandler(HttpServletRequest req, NullPointerException e){
        logger.error("发生空指针异常！原因是:",e);
        return ResultBody.error(exceptionEnum.BODY_NOT_MATCH);
    }


    /**
     * 处理其他异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =Exception.class)
    @ResponseBody
    public ResultBody exceptionHandler(HttpServletRequest req, Exception e){
        logger.error("未知异常！原因是:",e);
        return ResultBody.error(exceptionEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 数据库操作异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =DataIntegrityViolationException.class)
    @ResponseBody
    public ResultBody dataIntegrityViolationExceptionHandler(HttpServletRequest req, Exception e){
        logger.error("数据库操作异常！原因是:",e);
        return ResultBody.error(exceptionEnum.INTERNAL_SERVER_ERROR);
    }

    /**
     * 指定文件文件未找到异常
     * @param req
     * @param e
     * @return
     */
    @ExceptionHandler(value =FileNotFoundException.class)
    @ResponseBody
    public ResultBody fileNotFoundException(HttpServletRequest req, Exception e){
        logger.error("指定文件文件未找到异常！原因是:",e);
        return ResultBody.error(exceptionEnum.INTERNAL_SERVER_ERROR);
    }
}