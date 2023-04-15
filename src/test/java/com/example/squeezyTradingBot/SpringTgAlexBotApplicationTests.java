package com.example.squeezyTradingBot;

import com.example.squeezyTradingBot.config.WhiteListUserConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class squeezyTradingBotApplicationTests {

	private WhiteListUserConfig whiteListUserConfig;

	@Test
	void deserializationWhiteList() throws IOException, URISyntaxException {
		ObjectMapper objectMapper = new ObjectMapper();
		URL url = this.getClass().getClassLoader().getResource("WhiteListUsers.json");
		whiteListUserConfig = objectMapper.readValue(String.join(System.lineSeparator(),Files.readAllLines(Paths.get(url.toURI()))), WhiteListUserConfig.class);
		System.out.println(whiteListUserConfig);
	}

}
