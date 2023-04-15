package com.example.squeezyTradingBot.rest.controller;

import com.example.squeezyTradingBot.rest.responce.BaseResponse;
import com.example.squeezyTradingBot.rest.request.position.PositionClose;
import com.example.squeezyTradingBot.rest.request.position.PositionOpen;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/position")
public class PositionController extends BaseController {

    @PostMapping("/open")
    public BaseResponse open(@RequestBody PositionOpen request) {
        return processPostRequestAndSendResponce(request);
    }

    @PostMapping("/close")
    public BaseResponse close(@RequestBody PositionClose request) {
        return processPostRequestAndSendResponce(request);
    }

}
