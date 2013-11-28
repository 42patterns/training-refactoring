package com.example.webdictionary;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SearchWordServiceTest {

	private String commandWord;
	private String firstAnswers;
	private String secondAnswers;
	private String lastAnswers;
	private int totalSize;

	public SearchWordServiceTest(String commandWord, String firstAnswers,
			String secondAnswers, String lastAnswers, int totalSize) {
		super();
		this.commandWord = commandWord;
		this.firstAnswers = firstAnswers;
		this.secondAnswers = secondAnswers;
		this.lastAnswers = lastAnswers;
		this.totalSize = totalSize;
	}

	@Parameters
	public static List<Object[]> data() {
		return Arrays
				.asList(new Object[][] {
						{
								"search book",
								"bloczek",
								"książka",
								"biblia (termin określający pewien zestaw książek zawierających elementarną wiedzę np. %27Knuth%27, %27K%26R %27, %27Camel Book %27 )",
								24 },
						{
								"search hello",
								"cześć; witaj",
								"halo!",
								"gwizdówka białoskrzydła; Peneothello sigillatus (gatunek ptaka)",
								9 } });
	}

	@Test
	public void searchWordTest() {
		// given
		SearchWordService service = new SearchWordService();

		// when
		List<DictionaryWord> list = service.search(commandWord);

		// then
		assertThat(list.size(), is(equalTo(totalSize)));
		assertThat(list.get(0).getPolishWord(), is(equalTo(firstAnswers)));
		assertThat(list.get(1).getPolishWord(), is(equalTo(secondAnswers)));

		int lastItem = totalSize - 1;
		assertThat(list.get(lastItem).getPolishWord(),
				is(equalTo(lastAnswers)));
	}
}
