package fi.utu.ville.exercises.rapidnaming;

import java.io.File;
import java.util.ArrayList;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Image;

import fi.utu.ville.exercises.model.ExerciseData;

public class RapidnamingData implements ExerciseData {

	private static final long serialVersionUID = 7859129767103173914L;

	private int numberOfQuestions;
	private int timeShown;
	private RapidnamingMode rapidnamingMode;

	private String[] words;
	private String[] sequence;

	private ArrayList<RapidnamingDatahelp> pictures;

	private String color;
	private Image picture;

	// sanoille
	public RapidnamingData(int questions, int timeShown, String[] words, RapidnamingMode rapidnamingMode) {
		this.numberOfQuestions = questions;
		this.timeShown = timeShown;
		this.words = words;
		this.sequence = getSequence(words);
		this.rapidnamingMode = rapidnamingMode;
	}

	// kuville
	public RapidnamingData(int questions, int timeShown, RapidnamingMode rapidnamingMode) {
		this.numberOfQuestions = questions;
		this.timeShown = timeShown;
		this.rapidnamingMode = rapidnamingMode;
		this.pictures = getPictures();
	}

	public int getAmount() {
		return numberOfQuestions;
	}

	public boolean giveAnswersAlwaysAsNegative() {
		return false;
	}

	public int getTimeShown() {
		return timeShown;
	}

	public void setTimeShown(int timeShown) {
		this.timeShown = timeShown;
	}

	public String[] getWords() {
		return words;
	}

	public void setWords(String[] words) {
		this.words = words;
	}

	private String[] getSequence(String[] words) {
		int numero = words.length;
		for (int i = 0; i < numero; i++) {
			int random = i + (int) (Math.random() * (numero - i));
			String randomElement = words[random];
			words[random] = words[i];
			words[i] = randomElement;
		}
		return words;
	}

	public String[] getSequence() {
		return sequence;
	}

	public RapidnamingMode getMode() {
		return rapidnamingMode;
	}

	public void setMode(RapidnamingMode rapidnamingMode) {
		this.rapidnamingMode = rapidnamingMode;
	}

	public ArrayList<RapidnamingDatahelp> getPictures() {
		return pictures;
	}

	public void setPictures(ArrayList<RapidnamingDatahelp> pictures) {
		pictures.add(new RapidnamingDatahelp(1, 3, 4));
		pictures.add(new RapidnamingDatahelp(2, 2, 3));
		pictures.add(new RapidnamingDatahelp(3, 5, 3));
		pictures.add(new RapidnamingDatahelp(4, 4, 6));
		pictures.add(new RapidnamingDatahelp(5, 6, 4));
	}

	// oikeassa paikassa nämä?
	public void setPicture(int pictureNumber) {
		String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
		picture = new Image("", new FileResource(new File(basepath + "/WEB-INF/images/kuva" + pictureNumber + ".png")));
	}

	public Image getPicture() {
		return picture;
	}

	public void setColor(String colors) {
		color = colors;
	}

	public String getColor() {
		return color;
	}

}
