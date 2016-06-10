package fi.utu.ville.exercises.rapidnaming;

import fi.utu.ville.exercises.model.ExerciseData;

public class RapidnamingData implements ExerciseData {

	private static final long serialVersionUID = 7859129767103173914L;

	private int numberOfQuestions;
	private int[] limits;

	public RapidnamingData(int questions, int[] limits) {
		this.numberOfQuestions = questions;
		this.limits = limits;
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
}
