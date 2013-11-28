package com.example.webdictionary.iterators;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.example.webdictionary.WebDictionaryException;

public class OnetPageIterator implements PageIterator {

	private BufferedReader bufferedReader;
	private Iterator<String> iterator;

	public OnetPageIterator(String wordToFind) {
		List<String> words = prepareWordList(wordToFind);
		iterator = words.iterator();
	}

	public boolean hasNext() {
		return iterator.hasNext();
	}

	public String next() {
		return iterator.next();
	}

	public void remove() {
		throw new IllegalStateException("Not supported");
	}

	private List<String> prepareWordList(String wordToFind) {
		List<String> results = new ArrayList<String>();
		String urlString = "http://portalwiedzy.onet.pl/tlumacz.html?qs="
				+ wordToFind + "&tr=ang-auto&x=0&y=0";

		try {
			bufferedReader = new BufferedReader(new InputStreamReader(new URL(
					urlString).openStream()));

			results = extractWords();
		} catch (MalformedURLException ex) {
			throw new WebDictionaryException(ex);
		} catch (IOException ex) {
			throw new WebDictionaryException(ex);
		} finally {
			dispose();
		}

		return results;
	}

	private List<String> extractWords() {
		List<String> results = new ArrayList<String>();
		try {

			String line = bufferedReader.readLine();
			Pattern pat = Pattern.compile(".*?<div class=a2b style=\"padding: "
					+ "0px 0 1px 0px\">\\s?" + "(.+?)&nbsp;"
					+ ".+?<BR>(.*?)</div>.*?"
					);

			while (hasNextLine(line)) {
				Matcher matcher = pat.matcher(line);
				if (matcher.find()) {
					String englishWord = new String(matcher.group(1).getBytes(
							"ISO-8859-2"));
					
					String polishHtmlFragment = new String(matcher.group(2)
							.getBytes("ISO-8859-2"));

					List<String> words = extractTranslation(englishWord,
							polishHtmlFragment + "<BR>");

					results.addAll(words);
				}
				line = bufferedReader.readLine();
			}
		} catch (IOException e) {
			throw new WebDictionaryException(e);
		}

		return results;
	}

	private List<String> extractTranslation(String englishWord,
			String polishHtmlFragment) {

		List<String> result = new ArrayList<String>();
		Pattern pattern = Pattern.compile("(<B>\\d+</B>\\s)?(.*?)<BR>");
		Matcher matcher = pattern.matcher(polishHtmlFragment);
		while (matcher.find()) {
			String polishWord = matcher.group(2);
			result.add(polishWord);
			result.add(englishWord);
		}
		return result;
	}

	private void dispose() {
		try {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		} catch (IOException ex) {
			throw new WebDictionaryException(ex);
		}
	}

	private boolean hasNextLine(String line) {
		return (line != null);
	}

}
