package util;

public class Channel implements IChannel {
	private double loss;
	private double bandwidth;

	/*
	 * Costruttore
	 * loss: packet error rate
	 * bandwidth: capacità del canale [Bytes/s]
	 */
	public Channel(double loss, double bandwidth) {
		this.bandwidth = bandwidth;
		this.loss = loss;
	}

	public double getLoss() {
		return loss;
	}

	public double getBandwidth() {
		return bandwidth;
	}

}
