package fi.utu.ville.exercises.rapidnaming;

import java.util.ArrayList;

import org.vaadin.hene.expandingtextarea.ExpandingTextArea;
import org.vaadin.hene.expandingtextarea.ExpandingTextArea.RowsChangeEvent;
import org.vaadin.hene.expandingtextarea.ExpandingTextArea.RowsChangeListener;

import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.VerticalLayout;

import edu.vserver.math.MathTabbedEditorWrap;
import eu.michaelvogt.vaadin.attribute.Attribute;
import fi.utu.ville.standardutils.Localizer;
import fi.utu.ville.standardutils.ui.IntegerField;

public class RapidnamingEditor implements MathTabbedEditorWrap<RapidnamingData> {

	private static final long serialVersionUID = 1L;

	private OptionGroup valinta;

	// How many questions are shown to the user. Mathlayout can show max 20.
	private IntegerField numberOfExercises;

	private IntegerField timeShown;

	private ExpandingTextArea words;

	final RapidnamingData oldData;

	private VerticalLayout view;

	private RapidnamingMode rapidnamingMode;

	private final Localizer localizer;

	public RapidnamingEditor(RapidnamingData oldData, Localizer localizer, VerticalLayout view, RapidnamingMode rapidnamingMode) {
		this.localizer = localizer;
		this.oldData = oldData;
		this.view = new VerticalLayout();
		this.rapidnamingMode = rapidnamingMode;
	}

	@Override
	public VerticalLayout drawSettings() {

		valinta = new OptionGroup("Sanat vai Kuvat?");
		valinta.addItems("Sanat", "Kuvat");
		valinta.select("Sanat");

		view.addComponent(valinta);

		numberOfExercises = new IntegerField("Kysymysten määrä: (max 20)", 20);
		numberOfExercises.setValue(5);
		numberOfExercises.setWidth("40px");

		timeShown = new IntegerField("Aika joka kysymykset näytetään: (millisekunneissa)", 5);
		timeShown.setValue(1500);
		timeShown.setWidth("40px");

		words = new ExpandingTextArea("Sanat joita kysytään: (allekkain, pienellä!)");
		words.setValue(getDefaultWords());
		words.setImmediate(true);

		Attribute attribute = new Attribute("spellcheck", "false");
		attribute.extend(words);

		final Label sanoja = new Label("" + words.getRows());
		sanoja.setCaption("Sanoja");
		view.addComponents(numberOfExercises, timeShown, words, sanoja);
		words.addRowsChangeListener(new RowsChangeListener() {
			public void rowsChange(RowsChangeEvent event) {
				sanoja.setValue("" + (event.getRows() - 1));
			}
		});

		valinta.addValueChangeListener(event -> {
			if (valinta.getValue().equals("Sanat")) {
				rapidnamingMode = RapidnamingMode.WORDS;
				view.removeAllComponents();
				view.addComponents(valinta, numberOfExercises, timeShown, words, sanoja);
			} else if (valinta.getValue().equals("Kuvat")) {
				rapidnamingMode = RapidnamingMode.PICTURES;
				view.removeAllComponents();
				view.addComponents(valinta, numberOfExercises, timeShown);

			}

		});

		return view;

	}

	@Override
	public RapidnamingData getCurrData() {
		switch (rapidnamingMode) {
		case WORDS:
			return new RapidnamingData(numberOfExercises.getInteger(), timeShown.getInteger(), words.getValue().split("\n"), RapidnamingMode.WORDS);
		case PICTURES:
			ArrayList<RapidnamingDatahelp> list = new ArrayList<RapidnamingDatahelp>();
			list.add(new RapidnamingDatahelp(1, "Kuinka monta punaista palloa?", "4"));
			list.add(new RapidnamingDatahelp(1, "Kuinka monta vihreää palloa?", "3"));

			RapidnamingData data = new RapidnamingData(numberOfExercises.getInteger(), timeShown.getInteger(), RapidnamingMode.PICTURES, list);
			return data;
		default:
			System.out.println("Virhe editorissa!");
			return null;
		}

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
		String changed = "gabrielle\n" + "patel\n" + "brian\n" + "robinson\n" + "eduardo\n" + "haugen\n" + "hoen\n" + "johansen\n" + "alejandro\n" + "angel\n" + "karlsson\n" + "yahir\n" + "gustavsson\n" + "haiden\n" + "svensson\n" + "emily\n" + "stewart\n" + "corinne\n" + "davis\n" + "ryann";
		return changed;
	}

}
