package de.stevenschwenke.java.javafx.fastread;

import java.util.ArrayList;
import java.util.List;

public class TextTokenizer {

	public static List<Paragraph> tokenizeIntoParagraphs(String text) {
		List<Paragraph> paragraphs = new ArrayList<Paragraph>();
		for (String stringParagraph : text.split("\\n")) {
			if (!stringParagraph.isEmpty()) {
				paragraphs.add(new Paragraph(tokenizeIntoWords(stringParagraph)));
			}
		}
		return paragraphs;
	}

	public static List<Word> tokenizeIntoWords(String text) {
		List<Word> words = new ArrayList<Word>();
		for (String stringWord : text.split("\\s")) {
			words.add(new Word(stringWord));
		}
		return words;
	}

}
