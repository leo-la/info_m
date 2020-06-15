package com.tfswx.exception;

/**
 * 自定义异常类
 */
public class BaseException extends RuntimeException{

    private static final long serialVersionUID = 1L;

    /**
     * 异常码
     */
    protected String errorCode;

    /**
     * 异常信息
     */
    protected String errorMsg;

    public BaseException(){
        super();
    }

    public BaseException(IResponseInterface responseEnum){
        super(responseEnum.getCode());
        this.errorCode = responseEnum.getCode();
        this.errorMsg = responseEnum.getMessage();
    }

    public BaseException(IResponseInterface responseEnum,Throwable cause){
        super(responseEnum.getCode(),cause);
        this.errorCode = responseEnum.getCode();
        this.errorMsg = responseEnum.getMessage();
    }

    public BaseException(String errorMsg){
        super(errorMsg);
        this.errorMsg = errorMsg;
    }

    public BaseException(String errorMsg,String errorCode){
        super(errorCode);
        this.errorMsg = errorMsg;
        this.errorCode = errorCode;
    }

    public BaseException(String errorCode,String errorMsg,Throwable cause){
        super(errorCode,cause);
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
