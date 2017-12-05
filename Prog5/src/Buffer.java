import javax.swing.JTextArea;

class Buffer {
	
	private int contents;
	private boolean available = false;
	private JTextArea textArea;
	final private String newline = "\n";
	
	
	public Buffer(JTextArea textA) {
		this.textArea = textA;
	}
	
	public synchronized int get(Consumer consumer){
		System.out.println("Konsument <" + consumer.name + "> chce zabrac");
		while (available == false){
			try { 	textArea.append(newline + "Konsument <" + consumer.name + ">   bufor pusty - czekam");
				  wait();
				} catch (InterruptedException e) { }
		}
		int item = contents;
		available = false;
		textArea.append(newline + "Konsument <" + consumer.name + ">      zabral: " + contents);
		notifyAll();
		return item;
	}

	public synchronized void put(Producer producer, int item){
		textArea.append(newline + "Producent <" + producer.name + ">  chce oddac: " + item);
		while (available == true){
			try { 	textArea.append(newline + "Producent <" + producer.name + ">   bufor zajety - czekam");
				  wait();
				} catch (InterruptedException e) { }
		}
		contents = item;
		available = true;
		textArea.append(newline + "Producent <" + producer.name + ">       oddal: " + item);
		notifyAll();
	}
} // koniec klasy Buffer