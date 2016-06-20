package fi.utu.ville.exercises.rapidnaming;

import java.util.Calendar;

import org.vaadin.kim.countdownclock.CountdownClock;
import org.vaadin.kim.countdownclock.CountdownClock.EndEventListener;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import edu.vserver.exercises.math.essentials.layout.AbstractMathAnswer;
import edu.vserver.exercises.math.essentials.layout.MathExerciseView;
import edu.vserver.exercises.math.essentials.layout.MathLayoutController;
import fi.utu.ville.standardutils.Localizer;
import fi.utu.ville.standardutils.ui.IntegerField;

public class RapidnamingView extends VerticalLayout implements MathExerciseView<RapidnamingProblem> {

	private static final long serialVersionUID = 4938331703711987006L;

	private final RapidnamingData data;
	private final Localizer localizer;

	private TextField userAnswer;
	private IntegerField intAnswer;

	public RapidnamingView(RapidnamingData data, Localizer localizer) {
		this.data = data;
		this.localizer = localizer;

	}

	@Override
	public void drawProblem(RapidnamingProblem problem) {
		this.clearFields();
		this.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

		/*
		 * Jos tässävaiheessa painaa enteriä niin koko tehtävä menee sekaisin, en saanut laitettua
		 * "button" elementtiin shortcuttia, enkä sitä pois tehtävän seuraava kysymys painikkeesta.
		 */

		Button button = new Button("Aloita tehtävä");
		button.addClickListener(e -> {
			this.removeComponent(button);
			CountdownClock clock = new CountdownClock();
			Calendar c = Calendar.getInstance();
			c.add(Calendar.SECOND, 4);
			clock.setDate(c.getTime());
			clock.setFormat("<span style='font: bold 25px Arial; margin: 10px'>" + "Sana näytetään %s sekunnin kuluttua." + "</span>");
			clock.addEndEventListener(new EndEventListener() {
				public void countDownEnded(CountdownClock clock) {
					clearFields();
					if (data.getMode() == RapidnamingMode.WORDS) {
						showWord(problem);
					} else if (data.getMode() == RapidnamingMode.PICTURES) {
						showImage(problem);
					}
				}
			});

			this.addComponent(clock);
			margins();
		});

		this.addComponents(button);
		margins();
	}

	public void showWord(RapidnamingProblem problem) {

		/*
		 * countdownclock ei tarpeeksi nopea, jos aika on alle sekunnin, niin kuva ei näy melkein
		 * ollenkaan, mutta jos aika on yli sekunnin - alle kaksi sekuntia, kuvan näyttöaika ei
		 * muutu. Melko heppo tehtävä jopa näillä englanninkielisillä sanoilla jopa 9 vuotiaalle
		 * pikkuveljelle.
		 */

		CountdownClock clock = new CountdownClock();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MILLISECOND, data.getTimeShown());
		clock.setDate(c.getTime());

		String answer = getSolution(problem);
		String capitalized = answer.substring(0, 1).toUpperCase() + answer.substring(1);
		clock.setFormat("<span style='font: bold 25px Arial; margin: 10px'>" + capitalized + "</span>");

		clock.addEndEventListener(new EndEventListener() {
			public void countDownEnded(CountdownClock clock) {
				clearFields();
				wordGuestion(problem);
			}
		});

		this.addComponent(clock);
		margins();
	}

	public void wordGuestion(RapidnamingProblem problem) {
		userAnswer = new TextField(localizer.getUIText(RapidnamingUiConstants.ANSWER));
		userAnswer.focus();
		userAnswer.setCaption("Mikä sana oli?");

		this.addComponents(userAnswer);
		margins();
	}

	public void showImage(RapidnamingProblem problem) {

		/*
		 * countdownclock ei tarpeeksi nopea, jos aika on alle sekunnin, niin kuva ei näy melkein
		 * ollenkaan, mutta jos aika on yli sekunnin - alle kaksi sekuntia, kuvan näyttöaika ei
		 * muutu. Melko heppo tehtävä jopa näillä englanninkielisillä sanoilla jopa 9 vuotiaalle
		 * pikkuveljelle.
		 */

		CountdownClock clock = new CountdownClock();
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MILLISECOND, data.getTimeShown());
		clock.setDate(c.getTime());

		// ?
		this.addComponent(data.getPicture());

		clock.addEndEventListener(new EndEventListener() {
			public void countDownEnded(CountdownClock clock) {
				clearFields();
				wordGuestion(problem);
			}
		});

		// turhaa?
		this.addComponent(clock);
		margins();
	}

	public void imageGuestion(RapidnamingProblem problem) {
		intAnswer = new IntegerField(localizer.getUIText(RapidnamingUiConstants.ANSWER));
		intAnswer.focus();
		if (data.getColor().equals("green")) {
			intAnswer.setCaption("Kuinka monta vihreää palloa?"); // ympyrää?
		} else if (data.getColor().equals("red")) {
			intAnswer.setCaption("Kuinka monta punaista palloa?"); // vihreä ja punainen
																	// värikoodattuna?
		}

		this.addComponents(intAnswer);
		margins();
	}

	@Override
	public void showSolution(RapidnamingProblem problem) {

		String answer = getSolution(problem);
		if (data.getMode() == RapidnamingMode.WORDS) {
			String capitalized = answer.substring(0, 1).toUpperCase() + answer.substring(1);
			if (userAnswer.getValue().toLowerCase().equals(answer)) {
				userAnswer.setEnabled(false);
				userAnswer.addStyleName("Rapidnaming-disabled");
				Label oikein = new Label("Oikein!");
				oikein.addStyleName("oikein");
				this.addComponent(oikein);
			} else {
				userAnswer.setEnabled(false);
				userAnswer.addStyleName("Rapidnaming-disabled");
				Label vaarin = new Label("Väärin!");
				vaarin.addStyleName("vaarin");
				this.addComponent(vaarin);
				Label correct = new Label("Oikea vastaus oli: " + capitalized);
				correct.addStyleName("correctAnswer");
				this.addComponent(correct);
			}
		} else if (data.getMode() == RapidnamingMode.PICTURES) {
			if (intAnswer.getValue().equals(answer)) {
				intAnswer.setEnabled(false);
				intAnswer.addStyleName("Rapidnaming-disabled");
				Label oikein = new Label("Oikein!");
				oikein.addStyleName("oikein");
				this.addComponent(oikein);
			} else {
				intAnswer.setEnabled(false);
				intAnswer.addStyleName("Rapidnaming-disabled");
				Label vaarin = new Label("Väärin!");
				vaarin.addStyleName("vaarin");
				this.addComponent(vaarin);
				Label correct = new Label("Oikea vastaus oli: " + answer);
				correct.addStyleName("correctAnswer");
				this.addComponent(correct);
			}
		}
	}

	public String getSolution(RapidnamingProblem problem) {
		String solution = problem.getCorrectAnswer();
		return solution;
	}

	@Override
	public AbstractMathAnswer getAnswer() {
		if (data.getMode() == RapidnamingMode.WORDS) {
			String answer = userAnswer.getValue().toLowerCase();
			return new RapidnamingAnswer(answer);
		} else if (data.getMode() == RapidnamingMode.PICTURES) {
			String answer = intAnswer.getValue().toString();
			return new RapidnamingAnswer(answer);
		} else {
			// Jokin virhe tänne?
			return null;
		}

	}

	@Override
	public void lockControls() {
		// This is called when there are no more questions left
	}

	@Override
	public void clearFields() {
		this.removeAllComponents();

	}

	@Override
	public void setLayoutController(MathLayoutController cont) {
		// MathLayoutController gives access to the check button
	}

	private void margins() {
		this.setMargin(true);
		this.setSpacing(true);
	}

}
