import java.util.Arrays;

public class CarConsoleApp {

    private static final String GREETING_MESSAGE =
            "Car Console App\n" +
                    "Autor: Wojciech W�jcik\n" +
                    "Data:  21.10.2017 r.\n";

    private static final String MENU =
            "    M E N U   G � � W N E  \n" +
                    "1 - Podaj dane nowego auta\n" +
                    "2 - Usu� dane auta        \n" +
                    "3 - Modyfikuj dane auta   \n" +
                    "4 - Wczytaj dane z pliku  \n" +
                    "5 - Zapisz dane do pliku  \n" +
                    "0 - Zako�cz program       \n";

    private static final String CHANGE_MENU =
            "   Co zmieni�?     \n" +
                    "1 - Marka           \n" +
                    "2 - Rocznik       \n" +
                    "3 - Kolor  \n" +
                    "4 - Pojemno��  \n" +
                    "5 - Imi� w�a�ciciela  \n" +
                    "0 - Powr�t do menu g��wnego\n";


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
            sb.append("         Rocznik: " + car.getProdYear() + "\n");
            sb.append("           Kolor: " + car.getCollor().name() + "\n");
            sb.append("       Pojemno��: " + car.getEngineSize() + "\n");
            sb.append("Imi� w�a�ciciela: " + car.getOwnerName() + "\n");
        } else {
            sb.append("Brak danych" + "\n");
        }


        UI.printMessage(sb.toString());
    }

    /*
     * Meoda wczytuje w konsoli dane nowej osoby, tworzy nowy obiekt
     * klasy Person i wype�nia atrybuty wczytanymi danymi.
     * Walidacja poprawno�ci danych odbywa si� w konstruktorze i setterach
     * klasy Person. Je�li zostan� wykryte niepoprawne dane
     * to zostanie zg�oszony wyj�tek, kt�ry zawiera komunikat o b��dzie.
     */
    static Car createNewPerson() {
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

    /*
     * Metoda pozwala wczyta� nowe dane dla poszczeg�lnych atrybut�w
     * obiekty person i zmienia je poprzez wywo�anie odpowiednich setter�w z klasy Person.
     * Walidacja poprawno�ci wczyranych danych odbywa si� w setterach
     * klasy Person. Je�li zostan� wykryte niepoprawne dane
     * to zostanie zg�oszony wyj�tek, kt�ry zawiera komunikat o b��dzie.
     */
    static void changePersonData(Person person) {
        while (true) {
            UI.clearConsole();
            showPerson(person);

            try {
                switch (UI.enterInt(CHANGE_MENU + "==>> ")) {
                    case 1:
                        person.setFirstName(UI.enterString("Podaj imi�: "));
                        break;
                    case 2:
                        person.setLastName(UI.enterString("Podaj nazwisko: "));
                        break;
                    case 3:
                        person.setBirthYear(UI.enterString("Podaj rok ur.: "));
                        break;
                    case 4:
                        UI.printMessage("Dozwolone stanowiska:" + Arrays.deepToString(PersonJob.values()));
                        person.setJob(UI.enterString("Podaj stanowisko: "));
                        break;
                    case 0:
                        return;
                }  // koniec instrukcji switch
            } catch (PersonException e) {
                // Tu s� wychwytywane wyj�tki zg�aszane przez metody klasy Person
                // gdy nie s� spe�nione ograniczenia na�o�one na dopuszczelne warto�ci
                // poszczeg�lnych atrybut�w.
                // Drukowanie komunikatu o b��dzie zg�oszonym za pomoc� wyj�tku PersonException.
                UI.printErrorMessage(e.getMessage());
            }
        }
    }

    public void runMainLoop() {

        UI.printMessage(GREETING_MESSAGE);


        while (true) {
            UI.clearConsole();

            try {
                switch (UI.enterInt(MENU + "==>> ")) {
                    case 1:
                        // tworzenie nowego auta
                        currentCar =
                        break;
                    case 2:
                        // usuni�cie referencji/samochodu
                        currentCar = null;
                        UI.printInfoMessage("Dane aktualnej osoby zosta�y usuni�te");
                        break;
                    case 3:
                        // edycja danych
                        if (currentPerson == null) throw new PersonException("�adna osoba nie zosta�a utworzona.");
                        changePersonData(currentPerson);
                        break;
                    case 4: {
                        String file_name = UI.enterString("Podaj nazw� pliku: ");
                        currentPerson = Person.readFromFile(file_name);
                        UI.printInfoMessage("Dane aktualnej osoby zosta�y wczytane z pliku " + file_name);
                    }
                    break;
                    case 5: {
                        String file_name = UI.enterString("Podaj nazw� pliku: ");
                        Person.printToFile(file_name, currentPerson);
                        UI.printInfoMessage("Dane aktualnej osoby zosta�y zapisane do pliku " + file_name);
                    }

                    break;
                    case 0:
                        // zako�czenie dzia�ania programu
                        UI.printInfoMessage("\nProgram zako�czy� dzia�anie!");
                        System.exit(0);
                }
            } catch (CarException e) {
                UI.printErrorMessage(e.getMessage());
            }
        }
    }
}
