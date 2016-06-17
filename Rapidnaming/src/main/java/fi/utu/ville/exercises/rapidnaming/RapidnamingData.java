package fi.utu.ville.exercises.rapidnaming;

import fi.utu.ville.exercises.model.ExerciseData;

public class RapidnamingData implements ExerciseData {

	private static final long serialVersionUID = 7859129767103173914L;

	private int numberOfQuestions;
	private int timeShown;
	private String[] words;
	private final String[] sequence;
	private Mode mode;

	// sanoille
	public RapidnamingData(int questions, int timeShown, String[] words, Mode mode) {
		this.numberOfQuestions = questions;
		this.timeShown = timeShown;
		this.words = words;
		this.sequence = getSequence(words);
		this.mode = mode;
	}

	// kuville
	public RapidnamingData(int questions, int timeShown, Mode mode) {
		this.numberOfQuestions = questions;
		this.timeShown = timeShown;
		this.sequence = getSequence(words);
		this.mode = mode;
	}

	public int getAmount() {
		return numberOfQuestions;
	}

	public boolean giveAnswersAlwaysAsNegative() {
		return false;
	}

	public int getTimeShown() {
		return timeShown;
	}

	public void setTimeShown(int timeShown) {
		this.timeShown = timeShown;
	}

	public String[] getWords() {
		return words;
	}

	public void setWords(String[] words) {
		this.words = words;
	}

	private String[] getSequence(String[] words) {
		int numero = words.length;
		for (int i = 0; i < numero; i++) {
			int random = i + (int) (Math.random() * (numero - i));
			String randomElement = words[random];
			words[random] = words[i];
			words[i] = randomElement;
		}
		return words;
	}

	public String[] getSequence() {
		return sequence;
	}

	public Mode getMode() {
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}

}
