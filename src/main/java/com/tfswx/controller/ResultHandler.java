package com.tfswx.controller;

import com.tfswx.common.RequestStatus;
import com.tfswx.pojo.RequestResult;


public class ResultHandler {

    /**
     * 请求成功
     *
     * @param value 业务值
     * @param note  说明
     * @return
     */
    protected RequestResult success(Object value, String note) {
        return createRequestResult(RequestStatus.OK, value, note);
    }

    /**
     * 请求失败
     *
     * @param value 业务值
     * @param note  说明
     * @return
     */
    protected RequestResult failure(Object value, String note) {
        return createRequestResult(RequestStatus.ERROR, value, note);
    }

    /**
     * 请求拒绝
     *
     * @param value 业务值
     * @param note  说明
     * @return
     */
    protected RequestResult reject(Object value, String note) {
        return createRequestResult(RequestStatus.REJECT, value, note);
    }

    /**
     * 创建请求结果对象
     *
     * @param requestStatus
     * @param value
     * @param note
     * @return
     */
    private RequestResult createRequestResult(RequestStatus requestStatus, Object value, String note) {
        RequestResult result = new RequestResult();
        result.setStatus(requestStatus.getCode());
        result.setValue(value);
        result.setNote(note);
        return result;
    }
}
