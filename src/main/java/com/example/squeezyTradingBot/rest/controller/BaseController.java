package com.example.squeezyTradingBot.rest.controller;

import com.example.squeezyTradingBot.rest.request.BaseRequest;
import com.example.squeezyTradingBot.rest.request.BaseResponse;
import com.example.squeezyTradingBot.service.DistributionService;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.objects.InputFile;

@Slf4j
public abstract class BaseController {
    public static final String SUCCESS_STATUS = "success";
    public static final String ERROR_STATUS = "error";
    public static final int CODE_SUCCESS = 100;
    @Autowired
    private DistributionService distributionService;

    protected BaseResponse processGetRequestAndSendResponce(String message){
        distributionService.sendTgMessageToAllWhiteList(message);
        return new BaseResponse(SUCCESS_STATUS, CODE_SUCCESS);
    }

    protected BaseResponse processPostRequestAndSendResponce(BaseRequest baseRequest){
        log.info(String.valueOf(baseRequest));
        distributionService.sendTgMessageToAllWhiteList(baseRequest.toMessage());
        return new BaseResponse(SUCCESS_STATUS, CODE_SUCCESS);
    }

    protected BaseResponse processPostRequestAndSendResponce(BaseRequest baseRequest, InputFile inputFile){
        log.info(String.valueOf(baseRequest));
        distributionService.sendTgMessageToAllWhiteList(inputFile);
        return new BaseResponse(SUCCESS_STATUS, CODE_SUCCESS);
    }

}
