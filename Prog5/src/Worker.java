import java.util.concurrent.ThreadLocalRandom;

import javax.swing.JTextArea;

/* 
 *  Problem producenta i konsumenta
 *
 *  Autor: Paweł Rogaliński
 *   Data: 1 listopada 2017 r.
 */

abstract class Worker extends Thread {

	// Metoda usypia wątek na podany czas w milisekundach
	public static void sleep(int millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
		}
	}

	// Metoda usypia wątek na losowo dobrany czas z przedziału [min, max) milsekund
	public static void sleep(int min_millis, int max_milis) {
		sleep(ThreadLocalRandom.current().nextInt(min_millis, max_milis));
	}

	// Unikalny identyfikator przedmiotu wyprodukowanego
	// przez producenta i zużytego przez konsumenta
	// Ten identyfikator jest wspólny dla wszystkich producentów
	// i będzie zwiększany przy produkcji każdego nowego przedmiotu
	static int itemID = 0;

	// Minimalny i maksymalny czas produkcji przedmiotu
	public static int MIN_PRODUCER_TIME = 100;
	public static int MAX_PRODUCER_TIME = 1000;

	// Minimalny i maksymalny czas konsumpcji (zużycia) przedmiotu
	public static int MIN_CONSUMER_TIME = 100;
	public static int MAX_CONSUMER_TIME = 1000;

	final static String newline = "\n";
	String name;
	Buffer buffer;
	boolean running;
	JTextArea textArea;

	@Override
	public abstract void run();
}