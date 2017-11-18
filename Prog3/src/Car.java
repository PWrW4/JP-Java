/*
 *  Program: Operacje na obiektach klasy Car
 *     Plik: Car.java
 *           definicja typu wyliczeniowego Brand, Color
 *           definicja klasy CarException
 *           definicja publicznej klasy Car
 *
 *   Autor: Wojciech Wójcik
 *   Data: 08.11.2017 r.
 *   Git repo: https://bitbucket.org/pwr_wroc_w4/jp3
 */


import java.io.*;

/**
 * Enumerator w którym zawarte są marki samochodów.
 */
enum Brand {
    OTHER,
    FORD,
    MAZDA,
    HONDA,
    ALFA_ROMEO,
    SEAT
}

/**
 * Enumerator kolorów do klasy Car.java.
 */
enum CColor {
    OTHER,
    BLACK,
    SILVER,
    WHITE,
    RED,
    GREEN
}

/**
 * Klasa Car przechowuje dane o samochodzie.
 *
 * Klasa przechowuje następujące informacje o samochodzie
 * <ul>
 * 	<li>Marka</li>
 *	<li>Wielkość silnika</li>
 *	<li>Rok Produkcji</li>
 *	<li>Kolor samochodu</li>
 *	<li>Imię i nazwisko właściciela</li>
 *</ul>
 * @author  Wojciech Wójcik
 */
public class Car implements Serializable, Comparable<Car> {

	private static final long serialVersionUID = 1L;
	private Brand carBrand;
    private int carProdYear;
    private CColor carCColor;
    private int carEngineSize;
    private String carOwnerName;

    /**
     * Konstruktor klasy {@code Car}.
     *
     * <p>Pola {@code Brand} i {@code CColor} muszą być przypisane przez
     *  metodę {@code setCarBrand} i {@code setCarCColor}</p>
     *
     * @param carEngineSize pojemność silnika
     * @param carOwnerName imię właściciela
     * @param carProdYear rok produkcji auta
     */
    public Car(String carOwnerName, String carEngineSize, String carProdYear) throws CarException {
        setCarBrand(Brand.OTHER);
        setCarCColor(CColor.OTHER);
        setCarProdYear(carProdYear);
        setCarOwnerName(carOwnerName);
        setCarEngineSize(carEngineSize);
    }

    /**
     * Klasa zapisująca dane samochodu do pliku.
     * 
     * @param PrintWriter Obiekt który odpowiada za zapis do pliku
     * @param carToWrite samochód którego dane chcemy zapisać do pliku.
     */
    public static void printToFile(PrintWriter writer, Car carToWrite) {
        writer.println(carToWrite.carBrand + "#" + carToWrite.carCColor +
                "#" + carToWrite.carEngineSize + "#" + carToWrite.carProdYear + "#" + carToWrite.carOwnerName);
    }

    /**
     * Klasa zapisująca dane samochodu do pliku.
     * 
     * <p>Metoda tworzy <code>PrintWriter</code> po czym wywołuje metodę {@link #printToFile(PrintWriter,Car)} </p>
     * 
     * @exception CarException Wyjątek zgłaszany przy błędzie zapisu
     * 
     * @param file_name nazwa pliku w którym dane zostaną zapisane.
     * @param carToWrite samochód którego dane chcemy zapisać do pliku.
     */
    public static void printToFile(String file_name, Car carToWrite) throws CarException {
        try (PrintWriter writer = new PrintWriter(file_name)) {
            printToFile(writer, carToWrite);
        } catch (FileNotFoundException e) {
            throw new CarException("Nie odnaleziono pliku " + file_name);
        }
    }

    /**
     * Klasa wczytująca dane samochodu pliku.
     * 
     * 
     * @exception CarException Wyjątek zgłaszany przy błędzie odczytu
     * 
     * @param reader Bufor z którego czytane są informacje o samochodzie.
     */
    public static Car readFromFile(BufferedReader reader) throws CarException {
        try {
            String line = reader.readLine();
            String[] txt = line.split("#");
            Car _car = new Car(txt[4], txt[2], txt[3]);
            _car.setCarBrand(txt[0]);
            _car.setCarColor(txt[1]);
            return _car;
        } catch (IOException e) {
            throw new CarException("Wystąpił błąd podczas odczytu danych z pliku.");
        }
    }

    /**
     * Klasa wczytująca dane samochodu pliku.
     * 
     * <p>Metoda tworzy <code>BufferedReader</code> po czym wywołuje metodę {@link #readFromFile(BufferedReader)} </p>
     * 
     * @exception CarException Wyjątek zgłaszany przy nie znalezieniu pliku, lub jego błędzie oczytu.
     * 
     * @param file_name nazwa pliku w którym dane zostały zapisane.
     */
    public static Car readFromFile(String file_name) throws CarException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(file_name)))) {
            return Car.readFromFile(reader);
        } catch (FileNotFoundException e) {
            throw new CarException("Nie odnaleziono pliku " + file_name);
        } catch (IOException e) {
            throw new CarException("Wystąpił błąd podczas odczytu danych z pliku.");
        }
    }

    /**
     * Przeciążona metoda toString(), która wypisuje informacje o samochodzie.
     *  
     */
    @Override
    public String toString() {
        return carBrand.name() + " " + carCColor.name() + " " + carProdYear + " " + carEngineSize + " " + carOwnerName;
    }

    /**
     * Setter marki samochodu.
     * 
     *  @param carBrand marka samochodu.
     */
    public void setCarBrand(Brand carBrand) {
        this.carBrand = carBrand;
    }
    
    /**
     * Setter koloru samochodu.
     * 
     * 
     * @throws CarException w przypadku złego podania nazwy koloru jest zgłaszany wyjątek.
     * 
     *  @param colorString String zawierający nazwę koloru.
     */
    public void setCarColor(String colorString) throws CarException {
        if (colorString == null || colorString.equals("")) {
            carCColor = CColor.OTHER;
            return;
        }
        for (CColor CColor : CColor.values()) {
            if (colorString.equals(CColor.name())) {
                carCColor = CColor;
                return;
            }
        }
        throw new CarException("Nie ma takiego koloru");
    }
    
    /**
     * Setter Wilkości silnika.
     * 
     *  @throws CarException w przypadku złego podania wielkości silnika (>0) jest zgłaszany wyjątek.
     *  @param carEngineSize argument zawierający wielkość silnika.
     */
    public void setCarEngineSize(int carEngineSize) throws CarException {
        if (carEngineSize < 0)
            throw new CarException("Pojemność silnika musi byc wieksza od 0.");
        this.carEngineSize = carEngineSize;
    }

    
    /**
     * Setter roku produkcji samochodu.
     * 
     *  @throws CarException w przypadku złego podania wielkości silnika (>0 i <2018) jest zgłaszany wyjątek.
     *  @param carEngineSize argument zawierający rok produkcji.
     */
    public void setCarProdYear(int prodYear) throws CarException {
        if (prodYear < 1900 || prodYear > 2018/*Calendar.getInstance().get(Calendar.YEAR)*/)
            throw new CarException("Nieprawidłowa data, lub zła data ustawiona na urządzeniu.");
        this.carProdYear = prodYear;
    }

    /**
     * Getter imienia właśiciela.
     * 
     *  @return imię właściciela
     */
    public String getCarOwnerName() {
        return carOwnerName;
    }

    
    public void setCarOwnerName(String carOwnerName) throws CarException {
        if ((carOwnerName == null) || carOwnerName.equals(""))
            throw new CarException("Pole <Imię> musi być wypełnione.");
        this.carOwnerName = carOwnerName;
    }

    /**
     * Getter marki.
     * 
     *  @return marka samochodu
     */
    public Brand getCarBrand() {
        return carBrand;
    }

    /**
     * Setter Marki samochodu.
     * 
     *  @throws CarException w przypadku złego podania marki jest zgłaszany wyjątek.
     *  @param carBrancString argument zawierający tekst z nazwą marki samochodu.
     */
    public void setCarBrand(String carBrancString) throws CarException {
        if (carBrancString == null || carBrancString.equals("")) {
            carBrand = Brand.OTHER;
            return;
        }
        for (Brand brand : Brand.values()) {
            if (carBrancString.equals(brand.name())) {
                this.carBrand = brand;
                return;
            }
        }
        throw new CarException("Nie ma takiej marki");
    }

    /**
     * Getter koloru samochodu.
     * 
     *  @return kolor samochodu
     */
    public CColor getCarCColor() {
        return carCColor;
    }

    public void setCarCColor(CColor carCColor) {
        this.carCColor = carCColor;
    }

    /**
     * Getter pojemności.
     * 
     *  @return pojemność samochodu.
     */
    public int getCarEngineSize() {
        return carEngineSize;
    }

    /**
     * Setter pojemności samochodu.
     * 
     *  @throws CarException w przypadku złego podania wielkości silnika jest zgłaszany wyjątek.
     *  @param engineSize argument zawierający pojemność.
     */
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
    /**
     * Getter roku produkcji.
     * 
     *  @return rok produkcji.
     */
    public int getCarProdYear() {
        return carProdYear;
    }

    /**
     * 	Setter pojemności samochodu.
     * 
     *  @throws CarException w przypadku nieudanej konwersji na int lub pustego stringa jest zgłaszany wyjątek.
     *  @param prodYear argument zawierający rok produkcji.
     */
    public void setCarProdYear(String prodYear) throws CarException {
        if ((prodYear == null) || prodYear.equals(""))
            throw new CarException("Pole rok produkcji musi być wypełnione.");
        try {
            setCarProdYear(Integer.parseInt(prodYear));
        } catch (NumberFormatException e) {
            throw new CarException("Rok produkcji musi być liczbą");
        }
    }

    /**
     * 	Metoda porównująca dwa samochody.
     * 
     *  @param c samochód z którym porównujemy.
     */
    @Override
    public int compareTo(Car c) {
        return this.carOwnerName.compareTo(c.carOwnerName);
    }

    
    /**
     * 	Setter pojemności samochodu.
     * 
     *  @throws CarException w przypadku nieudanej konwersji na int lub pustego stringa jest zgłaszany wyjątek.
     *  @param prodYear argument zawierający rok produkcji.
     */
    @Override
    public boolean equals(Object obj) {

        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;

        Car otherCar = (Car) obj;

        if (otherCar.getCarOwnerName().equals(this.getCarOwnerName()))
            if (otherCar.getCarEngineSize() == this.getCarEngineSize())
                if (otherCar.getCarProdYear() == this.getCarProdYear())
                    if (otherCar.getCarBrand().equals(this.carBrand))
                        if (otherCar.getCarCColor().equals(this.getCarCColor()))
                            return true;
        return false;
    }

    /**
     * 	Zwraca hash code.
     * 
     *  @return Hash code jest generowany w dany sposób <code> 983 * getCarProdYear() * getCarOwnerName().hashCode() * getCarEngineSize() * getCarBrand().hashCode() * getCarCColor().hashCode();</code>
     */
    @Override
    public int hashCode() {
        //tutaj możliwe że trzeba się zaimplementować wyjątki (sprawdzanie czy coś jest nullem)
        return 983 * getCarProdYear() * getCarOwnerName().hashCode() * getCarEngineSize() * getCarBrand().hashCode() * getCarCColor().hashCode();
    }
}


class CarException extends Exception {

    private static final long serialVersionUID = 1L;

    public CarException(String message) {
        super(message);
    }
}