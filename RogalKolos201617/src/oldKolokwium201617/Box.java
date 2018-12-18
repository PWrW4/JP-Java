package oldKolokwium201617;



public class Box  {
	String boxName;
	int boxSize;
	

	@Override
	public int hashCode() {
		
		return Integer.hashCode(boxSize) + boxName.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (null == obj) 
			return false;
		if (this == obj) 
			return true;
        if (getClass() != obj.getClass())
            return false;
        
        Box box = (Box)obj;
        
        if (box.boxName == boxName) {
			if (boxSize == box.boxSize) {
				return true;
			}
		}
        
        return false;
	}
}
