package com.example.squeezyTradingBot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    @Value("${only.user}")
    Long onlyUser;

    @Bean
    public WhiteListUserConfig whiteListUsers() throws URISyntaxException, IOException {
        WhiteListUserConfig whiteListUserConfig;
        ObjectMapper objectMapper = new ObjectMapper();
        URL url = this.getClass().getClassLoader().getResource("WhiteListUsers.json");
        if(onlyUser != null){
            whiteListUserConfig = new WhiteListUserConfig();
            Set<Long> whiteList = new HashSet();
            whiteList.add(onlyUser);
            whiteListUserConfig.setWhiteListChatsID(whiteList);
        }else{
            whiteListUserConfig = objectMapper.readValue(String.join(System.lineSeparator(), Files.readAllLines(Paths.get(url.toURI()))), WhiteListUserConfig.class);
        }
        return whiteListUserConfig;
    }
}
