package oldKolokwium201617;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class KolokwiumApp extends JFrame implements ActionListener,MouseListener {

	public static void main(String[] args) {
		new KolokwiumApp();
	}
	
	JPanel panelik = new JPanel();
	
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


	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
