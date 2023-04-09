package com.example.squeezyTradingBot.service;

import com.example.squeezyTradingBot.config.BotConfig;
import com.example.squeezyTradingBot.config.WhiteListUserConfig;
import com.example.squeezyTradingBot.enums.Emoji;
import com.example.squeezyTradingBot.rest.request.BaseResponse;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;

@Service
@Slf4j
public class DistributionService {

    private final String PARSE_MODE = "Markdown";
    @Autowired
    private WhiteListUserConfig whiteListUserConfig;

    @Autowired
    private TelegramBot telegramBot;

    @Autowired
    private BotConfig botConfig;

    @PostConstruct
    public void init() {
        StringBuilder message = new StringBuilder(EmojiParser.parseToUnicode(Emoji.ROBOT_FACE.getCode())).append(" Squeezy telegramm bot was started!\n");
        message.append("Version: ").append(botConfig.getBotVersion());
        sendTgMessageToAllWhiteList(message.toString());
    }

    @PreDestroy
    public void squeezyExit(){
        StringBuilder message = new StringBuilder(EmojiParser.parseToUnicode(Emoji.ROBOT_FACE.getCode())).append(" Squeezy telegramm bot will be *STOPPED*!\n");
        message.append("Version: ").append(botConfig.getBotVersion()).append("\n");
        message.append("*Buy!*");
        sendTgMessageToAllWhiteList(message.toString());
    }

    public void sendTgMessageToAllWhiteList(String message){
        try {
            for (Long chatId:whiteListUserConfig.getWhiteListChatsID()) {
                SendMessage sendMessage = new SendMessage(chatId.toString(), message);
                sendMessage.setParseMode(PARSE_MODE);
                telegramBot.execute(sendMessage);
            }
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

    public void sendTgMessageToAllWhiteList(InputFile inputFile){
        try {
            SendDocument sendDocument = new SendDocument();
            sendDocument.setDocument(inputFile);
            for (Long chatId:whiteListUserConfig.getWhiteListChatsID()) {
                sendDocument.setChatId(String.valueOf(chatId));
                telegramBot.execute(sendDocument);
            }
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }

}
