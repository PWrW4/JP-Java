import java.util.Arrays;

public class CarConsoleApp {

    private static final String GREETING_MESSAGE =
                     "Car Console App\n" +
                    "Autor: Wojciech Wójcik\n" +
                    "Data:  21.10.2017 r.\n";

    private static final String MENU =
            "    M E N U   G £ Ó W N E  \n" +
                    "1 - Podaj dane nowego auta\n" +
                    "2 - Usuñ dane auta        \n" +
                    "3 - Modyfikuj dane auta   \n" +
                    "4 - Wczytaj dane z pliku  \n" +
                    "5 - Zapisz dane do pliku  \n" +
                    "0 - Zakoñcz program       \n";

    private static final String CHANGE_MENU =
            "   Co zmieniæ?     \n" +
                    "1 - Marka           \n" +
                    "2 - Rocznik       \n" +
                    "3 - Kolor  \n" +
                    "4 - Pojemnoœæ  \n" +
                    "5 - Imiê w³aœciciela  \n" +
                    "0 - Powrót do menu g³ównego\n";


    private static ConsoleUserDialog UI = new ConsoleUserDialog();


    public static void main(String[] args) {
        CarConsoleApp carApp = new CarConsoleApp();
        carApp.runMainLoop();
    }



    private Car currentCar = null;



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
                        // usuniêcie referencji/samochodu
                        currentCar = null;
                        UI.printInfoMessage("Dane aktualnej osoby zosta³y usuniête");
                        break;
                    case 3:
                        // edycja danych
                        if (currentPerson == null) throw new PersonException("¯adna osoba nie zosta³a utworzona.");
                        changePersonData(currentPerson);
                        break;
                    case 4: {
                        String file_name = UI.enterString("Podaj nazwê pliku: ");
                        currentPerson = Person.readFromFile(file_name);
                        UI.printInfoMessage("Dane aktualnej osoby zosta³y wczytane z pliku " + file_name);
                    }
                    break;
                    case 5: {
                        String file_name = UI.enterString("Podaj nazwê pliku: ");
                        Person.printToFile(file_name, currentPerson);
                        UI.printInfoMessage("Dane aktualnej osoby zosta³y zapisane do pliku " + file_name);
                    }

                    break;
                    case 0:
                        // zakoñczenie dzia³ania programu
                        UI.printInfoMessage("\nProgram zakoñczy³ dzia³anie!");
                        System.exit(0);
                }
            } catch (CarException e) {
                UI.printErrorMessage(e.getMessage());
            }
        }
    }




    static void showPerson(Car car) {
        StringBuilder sb = new StringBuilder();

        if (car != null) {
            sb.append("        Dane auta: \n");
            sb.append( "           Marka: " + car.getCarBrand().name() + "\n" );
            sb.append( "         Rocznik: " + car.getProdYear() + "\n" );
            sb.append( "           Kolor: " + car.getCollor().name() + "\n" );
            sb.append( "       Pojemnoœæ: " + car.getEngineSize() + "\n");
            sb.append( "Imiê w³aœciciela: " + car.getOwnerName() + "\n");
        } else {
            sb.append("Brak danych" + "\n");
        }


        UI.printMessage( sb.toString() );
    }


    /*
     * Meoda wczytuje w konsoli dane nowej osoby, tworzy nowy obiekt
     * klasy Person i wype³nia atrybuty wczytanymi danymi.
     * Walidacja poprawnoœci danych odbywa siê w konstruktorze i setterach
     * klasy Person. Jeœli zostan¹ wykryte niepoprawne dane
     * to zostanie zg³oszony wyj¹tek, który zawiera komunikat o b³êdzie.
     */
    static Person createNewPerson(){
        String first_name = UI.enterString("Podaj imiê: ");
        String last_name = UI.enterString("Podaj nazwisko: ");
        String birth_year = UI.enterString("Podaj rok ur.: ");
        UI.printMessage("Dozwolone stanowiska:" + Arrays.deepToString(PersonJob.values()));
        String job_name = UI.enterString("Podaj stanowisko: ");
        Person person;
        try {
            // Utworzenie nowego obiektu klasy Person oraz
            // ustawienie wartoœci wszystkich atrybutów.
            person = new Person(first_name, last_name);
            person.setBirthYear(birth_year);
            person.setJob(job_name);
        } catch (PersonException e) {
            // Tu s¹ wychwytywane wyj¹tki zg³aszane przez metody klasy Person
            // gdy nie s¹ spe³nione ograniczenia na³o¿one na dopuszczelne wartoœci
            // poszczególnych atrybutów.
            // Drukowanie komunikatu o b³êdzie zg³oszonym za pomoc¹ wyj¹tku PersonException.
            UI.printErrorMessage(e.getMessage());
            return null;
        }
        return person;
    }


    /*
     * Metoda pozwala wczytaæ nowe dane dla poszczególnych atrybutów
     * obiekty person i zmienia je poprzez wywo³anie odpowiednich setterów z klasy Person.
     * Walidacja poprawnoœci wczyranych danych odbywa siê w setterach
     * klasy Person. Jeœli zostan¹ wykryte niepoprawne dane
     * to zostanie zg³oszony wyj¹tek, który zawiera komunikat o b³êdzie.
     */
    static void changePersonData(Person person)
    {
        while (true) {
            UI.clearConsole();
            showPerson(person);

            try {
                switch (UI.enterInt(CHANGE_MENU + "==>> ")) {
                    case 1:
                        person.setFirstName(UI.enterString("Podaj imiê: "));
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
                    case 0: return;
                }  // koniec instrukcji switch
            } catch (PersonException e) {
                // Tu s¹ wychwytywane wyj¹tki zg³aszane przez metody klasy Person
                // gdy nie s¹ spe³nione ograniczenia na³o¿one na dopuszczelne wartoœci
                // poszczególnych atrybutów.
                // Drukowanie komunikatu o b³êdzie zg³oszonym za pomoc¹ wyj¹tku PersonException.
                UI.printErrorMessage(e.getMessage());
            }
        }
    }
}
