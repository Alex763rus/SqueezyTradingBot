package com.example.squeezyTradingBot.rest.controller;

import com.example.squeezyTradingBot.rest.request.BaseResponse;
import com.example.squeezyTradingBot.rest.request.SqueezyStart;
import com.example.squeezyTradingBot.rest.request.Unsorted;
import com.example.squeezyTradingBot.service.DistributionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PreDestroy;

@Slf4j
@RestController
@RequestMapping("/squeezy")
public class SqueezyController {

    private static final String SUCCESS_STATUS = "success";
    private static final String ERROR_STATUS = "error";
    private static final int CODE_SUCCESS = 100;

    @Autowired
    DistributionService distributionService;

    @PostMapping("/start")
    public BaseResponse start(@RequestBody SqueezyStart request) {
        log.info(String.valueOf(request));
        final BaseResponse response = new BaseResponse(SUCCESS_STATUS, CODE_SUCCESS);
        distributionService.sendTgMessageToAllWhiteList(request.toMessage());
        return response;
    }

    @PostMapping("/unsorted")
    public BaseResponse unsorted(@RequestBody Unsorted request) {
        log.info(String.valueOf(request));
        final BaseResponse response = new BaseResponse(SUCCESS_STATUS, CODE_SUCCESS);
        distributionService.sendTgMessageToAllWhiteList(request.toMessage());
        return response;
    }

}
