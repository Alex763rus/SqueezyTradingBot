package com.example.squeezyTradingBot.rest.controller;

import com.example.squeezyTradingBot.rest.request.BaseRequest;
import com.example.squeezyTradingBot.rest.request.BaseResponse;
import com.example.squeezyTradingBot.rest.request.PositionClose;
import com.example.squeezyTradingBot.rest.request.PositionOpen;
import com.example.squeezyTradingBot.service.DistributionService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/position")
public class PositionController extends BaseController {

    @GetMapping
    public BaseResponse showStatus() {
        return processGetRequestAndSendResponce("get OK");
    }

    @PostMapping("/open")
    public BaseResponse open(@RequestBody PositionOpen request) {
        return processPostRequestAndSendResponce(request);
    }

    @PostMapping("/close")
    public BaseResponse close(@RequestBody PositionClose request) {
        return processPostRequestAndSendResponce(request);
    }

}
