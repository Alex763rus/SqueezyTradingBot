package com.example.squeezyTradingBot.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
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
@Getter
@ToString
@Setter
@EnableWebMvc
@EnableTransactionManagement
@PropertySource("application.properties")
public class BotConfig {

    @Value("${bot.version}")
    String botVersion;

    @Value("${bot.stand}")
    String botStand;

    @Value("${bot.username}")
    String botUserName;

    @Value("${bot.token}")
    String botToken;

    @Value("${osa.ping.enabled}")
    Boolean osaPingEnabled;

    @Value("${osa.ping.delay}")
    Long osaPingDelay;

    @Bean
    public WhiteListUserConfig whiteListUsers() throws URISyntaxException, IOException {
        return WhiteListUserConfig.init()
                .setWhiteListChatsID(Set.of(799008767L/*, 358667973L*/))
                .build();
    }

}
