package com.design.common.utils;

import java.util.List;

public class JsonResult<T> {
    private Integer code;
    private String message;
    private T result;
    private T data;
    private List<T> resultList;
    private Integer total;

    public JsonResult() {
        super();
        this.code = ResultCode.SUCCESS.getCode();
        this.message = ResultCode.SUCCESS.getMessage();
    }

    public JsonResult(ResultCode code) {
        super();
        setResultCode(code);
    }

    public static JsonResult build(){
        return new JsonResult();
    }

    public static JsonResult error(){
        return new JsonResult(ResultCode.FAILURE);
    }

    public static <T> JsonResult buildResp(T t){
        JsonResult result = build();
        result.setResult(t);
        return result;
    }
    public static <T> JsonResult buildErrorResp(T t){
        JsonResult result = error();
        result.setResult(t);
        return result;
    }
    public static <T> JsonResult buildData(T t){
        JsonResult result = build();
        result.setData(t);
        return result;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setResultCode(ResultCode code){
        this.code = code.getCode();
        this.message = code.getMessage();
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public List<T> getResultList() {
        return resultList;
    }

    public void setResultList(List<T> resultList) {
        this.resultList = resultList;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
