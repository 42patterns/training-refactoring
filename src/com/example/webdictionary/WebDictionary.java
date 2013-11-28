package com.example.webdictionary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WebDictionary {
	List<DictionaryWord> savedWords = new ArrayList<DictionaryWord>();
	List<DictionaryWord> foundWords = new ArrayList<DictionaryWord>();

	public static void main(String[] args) {
		WebDictionary webDictionary = new WebDictionary();
		webDictionary.start();
	}

	public void start() {	
		processMenu();
	}
	
	private void processMenu() {
		boolean isRunning = true;
		Scanner scanner = new Scanner(System.in);
		printInfo();
		while (isRunning) {
			System.out.print("dictionary > ");
			String command = scanner.nextLine();
			if (command.equals("hello")) {
				printInfo();
			} else if (command.startsWith("search")) {
				searchWord(command);
			} else if (command.startsWith("save")) {
				saveWord(command);
			} else if (command.equals("showFound")) {
				showFoundWords();
			} else if (command.equals("showSaved")) {
				showSavedWords();
			} else if (command.equals("exit")) {
				isRunning = false;
			}
		}
		scanner.close();
	}

	protected void showSavedWords() {
		for (DictionaryWord el : savedWords) {
			System.out.println(el);
		}
	}

	protected void showFoundWords() {
		int i = 1;
		for (DictionaryWord el : foundWords) {
			System.out.println(i++ + ") " + el);
		}
	}

	protected void saveWord(String c) {
		int num = new Integer(c.split(" ")[1]);
		savedWords.add(foundWords.get(num - 1));
	}

	protected void searchWord(String c) {
		foundWords.clear();
		BufferedReader bufferedReader = null;
		String polishWord = null;
		String englishWord = null;
		int counter = 1;
		try {
			String[] commandParts = c.split(" ");
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
						foundWords.add(DictionaryWord.Builder
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
	}

	private boolean hasNextLine(String line) {
		return (line != null);
	}

	private void printInfo() {
		System.out.println("Welcome to Web Dictionary System .");
	}
}