package com.optus.counterapi;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.optus.counterapi.helpers.SearchRequestWrapper;
import com.optus.counterapi.helpers.SearchResponseWrapper;
import com.optus.counterapi.helpers.TopCountResultWrapper;
import com.optus.counterapi.services.CounterService;
import com.optus.counterapi.services.CounterServiceImpl;
import com.optus.counterapi.services.TextService;

@SpringBootTest
public class CounterServiceTest {
	
	@Autowired
	TextService realTextService;
	
	@Mock
	TextService textService;
	
	@InjectMocks
    private CounterService counterService = new CounterServiceImpl();
	
	@BeforeEach
    void setMockOutput() {
		
    }

    @DisplayName("Test Spring Integration")
    @Test
    void testContextCreated() {
        assertThat(counterService).isNotNull();
    }
    
    @DisplayName("Test search result for list of words")
    @Test
    void testSearchUsingList() {
    	
    	when(textService.getSourceText()).thenReturn("Sed has a Duis and Duis has a led");
    	
    	//Request Object
    	SearchRequestWrapper s = new SearchRequestWrapper();
    	s.getSearchText().add("Sed");
    	s.getSearchText().add("Duis");
    	s.getSearchText().add("Suis");
    	
    	SearchResponseWrapper res = counterService.getSearchResult(s);
    	
    	assertThat(res).isNotNull();
    	assertThat(res.getCounts()).isInstanceOf(List.class);
        	
    	assertThat(res.getCounts()).isNotNull();
    	assertThat(res.getCounts().size()).isEqualTo(3);
    	
    	Map<String, Long> map = res.getCounts().stream().collect(Collectors.toMap(e-> e.getKey(), e-> e.getValue()));
    	
    	assertThat(map.get("Sed")).isEqualTo(1L);
    	assertThat(map.get("Duis")).isEqualTo(2L);
    	assertThat(map.get("Suis")).isEqualTo(0L);
    }
    
    @DisplayName("Test top 3 count")
    @Test
    void testTopCounts() {
       	int top = 3;
       	Map<String, Long> wordOccurrence = new HashMap<>();
    	wordOccurrence.put("Sed", 1L);
    	wordOccurrence.put("Duis", 2L);
    	wordOccurrence.put("Suis", 5L);
    	wordOccurrence.put("Concor", 0L);
    	wordOccurrence.put("Davis", 3L);
    	wordOccurrence.put("Other", 2L);
       	
    	when(textService.getWordOccurrenceMap()).thenReturn(wordOccurrence);
    	
    	List<TopCountResultWrapper> res =  counterService.getTopCounts(top);
    	
    	assertThat(res).isNotNull();
    	assertThat(res.size()).isEqualTo(top);
    	assertThat(res.get(0).getCount()).isEqualTo(5);
    	assertThat(res.get(1).getCount()).isEqualTo(3);
    	assertThat(res.get(2).getCount()).isEqualTo(2);
    }
    
    @Test
	public void testCompare(){
    	String sameText = "good good good bad bad nothing goodman badman \n something else.";
    	
    	//TextService
    	realTextService.setSourceText(sameText);
		Map<String, Long> wordmap = realTextService.getWordOccurrenceMap();
		
		assertThat(wordmap).isNotNull();
		assertThat(wordmap.get("good")).isEqualTo(3L);
		assertThat(wordmap.get("bad")).isEqualTo(2L);
		assertThat(wordmap.get("nothing")).isEqualTo(1L);
		
		//CounterService
		when(textService.getSourceText()).thenReturn(sameText);
    	
    	//Request Object
    	SearchRequestWrapper s = new SearchRequestWrapper();
    	s.getSearchText().add("good");
    	s.getSearchText().add("bad");
    	s.getSearchText().add("nothing");
    	
    	SearchResponseWrapper res = counterService.getSearchResult(s);
    	
    	assertThat(res).isNotNull();
    	assertThat(res.getCounts()).isInstanceOf(List.class);
    	
    	Map<String, Long> map = res.getCounts().stream().collect(Collectors.toMap(e-> e.getKey(), e-> e.getValue()));
    	
    	assertThat(map).isNotNull();
    	assertThat(map.size()).isEqualTo(3);
    	assertThat(map.get("good")).isEqualTo(3L);
    	assertThat(map.get("bad")).isEqualTo(2L);
    	assertThat(map.get("nothing")).isEqualTo(1L);
	}
}
