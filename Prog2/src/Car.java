import java.util.Calendar;


/*
 *  Program: Operacje na obiektach klasy Car
 *     Plik: Car.java
 *           definicja typu wyliczeniowego CarBrand, Color
 *           definicja klasy CarException
 *           definicja publicznej klasy Car
 *
 *    Autor: Wojciech Wójcik
 *     Data:  21 pazdziernik 2017 r.
 */


enum CarBrand{
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
    private CarBrand Brand;
    private int ProdYear;
    private Color CarColor;
    private int EngineSize;
    private String OwnerName;

    public void setBrand(CarBrand brand) {
        Brand = brand;
    }

    public void setBrand(String brand) {
        //Brand = brand;
    }

    public void setCarColor(Color carColor) {
        CarColor = carColor;
    }

    public void setCollor(String colorString) {
        for (Color color : Color.values()){
            if (colorString.equals(Color.)){

            }
        }
        CarColor = carColor;
    }

    public void setEngineSize(int engineSize) throws CarException {
        if (engineSize<0)
            throw new CarException("Pojemność silnika musi byc wieksza od 0.");
        EngineSize = engineSize;
    }

    public void setProdYear(int prodYear) throws CarException{
        if (prodYear<1900 || prodYear>Calendar.getInstance().get(Calendar.YEAR))
            throw new CarException("Nieprawidłowa data, lub zła data ustawiona na urządzeniu.");
        ProdYear = prodYear;
    }

    public void setOwnerName(String ownerName) throws CarException{
        if ((ownerName == null) || ownerName.equals(""))
            throw new CarException("Pole <Imię> musi być wypełnione.");
        OwnerName = ownerName;
    }

    public String getOwnerName() {
        return OwnerName;
    }

    public CarBrand getBrand() {
        return Brand;
    }

    public Color getCollor() {
        return CarColor;
    }

    public int getEngineSize() {
        return EngineSize;
    }

    public int getProdYear() {
        return ProdYear;
    }
}

class CarException extends Exception{

    private static final long serialVersionUID = 1L;

    public CarException(String message) {
        super(message);
    }
}