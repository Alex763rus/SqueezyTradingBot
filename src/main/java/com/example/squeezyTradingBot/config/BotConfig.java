package com.example.squeezyTradingBot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.*;

@Configuration
@Data
@PropertySource("application.properties")
@EnableWebMvc
@EnableTransactionManagement
public class BotConfig {

    @Value("${bot.version}")
    String botVersion;


    @Value("${bot.stand}")
    String botStand;

    @Value("${bot.username}")
    String botUserName;

    @Value("${bot.token}")
    String botToken;

    @Value("${statistic.path.source}")
    String statisticPathSource;

    @Bean
    public WhiteListUserConfig whiteListUsers() throws URISyntaxException, IOException {
        WhiteListUserConfig whiteListUserConfig = new WhiteListUserConfig();
        Set<Long> whiteListChatsID = new HashSet<>();
        whiteListChatsID.add(799008767L);
        whiteListChatsID.add(358667973L);
        whiteListUserConfig.setWhiteListChatsID(whiteListChatsID);
        return whiteListUserConfig;
    }

}
