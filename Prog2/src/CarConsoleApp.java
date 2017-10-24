import java.util.Arrays;

public class CarConsoleApp {

    private static final String GREETING_MESSAGE =
            "Car Console App\n" +
                    "Autor: Wojciech Wójcik\n" +
                    "Data:  21.10.2017 r.\n";

    private static final String MENU =
            "    M E N U   G Ł Ó W N E  \n" +
                    "1 - Podaj dane nowego auta\n" +
                    "2 - Usuń dane auta        \n" +
                    "3 - Modyfikuj dane auta   \n" +
                    "4 - Wczytaj dane z pliku  \n" +
                    "5 - Zapisz dane do pliku  \n" +
                    "0 - Zakończ program       \n";

    private static final String CHANGE_MENU =
            "   Co zmienić?     \n" +
                    "1 - Marka           \n" +
                    "2 - Rocznik       \n" +
                    "3 - Kolor  \n" +
                    "4 - Pojemność  \n" +
                    "5 - Imię właściciela  \n" +
                    "0 - Powrót do menu głównego\n";


    private static ConsoleUserDialog UI = new ConsoleUserDialog();
    private Car currentCar = null;

    public static void main(String[] args) {
        CarConsoleApp carApp = new CarConsoleApp();
        carApp.runMainLoop();
    }

    static void showCar(Car car) {
        StringBuilder sb = new StringBuilder();

        if (car != null) {
            sb.append("        Dane auta: \n");
            sb.append("           Marka: " + car.getCarBrand().name() + "\n");
            sb.append("         Rocznik: " + car.getCarProdYear() + "\n");
            sb.append("           Kolor: " + car.getCollor().name() + "\n");
            sb.append("       Pojemność: " + car.getCarEngineSize() + "\n");
            sb.append("Imię właściciela: " + car.getCarOwnerName() + "\n");
        } else {
            sb.append("Brak danych" + "\n");
        }
        UI.printMessage(sb.toString());
    }


    static Car createNewCar() {
        UI.printMessage("Dozwolone Marki:" + Arrays.deepToString(Brand.values()));
        String _brand = UI.enterString("Podaj markę auta: ");
        String _prodYear = UI.enterString("Podaj rok produkcji: ");
        String _engineSize = UI.enterString("Podaj wielkość silnika w cm3: ");
        UI.printMessage("Dozwolone kolory:" + Arrays.deepToString(Color.values()));
        String _color = UI.enterString("Podaj kolor: ");
        String _ownerName = UI.enterString("Podaj imie i nazwisko właściciela");
        Car car;
        try {
            // utworzenie obiektu Car i "wpisanie" danych do niego
            car = new Car(_ownerName,_engineSize,_prodYear);
            car.setCarBrand(_brand);
            car.setCarColor(_color);
        } catch (CarException e) {
            UI.printErrorMessage(e.getMessage());
            return null;
        }
        return car;
    }


    static void changeCarData(Car carToEdit) {
        while (true) {
            UI.clearConsole();
            showCar(carToEdit);

            try {
                switch (UI.enterInt(CHANGE_MENU + "==>> ")) {
                    case 1:
                        UI.printMessage("Dozwolone stanowiska:" + Arrays.deepToString(Brand.values()));
                        carToEdit.setCarBrand(UI.enterString("Marka: "));
                        break;
                    case 2:
                        carToEdit.setProdYear(UI.enterString("Rocznik: "));
                        break;
                    case 3:
                        UI.printMessage("Dozwolone stanowiska:" + Arrays.deepToString(Color.values()));
                        carToEdit.setCarColor(UI.enterString("Kolor: "));
                        break;
                    case 4:
                        carToEdit.setEngineSize(UI.enterString("Pojemność: "));
                        break;
                    case  5:
                        carToEdit.setCarOwnerName(UI.enterString("Imię właściciela: "));
                    case 0:
                        return;
                }
            } catch (CarException e) {
                UI.printErrorMessage(e.getMessage());
            }
        }
    }

    public void runMainLoop() {

        UI.printMessage(GREETING_MESSAGE);


        while (true) {
            UI.clearConsole();

            try {
                if (currentCar != null){
                    showCar(currentCar);
                }
                switch (UI.enterInt(MENU + "==>> ")) {
                    case 1:
                        // tworzenie nowego auta
                        currentCar = createNewCar();
                        break;
                    case 2:
                        // usunięcie referencji/samochodu
                        currentCar = null;
                        UI.printInfoMessage("Dane zostały usunięte");
                        break;
                    case 3:
                        // edycja danych
                        if (currentCar == null) throw new CarException("Samochód nie został utworzony.");
                        changeCarData(currentCar);
                        break;
                    case 4: {
                        String file_name = UI.enterString("Podaj nazwę pliku: ");
                        //currentCar = Car.readFromFile(file_name);
                        UI.printInfoMessage("Dane aktualnej osoby zostały wczytane z pliku " + file_name);
                    }
                    break;
                    case 5: {
                        String file_name = UI.enterString("Podaj nazwę pliku: ");
                        //Car.printToFile(file_name, currentPerson);
                        UI.printInfoMessage("Dane aktualnej osoby zostały zapisane do pliku " + file_name);
                    }

                    break;
                    case 0:
                        // koniec programu
                        UI.printInfoMessage("\nProgram zakończył działanie!");
                        System.exit(0);
                }
            } catch (CarException e) {
                UI.printErrorMessage(e.getMessage());
            }
        }
    }
}
