package com.example.squeezyTradingBot.model.statistic;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(setterPrefix = "set", builderMethodName = "init", toBuilder = true)
public class TestData {

    private String groupType;
    private String side;
    private double squeezyPercent;
    private double maxProfitPercent;
    private double maxLossPercent;
}
