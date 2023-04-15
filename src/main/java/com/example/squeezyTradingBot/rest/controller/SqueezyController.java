package com.example.squeezyTradingBot.rest.controller;

import com.example.squeezyTradingBot.rest.request.squeezy.SqueezyOk;
import com.example.squeezyTradingBot.rest.responce.BaseResponse;
import com.example.squeezyTradingBot.rest.request.squeezy.SqueezyBlocking;
import com.example.squeezyTradingBot.rest.request.squeezy.SqueezyStart;
import com.example.squeezyTradingBot.rest.request.squeezy.Unsorted;
import com.example.squeezyTradingBot.service.ping.CheckSqueezyPingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/squeezy")
public class SqueezyController extends BaseController {

    @GetMapping
    public BaseResponse showStatus() {
        return processGetRequestAndSendResponce("get OK");
    }


    @PostMapping("/ok")
    public BaseResponse start(@RequestBody SqueezyOk request) {
        CheckSqueezyPingService.updateLastPingTime();
        return processPostRequestAndSendResponce(request);
    }

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
