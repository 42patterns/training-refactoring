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
	
	protected void processMenu() {
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
		BufferedReader r = null;
		String plW = null;
		String enW = null;
		int i = 1;
		try {
			r = new BufferedReader(new InputStreamReader(new URL(
					"http://www.dict.pl/dict?word=" + c.split(" ")[1]
							+ "&words=&lang=PL").openStream()));
			boolean w1 = true;
			String t = null;
			Pattern pat = Pattern
					.compile(".*<a href=\"dict\\?words?=(.*)&lang.*");
			while ((t = r.readLine()) != null) {
				Matcher matcher = pat.matcher(t);
				if (matcher.find()) {
					String t2 = matcher.group(matcher.groupCount());
					if (w1) {
						System.out.print(i + ") " + t2 + " => ");
						plW = new String(t2.getBytes(), "UTF8");
						w1 = false;
					} else {
						System.out.println(t2);
						w1 = true;
						enW = new String(t2.getBytes(), "UTF8");
						foundWords.add(DictionaryWord.Builder
								.withPolishWord(plW)
								.withEnglishWord(enW)
								.build());
						i++;
					}
				}
			}
		} catch (MalformedURLException ex) {
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			try {
				if (r != null) {
					r.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	protected void printInfo() {
		System.out.println("Welcome to Web Dictionary System .");
	}
}