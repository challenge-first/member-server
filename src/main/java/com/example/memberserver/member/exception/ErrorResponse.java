package com.example.memberserver.member.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {

    private int code;
    private String msg;

    public ErrorResponse(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
