package client;

import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Player {
	private ArrayList<byte[]> frameList;
	private int current;
	private int segments;
	private int frameRate;
	private boolean paused;
	private boolean playing;
	private Video player;
	private ImageIcon screen;
	private JFrame frame;
	private JLabel label;

    /*
    * Costruzione del player
    */
	public Player(int segments, int frameRate) {
		frameList = new ArrayList<byte[]>();
		current = 0;
		this.segments = segments;
		this.frameRate = frameRate;
		paused = true;
		playing = false;
	}

    /*
    * Inizia a mostrare il video
    */
	public void play() {
		// Crea la finestra in cui va il video
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setSize(660, 440);
		label = new JLabel("", screen, JLabel.CENTER);
		frame.add(label, BorderLayout.CENTER);
		frame.repaint();
		player = new Video();
		// Inizio del playout
		player.start();
		frame.setVisible(true);
	}

    /*
    * Aggiunge un frame al buffer
    */
	public void receiveFrame(byte[] frame) {
	    // Aggiunge un frame al buffer
        
        // Se non è iniziato il playout, fa partire il thread
        
        // Se il video è bloccato in attesa di frame, lo fa ripartire

	}

	private class Video extends Thread {
		public synchronized void run() {
			while (current <= segments) {
				
				// Se non c'è il frame nel buffer, attende che venga ricevuto
				
				// Mostra il frame corrente
				screen = new ImageIcon(frameList.get(current++));
				label.setIcon(screen);
				frame.repaint();

				// Aspetta (1000 / frameRate) millisecondi
				
				// Attenzione: se arriva un nuovo frame, deve continuare ad aspettare
		}
	}
}
}