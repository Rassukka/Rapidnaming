package fi.utu.ville.exercises.rapidnaming;

import edu.vserver.exercises.math.essentials.layout.AbstractMathState;
import fi.utu.ville.standardutils.Localizer;

public class RapidnamingState extends AbstractMathState<RapidnamingData, RapidnamingProblem> {

	private static final long serialVersionUID = -8617477584787810586L;

	private RapidnamingData data;

	private int count = 0;

	public RapidnamingState(RapidnamingData data, Localizer localizer) {
		super(data, localizer);
	}

	@Override
	protected int loadDataAndGetAmount(RapidnamingData data) {
		this.data = data;
		return data.getAmount();
	}

	@Override
	protected RapidnamingProblem createProblem() {

		/*
		 * ohjelma ei jostain syystä ota järjestyksessä vaan muutaman ensimmäisen jälkeen alkaa
		 * hyppimään edes takaisin?
		 */
		String answer = data.getWords()[count];
		if (data.getWords().length < count) {
			count = 0;
		}

		for (String s : data.getWords()) {
			System.out.print(s + " ");
		}

		System.out.println(count);
		System.out.println("");
		System.out.println("");

		count++;

		RapidnamingAnswer correctAnswer = new RapidnamingAnswer(answer);
		RapidnamingProblem problem = new RapidnamingProblem(answer);
		problem.setCorrectAnswer(correctAnswer);

		return problem;

	}

}
