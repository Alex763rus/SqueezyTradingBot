package com.example.squeezyTradingBot.service.function;

import com.example.squeezyTradingBot.model.jpa.User;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Timestamp;
import java.util.function.Function;

public class InitializationFunction {

    public static final Function<Message, User> CREATE_USER =
            source -> User.init()
                    .setChatId(source.getChatId())
                    .setFirstName(source.getChat().getFirstName())
                    .setLastName(source.getChat().getLastName())
                    .setUserName(source.getChat().getUserName())
                    .setRegisteredAt(new Timestamp(System.currentTimeMillis()))
                    .build();

}
