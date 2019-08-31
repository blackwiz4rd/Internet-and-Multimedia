package util;

public interface IChannel {
    /*
    * Metodo che ritorna la probabilità di errore sul pacchetto
    */
	public double getLoss();

    /*
    * Metodo che ritorna la capacità del canale
    */
	public double getBandwidth();

}
