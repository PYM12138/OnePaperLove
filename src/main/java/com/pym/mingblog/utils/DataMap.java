package com.pym.mingblog.utils;

import lombok.Data;
import java.util.LinkedHashMap;

/**
 * @Author: Ming
 * @Date 2023/7/30 16:20
 * @Description: 业务层数据统一封装
 */
@Data
public class DataMap<T> extends LinkedHashMap {
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String message;
    private Boolean IsSuccess;
    private T data;


    public static <T> DataMap<T> success() {
        DataMap<T> dataMap = new DataMap<T>();
        dataMap.IsSuccess = true;
        dataMap.code = CodeType.SUCCESS_STATUS.getCode();
        dataMap.message=CodeType.SUCCESS_STATUS.getMessage();
        return dataMap;
    }

    public static <T> DataMap<T> success(T code) {
        DataMap<T> dataMap = new DataMap<T>();
        dataMap.IsSuccess = true;
        if(code instanceof CodeType){ //把instanceOf 理解为 ==
            dataMap.code = ((CodeType) code).getCode();
            dataMap.message = ((CodeType) code).getMessage();
        } else if (code instanceof Integer){
            dataMap.code = (Integer) code;
        }
        return dataMap;
    }
    public static <T> DataMap<T> fail() {
        DataMap<T> dataMap = new DataMap<T>();
        dataMap.IsSuccess = false;
        dataMap.code = CodeType.FAIL_STATUS.getCode();
        dataMap.message=CodeType.FAIL_STATUS.getMessage();
        return dataMap;
    }

    public static <T> DataMap<T> fail(T code) {
        DataMap<T> dataMap = new DataMap<T>();
        dataMap.IsSuccess = false;
        if(code instanceof CodeType){
            dataMap.code = ((CodeType) code).getCode();
            dataMap.message = ((CodeType) code).getMessage();
        } else if (code instanceof Integer){
            dataMap.code = (Integer) code;
        }
        return dataMap;
    }

    public T getData() {
        return data;
    }

    public DataMap<T> setData(T data) {
        this.data = data;
        return this;
    }


}
