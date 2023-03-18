package com.example.squeezyTradingBot;

import com.example.squeezyTradingBot.config.WhiteListUserConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
class squeezyTradingBotApplicationTests {

	@Test
	void serializationWhiteList() throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		WhiteListUserConfig whiteListUserConfig = new WhiteListUserConfig();
		Set<Long> whiteListChatsID = new HashSet<>();
		whiteListChatsID.add(77777L);
		whiteListChatsID.add(8888L);
		whiteListUserConfig.setWhiteListChatsID(whiteListChatsID);

		String json = objectMapper.writeValueAsString(whiteListUserConfig);
		System.out.println(json);
	}


	private WhiteListUserConfig whiteListUserConfig;

	@Test
	void deserializationWhiteList(){
//		ObjectMapper objectMapper = new ObjectMapper();
//
//		File file = new File("src\\test\\resources\\WhiteListUsers.json");
//		try {
//			whiteListUserConfig = objectMapper.readValue(file, WhiteListUserConfig.class);
//		} catch (IOException e) {
//		}
//		System.out.println(whiteListUserConfig);
	}

}
