import java.io.*;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PhoneBook implements Serializable{

    ConcurrentHashMap<String,String> AddresList = new ConcurrentHashMap<String,String>();


    public String LOAD(String nazwa_pliku){

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nazwa_pliku))) {

            oos.writeObject(AddresList);
            System.out.println("Done");

        } catch (Exception ex) {
            ex.printStackTrace();
            return "ERROR";
        }
        return "OK";
    }

    public String SAVE(String nazwa_pliku){



		try (
		        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nazwa_pliku))) {

            AddresList = (ConcurrentHashMap) ois.readObject();

        } catch (Exception ex) {
            ex.printStackTrace();
            return "ERROR";
        }
        return "OK";
    }

    public String GET(String imie){

        Iterator<String> iterator = AddresList.keySet().iterator();

        while(iterator.hasNext())
        {
            String key = iterator.next();

            if(key.equals(imie))
            {
                //String[] keySplit =	key.toString().split(" ");

                //System.out.println("return " + keySplit[1]);
                //return keySplit[1];

                return key.toString() ; //wyświetla tylko imie
            }
        }
        return "get działa";

    }

    public String PUT( String imie,String numer){
        AddresList.put(imie, numer);
        return "OK";
    }

    public String REPLACE(String imie, String numer){
        Iterator<String> iterator = AddresList.keySet().iterator();

        while(iterator.hasNext())
        {
            String key = iterator.next();

            if(key.equals(imie))
            {
                AddresList.put(imie, numer);
            }
        }

        return "replace działa";
    }

    public String DELETE(String imie){
        Iterator<String> iterator = AddresList.keySet().iterator();

        while(iterator.hasNext())
        {
            String key = iterator.next();

            if(key.equals(imie))
            {
                AddresList.remove(imie);
                break;
            }
        }
        return "OK";
    }

    public String LIST(){
        return "OK";
    }

}
