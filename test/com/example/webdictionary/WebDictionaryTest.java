package com.example.webdictionary;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Before;
import org.junit.Test;

public class WebDictionaryTest extends WebDictionary {

	private final static String SEARCH_WORD = "search book";
	private final static String SAVE_FIRST = "save 2";
	private final ByteArrayOutputStream baos = new ByteArrayOutputStream();
	
	@Before
	public void setup() {
		searchWord(SEARCH_WORD);
		saveWord(SAVE_FIRST);
		System.setOut(new PrintStream(baos));
	}
	
	@Test
	public void saveFirstBookItem() {
		final String STARTS_WITH = "[książka, book";
		
		showSavedWords();
		assertTrue("Output " + baos.toString() + "doesn't start with '"+STARTS_WITH+"'", 
				baos.toString().startsWith(STARTS_WITH));
	}

}
