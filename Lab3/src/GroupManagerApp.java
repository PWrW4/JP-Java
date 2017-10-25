
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

/*
 * Program: Aplikacja okienkowa z GUI, kt�ra umo�liwia 
 *          zarz�dzanie grupami obiekt�w klasy Person.
 *    Plik: GroupManagerApp.java
 *          
 *   Autor: Pawe� Rogalinski
 *    Data: pazdziernik 2017 r.
 */

public class GroupManagerApp extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    private static final String GREETING_MESSAGE =
            "Program do zarz�dzania grupami os�b " +
                    "- wersja okienkowa\n\n" +
                    "Autor: Pawe� Rogalinski\n" +
                    "Data:  pa�dziernik 2017 r.\n";

    // Nazwa pliku w kt�rym s� zapisywane automatycznie dane przy
    // zamykaniu aplikacji i z kt�rego s� czytane dane po uruchomieniu.
    private static final String ALL_GROUPS_FILE = "LISTA_GRUP.BIN";

    // Utworzenie obiektu reprezentuj�cego g��wne okno aplikacji.
    // Po utworzeniu obiektu na pulpicie zostanie wy�wietlone
    // g��wne okno aplikacji.
    public static void main(String[] args) {
        new GroupManagerApp();
    }


    WindowAdapter windowListener = new WindowAdapter() {

        @Override
        public void windowClosed(WindowEvent e) {
            // Wywo�ywane gdy okno aplikacji jest zamykane za pomoc�
            // wywo�ania metody dispose()

            JOptionPane.showMessageDialog(null, "Program zako�czy� dzia�anie!");

        }


        @Override
        public void windowClosing(WindowEvent e) {
            // Wywo�ywane gdy okno aplikacji jest  zamykane za pomoc�
            // systemowego menu okna tzn. krzy�yk w naro�niku)
            windowClosed(e);
        }

    };



    // Zbi�r grup os�b, kt�rymi zarz�dza aplikacja
    private List<GroupOfPeople> currentList = new ArrayList<GroupOfPeople>();

    // Pasek menu wy�wietlany na panelu w g��wnym oknie aplikacji
    JMenuBar menuBar        = new JMenuBar();
    JMenu menuGroups        = new JMenu("Grupy");
    JMenu menuSpecialGroups = new JMenu("Grupy specjalne");
    JMenu menuAbout         = new JMenu("O programie");

    // Opcje wy�wietlane na panelu w g��wnym oknie aplikacji
    JMenuItem menuNewGroup           = new JMenuItem("Utw�rz grup�");
    JMenuItem menuEditGroup          = new JMenuItem("Edytuj grup�");
    JMenuItem menuDeleteGroup        = new JMenuItem("Usu� grup�");
    JMenuItem menuLoadGroup          = new JMenuItem("za�aduj grup� z pliku");
    JMenuItem menuSaveGroup          = new JMenuItem("Zapisz grup� do pliku");

    JMenuItem menuGroupUnion         = new JMenuItem("Po��czenie grup");
    JMenuItem menuGroupIntersection  = new JMenuItem("Cz�� wsp�lna grup");
    JMenuItem menuGroupDifference    = new JMenuItem("R�nica grup");
    JMenuItem menuGroupSymmetricDiff = new JMenuItem("R�nica symetryczna grup");

    JMenuItem menuAuthor             = new JMenuItem("Autor");

    // Przyciski wy�wietlane na panelu w g��wnym oknie aplikacji
    JButton buttonNewGroup = new JButton("Utw�rz");
    JButton buttonEditGroup = new JButton("Edytuj");
    JButton buttonDeleteGroup = new JButton(" Unu� ");
    JButton buttonLoadGroup = new JButton("Otw�rz");
    JButton buttonSavegroup = new JButton("Zapisz");

    JButton buttonUnion = new JButton("Suma");
    JButton buttonIntersection = new JButton("Iloczyn");
    JButton buttonDifference = new JButton("R�nica");
    JButton buttonSymmetricDiff = new JButton("R�nica symetryczna");


    // Widok tabeli z list� grup wy�wietlany
    // na panelu w oknie g��wnym aplikacji
    ViewGroupList viewList;


    public GroupManagerApp() {
        // Konfiguracja parametr�w g��wnego okna aplikacji
        setTitle("GroupManager - zarz�dzanie grupami os�b");
        setSize(450, 400);
        setResizable(false);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Dodanie s�uchacza zdarze� od okna aplikacji, kt�ry
        // umo�liwi automatyczny zapis danych do pliku,
        // gdy g��wne okno aplikacji jest zamykane.
        addWindowListener(new WindowAdapter() {
                              // To jest definicja anonimowej klasy (klasy bez nazwy)
                              // kt�ra dziedziczy po klasie WindowAdapter i przedefiniowuje
                              // metody windowClosed oraz windowClosing.

                              @Override
                              public void windowClosed(WindowEvent event) {
                                  // Wywo�ywane gdy okno aplikacji jest zamykane za pomoc�
                                  // wywo�ania metody dispose()
                                  try {
                                      saveGroupListToFile(ALL_GROUPS_FILE);
                                      JOptionPane.showMessageDialog(null, "Dane zosta�y zapisane do pliku " + ALL_GROUPS_FILE);
                                  } catch (PersonException e) {
                                      JOptionPane.showMessageDialog(null, e.getMessage(), "B��d", JOptionPane.ERROR_MESSAGE);
                                  }
                              }

                              @Override
                              public void windowClosing(WindowEvent e) {
                                  // Wywo�ywane gdy okno aplikacji jest zamykane za pomoc�
                                  // systemowego menu okna tzn. krzy�yk w naro�niku)
                                  windowClosed(e);
                              }

                          } // koniec klasy anonimowej
        ); // koniec wywo�ania metody addWindowListener

        try {
            // Automatyczne za�adowanie danych z pliku zanim
            // g��wne okno aplikacji zostanie pokazane na ekranie
            loadGroupListFromFile(ALL_GROUPS_FILE);
            JOptionPane.showMessageDialog(null, "Dane zosta�y wczytane z pliku " + ALL_GROUPS_FILE);
        } catch (PersonException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "B��d", JOptionPane.ERROR_MESSAGE);
        }


        // Utworzenie i konfiguracja menu aplikacji
        setJMenuBar(menuBar);
        menuBar.add(menuGroups);
        menuBar.add(menuSpecialGroups);
        menuBar.add(menuAbout);

        menuGroups.add(menuNewGroup);
        menuGroups.add(menuEditGroup);
        menuGroups.add(menuDeleteGroup);
        menuGroups.addSeparator();
        menuGroups.add(menuLoadGroup);
        menuGroups.add(menuSaveGroup);

        menuSpecialGroups.add(menuGroupUnion);
        menuSpecialGroups.add(menuGroupIntersection);
        menuSpecialGroups.add(menuGroupDifference);
        menuSpecialGroups.add(menuGroupSymmetricDiff);

        menuAbout.add(menuAuthor);

        // Dodanie s�uchaczy zdarze� do wszystkich opcji menu.
        // UWAGA: s�uchaczem zdarze� b�dzie metoda actionPerformed
        // zaimplementowana w tej klasie i wywo�ana dla
        // bie��cej instancji okna aplikacji - referencja this
        menuNewGroup.addActionListener(this);
        menuEditGroup.addActionListener(this);
        menuDeleteGroup.addActionListener(this);
        menuLoadGroup.addActionListener(this);
        menuSaveGroup.addActionListener(this);
        menuGroupUnion.addActionListener(this);
        menuGroupIntersection.addActionListener(this);
        menuGroupDifference.addActionListener(this);
        menuGroupSymmetricDiff.addActionListener(this);
        menuAuthor.addActionListener(this);

        // Dodanie s�uchaczy zdarze� do wszystkich przycisk�w.
        // UWAGA: s�uchaczem zdarze� b�dzie metoda actionPerformed
        // zaimplementowana w tej klasie i wywo�ana dla
        // bie��cej instancji okna aplikacji - referencja this
        buttonNewGroup.addActionListener(this);
        buttonEditGroup.addActionListener(this);
        buttonDeleteGroup.addActionListener(this);
        buttonLoadGroup.addActionListener(this);
        buttonSavegroup.addActionListener(this);
        buttonUnion.addActionListener(this);
        buttonIntersection.addActionListener(this);
        buttonDifference.addActionListener(this);
        buttonSymmetricDiff.addActionListener(this);

        // Utwotrzenie tabeli z list� os�b nale��cych do grupy
        viewList = new ViewGroupList(currentList, 400, 250);
        viewList.refreshView();

        // Utworzenie g��wnego panelu okna aplikacji.
        // Domy�lnym mened�erem rozk�adu dla panelu b�dzie
        // FlowLayout, kt�ry uk�ada wszystkie komponenty jeden za drugim.
        JPanel panel = new JPanel();

        // Dodanie i rozmieszczenie na panelu wszystkich
        // komponent�w GUI.
        panel.add(viewList);
        panel.add(buttonNewGroup);
        panel.add(buttonEditGroup);
        panel.add(buttonDeleteGroup);
        panel.add(buttonLoadGroup);
        panel.add(buttonSavegroup);
        panel.add(buttonUnion);
        panel.add(buttonIntersection);
        panel.add(buttonDifference);
        panel.add(buttonSymmetricDiff);

        // Umieszczenie Panelu w g��wnym oknie aplikacji.
        setContentPane(panel);

        // Pokazanie na ekranie g��wnego okna aplikacji
        // UWAGA: T� instrukcj� nale�y wykona� jako ostatni�
        // po zainicjowaniu i rozmieszczeniu na panelu
        // wszystkich komponent�w GUI.
        // Od tego momentu aplikacja uruchamia g��wn� p�tl� zdarze�
        // kt�ra dzia�a w nowym w�tku niezale�nie od pozosta�ej cz�ci programu.
        setVisible(true);
    }



    @SuppressWarnings("unchecked")
    void loadGroupListFromFile(String file_name) throws PersonException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file_name))) {
            currentList = (List<GroupOfPeople>)in.readObject();
        } catch (FileNotFoundException e) {
            throw new PersonException("Nie odnaleziono pliku " + file_name);
        } catch (Exception e) {
            throw new PersonException("Wyst�pi� b��d podczas odczytu danych z pliku.");
        }
    }


    void saveGroupListToFile(String file_name) throws PersonException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file_name))) {
            out.writeObject(currentList);
        } catch (FileNotFoundException e) {
            throw new PersonException("Nie odnaleziono pliku " + file_name);
        } catch (IOException e) {
            throw new PersonException("Wyst�pi� b��d podczas zapisu danych do pliku.");
        }
    }


    //  Metoda tworzy okno dialogowe do wyboru grupy podczas tworzenia
    //  grup specjalnych i innych operacji na grupach
    private  GroupOfPeople chooseGroup(Window parent, String message){
        Object[] groups = currentList.toArray();
        GroupOfPeople group = (GroupOfPeople)JOptionPane.showInputDialog(
                parent, message,
                "Wybierz grup�",
                JOptionPane.QUESTION_MESSAGE,
                null,
                groups,
                null);
        return group;
    }



    @Override
    public void actionPerformed(ActionEvent event) {
        // Odczytanie referencji do obiektu, kt�ry wygenerowa� zdarzenie.
        Object source = event.getSource();

        try {
            if (source == menuNewGroup || source == buttonNewGroup) {
                GroupOfPeople group = GroupOfPeopleWindowDialog.createNewGroupOfPeople(this);
                if (group != null) {
                    currentList.add(group);
                }
            }

            if (source == menuEditGroup || source == buttonEditGroup) {
                int index = viewList.getSelectedIndex();
                if (index >= 0) {
                    Iterator<GroupOfPeople> iterator = currentList.iterator();
                    while (index-- > 0)
                        iterator.next();
                    new GroupOfPeopleWindowDialog(this, iterator.next());
                }
            }

            if (source == menuDeleteGroup || source == buttonDeleteGroup) {
                int index = viewList.getSelectedIndex();
                if (index >= 0) {
                    Iterator<GroupOfPeople> iterator = currentList.iterator();
                    while (index-- >= 0)
                        iterator.next();
                    iterator.remove();
                }
            }

            if (source == menuLoadGroup || source == buttonLoadGroup) {
                JFileChooser chooser = new JFileChooser(".");
                int returnVal = chooser.showOpenDialog(this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    GroupOfPeople group = GroupOfPeople.readFromFile(chooser.getSelectedFile().getName());
                    currentList.add(group);
                }
            }

            if (source == menuSaveGroup || source == buttonSavegroup) {
                int index = viewList.getSelectedIndex();
                if (index >= 0) {
                    Iterator<GroupOfPeople> iterator = currentList.iterator();
                    while (index-- > 0)
                        iterator.next();
                    GroupOfPeople group = iterator.next();

                    JFileChooser chooser = new JFileChooser(".");
                    int returnVal = chooser.showSaveDialog(this);
                    if (returnVal == JFileChooser.APPROVE_OPTION) {
                        GroupOfPeople.printToFile( chooser.getSelectedFile().getName(), group );
                    }
                }
            }

            if (source == menuGroupUnion || source == buttonUnion) {
                String message1 =
                        "SUMA GRUP\n\n" +
                                "Tworzenie grupy zawieraj�cej wszystkie osoby z grupy pierwszej\n" +
                                "oraz wszystkie osoby z grupy drugiej.\n" +
                                "Wybierz pierwsz� grup�:";
                String message2 =
                        "SUMA GRUP\n\n" +
                                "Tworzenie grupy zawieraj�cej wszystkie osoby z grupy pierwszej\n" +
                                "oraz wszystkie osoby z grupy drugiej.\n" +
                                "Wybierz drug� grup�:";
                GroupOfPeople group1 = chooseGroup(this, message1);
                if (group1 == null)
                    return;
                GroupOfPeople group2 = chooseGroup(this, message2);
                if (group2 == null)
                    return;
                currentList.add( GroupOfPeople.createGroupUnion(group1, group2) );
            }

            if (source == menuGroupIntersection || source == buttonIntersection) {
                String message1 =
                        "ILOCZYN GRUP\n\n" +
                                "Tworzenie grupy os�b, kt�re nale�� zar�wno do grupy pierwszej,\n" +
                                "jak i do grupy drugiej.\n" +
                                "Wybierz pierwsz� grup�:";
                String message2 =
                        "ILOCZYN GRUP\n\n" +
                                "Tworzenie grupy os�b, kt�re nale�� zar�wno do grupy pierwszej,\n" +
                                "jak i do grupy drugiej.\n" +
                                "Wybierz drug� grup�:";
                GroupOfPeople group1 = chooseGroup(this, message1);
                if (group1 == null)
                    return;
                GroupOfPeople group2 = chooseGroup(this, message2);
                if (group2 == null)
                    return;
                currentList.add( GroupOfPeople.createGroupIntersection(group1, group2) );
            }

            if (source == menuGroupDifference || source == buttonDifference) {
                String message1 =
                        "RӯNICA GRUP\n\n" +
                                "Tworzenie grupy os�b, kt�re nale�� do grupy pierwszej\n" +
                                "i nie ma ich w grupie drugiej.\n" +
                                "Wybierz pierwsz� grup�:";
                String message2 =
                        "RӯNICA GRUP\n\n" +
                                "Tworzenie grupy os�b, kt�re nale�� do grupy pierwszej\n" +
                                "i nie ma ich w grupie drugiej.\n" +
                                "Wybierz drug� grup�:";
                GroupOfPeople group1 = chooseGroup(this, message1);
                if (group1 == null)
                    return;
                GroupOfPeople group2 = chooseGroup(this, message2);
                if (group2 == null)
                    return;
                currentList.add( GroupOfPeople.createGroupDifference(group1, group2) );
            }

            if (source == menuGroupSymmetricDiff || source == buttonSymmetricDiff) {
                String message1 = "RӯNICA SYMETRYCZNA GRUP\n\n"
                        + "Tworzenie grupy zawieraj�cej osoby nale��ce tylko do jednej z dw�ch grup,\n"
                        + "Wybierz pierwsz� grup�:";
                String message2 = "RӯNICA SYMETRYCZNA GRUP\n\n"
                        + "Tworzenie grupy zawieraj�cej osoby nale��ce tylko do jednej z dw�ch grup,\n"
                        + "Wybierz drug� grup�:";
                GroupOfPeople group1 = chooseGroup(this, message1);
                if (group1 == null)
                    return;
                GroupOfPeople group2 = chooseGroup(this, message2);
                if (group2 == null)
                    return;
                currentList.add( GroupOfPeople.createGroupSymmetricDiff(group1, group2) );
            }

            if (source == menuAuthor) {
                JOptionPane.showMessageDialog(this, GREETING_MESSAGE);
            }

        } catch (PersonException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "B��d", JOptionPane.ERROR_MESSAGE);
        }

        // Aktualizacja zawarto�ci tabeli z list� grup.
        viewList.refreshView();
    }

} // koniec klasy GroupManagerApp



/*
 * Pomocnicza klasa do wy�wietlania listy grup
 * w postaci tabeli na panelu okna g��wnego
 */
class ViewGroupList extends JScrollPane {
    private static final long serialVersionUID = 1L;

    private List<GroupOfPeople> list;
    private JTable table;
    private DefaultTableModel tableModel;

    public ViewGroupList(List<GroupOfPeople> list, int width, int height){
        this.list = list;
        setPreferredSize(new Dimension(width, height));
        setBorder(BorderFactory.createTitledBorder("Lista grup:"));

        String[] tableHeader = { "Nazwa grupy", "Typ kolekcji", "Liczba os�b" };
        tableModel = new DefaultTableModel(tableHeader, 0);
        table = new JTable(tableModel) {

            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false; // Blokada mo�liwo�ci edycji kom�rek tabeli
            }
        };
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setRowSelectionAllowed(true);
        setViewportView(table);
    }

    void refreshView(){
        tableModel.setRowCount(0);
        for (GroupOfPeople group : list) {
            if (group != null) {
                String[] row = { group.getName(), group.getType().toString(), "" + group.size() };
                tableModel.addRow(row);
            }
        }
    }

    int getSelectedIndex(){
        int index = table.getSelectedRow();
        if (index<0) {
            JOptionPane.showMessageDialog(this, "�adana grupa nie jest zaznaczona.", "B��d", JOptionPane.ERROR_MESSAGE);
        }
        return index;
    }

} // koniec klasy ViewGroupList