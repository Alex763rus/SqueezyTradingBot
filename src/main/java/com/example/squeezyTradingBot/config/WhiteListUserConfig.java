package com.example.squeezyTradingBot.config;


import lombok.Data;

import java.util.Set;

@Data
public class WhiteListUserConfig {
    private Set<Long> whiteListChatsID;

}
