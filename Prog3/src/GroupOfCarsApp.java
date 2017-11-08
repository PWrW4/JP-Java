import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;

//
//    Autor: Wojciech Wójcik
//    Data: 08.11.2017 r.
//    Git repo: https://bitbucket.org/pwr_wroc_w4/jp3

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
    public static void main(String[] args) throws CarException {
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
    GroupOfCars currentGroup = new GroupOfCars(GroupType.ARRAY_LIST, "superGrupa");


    // Pasek menu wyświetlany na panelu w głównym oknie aplikacji
    JMenuBar menuBar = new JMenuBar();
    JMenu menuCar = new JMenu("Lisa samochodów");
    JMenu menuSort = new JMenu("Sortowanie");
    JMenu menuProperties = new JMenu("Właściwośći");
    JMenu menuAbout = new JMenu("O programie");

    // Opcje wyświetlane na panelu w głównym oknie aplikacji
    JMenuItem menuAddCar = new JMenuItem("Utwórz samochód");
    JMenuItem menuEditCar = new JMenuItem("Edytuj samochód");
    JMenuItem menuDeleteCar = new JMenuItem("Usuń samochód");
    JMenuItem menuLoadCar = new JMenuItem("załaduj samochód z pliku");
    JMenuItem menuSaveCar = new JMenuItem("Zapisz samochód do pliku");

    JMenuItem menuSortBrand = new JMenuItem("Sortowanie Marki");
    JMenuItem menuSortEngineSize = new JMenuItem("Sortowanie Pojemności");
    JMenuItem menuSortColor = new JMenuItem("Sortowanie Koloru");
    JMenuItem menuSortOwnerName = new JMenuItem("Sortowanie Imienia Właśiciela");
    JMenuItem menuSortProdYear = new JMenuItem("Sortowanie roku produkcji");

    JMenuItem menuPropChangeName = new JMenuItem("Zmien nazwę grupy");
    JMenuItem menuPropChangeCollType = new JMenuItem("Zmien typ kolekcji");

    JMenuItem menuAuthor = new JMenuItem("Autor");

    // Przyciski wyświetlane na panelu w głównym oknie aplikacji
    JLabel groupNameLabel =      new JLabel("      Name: ");
    JLabel collectionLabel = new JLabel("  Kolekcja: ");
    JTextField groupNameField = new JTextField(10);
    JTextField collectionField = new JTextField(10);

    JButton buttonAddCar = new JButton("Utwórz niowe somochod");
    JButton buttonEditCar = new JButton("Edytuj somochod");
    JButton buttonDeleteCar = new JButton(" Unuń somochod");
    JButton buttonLoadCar = new JButton("Otwórz somochod z pliku");
    JButton buttonSaveCar = new JButton("Zapisz somochod do pliku");

    // Widok tabeli z listą grup wyświetlany
    // na panelu w oknie głównym aplikacji
    ViewCarList viewList;


    public GroupOfCarsApp() throws CarException {
        // Konfiguracja parametrów głównego okna aplikacji
        setTitle("Modyfikacja grup Samochodów");
        setSize(450, 500);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        collectionField.setEditable(false);
        collectionField.setText(GroupType.ARRAY_LIST.name());
        groupNameField.setEditable(false);
        groupNameField.setText("superGrupa");


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
        viewList = new ViewCarList(currentGroup, 400, 300);
        viewList.refreshView();

        // Utworzenie głównego panelu okna aplikacji.
        // Domyślnym menedżerem rozkładu dla panelu będzie
        // FlowLayout, który układa wszystkie komponenty jeden za drugim.
        JPanel panel = new JPanel();

        // Dodanie i rozmieszczenie na panelu wszystkich
        // komponentów GUI.
        panel.add(groupNameLabel);
        panel.add(groupNameField);
        panel.add(collectionLabel);
        panel.add(collectionField);
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

    static private GroupType choseCollectionWindow(Window parent, String message){
        GroupType group = (GroupType)JOptionPane.showInputDialog(parent,"Wynierz kolekcje:","Wynierz kolekcje:",JOptionPane.QUESTION_MESSAGE,null,GroupType.values(),GroupType.ARRAY_LIST);
        return group;
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        // Odczytanie referencji do obiektu, który wygenerował zdarzenie.
        Object source = event.getSource();

        try {

            if (source == menuAddCar || source == buttonAddCar) {
                currentGroup.add(CarWindowDialog.createNewCar(this));
            }

            if (source == menuEditCar || source == buttonEditCar) {
                Car carToEdit = null;
                int index = viewList.getSelectedIndex();
                if (index >= 0) {
                    Iterator<Car> iterator = currentGroup.iterator();
                    while (index-- >= 0)
                        carToEdit = iterator.next();
                    CarWindowDialog.changePersonData(this, carToEdit);
                }
            }

            if (source == menuDeleteCar || source == buttonDeleteCar) {
                int index = viewList.getSelectedIndex();
                if (index >= 0) {
                    Iterator<Car> iterator = currentGroup.iterator();
                    while (index-- >= 0)
                        iterator.next();
                    iterator.remove();
                }
            }

            if (source == menuLoadCar || source == buttonLoadCar) {
                JFileChooser chooser = new JFileChooser(".");
                int returnVal = chooser.showOpenDialog(this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    Car car = Car.readFromFile(chooser.getSelectedFile().getName());
                    currentGroup.add(car);
                }
            }

            if (source == menuSaveCar || source == buttonSaveCar) {
                int index = viewList.getSelectedIndex();
                if (index >= 0) {
                    Iterator<Car> iterator = currentGroup.iterator();
                    while (index-- > 0)
                        iterator.next();
                    Car car = iterator.next();

                    JFileChooser chooser = new JFileChooser(".");
                    int returnVal = chooser.showSaveDialog(this);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        Car.printToFile(chooser.getSelectedFile().getName(), car);
                    }
                }
            }

            if (source == menuSortBrand) {
                currentGroup.sortBrand();
            }

            if (source == menuSortEngineSize) {
                currentGroup.sortEngineSize();
            }

            if (source == menuSortColor) {
                currentGroup.sortCollor();
            }

            if (source == menuSortOwnerName) {
                currentGroup.sortOwnerName();
            }

            if (source == menuSortProdYear) {
                currentGroup.sortProdYear();
            }

            if (source == menuPropChangeName) {
                JFrame frame = new JFrame("Edycja nazwy grupy");
                currentGroup.setName(JOptionPane.showInputDialog(frame, "Podaj nazwę grupy:"));
                groupNameField.setText(currentGroup.getName());
            }

            if (source == menuPropChangeCollType) {
                currentGroup.setType(choseCollectionWindow(this,null));
                collectionField.setText(currentGroup.getType().name());
            }



            if (source == menuAuthor) {
                JOptionPane.showMessageDialog(this, GREETING_MESSAGE);
            }

        } catch (CarException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Błąd", JOptionPane.ERROR_MESSAGE);
        }

        // Aktualizacja zawartości tabeli z listą grup.
        viewList.refreshView();
    }

    public static GroupOfCars createNewGroupOfPeople(GroupManagerApp groupManagerApp) throws CarException {
        JFrame frame = new JFrame("Edycja nazwy grupy");
        String nameOfGroup = JOptionPane.showInputDialog(frame, "Podaj nazwę grupy:");
        GroupType groupType = choseCollectionWindow(groupManagerApp,null);
        GroupOfCarsApp APP = new GroupOfCarsApp();
        APP.currentGroup.setName(nameOfGroup);
        APP.currentGroup.setType(groupType);
        APP.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        return APP.currentGroup;
    }
}

//klasa viewcarList
class ViewCarList extends JScrollPane {
    private static final long serialVersionUID = 1L;

    private GroupOfCars goc;
    private JTable table;
    private DefaultTableModel tableModel;

    public ViewCarList(GroupOfCars _goc, int width, int height) {
        this.goc = _goc;
        setPreferredSize(new Dimension(width, height));
        setBorder(BorderFactory.createTitledBorder("Lista samochodow:"));

        String[] tableHeader = {"Marka", "Rok Produkcji", "Pojemność", "Kolor", "Imie właściciela"};
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

    void refreshView() {
        tableModel.setRowCount(0);
        for (Car car : goc) {
            if (car != null) {
                String[] row = {car.getCarBrand().name(), "" + car.getCarProdYear(), "" + car.getCarEngineSize(), car.getCarCColor().name(), car.getCarOwnerName()};
                tableModel.addRow(row);
            }
        }
    }

    int getSelectedIndex() {
        int index = table.getSelectedRow();
        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Żadana grupa nie jest zaznaczona.", "Błąd", JOptionPane.ERROR_MESSAGE);
        }
        return index;
    }

}