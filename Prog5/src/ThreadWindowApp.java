import javax.swing.*;

public class ThreadWindowApp extends JFrame {

	public static void main(String[] args) {
		new ThreadWindowApp();
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	JButton startButton = new JButton("Start");
	JButton stopButton = new JButton("Stop");
	JTextArea textArea = new JTextArea();
	JScrollPane scrollPaneForText = new JScrollPane(textArea);
	
	
	public ThreadWindowApp () {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(400, 500);
		setTitle("Aplikacja z wątkami");
		
		JPanel panel = new JPanel();
		
		panel.add(startButton);
		panel.add(stopButton);
		
		panel.add(scrollPaneForText);
		textArea.setEditable(false);
		textArea.setRows(20);
		textArea.setColumns(30);
		
		textArea.append("qwe");
		
        setContentPane(panel);
		setVisible(true);
	}

}
