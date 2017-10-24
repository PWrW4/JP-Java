import java.awt.Color;
import java.awt.Dialog;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;


/*
 * Program: Aplikacja okienkowa z GUI, która umożliwia testowanie
 *          operacji wykonywanych na obiektach klasy Car.
 *    Plik: CarWindowDialog.java
 *
 *   Autor: Wojciech Wójcik na podstawie programu Paweł Rogaliński
 *    Data: 24.10.2017 r.
 *
 *   Prog2 on Git repo: https://bitbucket.org/pwr_wroc_w4/jp3
 */
public class CarWindowDialog extends JDialog implements ActionListener {

    private static final long serialVersionUID = 1L;


    /*
     *  Referencja do obiektu, który zawiera dane osoby.
     */
    private Car car;


    // Utworzenie i inicjalizacja komponentów do do budowy
    // okienkowego interfejsu użytkownika
    JLabel brandLabel =    new JLabel("          Marka: ");
    JLabel colorLabel = new JLabel("Kolor: ");
    JLabel prodYearLabel =    new JLabel("    Rok produkcji: ");
    JLabel engineSizeLabel =     new JLabel("Pojemność: ");
    JLabel ownerNameLabel =     new JLabel("Imię właściciela: ");

    JComboBox <Brand> brandBox = new JComboBox<Brand>(Brand.values());
    JComboBox <CColor> colorBox = new JComboBox<CColor>(CColor.values());
    JTextField prodYearField = new JTextField(10);
    JTextField engineSizeField = new JTextField(10);
    JTextField ownerNameField = new JTextField(10);


    JButton OKButton = new JButton("  OK  ");
    JButton CancelButton = new JButton("Anuluj");


    /*
     * Konstruktor klasy PersonWindowDialog.
     *     parent - referencja do okna aplikacji, z którego
     *              zostało wywołane to okno dialogowe.
     *     person - referencja do obiektu reprezentującego osobę,
     *              której dane mają być modyfikowane.
     *              Jeśli person jest równe null to zostanie utworzony
     *              nowy obiekt klasy Person
     */
    private CarWindowDialog(Window parent, Car car) {
        // Wywołanie konstruktora klasy bazowej JDialog.
        // Ta instrukcja pododuje ustawienie jako rodzica nowego okna dialogowego
        // referencji do tego okna, z którego wywołano to okno dialogowe.
        // Drugi parametr powoduje ustawienie trybu modalności nowego okna diakogowego
        //       - DOCUMENT_MODAL oznacza, że okno rodzica będzie blokowane.
        super(parent, Dialog.ModalityType.DOCUMENT_MODAL);

        // Konfiguracja parametrów tworzonego okna dialogowego
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(220, 290);
        setLocationRelativeTo(parent);

        // zapamiętanie referencji do osoby, której dane będą modyfikowane.
        this.car = car;

        // Ustawienie tytułu okna oraz wypełnienie zawartości pól tekstowych
        if (car==null){
            setTitle("Nowe auto");
        } else{
            setTitle(car.toString());
            brandBox.setSelectedItem(car.getCarBrand());
            colorBox.setSelectedItem(car.getCarCColor());
            prodYearField.setText(""+car.getCarProdYear());
            engineSizeField.setText(""+car.getCarEngineSize());
            ownerNameField.setText(""+car.getCarOwnerName());

        }

        // Dodanie słuchaczy zdarzeń do przycisków.
        // UWAGA: słuchaczem zdarzeń będzie metoda actionPerformed
        //        zaimplementowana w tej klasie i wywołana dla
        //        bieżącej instancji okna dialogowego - referencja this
        OKButton.addActionListener( this );
        CancelButton.addActionListener( this );

        // Utworzenie głównego panelu okna dialogowego.
        // Domyślnym menedżerem rozdładu dla panelu będzie
        // FlowLayout, który układa wszystkie komponenty jeden za drugim.
        JPanel panel = new JPanel();

        // Zmiana koloru tła głównego panelu okna dialogowego
        panel.setBackground(Color.green);

        // Dodanie i rozmieszczenie na panelu wszystkich komponentów GUI.
        panel.add(brandLabel);
        panel.add(brandBox);

        panel.add(colorLabel);
        panel.add(colorBox);

        panel.add(prodYearLabel);
        panel.add(prodYearField);

        panel.add(engineSizeLabel);
        panel.add(engineSizeField);

        panel.add(ownerNameLabel);
        panel.add(ownerNameField);

        panel.add(OKButton);
        panel.add(CancelButton);

        // Umieszczenie Panelu w oknie dialogowym.
        setContentPane(panel);


        // Pokazanie na ekranie okna dialogowego
        // UWAGA: Tą instrukcję należy wykonać jako ostatnią
        // po zainicjowaniu i rozmieszczeniu na panelu
        // wszystkich komponentów GUI.
        // Od tego momentu aplikacja wyświetla nowe okno dialogowe
        // i bokuje główne okno aplikacji, z którego wywołano okno dialogowe
        setVisible(true);
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
        Object source = event.getSource();

        if (source == OKButton) {
            try {
                if (car == null) { // Utworzenie nowej osoby
                    car = new Car(ownerNameField.getText(), engineSizeField.getText(),prodYearField.getText());
                } else { // Aktualizacji imienia i nazwiska istniejącej osoby
                    car.setCarOwnerName(ownerNameField.getText());
                    car.setCarEngineSize(engineSizeField.getText());
                    car.setCarProdYear(prodYearField.getText());
                }
                // Aktualizacja pozostałych danych osoby
                car.setCarBrand((Brand) brandBox.getSelectedItem());
                car.setCarCColor((CColor) colorBox.getSelectedItem());

                // Zamknięcie okna i zwolnienie wszystkich zasobów.
                dispose();
            } catch (CarException e) {
                // Tu są wychwytywane wyjątki zgłaszane przez metody klasy Person
                // gdy nie są spełnione ograniczenia nałożone na dopuszczelne wartości
                // poszczególnych atrybutów.
                // Wyświetlanie modalnego okna dialogowego
                // z komunikatem o błędzie zgłoszonym za pomocą wyjątku PersonException.
                JOptionPane.showMessageDialog(this, e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
            }
        }

        if (source == CancelButton) {
            // Zamknięcie okna i zwolnienie wszystkich zasobów.
            dispose();
        }
    }


    /*
     * Meoda tworzy pomocnicze okno dialogowe, które tworzy
     * nowy obiekt klasy Person i umożliwia wprowadzenie danych
     * dla nowo utworzonej osoby.
     * Jako pierwszy parametr należy przekazać referencję do głównego okna
     * aplikacji, z którego ta metoda jest wywoływana.
     * Główne okno aplikacji zostanie zablokowane do momentu zamknięcia
     * okna dialogowego.
     * Po zatwierdzeniu zmian przyciskiem OK odbywa się  walidacja poprawności
     * danych w konstruktorze i setterach klasy Person.
     * Jeśli zostaną wykryte niepoprawne dane to zostanie przechwycony wyjątek
     * PersonException i zostanie wyświetlony komunikat o błędzie.
     *
     *  Po poprawnym wypełnieniu danych metoda zamyka okno dialogowe
     *  i zwraca referencję do nowo utworzonego obiektu klasy Person.
     */
    public static Car createNewCar(Window parent) {
        CarWindowDialog dialog = new CarWindowDialog(parent, null);
        return dialog.car;
    }

    /*
     * Meoda tworzy pomocnicze okno dialogowe, które umożliwia
     * modyfikację danych osoby reprezentowanej przez obiekt klasy Person,
     * który został przekazany jako drugi parametr.
     * Jako pierwszy parametr należy przekazać referencję do głównego okna
     * aplikacji, z którego ta metoda jest wywoływana.
     * Główne okno aplikacji zostanie zablokowane do momentu zamknięcia
     * okna dialogowego.
     * Po zatwierdzeniu zmian przyciskiem OK odbywa się  walidacja poprawności
     * danych w konstruktorze i setterach klasy Person.
     * Jeśli zostaną wykryte niepoprawne dane to zostanie przechwycony wyjątek
     * PersonException i zostanie wyświetlony komunikat o błędzie.
     *
     *  Po poprawnym wypełnieniu danych metoda aktualizuje dane w obiekcie person
     *  i zamyka okno dialogowe
     */
    public static void changePersonData(Window parent, Car car) {
        new CarWindowDialog(parent, car);
    }

}

