package com.example.webdictionary.iterators;

import static org.junit.Assert.*;

import org.junit.Test;

public class OnetPageIteratorTest {

	@Test
	public void searchForBook() {
		// given
		PageIterator iterator = new OnetPageIterator("shop");

		// when
		assertTrue(iterator.hasNext());

		// then
		assertEquals("sklepowy", iterator.next());
		assertEquals("shop", iterator.next());
		assertEquals("sklep", iterator.next());
		assertEquals("shop", iterator.next());		
	}

}
