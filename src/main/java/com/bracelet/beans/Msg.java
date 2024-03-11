package com.bracelet.beans;

import java.util.HashMap;
import java.util.Map;

public class Msg {

    private Integer code;

    private String message;

    private Map<String,Object> extend = new HashMap<String, Object>();

    public Msg add(String key,Object value){
        this.extend.put(key,value);
        return this;
    }

    public static Msg success(){
        Msg result = new Msg();
        result.setCode(1);
        result.setMessage("处理成功!");
        return result;
    }

    public static Msg fail(){
        Msg result = new Msg();
        result.setCode(2);
        result.setMessage("处理失败!");
        return result;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public String getMessage() {
        return message;
    }

}
