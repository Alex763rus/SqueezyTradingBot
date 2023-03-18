package com.example.squeezyTradingBot.model.mainMenu;

import com.example.squeezyTradingBot.service.StateService;
import com.example.squeezyTradingBot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class MainMenu implements MainMenuActivity {

    @Autowired
    protected UserService userService;

    @Autowired
    protected StateService stateService;

}
