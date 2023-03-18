package com.example.squeezyTradingBot.service;

import com.example.squeezyTradingBot.config.WhiteListUserConfig;
import com.example.squeezyTradingBot.model.jpa.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@Slf4j
public class DistributionService {

    @Autowired
    private WhiteListUserConfig whiteListUserConfig;

    @Autowired
    private TelegramBot telegramBot;

    public void sendTgMessageToAllWhiteList(String message){
        try {
            for (Long chatId:whiteListUserConfig.getWhiteListChatsID()) {
                telegramBot.execute(new SendMessage(chatId.toString(), message));
            }
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

}
