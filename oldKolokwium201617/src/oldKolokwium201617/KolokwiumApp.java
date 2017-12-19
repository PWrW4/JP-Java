package oldKolokwium201617;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


public class KolokwiumApp extends JFrame implements ActionListener {

	public static void main(String[] args) {
		new KolokwiumApp();
	}
	
	Panelik panelik = new Panelik();
	
	JButton author = new JButton("autor");
	JButton restartStopera = new JButton("Restart");
	
	JLabel stoper = new JLabel("0");
	
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public KolokwiumApp() {
		StoperThread stoperThread = new StoperThread(stoper);
		setTitle("Wojciech Wójcikik");
		setSize(400, 400);
		setResizable(false);
								
		author.addActionListener(this);
		restartStopera.addActionListener(this);
		
		panelik.add(stoper);
		panelik.add(restartStopera);
		panelik.add(author);
				
		setContentPane(panelik);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		stoperThread.run();
	}
	

	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		Object src =  e.getSource();
		
		if (src == author) {
			JOptionPane.showMessageDialog(this, "Wojciech Wójcik \n 23XXXX");
		}
		if (src == restartStopera) {
			stoper.setText("0");
		}
		
	}


}
