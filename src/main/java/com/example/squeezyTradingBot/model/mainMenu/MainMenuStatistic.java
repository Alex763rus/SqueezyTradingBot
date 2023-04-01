package com.example.squeezyTradingBot.model.mainMenu;

import com.example.squeezyTradingBot.config.BotConfig;
import com.example.squeezyTradingBot.model.statistic.TestData;
import com.example.squeezyTradingBot.model.jpa.User;
import com.example.squeezyTradingBot.service.ExcelService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.PartialBotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class MainMenuStatistic extends MainMenu {

    final String MENU_NAME = "/statistic";

    @Autowired
    private ExcelService excelService;

    @Autowired
    private BotConfig botConfig;

    @Override
    public String getMenuName() {
        return MENU_NAME;
    }

    @Override
    public PartialBotApiMethod menuRun(User user, Update update) {
        long chatId = update.getMessage().getChatId();
        List<List<String>> excelData = getExcelData();
        SendDocument sendDocument = new SendDocument();
        sendDocument.setDocument(excelService.createExcelDocument(instrument, excelData));
        sendDocument.setChatId(String.valueOf(chatId));
        return sendDocument;
    }

    private String instrument;
    private double stepSqueezy;
    private double stepProfit;
    private double stepLoss;

    private List<List<String>> getExcelData() {
        List<List<String>> excelData = new ArrayList<>();
        ArrayList<TestData> testData = new ArrayList<>();
        boolean isHead = true;
        try (BufferedReader reader = new BufferedReader(new FileReader(botConfig.getStatisticPathSource()))) {
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
                testData.add(
                        new TestData(lineArr[0] //groupType FlatSell
                                , lineArr[1]//side Sell
                                , Double.parseDouble(lineArr[2])//percent 1,0
                                , Double.parseDouble(lineArr[3])//percentProfit 1,5
                                , Double.parseDouble(lineArr[4]))//percentLoss 1,5
                );
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            Files.deleteIfExists(Path.of(botConfig.getStatisticPathSource()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        List<List<String>> excelData = new ArrayList<>();
        Map<Double, Map<Double, Long>> groupTypeResult =
                testData.stream().filter(e -> e.getGroupType().equalsIgnoreCase(groupType))
                        .collect(Collectors.groupingBy(TestData::getSqueezyPercent,
                                Collectors.groupingBy(TestData::getMaxProfitPercent, Collectors.counting())));

        TestData defaultTetData = new TestData();
        TestData maxSqueezyPercent = testData.stream().
                filter(e -> e.getGroupType().equalsIgnoreCase(groupType))
                .max(Comparator.comparingDouble(TestData::getSqueezyPercent))
                .orElse(defaultTetData);

        TestData maxProfitPercent = testData.stream().
                filter(e -> e.getGroupType().equalsIgnoreCase(groupType))
                .max(Comparator.comparingDouble(TestData::getMaxProfitPercent))
                .orElse(defaultTetData);

        //Заголовок:
        ArrayList<String> headers = new ArrayList<>();
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
            Map<Double, Long> line = groupTypeResult.get(Double.valueOf(y));
            ArrayList<String> excelDataLine = new ArrayList<>();
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
        List<List<String>> excelData = new ArrayList<>();
        Map<Double, Map<Double, Long>> groupTypeResult =
                testData.stream().filter(e -> e.getGroupType().equalsIgnoreCase(groupType))
                        .collect(Collectors.groupingBy(TestData::getSqueezyPercent,
                                Collectors.groupingBy(TestData::getMaxLossPercent, Collectors.counting())));

        TestData defaultTetData = new TestData();
        TestData maxSqueezyPercent = testData.stream().
                filter(e -> e.getGroupType().equalsIgnoreCase(groupType))
                .max(Comparator.comparingDouble(TestData::getSqueezyPercent))
                .orElse(defaultTetData);

        TestData maxLossPercent = testData.stream().
                filter(e -> e.getGroupType().equalsIgnoreCase(groupType))
                .max(Comparator.comparingDouble(TestData::getMaxLossPercent))
                .orElse(defaultTetData);

        //Заголовок:
        ArrayList<String> headers = new ArrayList<>();
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
            Map<Double, Long> line = groupTypeResult.get(Double.valueOf(y));
            ArrayList<String> excelDataLine = new ArrayList<>();
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
    @Override
    public String getDescription() {
        return "Статистика";
    }
}
