package com.example.squeezyTradingBot.rest.controller;

import com.example.squeezyTradingBot.rest.request.BaseResponse;
import com.example.squeezyTradingBot.rest.request.SqueezyBlocking;
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
public class SqueezyController extends BaseController {

    @PostMapping("/start")
    public BaseResponse start(@RequestBody SqueezyStart request) {
        return processPostRequestAndSendResponce(request);
    }

    @PostMapping("/blocking")
    public BaseResponse blocking(@RequestBody SqueezyBlocking request) {
        return processPostRequestAndSendResponce(request);
    }

    @PostMapping("/unsorted")
    public BaseResponse unsorted(@RequestBody Unsorted request) {
        return processPostRequestAndSendResponce(request);
    }

}
