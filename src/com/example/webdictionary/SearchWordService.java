package com.example.webdictionary;

import java.util.ArrayList;
import java.util.List;

public class SearchWordService {

	public SearchWordService() {
	}

	public List<DictionaryWord> search(String command) {
		List<DictionaryWord> words = new ArrayList<DictionaryWord>();

		String[] commandParts = command.split(" ");
		String wordToFind = commandParts[1];

		int counter = 1;
		PageIterator iterator = new PageIterator(wordToFind);

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
