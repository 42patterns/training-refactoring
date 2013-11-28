package com.example.webdictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SearchWordService {

	private BufferedReader bufferedReader = null;
	private final String command;
	
	public SearchWordService(String c) {
		this.command = c;
	}

	public List<DictionaryWord> search() {
		List<DictionaryWord> words = new ArrayList<DictionaryWord>();
		
			int counter = 1;
			prepareBufferedReader();
			String currentWord = moveToNextWord();

			while (hasNextWord(currentWord)) {
				DictionaryWord.Builder builder = DictionaryWord.Builder
						.withPolishWord(currentWord);
				
				currentWord = moveToNextWord();
				builder.withEnglishWord(currentWord);
				
				currentWord = moveToNextWord();
				
				DictionaryWord word = builder.build();
				words.add(word);
				
				System.out.println(counter + ") " + word.getPolishWord() + " => "
						+ word.getEnglishWord());
				counter++;
			}
			
			dispose();
			
		return words;
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

	private void prepareBufferedReader() {
		try {
			String[] commandParts = command.split(" ");
			String wordToFind = commandParts[1];
			String urlString = "http://www.dict.pl/dict?word=" + wordToFind
					+ "&words=&lang=PL";
			
			bufferedReader = new BufferedReader(new InputStreamReader(new URL(
					urlString).openStream()));
		} catch (MalformedURLException ex) {
			throw new WebDictionaryException(ex);
		} catch (IOException ex) {
			throw new WebDictionaryException(ex);
		}
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
	
	private boolean hasNextLine(String line) {
		return (line != null);
	}
	
	private boolean hasNextWord(String line) {
		return (line != null);
	}
}
