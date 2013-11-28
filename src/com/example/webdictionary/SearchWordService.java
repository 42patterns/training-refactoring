package com.example.webdictionary;

import java.util.ArrayList;
import java.util.List;

import com.example.webdictionary.iterators.PageIterator;
import com.example.webdictionary.iterators.factory.PageIteratorAbstractFactory;

public class SearchWordService {

	private PageIteratorAbstractFactory factory;
	
	public SearchWordService(PageIteratorAbstractFactory factory) {
		this.factory = factory;
	}

	public List<DictionaryWord> search(String command) {
		List<DictionaryWord> words = new ArrayList<DictionaryWord>();

		String[] commandParts = command.split(" ");
		String wordToFind = commandParts[1];

		int counter = 1;
		PageIterator iterator = factory.build(wordToFind);

		while (iterator.hasNext()) {
			DictionaryWord.Builder builder = DictionaryWord.Builder
					.withPolishWord(iterator.next());

			builder.withEnglishWord(iterator.next());

			DictionaryWord word = builder.build();
			words.add(word);

			System.out.println(counter + ") " + word.getPolishWord() + " => "
					+ word.getEnglishWord());
			counter++;
		}

		return words;
	}

}
