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


import java.io.*;

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
    private int carProdYear;
    private Color carColor;
    private int carEngineSize;
    private String carOwnerName;

    public Car(String carOwnerName, String carEngineSize, String CarProdYear) throws CarException {
        setCarBrand(Brand.OTHER);
        setCarColor(Color.OTHER);
        setProdYear(CarProdYear);
        setCarOwnerName(carOwnerName);
        setEngineSize(carEngineSize);
    }

    @Override
    public String toString(){return carBrand.name() + " " + carColor.name() + " " + carProdYear + " " + carEngineSize;}


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

    public void setCarEngineSize(int carEngineSize) throws CarException {
        if (carEngineSize <0)
            throw new CarException("Pojemność silnika musi byc wieksza od 0.");
        this.carEngineSize = carEngineSize;
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
        setCarEngineSize(_intEngineSize);
    }

    public void setCarProdYear(int prodYear) throws CarException{
        if (prodYear < 1900 || prodYear > 2018/*Calendar.getInstance().get(Calendar.YEAR)*/)
            throw new CarException("Nieprawidłowa data, lub zła data ustawiona na urządzeniu.");
        this.carProdYear = prodYear;
    }

    public void setProdYear(String prodYear) throws CarException{
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

    public Color getCollor() {
        return carColor;
    }

    public int getCarEngineSize() {
        return carEngineSize;
    }

    public int getCarProdYear() {
        return carProdYear;
    }


    public static void printToFile(PrintWriter writer, Car carToWrite){
        writer.println(carToWrite.carBrand + "#" + carToWrite.carColor +
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