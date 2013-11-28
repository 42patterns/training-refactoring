package com.example.webdictionary;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintStream;
import java.net.URI;
import java.util.Scanner;

public class WebDictionaryCharacteristicTest {

	final static String FIRST_FILE = "a.out";
	final static String SECOND_FILE = "a2.out";
	
	//echo word
	//search
	//save 1
	//showFound
	//showSaved
	public static void main(String... args) throws Exception {
		final PrintStream originalOutputStream = System.out;
		final WebDictionary webDictionary = new WebDictionary();
		
		//check output folder
		URI dir = WebDictionaryCharacteristicTest.class.getResource("/").toURI();
		File output = new File(new File(dir), FIRST_FILE);
		if (output.exists()) {
			output = new File(new File(dir), SECOND_FILE);
		}
		
		//input words
		File file = new File(WebDictionaryCharacteristicTest.class.getResource("/words.txt").toURI());
		BufferedReader reader = new BufferedReader(new FileReader(file));

		//generating output
		System.setOut(new PrintStream(output));
		String line = null;
		while ((line = reader.readLine()) != null) {
			System.err.println(line);
			webDictionary.searchWord("search " + line);
			webDictionary.saveWord("save 1");
			webDictionary.showFoundWords();
			webDictionary.showSavedWords();
			System.out.println("=======================================");
		}
		
		//comparison
		System.setOut(originalOutputStream);
		if (!output.exists()) {
			System.out.println("Only first file present, nothing to compare");
			System.exit(0);
		}  
		
		File originalFile = new File(new File(dir), FIRST_FILE);
		String originalFileString = new Scanner( originalFile, "UTF-8" ).useDelimiter("\\A").next();
		if (originalFileString.equals(new Scanner( output, "UTF-8" ).useDelimiter("\\A").next())) {
			System.out.println("Same files");
		} else {
			System.err.println("Different files");
		}
	}
	
}
