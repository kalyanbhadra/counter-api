package com.optus.counterapi.services;

import java.util.Map;

public interface TextService {
	public String getSourceText();
	public void setSourceText(String text);
	public Map<String, Long> getWordOccurrenceMap();
	public String getTextFromFile();
}
