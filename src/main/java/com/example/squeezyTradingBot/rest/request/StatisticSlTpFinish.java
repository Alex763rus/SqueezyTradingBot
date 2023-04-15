package com.example.squeezyTradingBot.rest.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
@Setter
public class StatisticSlTpFinish extends BaseRequest {
    private String fileName;

    @Override
    public String toMessage() {
        return fileName;
    }

}
