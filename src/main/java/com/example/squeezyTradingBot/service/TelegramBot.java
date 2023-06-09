package com.example.squeezyTradingBot.service;

import com.example.squeezyTradingBot.config.BotConfig;
import com.example.squeezyTradingBot.config.WhiteListUserConfig;
import com.example.squeezyTradingBot.model.jpa.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
@Slf4j
public class TelegramBot extends TelegramLongPollingBot {

    final long ADMIN_CHAT_ID = 799008767;
    @Autowired
    private BotConfig botConfig;

    @Autowired
    private MainMenuService mainMenuService;

    @Autowired
    private UserService userService;

    @PostConstruct
    public void init() {
        try {
            execute(new SetMyCommands(mainMenuService.getMainMenuComands(), new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }
        log.info("==" + "Server was starded. Version: " + botConfig.getBotVersion() + "==================================================================================================");
    }

    @Override
    public String getBotUsername() {
        return botConfig.getBotUserName();
    }

    @Override
    public String getBotToken() {
        return botConfig.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        PartialBotApiMethod answer;
        Message message;
        if (update.hasMessage()) {
            message = update.getMessage();
        } else if (update.hasCallbackQuery()) {
            message = update.getCallbackQuery().getMessage();
        } else {
            log.warn("Сообщение не содержит текста и нажатия на кнопку...");
            return;
        }
        User user = userService.getUser(message);
        if (user == null) {
            log.warn("Пользователь отсутствует в белом списке, ответа не получит:" + message.getChatId());
            return;
        }
        answer = mainMenuService.messageProcess(user, update);
        try {
            if (answer instanceof BotApiMethod) {
                execute((BotApiMethod) answer);
            }
            if (answer instanceof SendDocument) {
                execute((SendDocument) answer);
            }
        } catch (TelegramApiException e) {
            log.error("Ошибка во время обработки сообщения: " + e.getMessage());
        }

    }

    private void deleteLastMessage(Update update) throws TelegramApiException {
        EditMessageText editMessageText = new EditMessageText();
        long messageId = update.getCallbackQuery().getMessage().getMessageId();
        long chatId = update.getCallbackQuery().getMessage().getChatId();
        editMessageText.setChatId(String.valueOf(chatId));
        editMessageText.setMessageId((int) messageId);
        editMessageText.setText("Документ готов!");
        execute(editMessageText);
    }
}
