package com.optus.counterapi.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.optus.counterapi.helpers.SearchRequestWrapper;
import com.optus.counterapi.helpers.SearchResponseWrapper;
import com.optus.counterapi.helpers.TopCountResultWrapper;

/**
 * This is the service class for the APIs. Two public methods supply data for the restful APIs
 * 
 * @author kalyan
 *
 */
@Service
public class CounterServiceImpl implements CounterService {
	
	@Autowired
	TextService textService;

	@Override
	public SearchResponseWrapper getSearchResult(SearchRequestWrapper searchStringWrapper) {
		SearchResponseWrapper resp = buildResponse(searchStringWrapper);
		return resp;
	}

	/**
	 * This method returns top occurrence of words
	 */
	@Override
	public List<TopCountResultWrapper> getTopCounts(int top) {
		Map<String, Long> wordOccurrence = textService.getWordOccurrenceMap();
		
		// Sort by the more popular number
	    Set<Entry<String, Long>> set = wordOccurrence.entrySet();
	    List<Entry<String, Long>> list = new ArrayList<>(set);
	    Collections.sort(list, new Comparator<Entry<String, Long>>() {
	        @Override
	        public int compare(Entry<String, Long> a, Entry<String, Long> b) {
	            return b.getValue().intValue() - a.getValue().intValue();
	        }
	    });

	    List<TopCountResultWrapper> data = new LinkedList<>();
	    for (int i = 0; i < top && i < list.size(); i++) {
	    	data.add(new TopCountResultWrapper(list.get(i).getKey(),list.get(i).getValue()));
	    }
	    
        return data;
	}
	

	/**
	 * Returns counts for supplied words
	 * (getWordOccurrenceMap method of TextService can be used here. However, that might be much overhead
	 * for just searching few words.
	 * 
	 * @param searchStringWrapper List of words
	 * @return List of words and their counts
	 */
	private SearchResponseWrapper buildResponse(SearchRequestWrapper searchStringWrapper) {
		
		List<String> wholeWordsList = Stream.of(textService.getSourceText())
				.map(w -> w.split("\\s+"))
				.flatMap(Arrays::stream)
				.collect(Collectors.toList());
		
		Map<String, Long> res = searchStringWrapper.getSearchText().stream()
				.collect(Collectors.toMap(Function.identity(), w -> (long)Collections.frequency(wholeWordsList, w)));
		List<Entry<String, Long>> list = new ArrayList<>();
		list.addAll(res.entrySet());
		
		SearchResponseWrapper resp = new SearchResponseWrapper();
		resp.setCounts(list);
		return resp;
	}
}
