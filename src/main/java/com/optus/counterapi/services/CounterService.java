package com.optus.counterapi.services;

import java.util.List;

import com.optus.counterapi.helpers.SearchRequestWrapper;
import com.optus.counterapi.helpers.SearchResponseWrapper;
import com.optus.counterapi.helpers.TopCountResultWrapper;

public interface CounterService {
	public SearchResponseWrapper getSearchResult(SearchRequestWrapper searchStringWrapper);
	public List<TopCountResultWrapper> getTopCounts(int top);
}
