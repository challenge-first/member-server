package com.example.memberserver.member.dto.response;


import lombok.Getter;

@Getter
public class ResponseTokenDto {

    private String headerName;
    private String accessTokenValue;

    public ResponseTokenDto(String headerName, String accessTokenValue) {
        this.headerName = headerName;
        this.accessTokenValue = accessTokenValue;
    }
}
