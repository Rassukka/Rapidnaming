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

		count++;

		String answer = getSequence()[count];

		RapidnamingAnswer correctAnswer = new RapidnamingAnswer(answer);
		RapidnamingProblem problem = new RapidnamingProblem(answer);
		problem.setCorrectAnswer(correctAnswer);

		return problem;

	}

	private String[] getSequence() {
		String[] pairs = data.getWords();
		int numero = pairs.length;
		for (int i = 0; i < numero; i++) {
			int random = i + (int) (Math.random() * (numero - i));
			String randomElement = pairs[random];
			pairs[random] = pairs[i];
			pairs[i] = randomElement;
		}
		return pairs;
	}

}
