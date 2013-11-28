package com.example.webdictionary.iterators.factory;

import com.example.webdictionary.iterators.PageIterator;

public interface PageIteratorAbstractFactory {

	public PageIterator build(String wordToFind);
	
}
