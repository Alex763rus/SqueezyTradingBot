package com.example.squeezyTradingBot.service;

import com.example.squeezyTradingBot.config.BotConfig;
import com.example.squeezyTradingBot.model.jpa.User;
import com.example.squeezyTradingBot.model.mainMenu.*;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MainMenuService {

    @Autowired
    private MainMenuStart mainMenuStart;

    @Autowired
    private MainMenuDefault mainMenuActivityDefault;

    @Autowired
    private StateService stateService;
    private List<MainMenuActivity> mainMenu;

    @PostConstruct
    public void mainMenuInit() {
        mainMenu = new ArrayList();
        mainMenu.add(mainMenuStart);
    }

    public PartialBotApiMethod messageProcess(User user, Update update) {
        MainMenuActivity mainMenuActivity = mainMenu.stream()
                .filter(e -> update.hasMessage() && e.getMenuName().equals(update.getMessage().getText()))
                .findFirst().get();

        if (mainMenuActivity != null) {
            stateService.setMenu(user, mainMenuActivity);
        } else {
            mainMenuActivity = stateService.getMenu(user);
            if (mainMenuActivity == null) {
                log.warn("Не найдена команда с именем: " + update.getMessage().getText());
                mainMenuActivity = mainMenuActivityDefault;
            }
        }
        PartialBotApiMethod mainMenuAnswer = mainMenuActivity.menuRun(user, update);
        return mainMenuAnswer;
    }

    public List<BotCommand> getMainMenuComands() {
        return mainMenu.stream()
                .map(source -> BotCommand.builder().command(source.getMenuName()).description(source.getDescription()).build())
                .toList();
    }
}
