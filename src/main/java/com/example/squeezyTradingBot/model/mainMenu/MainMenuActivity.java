package com.example.squeezyTradingBot.model.mainMenu;

import com.example.squeezyTradingBot.model.jpa.User;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;


public interface MainMenuActivity {

    public String getMenuName();

    public String getDescription();

    public PartialBotApiMethod menuRun(User user, Update update);

}
