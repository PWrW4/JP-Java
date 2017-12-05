class Consumer extends Worker {
	
	public Consumer(String name , Buffer buffer){ 
		this.name = name;
		this.buffer = buffer;
	}

	@Override
	public void run(){ 
		running = true;
		int item;
		while(running){
			// Konsument pobiera przedmiot z bufora
			item = buffer.get(this);
			
			// Konsument zużywa popraany przedmiot.
			sleep(MIN_CONSUMER_TIME, MAX_CONSUMER_TIME);
			System.out.println("Konsument <" + name + ">       zużył: " + item);
		}
	}
	

	public void StopExec() {
		running = false;
	}
	
} // koniec klasy Consumer