package de.stevenschwenke.java.javafx.fastread;

import java.util.List;

public class Paragraph {

	private List<Word> words;

	public Paragraph(List<Word> words) {
		this.words = words;
	}

	public List<Word> getWords() {
		return words;
	}

}
