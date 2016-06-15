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

public class RapidnamingView extends VerticalLayout implements MathExerciseView<RapidnamingProblem> {

	private static final long serialVersionUID = 4938331703711987006L;

	private final RapidnamingData data;
	private final Localizer localizer;

	private TextField userAnswer;

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
					showPicture(problem);
				}
			});

			this.addComponent(clock);
			this.setMargin(true);
			this.setSpacing(true);
		});

		this.addComponent(button);
		this.setMargin(true);
		this.setSpacing(true);
	}

	public void showPicture(RapidnamingProblem problem) {

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
				inside(problem);
			}
		});

		this.addComponent(clock);
		this.setMargin(true);
		this.setSpacing(true);
	}

	public void inside(RapidnamingProblem problem) {
		userAnswer = new TextField(localizer.getUIText(RapidnamingUiConstants.ANSWER));
		userAnswer.focus();
		userAnswer.setCaption("Mikä sana oli?");

		this.addComponents(userAnswer);
		this.setMargin(true);
		this.setSpacing(true);
	}

	@Override
	public void showSolution(RapidnamingProblem problem) {
		String answer = getSolution(problem);
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
	}

	public String getSolution(RapidnamingProblem problem) {
		String solution = problem.getCorrectAnswer();
		return solution;
	}

	@Override
	public AbstractMathAnswer getAnswer() {
		String answer = userAnswer.getValue().toLowerCase();
		return new RapidnamingAnswer(answer);

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

}
