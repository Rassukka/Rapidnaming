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

		String answer = data.getSequence()[count];

		RapidnamingAnswer correctAnswer = new RapidnamingAnswer(answer);
		RapidnamingProblem problem = new RapidnamingProblem(answer);
		problem.setCorrectAnswer(correctAnswer);

		count++;

		return problem;

	}

}
