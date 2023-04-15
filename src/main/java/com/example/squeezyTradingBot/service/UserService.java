package com.example.squeezyTradingBot.service;

import com.example.squeezyTradingBot.config.WhiteListUserConfig;
import com.example.squeezyTradingBot.model.jpa.User;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;

import static com.example.squeezyTradingBot.service.function.InitializationFunction.CREATE_USER;

@Slf4j
@Service
@ToString
public class UserService {

    @Autowired
    private StateService stateService;

    @Autowired
    private WhiteListUserConfig whiteListUsers;

    public User getUser(Message message) {
        Long chatId = message.getChatId();
        User user = stateService.getUser(chatId);
        if (user == null) {
            if (!whiteListUsers.getWhiteListChatsID().contains(chatId)) {
                return null;
            }
            user = CREATE_USER.apply(message);
            stateService.registeredUser(user);
        }
        return user;
    }

}