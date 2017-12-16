package oldKolokwium201617;

import javax.swing.JLabel;

public class StoperThread implements Runnable {

	JLabel stoperLabel;
	
	public StoperThread(JLabel lab) {
		stoperLabel = lab;
	}
	
	@Override
	public void run() {
		int time;
		while(true) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				time = Integer.parseInt(stoperLabel.getText());
				time++;
				stoperLabel.setText(Integer.toString(time));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}

		}
	}
//
}
