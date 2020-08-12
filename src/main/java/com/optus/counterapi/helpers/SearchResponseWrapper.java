package com.optus.counterapi.helpers;

import java.util.List;
import java.util.Map.Entry;

public class SearchResponseWrapper {
	private List<Entry<String, Long>> counts;

	public List<Entry<String, Long>> getCounts() {
		return counts;
	}

	public void setCounts(List<Entry<String, Long>> counts) {
		this.counts = counts;
	}
}
