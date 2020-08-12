package com.optus.counterapi;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.optus.counterapi.services.TextService;

@SpringBootTest
public class TextServiceTest {

	@Autowired
	TextService textService;
	
	@Test
	public void testCounts(){
		textService.setSourceText("good good good bad bad nothing goodman badman");
		Map<String, Long> wordmap = textService.getWordOccurrenceMap();
		
		assertThat(wordmap).isNotNull();
		assertThat(wordmap.get("good")).isEqualTo(3L);
		assertThat(wordmap.get("bad")).isEqualTo(2L);
		assertThat(wordmap.get("nothing")).isEqualTo(1L);
		assertThat(wordmap.get("something")).isNull();
	}
}
