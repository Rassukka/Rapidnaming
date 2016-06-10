package fi.utu.ville.exercises.rapidnaming;

import edu.vserver.exercises.math.essentials.layout.AbstractMathState;
import fi.utu.ville.standardutils.Localizer;

public class RapidnamingState extends AbstractMathState<RapidnamingData, RapidnamingProblem> {

	private static final long serialVersionUID = -8617477584787810586L;

	private RapidnamingData data;

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

		// int[] numbers = new int[] { generator.nextInt(data.getLimit(1)), generator.nextInt(data.getLimit(2)) };
		// int answer = numbers[0] + numbers[1];
		//
		// if (data.giveAnswersAlwaysAsNegative()) {
		// answer = -Math.abs(answer);
		// }

		RapidnamingRandom r = new RapidnamingRandom();

		String answer = r.getSequence()[r.getRandomNum()];

		RapidnamingAnswer correctAnswer = new RapidnamingAnswer(answer);
		RapidnamingProblem problem = new RapidnamingProblem(answer);
		problem.setCorrectAnswer(correctAnswer);

		return problem;

	}

}
