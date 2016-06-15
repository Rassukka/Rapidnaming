package fi.utu.ville.exercises.rapidnaming;

import java.io.File;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;

public class RapidnamingPictures {

	private String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();

	private FileResource filename;

	public RapidnamingPictures(String filename) {
		this.setFilename(new FileResource(new File(basepath + "/WEB-INF/images/" + filename)));
	}

	public FileResource getFilename() {
		return filename;
	}

	public void setFilename(FileResource filename) {
		this.filename = filename;
	}

}
