package com.optus.counterapi.helpers;

import java.util.ArrayList;
import java.util.List;

public class SearchRequestWrapper {
	List<String> searchText;

	public List<String> getSearchText() {
		if(this.searchText == null){
			this.searchText = new ArrayList<String>();
		}
		return searchText;
	}

	public void setSearchText(List<String> searchText) {
		this.searchText = searchText;
	}
}