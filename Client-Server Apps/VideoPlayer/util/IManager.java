package util;

public interface IManager {
	/**
	 * Riceve un byte di dati
	 * @param data
	 */
	public void receiveData(byte data);
	
	/**
	 * Imposta la connessione attraverso cui mandare i dati
	 * @param connection
	 */
	public void setConnection(IConnection connection);
}
