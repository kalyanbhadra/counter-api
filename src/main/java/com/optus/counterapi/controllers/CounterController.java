package com.optus.counterapi.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.optus.counterapi.helpers.CsvUtil;
import com.optus.counterapi.helpers.SearchRequestWrapper;
import com.optus.counterapi.helpers.SearchResponseWrapper;
import com.optus.counterapi.services.CounterService;

/**
 * This is the controller class for two endpoints as per assignment
 * 
 * @author kalyan
 *
 */
@RestController
@RequestMapping(value = "/counter-api")
public class CounterController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CounterController.class);
	
	@Autowired
	CounterService counterService;
	
	/**
	 * This endpoint returns the top no of words (of the count provided) with the frequency in the paragraph
	 * 
	 * @param response
	 * @param count
	 * @throws IOException
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@GetMapping(path="/top/{count}", produces = "text/csv")
    public void getTopCountsInCsv(HttpServletResponse response, @PathVariable("count") int count) throws IOException {
		LOGGER.info("Counter api CSV endpoint called for top " + count + "words.");
		
		String filename = "searchResult.csv";
		
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; file="+filename);
        CsvUtil.downloadCsv(response.getWriter(), counterService.getTopCounts(count)) ;
    }
	
	/**
	 * This endpoint returns frequency of words given as input in JSON format
	 * @param searchText
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping(path="/search", consumes = "application/json", produces = "application/json")
	public ResponseEntity<SearchResponseWrapper> search(@RequestBody SearchRequestWrapper searchText) {
		LOGGER.info("Counter api search endpoint called for " + searchText.toString());
		
		SearchResponseWrapper resp = counterService.getSearchResult(searchText);
	    return new ResponseEntity<SearchResponseWrapper>(resp, HttpStatus.OK);
	}
}
