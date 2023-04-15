package com.example.squeezyTradingBot.rest.request.statistic;

import com.example.squeezyTradingBot.rest.request.BaseRequest;
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
