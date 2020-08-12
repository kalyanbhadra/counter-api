package com.optus.counterapi.helpers;

public class TopCountResultWrapper {
	private String searchText;
	private long count;
	
	public TopCountResultWrapper(String searchText, long count) {
		super();
		this.searchText = searchText;
		this.count = count;
	}
	public String getSearchText() {
		return searchText;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
}
