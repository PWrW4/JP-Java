import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

/*
 * Program: Aplikacja okienkowa z GUI, która umożliwia zarządzanie
 *          grupami obiektów klasy Person.
 *    Plik: GroupOfPeople.java
 *
 *   Autor: Paweł Rogalinski
 *    Data: pazdziernik 2017 r.
 */

/*
 *  Typ wyliczeniowy GroupType reprezentuje typy kolekcji,
 *  które mogą być wykorzystane do tworzenia grupy osób.
 *  w programie można wybrać dwa rodzaje kolekcji: listy i zbiory.
 *  Każdy rodzaj kolekcji może być implementowany przy pomocy
 *  różnych klas:
 *      Listy: klasa Vector, klasa ArrayList, klasa LinnkedList;
 *     Zbiory: klasa TreeSet, klasa HashSet.
 */
enum GroupType {
    VECTOR("Lista   (klasa Vector)"),
    ARRAY_LIST("Lista   (klasa ArrayList)"),
    LINKED_LIST("Lista   (klasa LinkedList)"),
    HASH_SET("Zbiór   (klasa HashSet)"),
    TREE_SET("Zbiór   (klasa TreeSet)");

    String typeName;

    private GroupType(String type_name) {
        typeName = type_name;
    }


    @Override
    public String toString() {
        return typeName;
    }


    public static GroupType find(String type_name){
        for(GroupType type : values()){
            if (type.typeName.equals(type_name)){
                return type;
            }
        }
        return null;
    }


    // Metoda createCollection() dla wybranego typu grupy
    // tworzy kolekcję obiektów klasy Person implementowaną
    // za pomocą właściwej klasy z pakietu Java Collection Framework.
    public Collection<Person> createCollection() throws PersonException {
        switch (this) {
            case VECTOR:      return new Vector<Person>();
            case ARRAY_LIST:  return new ArrayList<Person>();
            case HASH_SET:    return new HashSet<Person>();
            case LINKED_LIST: return new LinkedList<Person>();
            case TREE_SET:    return new TreeSet<Person>();
            default:          throw new PersonException("Podany typ kolekcji nie został zaimplementowany.");
        }
    }

}  // koniec klasy enum GroupType




/*
 * Klasa GroupOfPeople reprezentuje grupy osób, które są opisane za pomocą
 * trzech atrybutow: name, type oraz collection:
 *     name - nazwa grupy wybierana przez użytkownika
 *            (musi zawierać niepusty ciąg znaków).
 *     type - typ kolekcji, która ma być użyta do zapamiętania
 *            danych osób należących do tej grupy.
 *     collection - kolekcja obiektów klasy Person, w której
 *                  pamiętane są dane osób należących do tej grupy.
 *                  (Musi być to obiekt utworzony za pomocą metody
 *                  createCollection z typu wyliczeniowego GroupType).
 */
public class GroupOfPeople implements Iterable<Person>, Serializable{

    private static final long serialVersionUID = 1L;

    private String name;
    private GroupType type;
    private Collection<Person> collection;


    public GroupOfPeople(GroupType type, String name) throws PersonException {
        setName(name);
        if (type==null){
            throw new PersonException("Nieprawidłowy typ kolekcji.");
        }
        this.type = type;
        collection = this.type.createCollection();
    }


    public GroupOfPeople(String type_name, String name) throws PersonException {
        setName(name);
        GroupType type = GroupType.find(type_name);
        if (type==null){
            throw new PersonException("Nieprawidłowy typ kolekcji.");
        }
        this.type = type;
        collection = this.type.createCollection();
    }


    public String getName() {
        return name;
    }


    public void setName(String name) throws PersonException {
        if ((name == null) || name.equals(""))
            throw new PersonException("Nazwa grupy musi być określona.");
        this.name = name;
    }


    public GroupType getType() {
        return type;
    }


    public void setType(GroupType type) throws PersonException {
        if (type == null) {
            throw new PersonException("Typ kolekcji musi być określny.");
        }
        if (this.type == type)
            return;
        // Gdy następuje zmiana typu kolekcji to osoby zapamiętane
        // w dotychczasowej kolekcji muszą zostać przepisane do nowej
        // kolekcji, która jest implementowana, przy pomocy
        // klasy właściwej dla nowego typu kolekcji.
        Collection<Person> oldCollection = collection;
        collection = type.createCollection();
        this.type = type;
        for (Person person : oldCollection)
            collection.add(person);
    }


    public void setType(String type_name) throws PersonException {
        for(GroupType type : GroupType.values()){
            if (type.toString().equals(type_name)) {
                setType(type);
                return;
            }
        }
        throw new PersonException("Nie ma takiego typu kolekcji.");
    }


    // Zamiast gettera getCollection zostały zaimplementowane
    // niezbędne metody delegowane z interfejsu Collection,
    // które umożliwiają wykonanie wszystkich operacji na
    // kolekcji osób.

    public boolean add(Person e) {
        return collection.add(e);
    }

    public Iterator<Person> iterator() {
        return collection.iterator();
    }

    public int size() {
        return collection.size();
    }


    // Poniżej zostały zaimplementowane metody umożliwiające
    // sortowanie listy osób według poszczególnych atrybutów.
    // UWAGA: sortowanie jest możliwe tylko dla kolekcji typu Lista.
    public void sortName() throws PersonException {
        if (type==GroupType.HASH_SET|| type==GroupType.TREE_SET ){
            throw new PersonException("Kolekcje typu SET nie mogą być sortowane.");
        }
        // Przy sortowaniu jako komparator zostanie wykorzystana
        // metoda compareTo będąca implementacją interfejsu
        // Comparable w klasie Person.
        Collections.sort((List<Person>)collection);
    }

    public void sortBirthYear() throws PersonException {
        if (type == GroupType.HASH_SET || type == GroupType.TREE_SET) {
            throw new PersonException("Kolekcje typu SET nie mogą być sortowane.");
        }
        // Przy sortowaniu jako komparator zostanie wykorzystany
        // obiekt klasy anonimowej (klasa bez nazwy), która implementuje
        // interfejs Comparator i zawiera tylko jedną metodę compare.
        Collections.sort((List<Person>) collection, new Comparator<Person>() {

            @Override
            public int compare(Person o1, Person o2) {
                if (o1.getBirthYear() < o2.getBirthYear())
                    return -1;
                if (o1.getBirthYear() > o2.getBirthYear())
                    return 1;
                return 0;
            }

        });
    }

    public void sortJob() throws PersonException {
        if (type == GroupType.HASH_SET || type == GroupType.TREE_SET) {
            throw new PersonException("Kolekcje typu SET nie mogą być sortowane.");
        }
        // Przy sortowaniu jako komparator zostanie wykorzystany
        // obiekt klasy anonimowej (klasa bez nazwy), która implementuje
        // interfejs Comparator i zawiera tylko jedną metodę compare.
        Collections.sort((List<Person>) collection, new Comparator<Person>() {

            @Override
            public int compare(Person o1, Person o2) {
                return o1.getJob().toString().compareTo(o2.getJob().toString());
            }

        });
    }


    @Override
    public String toString() {
        return name + "  [" + type + "]";
    }


    public static void printToFile(PrintWriter writer, GroupOfPeople group) {
        writer.println(group.getName());
        writer.println(group.getType());
        for (Person person : group.collection)
            Person.printToFile(writer, person);
    }


    public static void printToFile(String file_name, GroupOfPeople group) throws PersonException {
        try (PrintWriter writer = new PrintWriter(file_name)) {
            printToFile(writer, group);
        } catch (FileNotFoundException e){
            throw new PersonException("Nie odnaleziono pliku " + file_name);
        }
    }


    public static GroupOfPeople readFromFile(BufferedReader reader) throws PersonException{
        try {
            String group_name = reader.readLine();
            String type_name = reader.readLine();
            GroupOfPeople groupOfPeople = new GroupOfPeople(type_name, group_name);

            Person person;
            while((person = Person.readFromFile(reader)) != null)
                groupOfPeople.collection.add(person);
            return groupOfPeople;
        } catch(IOException e){
            throw new PersonException("Wystąpił błąd podczas odczytu danych z pliku.");
        }
    }


    public static GroupOfPeople readFromFile(String file_name) throws PersonException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(file_name)))) {
            return GroupOfPeople.readFromFile(reader);
        } catch (FileNotFoundException e){
            throw new PersonException("Nie odnaleziono pliku " + file_name);
        } catch(IOException e){
            throw new PersonException("Wystąpił błąd podczas odczytu danych z pliku.");
        }
    }


    //#######################################################################
    //#######################################################################
    //
    // Poniżej umieszczono cztery pomocnicze metody do tworzenia specjalnych
    // grup, które są wynikiem wykonania wybranych operacji na dwóch grupach
    // żródłowych. Możliwe są następujące operacje:
    //   SUMA  - grupa osób zawierająca wszystkie osoby z grupy pierwszej
    //           oraz wszystkie osoby z grupy drugiej;
    //   ILICZYN - grupa osób, które należą zarówno do grupy pierwszej jak i
    //             do grupy drugiej;
    //   RÓŻNICA - grupa osób, które należą do grupy pierwszej
    //             i nie ma ich w grupie drugiej
    //   RÓŻNICA SYMETRYCZNA - grupa osób, które należą do grupy pierwszej
    //             i nie ma ich w grupie drugiej oraz te osoby, które należą
    //             do grupy drugiej i nie ma w grupie pierwszej.
    //
    //   Nazwa grupy specjalnej jest tworzona według następującego wzorca"
    //          ( nazwa1 NNN nazwa2 )
    //   gdzie
    //         nazwa1 - nazwa pierwszej grupy osób,
    //         nazwa2 - nazwa drugiej grupy osób,
    //         NNN - symbol operacji wykonywanej na grupach osób:
    //                   "OR"  - dla operacji typu SUMA,
    //                   "AND" - dla operacji typu ILOCZYN,
    //                   "SUB" - dla operacji typu Różńica,
    //                   "XOR" - dla operacji typu RóżNICA SYMETRYCZNA.
    //
    //   Typ grupy specjalnej zależy od typu grup żródłowych i jest wybierany
    //   według następujących reguł:
    //  	 - Jeśli obie grupy żródłowe są tego samego rodzaju (lista lub zbiór)
    //  	   to grupa wynikpwa ma taki typ jak pierwsza grupa żródłowa.
    //       - Jeśli grupy żródłowe różnią się rodzajem (jedna jest listą, a druga zbioerm)
    //         to grupa wynikowa ma taki sam typ jak grupa żródłowa, która jest zbiorem.
    //
    //   Ilustruje to poniższa tabelka
    //       |=====================================================================|
    //       |   grupy żródłowe    |   grupa  |  uwagi dodatkowe                   |
    //       | pierwsza |  druga   | wynikowa |                                    |
    //       |==========|==========|==========|====================================|
    //       |  lista   |  lista   |   lista  | typ dziedziczony z grupy pierwszej |
    //       |  lista   |  zbiór   |   zbiór  | typ dziedziczony z grupy drugiej   |
    //       |  zbiór   |  lista   |   lista  | typ dziedziczony z grupy pierwszej |
    //       |  zbiór   |  zbiór   |   zbiór  | typ dziedziczony z grupy pierwszej |
    //       =======================================================================
    //
    //##################################################################################
    //##################################################################################

    public static GroupOfPeople createGroupUnion(GroupOfPeople g1,GroupOfPeople g2) throws PersonException {
        String name = "(" + g1.name + " OR " + g2.name +")";
        GroupType type;
        if (g2.collection instanceof Set && !(g1.collection instanceof Set) ){
            type = g2.type;
        } else {
            type = g1.type;
        }
        GroupOfPeople group = new GroupOfPeople(type, name);
        group.collection.addAll(g1.collection);
        group.collection.addAll(g2.collection);
        return group;
    }

    public static GroupOfPeople createGroupIntersection(GroupOfPeople g1,GroupOfPeople g2) throws PersonException {
        String name = "(" + g1.name + " AND " + g2.name +")";
        GroupType type;
        if (g2.collection instanceof Set && !(g1.collection instanceof Set) ){
            type = g2.type;
        } else {
            type = g1.type;
        }
        GroupOfPeople group = new GroupOfPeople(type, name);

        //##############################################################################
        //#                                                                            #
        //# Tu należy dopisać instrukcje które wyznaczą część wspólną dwóch            #
        //#      grup żródłowych                                                       #
        //#   Do grupy należy dodać te osoby, które należą zarówno do grupy pierwszej  #
        //#    jak i do grupy drugiej;                                                 #
        //#                                                                            #
        //##############################################################################

        return group;
    }

    public static GroupOfPeople createGroupDifference(GroupOfPeople g1,GroupOfPeople g2) throws PersonException {
        String name = "(" + g1.name + " SUB " + g2.name +")";
        GroupType type;
        if (g2.collection instanceof Set && !(g1.collection instanceof Set) ){
            type = g2.type;
        } else {
            type = g1.type;
        }
        GroupOfPeople group = new GroupOfPeople(type, name);

        //##############################################################################
        //#                                                                            #
        //# Tu należy dopisać instrukcje które wyznaczą różnicę dwóch                  #
        //#      grup żródłowych                                                       #
        //#   Do grupy należy dodać te osoby, które należą do grupy pierwszej          #
        //#     i nie należą do grupy drugiej;                                         #
        //#                                                                            #
        //##############################################################################

        return group;
    }


    public static GroupOfPeople createGroupSymmetricDiff(GroupOfPeople g1,GroupOfPeople g2) throws PersonException {
        String name = "(" + g1.name + " XOR " + g2.name +")";
        GroupType type;
        if (g2.collection instanceof Set && !(g1.collection instanceof Set) ){
            type = g2.type;
        } else {
            type = g1.type;
        }

        GroupOfPeople group = new GroupOfPeople(type, name);

        //##############################################################################
        //#                                                                            #
        //# Tu należy dopisać instrukcje które wyznaczą różnicę symetryczną dwóch      #
        //#      grup żródłowych                                                       #
        //#   Do grupy należy dodać te osoby, które należą tylko do grupy pierwszej    #
        //#     lub należą tylko do grupy drugiej;                                     #
        //#                                                                            #
        //##############################################################################

        return group;
    }

} // koniec klasy GroupOfPeople
