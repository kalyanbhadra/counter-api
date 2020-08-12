package com.optus.counterapi;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.event.annotation.BeforeTestClass;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.optus.counterapi.controllers.CounterController;
import com.optus.counterapi.helpers.SearchRequestWrapper;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class CounterControllerTest {
	
	Logger logger = LoggerFactory.getLogger(CounterControllerTest.class);
	
	@Autowired
	private CounterController controller;

	@Autowired
	private ObjectMapper objectMapper;
	
	@LocalServerPort
	private int port;
	
	@Autowired
	private TestRestTemplate restTemplate;

	@BeforeTestClass
	public void setUp() throws Exception {
	}

	@Test
	public void contexLoads() throws Exception {
		assertThat(controller).isNotNull();
	}
	
	@Test
	public void testTopCountApi() throws JsonProcessingException, Exception{
		
		ResponseEntity<String> responseEntityStr = restTemplate
				.withBasicAuth("optus", "candidates")
			     .postForEntity("http://localhost:" + port + "/counter-api/top/3", null, String.class);
			    assertThat(responseEntityStr.getBody()).isNotNull();
	}
	
	@Test
	public void searchTestApiTest() throws JsonProcessingException, Exception{
		//Request Object
		SearchRequestWrapper s = new SearchRequestWrapper();
		s.getSearchText().add("Sed");
		s.getSearchText().add("Duis");
		
		ResponseEntity<String> responseEntityStr = restTemplate
				.withBasicAuth("optus", "candidates")
			     .postForEntity("http://localhost:" + port + "/counter-api/search", s, String.class);
			    JsonNode root = objectMapper.readTree(responseEntityStr.getBody());
			    assertThat(responseEntityStr.getBody()).isNotNull();
			    assertThat(root.asText()).isNotNull();
			    //assertThat(root.toString().equals("{\"counts\":[{\"Sed\":0,\"Duis\":2}]}"));
	}
}
