package com.example.squeezyTradingBot.service.ping;

import com.example.squeezyTradingBot.config.BotConfig;
import com.example.squeezyTradingBot.rest.request.squeezy.SqueezyNoPing;
import com.example.squeezyTradingBot.service.DistributionService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
@Getter
@Setter
public class CheckSqueezyPingService implements Runnable {

    private static volatile Long lastPingTime;

    @Autowired
    private BotConfig botConfig;
    @Autowired
    private DistributionService distributionService;

    @PostConstruct
    void init() {
        if(!botConfig.getOsaPingEnabled()){
            return;
        }
        updateLastPingTime();
        Thread thread = new Thread(this::run);
        thread.setDaemon(true);
        thread.start();
    }

    public static void updateLastPingTime(){
        lastPingTime = System.currentTimeMillis();
    }
    @Override
    public void run() {
        try {
            while (true) {
                if ((System.currentTimeMillis() - lastPingTime) > botConfig.getOsaPingDelay()) {
                    distributionService.sendTgMessageToAllWhiteList((new SqueezyNoPing()).toMessage());
                }
                Thread.sleep(botConfig.getOsaPingDelay());
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
