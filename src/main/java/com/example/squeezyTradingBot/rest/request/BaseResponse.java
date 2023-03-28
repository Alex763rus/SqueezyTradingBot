package com.example.squeezyTradingBot.rest.request;

import lombok.Data;

@Data
public class BaseResponse {
    private final String status;
    private final Integer code;

    public BaseResponse(String status, Integer code) {
        this.status = status;
        this.code = code;
    }
}
