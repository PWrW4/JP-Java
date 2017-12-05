class Producer extends Worker {

	public Producer(String name , Buffer buffer){ 
		this.name = name;
		this.buffer = buffer;
	}
	
	@Override
	public void run(){ 
		running = true;
		int item;
		while(running){
			// Producent "produkuje" nowy przedmiot.
			item = itemID++;
			System.out.println("Producent <" + name + ">   produkuje: " + item);
			sleep(MIN_PRODUCER_TIME, MAX_PRODUCER_TIME);
			
			// Producent umieszcza przedmiot w buforze.
			buffer.put(this, item);
		}
	}
	
	

	public void StopExec() {
		running = false;
	}
	
} // koniec klasy Producer