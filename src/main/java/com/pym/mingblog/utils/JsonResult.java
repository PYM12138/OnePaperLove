package com.pym.mingblog.utils;

import com.alibaba.fastjson.JSON;

import java.util.LinkedHashMap;

/**
 * @Author: Ming
 * @Date 2023/7/31 0:05
 * Describe: 控制层Json数据处理
 */
public class JsonResult {

    /**
     * 参照Unix命令行返回值 0代表成功，大于0代表不同的错误
     */
    private static final int DEFAULT_STATUS_SUCCESS = 0;
    private static final int DEFAULT_STATUS_FAIL = 1;

    private static final String DEFAULT_STATUS_KEY = "status";
    private static final String DEFAULT_MESSAGE_KEY = "message";
    private static final String DEFAULT_DATA_KEY = "data";

    private JsonResult() {
    }

    public static JsonData success() {
        return build(DEFAULT_STATUS_KEY, DEFAULT_STATUS_SUCCESS);
    }

    public static JsonData success(Object code) {
        return build(DEFAULT_STATUS_KEY, code);
    }

    public static JsonData success(String codeKey, Object code) {
        return build(codeKey, code);
    }


    public static JsonData fail() {
        return build(DEFAULT_STATUS_KEY, DEFAULT_STATUS_FAIL);
    }

    public static JsonData fail(Object code) {
        return build(DEFAULT_STATUS_KEY, code);
    }

    public static JsonData fail(String codeKey, Object code) {
        return build(codeKey, code);
    }

    /**
     * 构建一个包含指定键值对的JsonData对象 此方法主要用于内部构造响应对象，根据给定的key和value，
     * 判断value是否为CodeType类型，如果是，则同时添加状态码和消息，否则仅添加值
     *
     * @param key 键，用于标识响应数据的类型，如"success"或"fail"
     * @param value 值，实际响应的数据，可以是任意类型，如果实现了CodeType接口，则会添加状态码和消息
     * @return 返回一个JsonData对象，包含构造的响应数据
     */
    private static JsonData build(String key, Object value) {
        JsonData jsonData = new JsonData(3);
        if(value instanceof CodeType){
            jsonData.put(key, ((CodeType) value).getCode());
            jsonData.put(DEFAULT_MESSAGE_KEY, ((CodeType) value).getMessage());
        } else {
            jsonData.put(key, value);
        }

        return jsonData;
    }

    public static JsonData build() {
        return new JsonData();
    }

    //包装DataMap的数据，因为DataMap会去接受各种类型数据，不方便转换，所以这里统一转换
    public static JsonData build(DataMap dataMap) {
        JsonData jsonData = new JsonData(3);
        if (dataMap.getCode() != null) {
            jsonData.put(DEFAULT_STATUS_KEY, dataMap.getCode());
        } else {
            if (dataMap.getIsSuccess() != null) {//这里判断数据是否成功，然后返回状态码
                if (dataMap.getIsSuccess()) {
                    jsonData.put(DEFAULT_STATUS_KEY, DEFAULT_STATUS_SUCCESS);
                } else {
                    jsonData.put(DEFAULT_STATUS_KEY, DEFAULT_STATUS_FAIL);
                }
            }
        }
        if (dataMap.getMessage() != null) {
            jsonData.put(DEFAULT_MESSAGE_KEY, dataMap.getMessage());
        }
        if (dataMap.getData() != null) {
            jsonData.put(DEFAULT_DATA_KEY, dataMap.getData());
        }
        return jsonData;
    }

    public static class JsonData extends LinkedHashMap {
        //序列化版本
        private static final long serialVersionUID = 1L;

        private JsonData() {
        }

        //size 传递给父类构造函数的参数，用于指定数据的大小
        private JsonData(int size) {
            super(size);
        }
        //额外扩展的，当你的数据不是DataMap的时候，又需要去转换为JSON，就用这下面的方法
        public JsonData message(String messageKey, String message) {
            this.put(messageKey, message);
            return this;
        }

        public JsonData message(String message) {
            return message(DEFAULT_MESSAGE_KEY, message);
        }

        public JsonData data(String dataKey, Object data) {
            this.put(dataKey, data);
            return this;
        }

        public JsonData data(Object data) {
            return data(DEFAULT_DATA_KEY, data);
        }

        public JsonData add(String key, Object value) {
            this.put(key, value);
            return this;
        }

        public String toJSON() {
            //这个方法只能将基本的数据类型和数据集合转换为JSON格式的String类型，所以DataMap是不能直接转换的
            return JSON.toJSONString(this);
        }
    }

}
