package com.example.webdictionary.iterators;

import static org.junit.Assert.*;

import org.junit.Test;

public class DictPageIteratorTest {

	@Test
	public void searchForBook() {
		//given
		DictPageIterator iterator = new DictPageIterator("book");
		
		//when
		assertTrue(iterator.hasNext());
		
		//then
		assertEquals("bloczek", iterator.next());
		assertEquals("book", iterator.next());
	}
	
}
