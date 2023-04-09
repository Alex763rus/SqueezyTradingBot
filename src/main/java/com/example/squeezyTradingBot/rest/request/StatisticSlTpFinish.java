package com.example.squeezyTradingBot.rest.request;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class StatisticSlTpFinish extends BaseRequest {
    private String fileName;

    @Override
    public String toMessage() {
        return fileName;
    }

}
