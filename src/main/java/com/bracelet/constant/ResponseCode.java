package com.bracelet.constant;

public enum  ResponseCode {
    //响应码
    SERVER_ERROR(-1),
    REQUEST_SUCCESSED(0),
    PARAM_ILLEGAL(1),
    ENTITY_NOT_EXISTS(2),
    PASSWORD_ERROR(3),
    ENTITY_ALREADY_EXISTS(4),
    NOT_LOGIN(5),
    TIME_INVALID(6),
    ALREADY_LOGIN(9),
    PARAM_ERROR(10),
    RETURN_EMPTY(11),
    TOKEN_ERROR(12);

    private int value;

    ResponseCode(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }

}
