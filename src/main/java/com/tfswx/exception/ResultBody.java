package com.tfswx.exception;

import lombok.Data;

import java.io.Serializable;

/**
 * 请求结果封装
 *
 * @author nl
 * @since 2020年6月15日
 */
@Data
public class ResultBody implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    /**
     * 状态
     */
    private String code;
    /**
     * 说明
     */
    private String message;
    /**
     * 业务值
     */
    private Object result;

    public ResultBody(){
    }

    public ResultBody(IResponseInterface result){
        this.code = result.getCode();
        this.message = result.getMessage();
    }


    /**
     * 成功
     *
     * @return
     */
    public static ResultBody success() {
        return success(null);
    }

    /**
     * 成功
     * @param data
     * @return
     */
    public static ResultBody success(Object data) {
        ResultBody rb = new ResultBody();
        rb.setCode(exceptionEnum.SUCCESS.getCode());
        rb.setMessage(exceptionEnum.SUCCESS.getMessage());
        rb.setResult(data);
        return rb;
    }

    /**
     * 失败
     */
    public static ResultBody error(IResponseInterface errorInfo) {
        ResultBody rb = new ResultBody();
        rb.setCode(errorInfo.getCode());
        rb.setMessage(errorInfo.getMessage());
        rb.setResult(null);
        return rb;
    }

    /**
     * 失败
     */
    public static ResultBody error(String code, String message) {
        ResultBody rb = new ResultBody();
        rb.setCode(code);
        rb.setMessage(message);
        rb.setResult(null);
        return rb;
    }

    /**
     * 失败
     */
    public static ResultBody error( String message) {
        ResultBody rb = new ResultBody();
        rb.setCode("-1");
        rb.setMessage(message);
        rb.setResult(null);
        return rb;
    }

}
