package com.example.squeezyTradingBot.rest.controller;

import com.example.squeezyTradingBot.model.statistic.TestData;
import com.example.squeezyTradingBot.rest.request.BaseResponse;
import com.example.squeezyTradingBot.rest.request.SqueezyStart;
import com.example.squeezyTradingBot.rest.request.StatisticSlTpFinish;
import com.example.squeezyTradingBot.service.DistributionService;
import com.example.squeezyTradingBot.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/statistic")
public class StatisticController extends BaseController {

    @Autowired
    private ExcelService excelService;

    private String instrument;
    private double stepSqueezy;
    private double stepProfit;
    private double stepLoss;

    @PostMapping("/sltpfinish")
    public BaseResponse sltpfinish(@RequestBody StatisticSlTpFinish request) {
        log.info(String.valueOf(request));
        val excelData = getExcelData(request.getFileName());
        val fileName = request.getFileName();
        val excelFileName = fileName.substring(fileName.lastIndexOf("\\") + 1, fileName.lastIndexOf(".")) + "_";
        val inputFile = excelService.createExcelDocument(excelFileName, instrument, excelData);
        return processPostRequestAndSendResponce(request, inputFile);
    }

    private List<List<String>> getExcelData(String statisticFileName) {
        val excelData = new ArrayList<List<String>>();
        val testData = new ArrayList<TestData>();
        boolean isHead = true;
        try (val reader = new BufferedReader(new FileReader(statisticFileName))) {
            String buf;
            while ((buf = reader.readLine()) != null) {
                String[] lineArr = buf.replace(",", ".").split(";");
                if (isHead) {
                    instrument = lineArr[0];
                    stepSqueezy = Double.parseDouble(lineArr[1]);
                    stepProfit = Double.parseDouble(lineArr[2]);
                    stepLoss = Double.parseDouble(lineArr[3]);
                    isHead = false;
                    continue;
                }
                testData.add(TestData.init()
                        .setGroupType(lineArr[0])
                        .setSide(lineArr[1])
                        .setSqueezyPercent(Double.parseDouble(lineArr[2]))
                        .setMaxProfitPercent(Double.parseDouble(lineArr[3]))
                        .setMaxLossPercent(Double.parseDouble(lineArr[4]))
                        .build()
                );
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//        try {
//            Files.deleteIfExists(Path.of(botConfig.getStatisticPathSource()));
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
        excelData.addAll(getPrepareDataProfit(testData, "UPBUY"));
        excelData.addAll(getPrepareDataProfit(testData, "UPSELL"));
        excelData.addAll(getPrepareDataProfit(testData, "DOWNBUY"));
        excelData.addAll(getPrepareDataProfit(testData, "DOWNSELL"));
        excelData.addAll(getPrepareDataProfit(testData, "FLATBUY"));
        excelData.addAll(getPrepareDataProfit(testData, "FLATSELL"));

        excelData.addAll(getPrepareDataLoss(testData, "UPBUY"));
        excelData.addAll(getPrepareDataLoss(testData, "UPSELL"));
        excelData.addAll(getPrepareDataLoss(testData, "DOWNBUY"));
        excelData.addAll(getPrepareDataLoss(testData, "DOWNSELL"));
        excelData.addAll(getPrepareDataLoss(testData, "FLATBUY"));
        excelData.addAll(getPrepareDataLoss(testData, "FLATSELL"));
        return excelData;
    }

    private List<List<String>> getPrepareDataProfit(ArrayList<TestData> testData, String groupType) {
        val excelData = new ArrayList<List<String>>();
        val groupTypeResult = testData.stream().filter(e -> e.getGroupType().equalsIgnoreCase(groupType))
                .collect(Collectors.groupingBy(TestData::getSqueezyPercent,
                        Collectors.groupingBy(TestData::getMaxProfitPercent, Collectors.counting())));

        val maxSqueezyPercent = testData.stream().
                filter(e -> e.getGroupType().equalsIgnoreCase(groupType))
                .max(Comparator.comparingDouble(TestData::getSqueezyPercent))
                .orElse(TestData.init().build());

        val maxProfitPercent = testData.stream().
                filter(e -> e.getGroupType().equalsIgnoreCase(groupType))
                .max(Comparator.comparingDouble(TestData::getMaxProfitPercent))
                .orElse(TestData.init().build());

        //Заголовок:
        val headers = new ArrayList<String>();
        excelData.add(Arrays.asList("сквиз", "\\", "профит"));
        for (double x = 0; x <= maxProfitPercent.getMaxProfitPercent(); x = x + stepProfit) {
            if (x == 0) {
                headers.add(groupType);
            }
            headers.add(x + ":");
        }
        excelData.add(headers);

        //Данные:
        for (double y = 0; y <= maxSqueezyPercent.getSqueezyPercent(); y = y + stepSqueezy) {
            val line = groupTypeResult.get(Double.valueOf(y));
            val excelDataLine = new ArrayList<String>();
            excelDataLine.add(y + ":");
            for (double x = 0; x <= maxProfitPercent.getMaxProfitPercent(); x = x + stepProfit) {
                if (line == null) {
                    excelDataLine.add("0");
                } else {
                    excelDataLine.add(String.valueOf(line.getOrDefault(Double.valueOf(x), Long.valueOf(0))));
                }
            }
            excelData.add(excelDataLine);
        }
        return excelData;
    }


    private List<List<String>> getPrepareDataLoss(ArrayList<TestData> testData, String groupType) {
        val excelData = new ArrayList<List<String>>();
        val groupTypeResult = testData.stream().filter(e -> e.getGroupType().equalsIgnoreCase(groupType))
                .collect(Collectors.groupingBy(TestData::getSqueezyPercent,
                        Collectors.groupingBy(TestData::getMaxLossPercent, Collectors.counting())));

        val maxSqueezyPercent = testData.stream().
                filter(e -> e.getGroupType().equalsIgnoreCase(groupType))
                .max(Comparator.comparingDouble(TestData::getSqueezyPercent))
                .orElse(TestData.init().build());

        val maxLossPercent = testData.stream().
                filter(e -> e.getGroupType().equalsIgnoreCase(groupType))
                .max(Comparator.comparingDouble(TestData::getMaxLossPercent))
                .orElse(TestData.init().build());

        //Заголовок:
        val headers = new ArrayList<String>();
        excelData.add(Arrays.asList("сквиз", "\\", "просадка"));
        for (double x = 0; x <= maxLossPercent.getMaxLossPercent(); x = x + stepLoss) {
            if (x == 0) {
                headers.add(groupType);
            }
            headers.add(x + ":");
        }
        excelData.add(headers);

        //Данные:
        for (double y = 0; y <= maxSqueezyPercent.getSqueezyPercent(); y = y + stepSqueezy) {
            val line = groupTypeResult.get(Double.valueOf(y));
            val excelDataLine = new ArrayList<String>();
            excelDataLine.add(y + ":");
            for (double x = 0; x <= maxLossPercent.getMaxLossPercent(); x = x + stepLoss) {
                if (line == null) {
                    excelDataLine.add("0");
                } else {
                    excelDataLine.add(String.valueOf(line.getOrDefault(Double.valueOf(x), Long.valueOf(0))));
                }
            }
            excelData.add(excelDataLine);
        }
        return excelData;
    }
}
