package com.optus.counterapi.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	
	@Autowired
	CounterService counterService;
	
	@GetMapping(path="/top/{count}", produces = "text/csv")
    public void getTopCountsInCsv(HttpServletResponse response, @PathVariable("count") int count) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; file=searchResult.csv");
        CsvUtil.downloadCsv(response.getWriter(), counterService.getTopCounts(count)) ;
    }
	
	@PostMapping(path="/search", consumes = "application/json", produces = "application/json")
	public ResponseEntity<SearchResponseWrapper> search(@RequestBody SearchRequestWrapper searchText) {
		SearchResponseWrapper resp = counterService.getSearchResult(searchText);
	    return new ResponseEntity<SearchResponseWrapper>(resp, HttpStatus.OK);
	}
}
