package com.optus.counterapi.helpers;

import java.util.List;
import java.util.Map;

public class SearchResponseWrapper {
	private List<Map<String, Long>> counts;

	public List<Map<String, Long>> getCounts() {
		return counts;
	}

	public void setCounts(List<Map<String, Long>> counts) {
		this.counts = counts;
	}
}
