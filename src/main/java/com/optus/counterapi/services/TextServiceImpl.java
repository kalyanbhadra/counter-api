package com.optus.counterapi.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;


/**
 * This class works as a source of the text formatted text
 * 
 * @author kalyan
 *
 */
@Service
public class TextServiceImpl implements TextService {
	
	@Value("classpath:text/sourceText.txt")
	private Resource resource;
	
	private Map<String, Long> wordOccurrenceMap;
	private String sourceText;
	
	public String getSourceText(){
		if(sourceText == null){
			sourceText = getTextFromFile();
		}
		return this.sourceText;
	}
	
	public void setSourceText(String text){
		this.sourceText = text;
	}
	
	
	/**
	 * This method generates a map of all words and their occurrence in the message.
	 * @return
	 */
	public Map<String, Long> getWordOccurrenceMap(){
		if(Optional.ofNullable(wordOccurrenceMap).isPresent()){
			return wordOccurrenceMap;
		} else {
			List<String> wholeWordsList = Stream.of(this.getSourceText())
											.map(w -> w.split("\\s+"))
											.flatMap(Arrays::stream)
											.collect(Collectors.toList());
			wordOccurrenceMap = wholeWordsList.stream().collect(Collectors.toMap(w -> w, w -> 1L, Long::sum));
			return wordOccurrenceMap;
		}
	}
	
	/**
	 * This method reads a text file form application classpath and return the content as a String.
	 */
	public String getTextFromFile() {
		StringBuffer sourceText = new StringBuffer();

		try (InputStream in = resource.getInputStream();
				InputStreamReader isr = new InputStreamReader(in, StandardCharsets.UTF_8);
				BufferedReader br = new BufferedReader(isr)) {

			br.lines().forEach(line -> sourceText.append(line));
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		return sourceText.toString();
	}
}