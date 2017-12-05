import java.util.ArrayList;
import java.util.List;
import javax.swing.*;

public class ThreadWindowApp extends JFrame {

	public static void main(String[] args) {
		new ThreadWindowApp();
	}
	
	Buffer buffer = new Buffer();
	List<Producer> producerList = new ArrayList<Producer>();
	List<Consumer> consumerList = new ArrayList<Consumer>();
	
	String[] intArreyForComboBox = {"1","2","3","4","5","6","7","8"};
	
	int bufferListSize = 2;
	int producerListSize = 2;
	int consumerListSize = 2;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	JButton startButton = new JButton("Start");
	JButton stopButton = new JButton("Stop");
	
	JButton minConsButton = new JButton("Minimalny czas konsumpcji");
	JButton maxConsButton = new JButton("Maksymalny czas konsumpcji");
	JButton minProdButton = new JButton("Minimalny czas produkcji");
	JButton MaxProdButton = new JButton("Maksymalny czas produkcji");
	
	JLabel comboBufferLabel = new JLabel("Rozmiar bufora: ");
	JLabel comboProdLabel = new JLabel("Ilość Producentów: ");
	JLabel comboConsLabel = new JLabel("Ilość Konsumentów: ");
	JComboBox<String> comboIntBufer  = new JComboBox<String>(intArreyForComboBox);
	JComboBox<String> comboIntProd  = new JComboBox<String>(intArreyForComboBox);
	JComboBox<String> comboIntCons  = new JComboBox<String>(intArreyForComboBox);
	
	JTextArea textArea = new JTextArea();
	JScrollPane scrollPaneForText = new JScrollPane(textArea);
	
	
	public ThreadWindowApp () {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 600);
		setTitle("Aplikacja z wątkami");
		
		JPanel panel = new JPanel();
		
		panel.add(startButton);
		panel.add(stopButton);
		
		panel.add(minConsButton);
		panel.add(maxConsButton);
		panel.add(minProdButton);
		panel.add(MaxProdButton);
		
		panel.add(comboBufferLabel);
		panel.add(comboIntBufer);
		panel.add(comboProdLabel);
		panel.add(comboIntProd);
		panel.add(comboConsLabel);
		panel.add(comboIntCons);
		
		panel.add(scrollPaneForText);
		textArea.setEditable(false);
		textArea.setRows(25);
		textArea.setColumns(40);
		
        setContentPane(panel);
		setVisible(true);
	}

}
