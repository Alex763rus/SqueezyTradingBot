package com.example.squeezyTradingBot.config;

import lombok.Getter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.util.Set;
@Getter
@SuperBuilder(setterPrefix = "set", builderMethodName = "init", toBuilder = true)
@ToString
public class WhiteListUserConfig {
    private Set<Long> whiteListChatsID;

}
