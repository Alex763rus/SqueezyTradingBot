package com.example.squeezyTradingBot.model.statistic;

import lombok.Data;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder(setterPrefix = "set", builderMethodName = "init", toBuilder = true)
public class TestData {


    public TestData() {
        squeezyPercent = 0;
        maxProfitPercent = 0;
        maxLossPercent = 0;
    }

    public TestData(String groupType, String side, double squeezyPercent, double maxProfitPercent, double maxLossPercent) {
        this.groupType = groupType;
        this.side = side;
        this.squeezyPercent = squeezyPercent;
        this.maxProfitPercent = maxProfitPercent;
        this.maxLossPercent = maxLossPercent;
    }

    private String groupType;
    private String side;

    private double squeezyPercent;

    private double maxProfitPercent;

    private double maxLossPercent;
}
