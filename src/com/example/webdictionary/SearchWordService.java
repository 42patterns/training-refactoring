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

	private final String command;
	
	public SearchWordService(String c) {
		this.command = c;
	}

	public List<DictionaryWord> search() {
		List<DictionaryWord> words = new ArrayList<DictionaryWord>();
		BufferedReader bufferedReader = null;
		String polishWord = null;
		String englishWord = null;
		int counter = 1;
		try {
			String[] commandParts = command.split(" ");
			String wordToFind = commandParts[1];
			String urlString = "http://www.dict.pl/dict?word=" + wordToFind
					+ "&words=&lang=PL";
			
			bufferedReader = new BufferedReader(new InputStreamReader(new URL(
					urlString).openStream()));
			boolean polish = true;
			String line = bufferedReader.readLine();
			Pattern pat = Pattern
					.compile(".*<a href=\"dict\\?words?=(.*)&lang.*");
			while (hasNextLine(line)) {
				Matcher matcher = pat.matcher(line);
				if (matcher.find()) {
					String foundWord = matcher.group(matcher.groupCount());
					if (polish) {
						System.out.print(counter + ") " + foundWord + " => ");
						polishWord = new String(foundWord.getBytes(), "UTF8");
						polish = false;
					} else {
						System.out.println(foundWord);
						polish = true;
						englishWord = new String(foundWord.getBytes(), "UTF8");
						words.add(DictionaryWord.Builder
								.withPolishWord(polishWord)
								.withEnglishWord(englishWord)
								.build());
						counter++;
					}
				}
				line = bufferedReader.readLine();
			}
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}	
		
		return words;
	}
	
	private boolean hasNextLine(String line) {
		return (line != null);
	}
}
