package com.example.webdictionary.iterators.factory;

import com.example.webdictionary.iterators.DictPageIterator;
import com.example.webdictionary.iterators.PageIterator;

public class DictPageIteratorFactory implements PageIteratorAbstractFactory {

	public PageIterator build(String wordToFind) {
		return new DictPageIterator(wordToFind);
	}

}
