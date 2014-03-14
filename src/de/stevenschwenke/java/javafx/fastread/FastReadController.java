package de.stevenschwenke.java.javafx.fastread;

/**
 * Sample Skeleton for "editorPane.fxml" Controller Class
 * You can copy and paste this code into your favorite IDE
 **/

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.web.WebView;

public class FastReadController implements Initializable {

	// TODO FEedback: ROter Buchstabe muss immer an selber Stelle bleiben.

	private double degreeOfwordInsertionOffset = -0.2;

	@FXML
	private WebView webview;

	@FXML
	private ProgressBar progressBar;
	@FXML
	private TextArea inputTextArea;
	@FXML
	private Button startButton;
	@FXML
	private Slider generalSpeedSlider;
	@FXML
	private Label statusLabel;

	private ReadingTask task;

	public void initialize(URL fxmlFileLocation, ResourceBundle resources) {
		generalSpeedSlider.setMin(0.0);
		generalSpeedSlider.setMax(2.0);
		generalSpeedSlider.setValue(1.0);
		generalSpeedSlider.setShowTickLabels(true);
		generalSpeedSlider.setShowTickLabels(true);
		generalSpeedSlider.setShowTickMarks(true);
		generalSpeedSlider.setMajorTickUnit(1);
		generalSpeedSlider.setMinorTickCount(1);
		generalSpeedSlider.setBlockIncrement(10);

		inputTextArea.setText("Just add some text here and press \"read\".\nThe text will be read to you word by word. "
				+ "The time each word is presented depends from the length of the word and if there is a "
				+ "comma or point after the word. You can scale the speed with the slider next to the "
				+ "text field.\nThis application is inspired by spritz (spritzinc.com).");
	}

	protected void updateTextField(final String word) {

		StringBuilder b = new StringBuilder(word);

		// coloring a character
		int offset = (int) ((int) word.length() * degreeOfwordInsertionOffset);
		System.out.println("Coloring offset: " + offset);
		int insertPoint = word.length() / 2 + offset;
		b.insert(insertPoint + 1, "</font>");
		b.insert(insertPoint, "<font color=red>");

		// centering the text roughly around the colored character
		for (int i = 0; i <= -offset; i++) {
			b.insert(0, "&nbsp;");
		}

		final String word2 = b.toString();
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				webview.getEngine().loadContent("<html><p style=\"text-align:center;font-family: Arial;font-size:1.2em;\" >" + word2 + "</p></html>");
			}
		});
	}

	protected void updateProgressBar(double progress) {
		progressBar.setProgress(progress);
	}

	protected void updateStatusLabel(final String text) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				statusLabel.setText(text);
			}
		});
	}

	@FXML
	private void start() {

		final String text = inputTextArea.getText();

		task = new ReadingTask(this, text, generalSpeedSlider.valueProperty());
		new Thread(task).start();
	}

	@FXML
	private void back() {
		// TODO
	}

	@FXML
	private void backback() {
		task.backOneParagraph();
	}

	@FXML
	private void stop() {
		// TODO
	}
}
