package com.example.webdictionary.iterators;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.webdictionary.WebDictionaryException;

public class DictPageIterator implements PageIterator {

	private BufferedReader bufferedReader;
	private Iterator<String> iterator;
	
	public DictPageIterator(String wordToFind) {
		List<String> words = prepareWordList(wordToFind);
		iterator = words.iterator();
	}
	
	public boolean hasNext() {
		return iterator.hasNext();
	}

	public String next() {
		return iterator.next();
	}

	public void remove() {
		throw new IllegalStateException("Not supported");
	}
	
	private List<String> prepareWordList(String wordToFind) {
		List<String> results = new ArrayList<String>();
		String urlString = "http://www.dict.pl/dict?word=" + wordToFind
				+ "&words=&lang=PL";
		try {
			bufferedReader = new BufferedReader(new InputStreamReader(new URL(
					urlString).openStream()));
			
			String word = moveToNextWord();
			while (hasNextWord(word)) {
				results.add(word);
				word = moveToNextWord();
			}

		} catch (MalformedURLException ex) {
			throw new WebDictionaryException(ex);
		} catch (IOException ex) {
			throw new WebDictionaryException(ex);
		} finally {
			dispose();
		}
		
		return results;
	}
	
	private String moveToNextWord() {
		try {
			
			String line = bufferedReader.readLine();
			Pattern pat = Pattern
					.compile(".*<a href=\"dict\\?words?=(.*)&lang.*");
			
			while (hasNextLine(line)) {
				Matcher matcher = pat.matcher(line);
				if (matcher.find()) {
					return matcher.group(matcher.groupCount());
				} else {
					line = bufferedReader.readLine();
				}
			}

		} catch (IOException e) {
			throw new WebDictionaryException(e);
		}
		
		return null;
	}

	private void dispose() {
		try {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		} catch (IOException ex) {
			throw new WebDictionaryException(ex);
		}
	}
	
	private boolean hasNextWord(String line) {
		return (line != null);
	}
	
	private boolean hasNextLine(String line) {
		return (line != null);
	}

}
