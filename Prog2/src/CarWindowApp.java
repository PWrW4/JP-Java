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
 * Program: Aplikacja okienkowa z GUI, do klasy Car.java
 *    Plik: PersonWindowApp.java
 *
 *   Autor: Wojciech Wójcik na podstawie programu Paweł Rogaliński
 *    Data: 24.10.2017 r.
 *
 *    Prog2 on Git repo: https://bitbucket.org/pwr_wroc_w4/jp3
 */
public class CarWindowApp extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private static final String GREETING_MESSAGE =
            "Program Car - wersja okienkowa\n" +
                    "Autor: Wojciech Wójcik\n" +
                    "Data:  24.10.2017 r.\n";


    public static void main(String[] args) {
        // Utworzenie obiektu reprezentującego główne okno aplikacji.
        // Po utworzeniu obiektu na pulpicie zostanie wyświetlone
        // główne okno aplikacji.
        new CarWindowApp();
    }



    /*
     *  Referencja do obiektu, który zawiera dane aktualnej osoby.
     */
    private Car currentCar;


    // Font dla etykiet o stałej szerokości znaków
    Font font = new Font("MonoSpaced", Font.BOLD, 12);

    // Etykiety wyświetlane na panelu w głównym oknie aplikacji
    JLabel brandLabel = new JLabel("           Marka: ");
    JLabel colorLabel = new JLabel("           Kolor: ");
    JLabel prodYearLabel = new JLabel("Rok produkcji: ");
    JLabel engineSizeLabel = new JLabel(  "Pojemność: ");
    JLabel ownerNameLabel = new JLabel(" Imię właśc.: ");

    // Pola tekstowe wyświetlane na panelu w głównym oknie aplikacji
    JTextField brandField = new JTextField(10);
    JTextField colorField = new JTextField(10);
    JTextField prodYearField = new JTextField(10);
    JTextField engineSizeField = new JTextField(10);
    JTextField ownerNameField     = new JTextField(10);

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
    public CarWindowApp(){
        // Konfiguracja parametrów głównego okna aplikacji
        setTitle("PersonWindowApp");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(270, 290);
        setResizable(false);
        setLocationRelativeTo(null);

        // Zmiana domyślnego fontu dla wszystkich etykiet
        // Użyto fontu o stałej szerokości znaków, by wyrównać
        // szerokość wszystkich etykiet.
        brandLabel.setFont(font);
        colorLabel.setFont(font);
        prodYearLabel.setFont(font);
        engineSizeLabel.setFont(font);
        ownerNameLabel.setFont(font);

        // Zablokowanie możliwości edycji tekstów we wszystkich
        // polach tekstowych.  (pola nieedytowalne)
        brandField.setEditable(false);
        colorField.setEditable(false);
        prodYearField.setEditable(false);
        engineSizeField.setEditable(false);
        ownerNameField.setEditable(false);


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
        panel.add(brandLabel);
        panel.add(brandField);

        panel.add(colorLabel);
        panel.add(colorField);

        panel.add(prodYearLabel);
        panel.add(prodYearField);

        panel.add(engineSizeLabel);
        panel.add(engineSizeField);

        panel.add(ownerNameLabel);
        panel.add(ownerNameField);

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

        // Pokazanie tego co "narysowaliśmy"
        setVisible(true);
    }


    /*
     * Metoda wypełnia wszystkie pola tekstowe danymi
     * aktualnej osoby.
     */
    void showCurrentPerson() {
        if (currentCar == null) {
            brandField.setText("");
            colorField.setText("");
            prodYearField.setText("");
            engineSizeField.setText("");
            ownerNameField.setText("");
        } else {
            brandField.setText(currentCar.getCarBrand().name());
            colorField.setText(currentCar.getCarCColor().name());
            prodYearField.setText("" + currentCar.getCarProdYear());
            engineSizeField.setText("" + currentCar.getCarEngineSize());
            ownerNameField.setText("" + currentCar.getCarOwnerName());
        }
    }


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


}
