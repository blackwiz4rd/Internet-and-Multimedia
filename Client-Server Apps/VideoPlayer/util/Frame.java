package util;



public class Frame implements IFrame {

	private String name;
	private int size;
	private double quality;

    /*
    * Costruttore vuoto
    */
	public Frame() {
		name = "";
		size = 0;
		quality = 0;
	}

    /*
    * Costruttore con parametri
    */
	public Frame(String name, int size, double quality) {
		this.name = name;
		this.size = size;
		this.quality = quality;
	}

	public String getName() {
		return name;
	}

	public void setName(String filename) {
		this.name = filename;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public double getQuality() {
		return quality;
	}

	public void setQuality(double quality) {
		this.quality = quality;
	}

}
