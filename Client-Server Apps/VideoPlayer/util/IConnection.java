package util;

public interface IConnection {
	/**
	 * Inizializza il connection manager e crea il socket
	 * @param address l'indirizzo dell'altro host
	 * @param port il numero di porta
	 * @param manager il connection manager che processa i dati ricevuti
	 */
	public void initialize(byte[] address, int port, IManager manager);
	
	/**
	 * Invia un pacchetto tramite il socket
	 * @param data i dati da inviare
	 * @return false se il pacchetto non viene inviato correttamente
	 */
	public boolean sendData(byte[] data);

}
