import org.omg.CORBA.PERSIST_STORE;

import java.util.Calendar;


/*
 *  Program: Operacje na obiektach klasy Car
 *     Plik: Car.java
 *           definicja typu wyliczeniowego Brand, Color
 *           definicja klasy CarException
 *           definicja publicznej klasy Car
 *
 *    Autor: Wojciech Wójcik
 *     Data:  21 pazdziernik 2017 r.
 */


enum Brand {
     OTHER,
     FORD,
     MAZDA,
     HONDA,
     ALFA_ROMEO,
     SEAT
}

enum Color {
    OTHER,
    BLACK,
    SILVER,
    WHITE,
    RED,
    GREEN
}

public class Car {
    private Brand carBrand;
    private int prodYear;
    private Color carColor;
    private int engineSize;
    private String ownerName;

    public Car(String ownerName,String engineSize,String prodYear) throws CarException {
        setCarBrand(Brand.OTHER);
        setCarColor(Color.OTHER);
        setProdYear(prodYear);
        setOwnerName(ownerName);
        setEngineSize(engineSize);
    }

    @Override
    public String toString(){return carBrand.name() + " " + prodYear + " " + engineSize;}


    public void setCarBrand(Brand carBrand) {
        this.carBrand = carBrand;
    }

    public void setCarBrand(String carBrancString) throws CarException {
        if (carBrancString == null || carBrancString.equals("")){
            carBrand = Brand.OTHER;
            return;
        }
        for (Brand brand : Brand.values()){
            if (carBrancString.equals(brand.name())){
                this.carBrand = brand;
                return;
            }
        }
        throw new CarException("Nie ma takiej marki");
    }

    public void setCarColor(Color carColor) {
        this.carColor = carColor;
    }

    public void setCarColor(String colorString) throws CarException {
        if (colorString == null || colorString.equals("")){
            carColor = Color.OTHER;
            return;
        }
        for (Color color : Color.values()){
            if (colorString.equals(color.name())){
                carColor = color;
                return;
            }
        }
        throw new CarException("Nie ma takiego koloru");
    }

    public void setEngineSize(int engineSize) throws CarException {
        if (engineSize<0)
            throw new CarException("Pojemność silnika musi byc wieksza od 0.");
        this.engineSize = engineSize;
    }

    public void setEngineSize(String engineSize) throws CarException {
        int _intEngineSize;
        if ((engineSize == null) || engineSize.equals(""))
            throw new CarException("Pole pojemność silnika musi być wypełnione.");
        try {
            _intEngineSize = Integer.parseInt(engineSize);
        } catch (NumberFormatException e) {
            throw new CarException("Rok produkcji musi być liczbą");
        }
        setProdYear(_intEngineSize);
    }

    public void setProdYear(int prodYear) throws CarException{
        if (prodYear<1900 || prodYear>Calendar.getInstance().get(Calendar.YEAR))
            throw new CarException("Nieprawidłowa data, lub zła data ustawiona na urządzeniu.");
        this.prodYear = prodYear;
    }

    public void setProdYear(String prodYear) throws CarException{
        int _intProdYear;
        if ((prodYear == null) || prodYear.equals(""))
            throw new CarException("Pole rok produkcji musi być wypełnione.");
        try {
            _intProdYear = Integer.parseInt(prodYear);
        } catch (NumberFormatException e) {
            throw new CarException("Rok produkcji musi być liczbą");
        }
        setProdYear(_intProdYear);
    }

    public void setOwnerName(String ownerName) throws CarException{
        if ((ownerName == null) || ownerName.equals(""))
            throw new CarException("Pole <Imię> musi być wypełnione.");
        this.ownerName = ownerName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public Brand getCarBrand() {
        return carBrand;
    }

    public Color getCollor() {
        return carColor;
    }

    public int getEngineSize() {
        return engineSize;
    }

    public int getProdYear() {
        return prodYear;
    }
}

class CarException extends Exception{

    private static final long serialVersionUID = 1L;

    public CarException(String message) {
        super(message);
    }
}