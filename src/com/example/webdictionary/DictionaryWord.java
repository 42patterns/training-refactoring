package com.example.webdictionary;

import java.util.Date;

public class DictionaryWord {

	private String polishWord;
	private String englishWord;

	public DictionaryWord() {

	}

	public DictionaryWord(String polishWord, String englishWord, Date date) {
		super();
		this.polishWord = polishWord;
		this.englishWord = englishWord;
	}

	public String getPolishWord() {
		return polishWord;
	}

	public void setPolishWord(String polishWord) {
		this.polishWord = polishWord;
	}

	public String getEnglishWord() {
		return englishWord;
	}

	public void setEnglishWord(String englishWord) {
		this.englishWord = englishWord;
	}

	@Override
	public String toString() {
		return "[" + polishWord + ", " + englishWord + "]";
	}

	private DictionaryWord(Builder builder) {
		this.polishWord = builder.polishWord;
		this.englishWord = builder.englishWord;
	}

	public static class Builder {
		private String polishWord;
		private String englishWord;

		public static Builder withPolishWord(String word) {
			Builder b = new Builder();
			b.polishWord = word;

			return b;
		}

		public Builder withEnglishWord(String word) {
			this.englishWord = word;
			return this;
		}

		public DictionaryWord build() {
			return new DictionaryWord(this);
		}
	}

}
