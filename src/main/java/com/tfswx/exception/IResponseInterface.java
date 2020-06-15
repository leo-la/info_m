package com.tfswx.exception;

public interface IResponseInterface {

    /**
     * 异常码
     * @return
     */
    String getCode();

    /**
     * 异常信息
     * @return
     */
    String getMessage();
}
