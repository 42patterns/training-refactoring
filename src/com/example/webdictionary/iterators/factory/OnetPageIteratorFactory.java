package com.example.webdictionary.iterators.factory;

import com.example.webdictionary.iterators.OnetPageIterator;
import com.example.webdictionary.iterators.PageIterator;

public class OnetPageIteratorFactory implements PageIteratorAbstractFactory {

	public PageIterator build(String wordToFind) {
		return new OnetPageIterator(wordToFind);
	}

}
