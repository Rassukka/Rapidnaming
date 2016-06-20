package fi.utu.ville.exercises.rapidnaming;

import java.util.Random;

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

		if (data.getMode() == RapidnamingMode.WORDS) {
			/*
			 * ohjelma ei jostain syystä ota järjestyksessä vaan muutaman ensimmäisen jälkeen alkaa
			 * hyppimään edes takaisin?
			 */

			if (data.getSequence().length < count) {
				count = 0;
			}

			for (String s : data.getSequence()) {
				System.out.print(s + " ");
			}

			System.out.println(count);
			System.out.println("");
			System.out.println("");

			String answer = data.getSequence()[count];

			count++;

			RapidnamingAnswer correctAnswer = new RapidnamingAnswer(answer);
			RapidnamingProblem problem = new RapidnamingProblem(answer);
			problem.setCorrectAnswer(correctAnswer);

			return problem;

		} else if (data.getMode() == RapidnamingMode.PICTURES) {

			Random r = new Random();

			int kys = r.nextInt(data.getPictures().size());
			int temp = (Math.random() <= 0.5) ? 1 : 2;

			if (temp == 1) {

				int answer = data.getPictures().get(kys).getGreens();

				RapidnamingAnswer correctAnswer = new RapidnamingAnswer("" + answer);
				RapidnamingProblem problem = new RapidnamingProblem("" + answer);
				problem.setCorrectAnswer(correctAnswer);
				data.setPicture(data.getPictures().get(kys).getPictureNumber());
				data.setColor("green");

				return problem;

			} else {

				int answer = data.getPictures().get(kys).getReds();

				RapidnamingAnswer correctAnswer = new RapidnamingAnswer("" + answer);
				RapidnamingProblem problem = new RapidnamingProblem("" + answer);
				problem.setCorrectAnswer(correctAnswer);
				data.setPicture(data.getPictures().get(kys).getPictureNumber());
				data.setColor("red");

				return problem;
			}

		} else {
			// tai joku virhe?
			return null;
		}

	}

}
