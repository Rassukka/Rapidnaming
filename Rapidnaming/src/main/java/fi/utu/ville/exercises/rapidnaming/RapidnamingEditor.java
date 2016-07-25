package fi.utu.ville.exercises.rapidnaming;

import java.io.File;
import java.util.ArrayList;

import org.vaadin.hene.expandingtextarea.ExpandingTextArea;
import org.vaadin.hene.expandingtextarea.ExpandingTextArea.RowsChangeEvent;
import org.vaadin.hene.expandingtextarea.ExpandingTextArea.RowsChangeListener;

import com.vaadin.server.FileResource;
import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.Table;
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

	private Table table;

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

		table = new Table();
		table.setCaption("Valitse tehtävässä esiintyvät kuvat tästä");

		table.addContainerProperty("Kuva", Image.class, null);
		table.addContainerProperty("Valitse kuvat", CheckBox.class, null);

		for (int i = 0; i < numberOfImages() + 1; i++) {
			Object id = table.addItem();
			table.getContainerProperty(id, "Kuva").setValue(getImage(i));
			table.getContainerProperty(id, "Valitse kuvat").setValue(new CheckBox());
		}
		table.setPageLength(3);

		//
		// Panel panel = new Panel("Kaikki Kuvat");
		// panel.setWidth(100, Unit.PERCENTAGE);
		// panel.setHeightUndefined();
		// view.addComponent(panel);
		//
		// Table table = new Table("Valitut Kuvat");
		//
		// // Define two columns for the built-in container
		// table.addContainerProperty("kuva0", Image.class, null);
		// table.addContainerProperty("kuva1", Image.class, null);
		//
		// // Add a row the hard way
		// Object newItemId = table.addItem();
		// Item row1 = table.getItem(newItemId);
		// row1.getItemProperty("kuva0").setValue(getImage(0));
		// row1.getItemProperty("kuva1").setValue(getImage(1));
		//
		// // Add a few other rows using shorthand addItem()
		// table.addItem(new Object[] { getImage(0) }, 2);
		// table.addItem(new Object[] { getImage(1) }, 3);
		//
		// // Show exactly the currently contained rows (items)
		// table.setPageLength(table.size());
		//
		// panel.setContent(table);
		//
		// // Collect the results of the iteration into this string.
		//
		// // Iterate over the item identifiers of the table.
		// for (Iterator<?> i = table.getItemIds().iterator(); i.hasNext();) {
		// // Get the current item identifier, which is an integer.
		// int iid = (Integer) i.next();
		//
		// // Now get the actual item from the table.
		// Item item = table.getItem(iid);
		//
		// // And now we can get to the actual checkbox object.
		// Button button = (Button) (item.getItemProperty("ismember").getValue());
		//
		// }

		// panel.setContent(table);
		//
		// Panel panel2 = new Panel("Valitut Kuvat");
		// panel2.setWidth(100, Unit.PERCENTAGE);
		// panel2.setHeightUndefined();
		// view.addComponent(panel2);
		//
		//
		// panel2.setContent(table);

		valinta.addValueChangeListener(event ->

		{
			if (valinta.getValue().equals("Sanat")) {
				rapidnamingMode = RapidnamingMode.WORDS;
				view.removeAllComponents();
				view.addComponents(valinta, numberOfExercises, timeShown, words, sanoja);
			} else if (valinta.getValue().equals("Kuvat")) {
				rapidnamingMode = RapidnamingMode.PICTURES;
				view.removeAllComponents();
				view.addComponents(valinta, numberOfExercises, timeShown, table);
			}

		});

		return view;

	}

	@SuppressWarnings("deprecation")
	@Override
	public RapidnamingData getCurrData() {
		switch (rapidnamingMode) {
		case WORDS:
			return new RapidnamingData(numberOfExercises.getInteger(), timeShown.getInteger(), words.getValue().split("\n"), RapidnamingMode.WORDS);
		case PICTURES:

			ArrayList<Object> selectedImages = new ArrayList<Object>();

			for (Object id : table.getItemIds()) {
				CheckBox checkBox = (CheckBox) table.getContainerProperty(id, "Valitse kuvat").getValue();

				if (checkBox.booleanValue()) {
					selectedImages.add(id);
				}
			}
			ArrayList<Integer> imageNumbers = new ArrayList<Integer>();
			for (Object object : selectedImages) {
				if (object instanceof Integer) {
					imageNumbers.add((Integer) object);
				}
			}

			ArrayList<RapidnamingDatahelp> lista = pictures(imageNumbers);

			return new RapidnamingData(numberOfExercises.getInteger(), timeShown.getInteger(), RapidnamingMode.PICTURES, lista);
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

	private ArrayList<RapidnamingDatahelp> pictures(ArrayList<Integer> imageNumbers) {

		ArrayList<RapidnamingDatahelp> lista = new ArrayList<>();

		lista.add(new RapidnamingDatahelp(0, "Kuinka monta punaista palloa?", "4"));
		lista.add(new RapidnamingDatahelp(0, "Kuinka monta vihreää palloa?", "3"));
		lista.add(new RapidnamingDatahelp(1, "Kuinka monta punaista palloa?", "3"));
		lista.add(new RapidnamingDatahelp(1, "Kuinka monta vihreää palloa?", "2"));
		lista.add(new RapidnamingDatahelp(2, "Kuinka monta punaista palloa?", "3"));
		lista.add(new RapidnamingDatahelp(2, "Kuinka monta vihreää palloa?", "5"));
		lista.add(new RapidnamingDatahelp(3, "Kuinka monta punaista palloa?", "6"));
		lista.add(new RapidnamingDatahelp(3, "Kuinka monta vihreää palloa?", "4"));
		lista.add(new RapidnamingDatahelp(4, "Kuinka monta punaista palloa?", "4"));
		lista.add(new RapidnamingDatahelp(4, "Kuinka monta vihreää palloa?", "6"));

		if (imageNumbers.isEmpty()) {
			return lista;

		} else {

			ArrayList<RapidnamingDatahelp> lista2 = new ArrayList<>();

			for (RapidnamingDatahelp y : lista) {
				for (Integer x : imageNumbers) {
					if (y.getPictureNumber() == x) {
						lista2.add(y);
					}
				}
			}

			return lista2;
		}
	}

	private Image getImage(int pictureNumber) {
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		Image image = new Image("", new FileResource(new File(basepath + "/WEB-INF/images/kuva" + pictureNumber + ".png")));
		image.setWidth(160, Unit.PIXELS);
		image.setHeight(100, Unit.PIXELS);
		return image;
	}

	private int numberOfImages() {
		int number = 0;
		ArrayList<RapidnamingDatahelp> lista = pictures(new ArrayList<Integer>());
		for (RapidnamingDatahelp y : lista) {
			if (number < y.getPictureNumber()) {
				number = y.getPictureNumber();
			}
		}
		return number;
	}

}
