package com.example.squeezyTradingBot.rest.controller;

import com.example.squeezyTradingBot.rest.request.BaseResponse;
import com.example.squeezyTradingBot.rest.request.PositionClose;
import com.example.squeezyTradingBot.rest.request.PositionOpen;
import com.example.squeezyTradingBot.service.DistributionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/position")
public class PositionController {

    private static final String SUCCESS_STATUS = "success";
    private static final String ERROR_STATUS = "error";
    private static final int CODE_SUCCESS = 100;

    @Autowired
    DistributionService distributionService;

    @GetMapping
    public BaseResponse showStatus() {
        distributionService.sendTgMessageToAllWhiteList(String.valueOf("get OK"));
        return new BaseResponse(SUCCESS_STATUS, 1);
    }

    @PostMapping("/open")
    public BaseResponse open(@RequestBody PositionOpen request) {
        log.info(String.valueOf(request));
        final BaseResponse response = new BaseResponse(SUCCESS_STATUS, CODE_SUCCESS);
        distributionService.sendTgMessageToAllWhiteList(request.toMessage());
        return response;
    }

    @PostMapping("/close")
    public BaseResponse close(@RequestBody PositionClose request) {
        log.info(String.valueOf(request));
        final BaseResponse response = new BaseResponse(SUCCESS_STATUS, CODE_SUCCESS);
        distributionService.sendTgMessageToAllWhiteList(request.toMessage());
        return response;
    }

}
