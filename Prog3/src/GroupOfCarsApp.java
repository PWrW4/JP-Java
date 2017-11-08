import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class GroupOfCarsApp extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private static final String GREETING_MESSAGE =
                  "Modyfikacja Grup samochodów " +
                    "- wersja okienkowa\n\n" +
                    "Autor: Wojciech Wójcik\n" +
                    "Data:  8.11.2017 r.\n";

    // Nazwa pliku w którym są zapisywane automatycznie dane przy
    // zamykaniu aplikacji i z którego są czytane dane po uruchomieniu.
    private static final String ALL_GROUPS_FILE = "LISTA_GRUP.BIN";

    // Utworzenie obiektu reprezentującego główne okno aplikacji.
    // Po utworzeniu obiektu na pulpicie zostanie wyświetlone
    // główne okno aplikacji.
    public static void main(String[] args) {
        new GroupOfCarsApp();
    }


    WindowAdapter windowListener = new WindowAdapter() {

        @Override
        public void windowClosed(WindowEvent e) {
            // Wywoływane gdy okno aplikacji jest zamykane za pomocą
            // wywołania metody dispose()

            JOptionPane.showMessageDialog(null, "Program zakończył działanie!");

        }


        @Override
        public void windowClosing(WindowEvent e) {
            // Wywoływane gdy okno aplikacji jest  zamykane za pomocą
            // systemowego menu okna tzn. krzyżyk w narożniku)
            windowClosed(e);
        }

    };



    // Zbiór samochodow, którymi zarządza aplikacja
    private List<Car> currentList = new ArrayList<Car>();

    // Pasek menu wyświetlany na panelu w głównym oknie aplikacji
    JMenuBar menuBar     = new JMenuBar();
    JMenu menuCar        = new JMenu("Lisa samochodów");
    JMenu menuSort       = new JMenu("Sortowanie");
    JMenu menuProperties = new JMenu("Właściwośći");
    JMenu menuAbout      = new JMenu("O programie");

    // Opcje wyświetlane na panelu w głównym oknie aplikacji
    JMenuItem menuAddCar          = new JMenuItem("Utwórz samochód");
    JMenuItem menuEditCar          = new JMenuItem("Edytuj samochód");
    JMenuItem menuDeleteCar        = new JMenuItem("Usuń samochód");
    JMenuItem menuLoadCar          = new JMenuItem("załaduj samochód z pliku");
    JMenuItem menuSaveCar          = new JMenuItem("Zapisz samochód do pliku");

    JMenuItem menuSortBrand         = new JMenuItem("Sortowanie Marki");
    JMenuItem menuSortEngineSize  = new JMenuItem("Sortowanie Pojemności");
    JMenuItem menuSortColor    = new JMenuItem("Sortowanie Koloru");
    JMenuItem menuSortOwnerName = new JMenuItem("Sortowanie Imienia Właśiciela");
    JMenuItem menuSortProdYear = new JMenuItem("Sortowanie roku produkcji");

    JMenuItem menuPropChangeName = new JMenuItem("zmien nazwe grupy");
    JMenuItem menuPropChangeCollType = new JMenuItem("zmien typ kolekcji");

    JMenuItem menuAuthor             = new JMenuItem("Autor");

    // Przyciski wyświetlane na panelu w głównym oknie aplikacji
    JButton buttonAddCar  = new JButton("Utwórz niowe somochod");
    JButton buttonEditCar = new JButton("Edytuj somochod");
    JButton buttonDeleteCar = new JButton(" Unuń somochod");
    JButton buttonLoadCar = new JButton("Otwórz somochod z pliku");
    JButton buttonSaveCar = new JButton("Zapisz somochod do pliku");

    // Widok tabeli z listą grup wyświetlany
    // na panelu w oknie głównym aplikacji
    ViewCarList viewList;



    public GroupOfCarsApp() {
        // Konfiguracja parametrów głównego okna aplikacji
        setTitle("Modyfikacja grup Samochodów");
        setSize(450, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        // Utworzenie i konfiguracja menu aplikacji
        setJMenuBar(menuBar);
        menuBar.add(menuCar);
        menuBar.add(menuSort);
        menuBar.add(menuProperties);
        menuBar.add(menuAbout);

        menuCar.add(menuAddCar);
        menuCar.add(menuEditCar);
        menuCar.add(menuDeleteCar);
        menuCar.addSeparator();
        menuCar.add(menuLoadCar);
        menuCar.add(menuSaveCar);


        menuSort.add(menuSortBrand);
        menuSort.add(menuSortEngineSize);
        menuSort.add(menuSortColor);
        menuSort.add(menuSortOwnerName);
        menuSort.add(menuSortProdYear);

        menuProperties.add(menuPropChangeName);
        menuProperties.add(menuPropChangeCollType);

        menuAbout.add(menuAuthor);

        // Dodanie słuchaczy zdarzeń do wszystkich opcji menu.
        // UWAGA: słuchaczem zdarzeń będzie metoda actionPerformed
        // zaimplementowana w tej klasie i wywołana dla
        // bieżącej instancji okna aplikacji - referencja this
        menuAddCar.addActionListener(this);
        menuEditCar.addActionListener(this);
        menuDeleteCar.addActionListener(this);
        menuLoadCar.addActionListener(this);
        menuSaveCar.addActionListener(this);
        menuSortBrand.addActionListener(this);
        menuSortEngineSize.addActionListener(this);
        menuSortColor.addActionListener(this);
        menuSortOwnerName.addActionListener(this);
        menuSortProdYear.addActionListener(this);
        menuPropChangeName.addActionListener(this);
        menuPropChangeCollType.addActionListener(this);
        menuAuthor.addActionListener(this);

        // Dodanie słuchaczy zdarzeń do wszystkich przycisków.
        // UWAGA: słuchaczem zdarzeń będzie metoda actionPerformed
        // zaimplementowana w tej klasie i wywołana dla
        // bieżącej instancji okna aplikacji - referencja this
        buttonAddCar.addActionListener(this);
        buttonEditCar.addActionListener(this);
        buttonDeleteCar.addActionListener(this);
        buttonLoadCar.addActionListener(this);
        buttonSaveCar.addActionListener(this);


        // Utwotrzenie tabeli z listą osób należących do grupy
        viewList = new ViewCarList(currentList, 400, 350);
        viewList.refreshView();

        // Utworzenie głównego panelu okna aplikacji.
        // Domyślnym menedżerem rozkładu dla panelu będzie
        // FlowLayout, który układa wszystkie komponenty jeden za drugim.
        JPanel panel = new JPanel();

        // Dodanie i rozmieszczenie na panelu wszystkich
        // komponentów GUI.
        panel.add(viewList);
        panel.add(buttonAddCar);
        panel.add(buttonEditCar);
        panel.add(buttonDeleteCar);
        panel.add(buttonLoadCar);
        panel.add(buttonSaveCar);


        // Umieszczenie Panelu w głównym oknie aplikacji.
        setContentPane(panel);

        // Pokazanie na ekranie głównego okna aplikacji
        // UWAGA: Tą instrukcję należy wykonać jako ostatnią
        // po zainicjowaniu i rozmieszczeniu na panelu
        // wszystkich komponentów GUI.
        // Od tego momentu aplikacja uruchamia główną pętlę zdarzeń
        // która działa w nowym wątku niezależnie od pozostałej części programu.
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent event) {
        // Odczytanie referencji do obiektu, który wygenerował zdarzenie.
        Object source = event.getSource();

        //try {

            if (source == menuAddCar || source == buttonAddCar) {

            }

            if (source == menuEditCar || source == buttonEditCar) {

            }

            if (source == menuDeleteCar || source == buttonDeleteCar) {

            }

            if (source == menuLoadCar || source == buttonLoadCar) {

            }

            if (source == menuSaveCar || source == buttonSaveCar) {

            }

            if (source == menuSortBrand) {

            }

            if (source == menuSortEngineSize) {

            }

            if (source == menuSortColor) {

            }

            if (source == menuSortOwnerName) {

            }

            if (source == menuSortProdYear) {

            }

            if (source == menuPropChangeName) {

            }

            if (source == menuPropChangeCollType) {

            }


            if (source == menuAuthor) {
                JOptionPane.showMessageDialog(this, GREETING_MESSAGE);
            }

//        } catch (CarException e) {
//            JOptionPane.showMessageDialog(this, e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
//        }

        // Aktualizacja zawartości tabeli z listą grup.
        viewList.refreshView();
    }
}

//klasa viewcarList
class ViewCarList extends JScrollPane {
    private static final long serialVersionUID = 1L;

    private List<Car> list;
    private JTable table;
    private DefaultTableModel tableModel;

    public ViewCarList(List<Car> list, int width, int height){
        this.list = list;
        setPreferredSize(new Dimension(width, height));
        setBorder(BorderFactory.createTitledBorder("Lista samochodow:"));

        String[] tableHeader = { "Marka", "Rok Produkcji", "Pojemność", "Kolor", "Imie właściciela" };
        tableModel = new DefaultTableModel(tableHeader, 0);
        table = new JTable(tableModel) {

            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; // Blokada możliwości edycji komórek tabeli
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        setViewportView(table);
    }

    void refreshView(){
        tableModel.setRowCount(0);
        for (Car car : list) {
            if (car != null) {
                String[] row = { car.getCarBrand().name(),"" + car.getCarProdYear(),"" + car.getCarEngineSize(), car.getCarCColor().name(),car.getCarOwnerName() };
                tableModel.addRow(row);
            }
        }
    }

    int getSelectedIndex(){
        int index = table.getSelectedRow();
        if (index<0) {
            JOptionPane.showMessageDialog(this, "Żadana grupa nie jest zaznaczona.", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
        return index;
    }

}