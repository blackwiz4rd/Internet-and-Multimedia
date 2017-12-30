package util;

public interface IFrame {
	/**
	 * 
	 * Ritorna il nome del file che contiene il frame
	 */
	public String getName();

	/**
	 * 
	 * Imposta il nome del file che contiene il frame
	 */
	public void setName(String filename);

	/**
	 * 
	 * Ritorna la dimensione del file (in byte)
	 */
	public int getSize();

	/**
	 * 
	 * Imposta la dimensione del file (in byte)
	 */
	public void setSize(int size);

	/**
	 * 
	 * Ritorna la misura di qualità del frame
	 */
	public double getQuality();

	/**
	 * 
	 * Imposta la qualità del frame
	 */
	public void setQuality(double quality);

}
