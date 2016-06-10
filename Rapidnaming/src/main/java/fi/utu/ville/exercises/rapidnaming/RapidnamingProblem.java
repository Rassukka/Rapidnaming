package fi.utu.ville.exercises.rapidnaming;

import edu.vserver.exercises.math.essentials.layout.AbstractMathAnswer;
import edu.vserver.exercises.math.essentials.layout.Problem;
import fi.utu.ville.standardutils.Localizer;

public class RapidnamingProblem implements Problem {

	private static final long serialVersionUID = 1132903398317834085L;
	private RapidnamingAnswer userAnswer;
	private RapidnamingAnswer correct;

	private boolean correctness;
	private String expression;

	public RapidnamingProblem(String expression) {
		this.expression = expression;
	}

	@Override
	public boolean tryAnswer(AbstractMathAnswer answer) {

		userAnswer = (RapidnamingAnswer) answer;
		correctness = userAnswer.getAnswer().equals(correct.getAnswer());

		return correctness;
	}

	@Override
	public boolean isCorrect() {
		return correctness;
	}

	@Override
	public String getQuestion(Localizer localizer) {
		return expression;
	}

	@Override
	public String getCorrectAnswer() {
		return correct.getAnswer();
	}

	public void setCorrectAnswer(RapidnamingAnswer correctAnswer) {
		correct = correctAnswer;
	}

	@Override
	public String getUserAnswer() {
		return userAnswer == null ? "" : userAnswer.getAnswer();
	}
}
