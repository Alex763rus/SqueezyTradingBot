package com.example.squeezyTradingBot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashSet;
import java.util.Set;

@Configuration
@Data
@PropertySource("application.properties")
public class BotConfig {

    @Value("${bot.version}")
    String botVersion;

    @Value("${bot.username}")
    String botUserName;

    @Value("${bot.token}")
    String botToken;

    @Value("${message.path}")
    String messagePath;

    @Bean
    public WhiteListUserConfig whiteListUsers(){
        WhiteListUserConfig whiteListUserConfig = new WhiteListUserConfig();
        Set<Long> whiteListChatsID = new HashSet<>();
        whiteListChatsID.add(799008767L);
        whiteListChatsID.add(358667973L);
        whiteListUserConfig.setWhiteListChatsID(whiteListChatsID);
        return whiteListUserConfig;
    }
}
