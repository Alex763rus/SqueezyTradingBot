package com.example.squeezyTradingBot.model.mainMenu;

import com.example.squeezyTradingBot.service.ButtonService;
import com.example.squeezyTradingBot.service.ExcelService;
import com.example.squeezyTradingBot.service.StateService;
import com.example.squeezyTradingBot.service.database.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class MainMenu implements MainMenuActivity {

    @Autowired
    protected UserService userService;

    @Autowired
    protected StateService stateService;

    @Autowired
    protected ButtonService buttonService;

    @Autowired
    protected ExcelService excelService;

}
