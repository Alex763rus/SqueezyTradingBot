package com.example.squeezyTradingBot.service;

import com.example.squeezyTradingBot.config.BotConfig;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.nio.file.Paths;

@Service
@Slf4j
@Data
public class IntegrationService implements Runnable {

    @Autowired
    private BotConfig botConfig;

    @Autowired
    private DistributionService distributionService;

    @PostConstruct
    public void init(){
        Thread thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
        distributionService.sendTgMessageToAllWhiteList("Server was started. Version: " + botConfig.getBotVersion());
    }
    @Override
    public void run() {
        File dir = new File(botConfig.getMessagePath());
        try {
            if (!dir.exists() || !dir.isDirectory()) {
                log.error("Отсутствует каталог для собщений. Оповещение работать не будет!:" + botConfig.getMessagePath());
                distributionService.sendTgMessageToAllWhiteList("Отсутствует каталог для собщений. Оповещение работать не будет!:" + botConfig.getMessagePath());
                return;
            }
            waitMessages(dir);
        } catch (InterruptedException e) {
            log.error(e.getMessage());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void waitMessages(File dirMessages) throws InterruptedException, IOException {
        while (true) {
            Thread.sleep(10000);
            File[] files = dirMessages.listFiles();
            if (files.length > 0) {
                for (int i = 0; i < files.length; ++i){
                    if (!files[i].canRead() || !files[i].canExecute()) {
                        continue;
                    }
                    String content = readFile(String.valueOf(files[i]), StandardCharsets.UTF_8);
                    if(content == null){
                        continue;
                    }
                    log.info("Успешно обработан файл:" + files[i].getName() + " " + content);
                    distributionService.sendTgMessageToAllWhiteList(content);
                    Files.delete(files[i].toPath());
                }
            }
        }
    }
    private String readFile(String path, Charset encoding) throws IOException
    {
        List<String> lines = Files.readAllLines(Paths.get(path), encoding);
        if(lines==null || lines.size()==0){
            return null;
        }
        if(lines.get(0) == null || lines.get(0).length()==0){
            return null;
        }
        return String.join(System.lineSeparator(),lines);
    }
}
