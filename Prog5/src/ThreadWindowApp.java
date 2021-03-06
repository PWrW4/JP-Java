import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.text.DefaultCaret;


/*
 * 				Program okienkowy z wątkami
 *              Autor: Wojciech Wójcik
 *              Data:  05.12.2017 r.
 *  			repozytorium git https://bitbucket.org/pwr_wroc_w4/jp3
 */

public class ThreadWindowApp extends JFrame implements ActionListener {

	public static void main(String[] args) {
		new ThreadWindowApp();
	}
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
    private static final String ABOUT_MESSAGE =
            "Program okienkowy z wątkami \n" +
                    "Autor: Wojciech Wójcik\n" +
                    "Data:  05.12.2017 r.\n" +
                    "link do repozytorium git zakomentowany w klasie ThreadWindowApp";
	
	private final String[] intArreyForComboBox = {"1","2","3","4","5","6","7","8"};
	
	JButton startButton = new JButton("Start");
	JButton stopButton = new JButton("Stop");
	
	JButton minConsButton = new JButton("Minimalny czas konsumpcji");
	JButton maxConsButton = new JButton("Maksymalny czas konsumpcji");
	JButton minProdButton = new JButton("Minimalny czas produkcji");
	JButton MaxProdButton = new JButton("Maksymalny czas produkcji");
	JButton CleartextAreaButton = new JButton("Czyszczenie konsoli");
	JButton AboutButton = new JButton("About");
	
	JLabel comboBufferLabel = new JLabel("Rozmiar bufora: ");
	JLabel comboProdLabel = new JLabel("Ilość Producentów: ");
	JLabel comboConsLabel = new JLabel("Ilość Konsumentów: ");
	JComboBox<String> comboIntBufer  = new JComboBox<String>(intArreyForComboBox);
	JComboBox<String> comboIntProd  = new JComboBox<String>(intArreyForComboBox);
	JComboBox<String> comboIntCons  = new JComboBox<String>(intArreyForComboBox);
	
	JTextArea textArea = new JTextArea();
	JScrollPane scrollPaneForText = new JScrollPane(textArea);
	
	
	Buffer buffer = new Buffer(textArea);
	List<Producer> producerList = new ArrayList<Producer>();
	List<Consumer> consumerList = new ArrayList<Consumer>();
	

	
	int bufferListSize = 2;
	int producerListSize = 2;
	int consumerListSize = 2;
	
	public ThreadWindowApp () {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(500, 650);
		setTitle("Aplikacja z wątkami");
		
		startButton.addActionListener(this);
		stopButton.addActionListener(this);
		minConsButton.addActionListener(this);
		minProdButton.addActionListener(this);
		maxConsButton.addActionListener(this);
		MaxProdButton.addActionListener(this);
		comboIntBufer.addActionListener(this);
		comboIntCons.addActionListener(this);
		comboIntProd.addActionListener(this);
		CleartextAreaButton.addActionListener(this);
		AboutButton.addActionListener(this);
		
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
		
		panel.add(CleartextAreaButton);
		panel.add(AboutButton);
		
		textArea.setEditable(false);
		textArea.setRows(25);
		textArea.setColumns(40);
		comboIntBufer.setSelectedIndex(1);
		buffer.setBuffer(2);
		comboIntProd.setSelectedIndex(1);
		comboIntCons.setSelectedIndex(1);
		
		DefaultCaret caret = (DefaultCaret)textArea.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		setProdAndCons();
		
        setContentPane(panel);
		setVisible(true);
	}


	private void setProdAndCons() {
		producerList = new ArrayList<Producer>();
		consumerList = new ArrayList<Consumer>();
		
		for (int i = 0; i < producerListSize; i++) {
			producerList.add(new Producer("Producent_" + i, buffer,textArea));
		}
		
		for (int i = 0; i < consumerListSize; i++) {
			consumerList.add(new Consumer("Konsument_" + i, buffer,textArea));
		}
	}
	
	private void startSim() {
		for (Consumer consumer : consumerList) {
			consumer.start();
		}
		for (Producer producer : producerList) {
			producer.start();
		}
	}
	
	private void stoptSim() {
		for (Consumer consumer : consumerList) {
			consumer.StopExec();
			consumer.interrupt();
		}
		for (Producer producer : producerList) {
			producer.StopExec();
			producer.interrupt();
		}
		Worker.itemID = 1;
	}

	
	public static int parseDefault(String number, int defaultVal) {
		  try {
		    return Integer.parseInt(number);
		  } catch (NumberFormatException e) {
		    return defaultVal;
		  }
		}
	
	
	@Override
	public void actionPerformed(ActionEvent evt) {
        Object source = evt.getSource();
		
        if (source == startButton) {
			startSim();
		}
        
        if (source == stopButton) {
			stoptSim();
			setProdAndCons();
		}
        
        if (source == minConsButton) {
			stoptSim();
			setProdAndCons();
			Worker.MIN_CONSUMER_TIME = parseDefault(JOptionPane.showInputDialog("MIN_CONSUMER_TIME-Podaj czas w ms:"),Worker.MIN_CONSUMER_TIME); 
		}
        
        if (source == maxConsButton) {
			stoptSim();
			setProdAndCons();
			Worker.MAX_CONSUMER_TIME = parseDefault(JOptionPane.showInputDialog("MAX_CONSUMER_TIME-Podaj czas w ms:"),Worker.MAX_CONSUMER_TIME);
		}
        
        if (source == minProdButton) {
			stoptSim();
			setProdAndCons();
			Worker.MIN_PRODUCER_TIME = parseDefault(JOptionPane.showInputDialog("MIN_PRODUCER_TIME-Podaj czas w ms:"),Worker.MIN_PRODUCER_TIME);
		}
        
        if (source == MaxProdButton) {
			stoptSim();
			setProdAndCons();
			Worker.MAX_PRODUCER_TIME = parseDefault(JOptionPane.showInputDialog("MAX_PRODUCER_TIME-Podaj czas w ms:"),Worker.MAX_PRODUCER_TIME);
		}
        
        if (source == comboIntCons) {
			stoptSim();
			setProdAndCons();
			consumerListSize = Integer.parseInt((String)comboIntCons.getSelectedItem());
		}
        
        if (source == comboIntProd) {
			stoptSim();
			setProdAndCons();
			producerListSize = Integer.parseInt((String)comboIntProd.getSelectedItem());
		}
        
        if (source == comboIntBufer) {
			stoptSim();
			setProdAndCons();
			bufferListSize = Integer.parseInt((String)comboIntBufer.getSelectedItem());
			buffer.setBuffer(bufferListSize);
		}    
        
        if (source == CleartextAreaButton) {
        textArea.setText(""); 
		}  
        
        if (source == AboutButton) {
        JOptionPane.showMessageDialog(this, ABOUT_MESSAGE);
		}  
        
	}

}
