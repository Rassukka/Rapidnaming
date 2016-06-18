package fi.utu.ville.exercises.rapidnaming;

import java.io.File;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Image;

public class RapidnamingPictures {

	private String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

	private int pictureNumber;

	public RapidnamingPictures(int pictureNumber) {
		this.pictureNumber = pictureNumber;
	}

	public Image getImage() {
		return new Image("", new FileResource(new File(basepath + "/WEB-INF/images/kuva" + pictureNumber + ".png")));
	}

}
