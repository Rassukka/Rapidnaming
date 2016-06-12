package fi.utu.ville.exercises.rapidnaming;

import com.vaadin.ui.Layout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.VerticalLayout;

import edu.vserver.math.MathTabbedEditorWrap;
import fi.utu.ville.standardutils.Localizer;
import fi.utu.ville.standardutils.ui.IntegerField;

public class RapidnamingEditor implements MathTabbedEditorWrap<RapidnamingData> {

	private static final long serialVersionUID = 1L;

	// How many questions are shown to the user. Mathlayout can show max 20.
	private IntegerField numberOfExercises;

	private IntegerField timeShown;

	private TextArea words;

	final RapidnamingData oldData;

	private final Localizer localizer;

	public RapidnamingEditor(RapidnamingData oldData, Localizer localizer) {
		this.localizer = localizer;
		this.oldData = oldData;
	}

	@Override
	public VerticalLayout drawSettings() {

		VerticalLayout view = new VerticalLayout();

		numberOfExercises = new IntegerField("Kysymysten määrä: (max 20)", 20);
		numberOfExercises.setValue(5);
		numberOfExercises.setWidth("40px");

		timeShown = new IntegerField("Aika joka kysymykset näytetään: (millisekunneissa)", 5);
		timeShown.setValue(1500);
		timeShown.setWidth("40px");

		words = new TextArea("Sanat joita kysytään: (allekkain)");
		words.setValue(getDefaultWords());

		view.addComponents(numberOfExercises, timeShown, words);

		return view;

	}

	@Override
	public RapidnamingData getCurrData() {
		return new RapidnamingData(numberOfExercises.getInteger(), new int[] { 5, 5 }, timeShown.getInteger(), words.getValue().split("\n"));
	}

	@Override
	public Layout drawEditorLayout() {
		return drawSettings();
	}

	@Override
	public Boolean validateData() {
		return true;
	}

	@Override
	public String setTitleText() {
		return localizer.getUIText("Rapidnaming");
	}

	private String getDefaultWords() {
		String changed = "gabrielle\n" + "patel\n" + "brian\n" + "robinson\n" + "eduardo\n" + "haugen\n" + "hoen\n" + "johansen\n" + "alejandro\n" + "macdonald\n" + "angel\n" + "karlsson\n" + "yahir\n" + "gustavsson\n" + "haiden\n" + "svensson\n" + "emily\n" + "stewart\n" + "corinne\n" + "davis\n" + "ryann\n" + "davis\n" + "yurem\n" + "jackson\n" + "kelly\n" + "gustavsson\n" + "eileen\n" + "walker\n" + "katelyn\n" + "martin\n" + "israel\n" + "carlsson\n" + "quinn\n" + "hansson\n" + "makena\n" + "smith\n" + "danielle\n" + "watson\n" + "leland\n" + "harris\n" + "gunner\n" + "karlsen\n" + "jamar\n" + "olsson\n" + "lara\n" + "martin\n" + "ann\n" + "andersson\n" + "remington\n" + "andersson\n" + "rene\n" + "carlsson\n" + "elvis\n" + "olsen\n" + "solomon\n" + "jaydan\n" + "jackson\n" + "bernard\n" + "nilsen\n";
		return changed;
	}

}
