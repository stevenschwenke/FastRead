package de.stevenschwenke.java.javafx.fastread;

import java.util.List;

import javafx.beans.property.DoubleProperty;
import javafx.concurrent.Task;

public class ReadingTask extends Task<Void> {

	boolean triggerParagraphRewind;

	DoubleProperty generalSpeedProperty;

	private static final int milliesForComma = 80;
	private static final int milliesForPoint = 100;

	private int minimalPauseBetweenWords = 100;

	private int multiplierForWordLength = 30;

	private FastReadController controller;
	private String text;

	private int wordCount = 1;
	private int paragraphCount = 1;

	public ReadingTask(FastReadController controller, String text, DoubleProperty generalSpeedProperty) {
		super();
		this.controller = controller;
		this.text = text;
		this.generalSpeedProperty = generalSpeedProperty;
	}

	@Override
	protected Void call() throws Exception {
		final double startTime = System.currentTimeMillis();
		List<Paragraph> paragraphs = TextTokenizer.tokenizeIntoParagraphs(text);

		final String[] words = text.split("\\s");

		for (int nextParagraphIndex = 0; nextParagraphIndex < paragraphs.size(); nextParagraphIndex++) {

			Paragraph paragraph = paragraphs.get(nextParagraphIndex);

			System.out.println("Beginning paragraph " + paragraphCount);
			paragraphCount++;

			for (final Word word : paragraph.getWords()) {

				if (triggerParagraphRewind && nextParagraphIndex >= 0) {
					triggerParagraphRewind = false;
					nextParagraphIndex--;
					break;
				}

				controller.updateTextField(word.toString());
				controller.updateProgressBar((double) wordCount / (double) words.length);
				wordCount++;

				int millisComma = word.endsWithComma() ? milliesForComma : 0;
				int milliesPoint = word.endsWithPoint() ? milliesForPoint : 0;
				int milliesSemicolon = word.endsWithSemicolon() ? milliesForPoint : 0;
				int milliesWord = word.length() * multiplierForWordLength;
				double generalSpeedMultiplier = 1 / generalSpeedProperty.getValue();
				int millis = (int) ((minimalPauseBetweenWords + milliesWord + millisComma + milliesSemicolon + milliesPoint) * generalSpeedMultiplier);
				System.out.println("Time for \"" + word + "\": (" + milliesWord + " + " + millisComma + " + " + milliesSemicolon + " + " + milliesPoint
						+ ") x " + generalSpeedMultiplier + " = " + millis);
				Thread.sleep(millis);
			}
		}

		double runtimeInMinutes = (System.currentTimeMillis() - startTime) / 1000 / 60;
		controller.updateStatusLabel(words.length + " words in " + runtimeInMinutes + " minutes.");
		System.out.println(words.length + " words in " + runtimeInMinutes + " minutes.");

		return null;
	}

	public void backOneParagraph() {
		triggerParagraphRewind = true;
	}
}
