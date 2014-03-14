package de.stevenschwenke.java.javafx.fastread;

public class Word {

	private String contentString;

	public Word(String stringWord) {
		this.contentString = stringWord;
	}

	public boolean endsWithComma() {
		return contentString.contains(",");
	}

	public boolean endsWithPoint() {
		return contentString.contains(".");
	}

	public boolean endsWithSemicolon() {
		return contentString.contains(";");
	}

	public int length() {
		return contentString.length();
	}

	public String toString() {
		return contentString;
	}
}
