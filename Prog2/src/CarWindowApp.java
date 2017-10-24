import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


/*
 * Program: Aplikacja okienkowa z GUI, która umożliwia testowanie
 *          operacji wykonywanych na obiektach klasy Person.
 *    Plik: PersonWindowApp.java
 *
 *   Autor: Paweł Rogalinski
 *    Data: pazdziernik 2017 r.
 */
public class PersonWindowApp extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private static final String GREETING_MESSAGE =
            "Program Person - wersja okienkowa\n" +
                    "Autor: Paweł Rogalinski\n" +
                    "Data:  październik 2017 r.\n";


    public static void main(String[] args) {
        // Utworzenie obiektu reprezentującego główne okno aplikacji.
        // Po utworzeniu obiektu na pulpicie zostanie wyświetlone
        // główne okno aplikacji.
        new PersonWindowApp();

        // UWAGA: można utworzyć kilka okien aplikacji.
        // Wsystkie okna będą wyświetlone w tym samum miejscu na pulpicie.
        // Po uruchomieniu kilku okien należy je ręcznie porozsuwać.
        // Każde okno działa niezależnie od pozostałych.
        // Aplikacja kończy swoje działanie po zamknięciu wszystkich utworzonych okien
        // lub po naciśnięciu przycisku "Zakończ aplikację",
        //
        // Jeżeli chcesz wypróbować działanie kilku okien odkomentój poniższe linie
        // new PersonWindowApp();
        // new PersonWindowApp();
    }



    /*
     *  Referencja do obiektu, który zawiera dane aktualnej osoby.
     */
    private Car currentCar;


    // Font dla etykiet o stałej szerokości znaków
    Font font = new Font("MonoSpaced", Font.BOLD, 12);

    // Etykiety wyświetlane na panelu w głównym oknie aplikacji
    JLabel surnameLabel = new JLabel("  Nazwisko: ");
    JLabel nameLabel    = new JLabel("      Imię: ");
    JLabel yearLabel    = new JLabel("   Rok ur.: ");
    JLabel jobLabel     = new JLabel("Stanowisko: ");

    // Pola tekstowe wyświetlane na panelu w głównym oknie aplikacji
    JTextField firstNameField = new JTextField(10);
    JTextField lastNameField    = new JTextField(10);
    JTextField yearField    = new JTextField(10);
    JTextField jobField     = new JTextField(10);

    // Przyciski wyświetlane na panelu w głównym oknie aplikacji
    JButton newButton    = new JButton("Nowa osoba");
    JButton editButton   = new JButton("Zmień dane");
    JButton saveButton   = new JButton("Zapisz do pliku");
    JButton loadButton   = new JButton("Wczytaj z pliku");
    JButton deleteButton = new JButton("Usuń osobę");
    JButton infoButton   = new JButton("O programie");
    JButton exitButton   = new JButton("Zakończ aplikację");


    /*
     * Utworzenie i konfiguracja głównego okna apkikacji
     */
    public PersonWindowApp(){
        // Konfiguracja parametrów głównego okna aplikacji
        setTitle("PersonWindowApp");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(270, 270);
        setResizable(false);
        setLocationRelativeTo(null);

        // Zmiana domyślnego fontu dla wszystkich etykiet
        // Użyto fontu o stałej szerokości znaków, by wyrównać
        // szerokość wszystkich etykiet.
        surnameLabel.setFont(font);
        nameLabel.setFont(font);
        yearLabel.setFont(font);
        jobLabel.setFont(font);

        // Zablokowanie możliwości edycji tekstów we wszystkich
        // polach tekstowych.  (pola nieedytowalne)
        firstNameField.setEditable(false);
        lastNameField.setEditable(false);
        yearField.setEditable(false);
        jobField.setEditable(false);


        // Dodanie słuchaczy zdarzeń do wszystkich przycisków.
        // UWAGA: słuchaczem zdarzeń będzie metoda actionPerformed
        //        zaimplementowana w tej klasie i wywołana dla
        //        bieżącej instancji okna aplikacji - referencja this
        newButton.addActionListener(this);
        editButton.addActionListener(this);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
        deleteButton.addActionListener(this);
        infoButton.addActionListener(this);
        exitButton.addActionListener(this);

        // Utworzenie głównego panelu okna aplikacji.
        // Domyślnym menedżerem rozdładu dla panelu będzie
        // FlowLayout, który układa wszystkie komponenty jeden za drugim.
        JPanel panel = new JPanel();

        // Dodanie i rozmieszczenie na panelu wszystkich
        // komponentów GUI.
        panel.add(surnameLabel);
        panel.add(firstNameField);

        panel.add(nameLabel);
        panel.add(lastNameField);

        panel.add(yearLabel);
        panel.add(yearField);

        panel.add(jobLabel);
        panel.add(jobField);

        panel.add(newButton);
        panel.add(deleteButton);
        panel.add(saveButton);
        panel.add(loadButton);
        panel.add(editButton);
        panel.add(infoButton);
        panel.add(exitButton);

        // Umieszczenie Panelu w głównym oknie aplikacji.
        setContentPane(panel);

        // Wypełnienie pól tekstowych danymi aktualnej osoby.
        showCurrentPerson();

        // Pokazanie na ekranie głównego okna aplikacji
        // UWAGA: Tą instrukcję należy wykonać jako ostatnią
        // po zainicjowaniu i rozmieszczeniu na panelu
        // wszystkich komponentów GUI.
        // Od tego momentu aplikacja uruchamia główną pętlę zdarzeń
        // która działa w nowym wątku niezależnie od pozostałej części programu.
        setVisible(true);
    }


    /*
     * Metoda wypełnia wszystkie pola tekstowe danymi
     * aktualnej osoby.
     */
    void showCurrentPerson() {
        if (currentCar == null) {
            firstNameField.setText("");
            lastNameField.setText("");
            yearField.setText("");
            jobField.setText("");
        } else {
            firstNameField.setText(currentPerson.getFirstName());
            lastNameField.setText(currentPerson.getLastName());
            yearField.setText("" + currentPerson.getBirthYear());
            jobField.setText("" + currentPerson.getJob());
        }
    }


    /*
     * Implementacja interfejsu ActionListener.
     *
     * Metoda actionPerformrd bedzie automatycznie wywoływana
     * do obsługi wszystkich zdarzeń od obiektów, którym jako słuchacza zdarzeń
     * dołączono obiekt reprezentujący bieżącą instancję okna aplikacji (referencja this)
     */
    @Override
    public void actionPerformed(ActionEvent event) {
        // Odczytanie referencji do obiektu, który wygenerował zdarzenie.
        Object eventSource = event.getSource();

        try {
            if (eventSource == newButton) {
                currentCar = CarWindowDialog.createNewCar(this);
            }
            if (eventSource == deleteButton) {
                currentCar = null;
            }
            if (eventSource == saveButton) {
                String fileName = JOptionPane.showInputDialog("Podaj nazwę pliku");
                if (fileName == null || fileName.equals("")) return;  // Cancel lub pusta nazwa pliku.
                Car.printToFile(fileName, currentCar);
            }
            if (eventSource == loadButton) {
                String fileName = JOptionPane.showInputDialog("Podaj nazwę pliku");
                if (fileName == null || fileName.equals("")) return;  // Cancel lub pusta nazwa pliku.
                currentCar = Car.readFromFile(fileName);
            }
            if (eventSource == editButton) {
                if (currentCar == null) throw new CarException("Żadna osoba nie została utworzona.");
                CarWindowDialog.changePersonData(this, currentCar);
            }
            if (eventSource == infoButton) {
                JOptionPane.showMessageDialog(this, GREETING_MESSAGE);
            }
            if (eventSource == exitButton) {
                System.exit(0);
            }
        } catch (CarException e) {
            // Tu są wychwytywane wyjątki zgłaszane przez metody klasy Person
            // gdy nie są spełnione ograniczenia nałożone na dopuszczelne wartości
            // poszczególnych atrybutów.
            // Wyświetlanie modalnego okna dialogowego
            // z komunikatem o błędzie zgłoszonym za pomocą wyjątku PersonException.
            JOptionPane.showMessageDialog(this, e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
        }

        // Aktualizacja zawartości wszystkich pól tekstowych.
        showCurrentPerson();
    }


} // koniec klasy PersonWindowApp
