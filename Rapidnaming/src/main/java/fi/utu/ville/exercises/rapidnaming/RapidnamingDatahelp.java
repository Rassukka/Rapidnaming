package fi.utu.ville.exercises.rapidnaming;

public class RapidnamingDatahelp {

	private int pictureNumber;
	private int greens;
	private int reds;

	public RapidnamingDatahelp(int pictureNumber, int greens, int reds) {
		this.pictureNumber = pictureNumber;
		this.greens = greens;
		this.reds = reds;
	}

	public int getPictureNumber() {
		return pictureNumber;
	}

	public void setPictureNumber(int pictureNumber) {
		this.pictureNumber = pictureNumber;
	}

	public int getGreens() {
		return greens;
	}

	public void setGreens(int geens) {
		this.greens = geens;
	}

	public int getReds() {
		return reds;
	}

	public void setReds(int reds) {
		this.reds = reds;
	}

}
