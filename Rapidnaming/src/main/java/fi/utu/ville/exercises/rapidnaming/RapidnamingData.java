package fi.utu.ville.exercises.rapidnaming;

import fi.utu.ville.exercises.model.ExerciseData;

public class RapidnamingData implements ExerciseData {

	private static final long serialVersionUID = 7859129767103173914L;

	private int numberOfQuestions;
	private int[] limits;
	private int timeShown;
	private String[] words;

	public RapidnamingData(int questions, int[] limits, int timeShown, String[] words) {
		this.numberOfQuestions = questions;
		this.limits = limits;
		this.timeShown = timeShown;
		this.words = words;
	}

	public int getAmount() {
		return numberOfQuestions;
	}

	public int getLimit(int termNumber) {
		return limits[termNumber - 1];
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

}
