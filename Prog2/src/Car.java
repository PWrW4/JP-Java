/*
 *  Program: Operacje na obiektach klasy Car
 *     Plik: Car.java
 *           definicja typu wyliczeniowego Brand, Color
 *           definicja klasy CarException
 *           definicja publicznej klasy Car
 *
 *    Autor: Wojciech Wójcik
 *     Data:  21 pazdziernik 2017 r.
 *
 *     Prog2 on Git repo: https://bitbucket.org/pwr_wroc_w4/jp3
 */


import java.io.*;

enum Brand {
     OTHER,
     FORD,
     MAZDA,
     HONDA,
     ALFA_ROMEO,
     SEAT
}

enum CColor {
    OTHER,
    BLACK,
    SILVER,
    WHITE,
    RED,
    GREEN
}

public class Car {
    private Brand carBrand;
    private int carProdYear;
    private CColor carCColor;
    private int carEngineSize;
    private String carOwnerName;

    public Car(String carOwnerName, String carEngineSize, String carProdYear) throws CarException {
        setCarBrand(Brand.OTHER);
        setCarCColor(CColor.OTHER);
        setCarProdYear(carProdYear);
        setCarOwnerName(carOwnerName);
        setCarEngineSize(carEngineSize);
    }

    @Override
    public String toString(){return carBrand.name() + " " + carCColor.name() + " " + carProdYear + " " + carEngineSize;}


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

    public void setCarCColor(CColor carCColor) {
        this.carCColor = carCColor;
    }

    public void setCarColor(String colorString) throws CarException {
        if (colorString == null || colorString.equals("")){
            carCColor = CColor.OTHER;
            return;
        }
        for (CColor CColor : CColor.values()){
            if (colorString.equals(CColor.name())){
                carCColor = CColor;
                return;
            }
        }
        throw new CarException("Nie ma takiego koloru");
    }

    public void setCarEngineSize(int carEngineSize) throws CarException {
        if (carEngineSize <0)
            throw new CarException("Pojemność silnika musi byc wieksza od 0.");
        this.carEngineSize = carEngineSize;
    }

    public void setCarEngineSize(String engineSize) throws CarException {
        int _intEngineSize;
        if ((engineSize == null) || engineSize.equals(""))
            throw new CarException("Pole pojemność silnika musi być wypełnione.");
        try {
            _intEngineSize = Integer.parseInt(engineSize);
        } catch (NumberFormatException e) {
            throw new CarException("Rok produkcji musi być liczbą");
        }
        setCarEngineSize(_intEngineSize);
    }

    public void setCarProdYear(int prodYear) throws CarException{
        if (prodYear < 1900 || prodYear > 2018/*Calendar.getInstance().get(Calendar.YEAR)*/)
            throw new CarException("Nieprawidłowa data, lub zła data ustawiona na urządzeniu.");
        this.carProdYear = prodYear;
    }

    public void setCarProdYear(String prodYear) throws CarException{
        if ((prodYear == null) || prodYear.equals(""))
            throw new CarException("Pole rok produkcji musi być wypełnione.");
        try {
            setCarProdYear(Integer.parseInt(prodYear));
        } catch (NumberFormatException e) {
            throw new CarException("Rok produkcji musi być liczbą");
        }
    }

    public void setCarOwnerName(String carOwnerName) throws CarException{
        if ((carOwnerName == null) || carOwnerName.equals(""))
            throw new CarException("Pole <Imię> musi być wypełnione.");
        this.carOwnerName = carOwnerName;
    }

    public String getCarOwnerName() {
        return carOwnerName;
    }

    public Brand getCarBrand() {
        return carBrand;
    }

    public CColor getCarCColor() { return carCColor;    }

    public int getCarEngineSize() {
        return carEngineSize;
    }

    public int getCarProdYear() {
        return carProdYear;
    }


    public static void printToFile(PrintWriter writer, Car carToWrite){
        writer.println(carToWrite.carBrand + "#" + carToWrite.carCColor +
                "#" + carToWrite.carEngineSize + "#" + carToWrite.carProdYear + "#" + carToWrite.carOwnerName);
    }


    public static void printToFile(String file_name, Car carToWrite) throws CarException {
        try (PrintWriter writer = new PrintWriter(file_name)) {
            printToFile(writer, carToWrite);
        } catch (FileNotFoundException e){
            throw new CarException("Nie odnaleziono pliku " + file_name);
        }
    }

    public static Car readFromFile(BufferedReader reader) throws CarException{
        try {
            String line = reader.readLine();
            String[] txt = line.split("#");
            Car _car = new Car(txt[4], txt[2],txt[3]);
            _car.setCarBrand(txt[0]);
            _car.setCarColor(txt[1]);
            return _car;
        } catch(IOException e){
            throw new CarException("Wystąpił błąd podczas odczytu danych z pliku.");
        }
    }

    public static Car readFromFile(String file_name) throws CarException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(file_name)))) {
            return Car.readFromFile(reader);
        } catch (FileNotFoundException e){
            throw new CarException("Nie odnaleziono pliku " + file_name);
        } catch(IOException e){
            throw new CarException("Wystąpił błąd podczas odczytu danych z pliku.");
        }
    }




}

class CarException extends Exception{

    private static final long serialVersionUID = 1L;

    public CarException(String message) {
        super(message);
    }
}