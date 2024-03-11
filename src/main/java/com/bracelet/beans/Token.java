package com.bracelet.beans;

import java.util.HashMap;
import java.util.Map;

public class Token {
    //未使用
    private String id;
    private Map<String,Object> extend = new HashMap<String,Object>();

    public Token(String id, Map<String, Object> extend) {
        this.id = id;
        this.extend = extend;
    }

    public Token() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Map<String, Object> getExtend() {
        return extend;
    }

    public void setExtend(Map<String, Object> extend) {
        this.extend = extend;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", extend=" + extend +
                '}';
    }
}
