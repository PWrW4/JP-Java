
/*
 *   Plik: GroupOfCars.java
 *
 *   Opis: Zarządzanie kolekcjami klasy Car.java do programu okienkowego.
 *
 *   Autor: Wojciech Wójcik
 *   Data: 07.11.2017 r.
 */

import java.io.*;
import java.util.*;

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


    public static GroupType find(String type_name) {
        for (GroupType type : values()) {
            if (type.typeName.equals(type_name)) {
                return type;
            }
        }
        return null;
    }


    public Collection<Car> createCollection() throws CarException {
        switch (this) {
            case VECTOR:
                return new Vector<Car>();
            case ARRAY_LIST:
                return new ArrayList<Car>();
            case HASH_SET:
                return new HashSet<Car>();
            case LINKED_LIST:
                return new LinkedList<Car>();
            case TREE_SET:
                return new TreeSet<Car>();
            default:
                throw new CarException("Podany typ kolekcji nie został zaimplementowany.");
        }
    }

}

public class GroupOfCars implements Iterable<Car>, Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private GroupType type;
    private Collection<Car> collection;


    public GroupOfCars(GroupType type, String name) throws CarException {
        setName(name);
        if (type == null) {
            throw new CarException("Nieprawidłowy typ kolekcji.");
        }
        this.type = type;
        collection = this.type.createCollection();
    }


    public GroupOfCars(String type_name, String name) throws CarException {
        setName(name);
        GroupType type = GroupType.find(type_name);
        if (type == null) {
            throw new CarException("Nieprawidłowy typ kolekcji.");
        }
        this.type = type;
        collection = this.type.createCollection();
    }


    public String getName() {
        return name;
    }


    public void setName(String name) throws CarException {
        if ((name == null) || name.equals(""))
            throw new CarException("Nazwa grupy musi być określona.");
        this.name = name;
    }


    public GroupType getType() {
        return type;
    }


    public void setType(GroupType type) throws CarException {
        if (type == null) {
            throw new CarException("Typ kolekcji musi być określny.");
        }
        if (this.type == type)
            return;
        // Gdy następuje zmiana typu kolekcji to osoby zapamiętane
        // w dotychczasowej kolekcji muszą zostać przepisane do nowej
        // kolekcji, która jest implementowana, przy pomocy
        // klasy właściwej dla nowego typu kolekcji.
        Collection<Car> oldCollection = collection;
        collection = type.createCollection();
        this.type = type;
        for (Car person : oldCollection)
            collection.add(person);
    }


    public void setType(String type_name) throws CarException {
        for (GroupType type : GroupType.values()) {
            if (type.toString().equals(type_name)) {
                setType(type);
                return;
            }
        }
        throw new CarException("Nie ma takiego typu kolekcji.");
    }


    // Zamiast gettera getCollection zostały zaimplementowane
    // niezbędne metody delegowane z interfejsu Collection,
    // które umożliwiają wykonanie wszystkich operacji na
    // kolekcji osób.

    public boolean add(Car e) {
        return collection.add(e);
    }

    public Iterator<Car> iterator() {
        return collection.iterator();
    }

    public int size() {
        return collection.size();
    }

    public void sortProdYear() throws CarException {
        if (type == GroupType.HASH_SET || type == GroupType.TREE_SET) {
            throw new CarException("Kolekcje typu SET nie mogą być sortowane.");
        }

        Collections.sort((List<Car>) collection, new Comparator<Car>() {

            @Override
            public int compare(Car o1, Car o2) {
                if (o1.getCarProdYear() < o2.getCarProdYear())
                    return -1;
                if (o1.getCarProdYear() > o2.getCarProdYear())
                    return 1;
                return 0;
            }

        });
    }

    public void sortEngineSize() throws CarException {
        if (type == GroupType.HASH_SET || type == GroupType.TREE_SET) {
            throw new CarException("Kolekcje typu SET nie mogą być sortowane.");
        }

        Collections.sort((List<Car>) collection, new Comparator<Car>() {

            @Override
            public int compare(Car o1, Car o2) {
                if (o1.getCarEngineSize() < o2.getCarEngineSize())
                    return -1;
                if (o1.getCarEngineSize() > o2.getCarEngineSize())
                    return 1;
                return 0;
            }

        });
    }

    public void sortBrand() throws CarException {
        if (type == GroupType.HASH_SET || type == GroupType.TREE_SET) {
            throw new CarException("Kolekcje typu SET nie mogą być sortowane.");
        }

        Collections.sort((List<Car>) collection, new Comparator<Car>() {

            @Override
            public int compare(Car o1, Car o2) {
                return o1.getCarBrand().toString().compareTo(o2.getCarBrand().toString());
            }

        });
    }

    public void sortCollor() throws CarException {
        if (type == GroupType.HASH_SET || type == GroupType.TREE_SET) {
            throw new CarException("Kolekcje typu SET nie mogą być sortowane.");
        }

        Collections.sort((List<Car>) collection, new Comparator<Car>() {

            @Override
            public int compare(Car o1, Car o2) {
                return o1.getCarCColor().toString().compareTo(o2.getCarCColor().toString());
            }

        });
    }

    public void sortOwnerName() throws CarException {
        if (type == GroupType.HASH_SET || type == GroupType.TREE_SET) {
            throw new CarException("Kolekcje typu SET nie mogą być sortowane.");
        }

        Collections.sort((List<Car>) collection, new Comparator<Car>() {

            @Override
            public int compare(Car o1, Car o2) {
                return o1.getCarOwnerName().compareTo(o2.getCarOwnerName());
            }

        });
    }


    @Override
    public String toString() {
        return name + "  [" + type + "]";
    }


    public static void printToFile(PrintWriter writer, GroupOfCars group) {
        writer.println(group.getName());
        writer.println(group.getType());
        for (Car person : group.collection)
            Car.printToFile(writer, person);
    }


    public static void printToFile(String file_name, GroupOfCars group) throws CarException {
        try (PrintWriter writer = new PrintWriter(file_name)) {
            printToFile(writer, group);
        } catch (FileNotFoundException e) {
            throw new CarException("Nie odnaleziono pliku " + file_name);
        }
    }


    public static GroupOfCars readFromFile(BufferedReader reader) throws CarException {
        try {
            String group_name = reader.readLine();
            String type_name = reader.readLine();
            GroupOfCars groupOfPeople = new GroupOfCars(type_name, group_name);

            Car person;
            while ((person = Car.readFromFile(reader)) != null)
                groupOfPeople.collection.add(person);
            return groupOfPeople;
        } catch (IOException e) {
            throw new CarException("Wystąpił błąd podczas odczytu danych z pliku.");
        }
    }


    public static GroupOfCars readFromFile(String file_name) throws CarException {
        try (BufferedReader reader = new BufferedReader(new FileReader(new File(file_name)))) {
            return GroupOfCars.readFromFile(reader);
        } catch (FileNotFoundException e) {
            throw new CarException("Nie odnaleziono pliku " + file_name);
        } catch (IOException e) {
            throw new CarException("Wystąpił błąd podczas odczytu danych z pliku.");
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

    public static GroupOfCars createGroupUnion(GroupOfCars g1, GroupOfCars g2) throws CarException {
        String name = "(" + g1.name + " OR " + g2.name + ")";
        GroupType type;
        if (g2.collection instanceof Set && !(g1.collection instanceof Set)) {
            type = g2.type;
        } else {
            type = g1.type;
        }
        GroupOfCars group = new GroupOfCars(type, name);
        group.collection.addAll(g1.collection);
        group.collection.addAll(g2.collection);
        return group;
    }

    public static GroupOfCars createGroupIntersection(GroupOfCars g1, GroupOfCars g2) throws CarException {
        String name = "(" + g1.name + " AND " + g2.name + ")";
        GroupType type;
        if (g2.collection instanceof Set && !(g1.collection instanceof Set)) {
            type = g2.type;
        } else {
            type = g1.type;
        }
        GroupOfCars group = new GroupOfCars(type, name);

        //##############################################################################
        //#                                                                            #
        //# Tu należy dopisać instrukcje które wyznaczą część wspólną dwóch            #
        //#      grup żródłowych                                                       #
        //#   Do grupy należy dodać te osoby, które należą zarówno do grupy pierwszej  #
        //#    jak i do grupy drugiej;                                                 #
        //#                                                                            #
        //##############################################################################

        for (Car c1 : g1) {
            for (Car c2 : g2) {
                if (c1.equals(c2)) {
                    group.add(c1);
                    break;
                }
            }
        }

        return group;
    }

    public static GroupOfCars createGroupDifference(GroupOfCars g1, GroupOfCars g2) throws CarException {
        String name = "(" + g1.name + " SUB " + g2.name + ")";
        GroupType type;
        if (g2.collection instanceof Set && !(g1.collection instanceof Set)) {
            type = g2.type;
        } else {
            type = g1.type;
        }
        GroupOfCars group = new GroupOfCars(type, name);

        //##############################################################################
        //#                                                                            #
        //# Tu należy dopisać instrukcje które wyznaczą różnicę dwóch                  #
        //#      grup żródłowych                                                       #
        //#   Do grupy należy dodać te osoby, które należą do grupy pierwszej          #
        //#     i nie należą do grupy drugiej;                                         #
        //#                                                                            #
        //##############################################################################
        boolean add = true;

        for (Car c1 : g1) {
            for (Car c2 : g2) {
                if (c1.equals(c2)) {
                    add = false;
                    break;
                }
            }
            if (add)
                group.add(c1);
            add = true;
        }

        return group;
    }


    public static GroupOfCars createGroupSymmetricDiff(GroupOfCars g1, GroupOfCars g2) throws CarException {
        String name = "(" + g1.name + " XOR " + g2.name + ")";
        GroupType type;
        if (g2.collection instanceof Set && !(g1.collection instanceof Set)) {
            type = g2.type;
        } else {
            type = g1.type;
        }

        GroupOfCars group = new GroupOfCars(type, name);

        //##############################################################################
        //#                                                                            #
        //# Tu należy dopisać instrukcje które wyznaczą różnicę symetryczną dwóch      #
        //#      grup żródłowych                                                       #
        //#   Do grupy należy dodać te osoby, które należą tylko do grupy pierwszej    #
        //#     lub należą tylko do grupy drugiej;                                     #
        //#                                                                            #
        //##############################################################################

        boolean add = true;

        for (Car c1 : g1) {
            for (Car c2 : g2) {
                if (c1.equals(c2)) {
                    add = false;
                    break;
                }
            }
            if (add)
                group.add(c1);
            add = true;
        }


        for (Car c2 : g2) {
            for (Car c1 : g1) {
                if (c1.equals(c1)) {
                    add = false;
                    break;
                }
            }
            if (add)
                group.add(c2);
            add = true;
        }

        return group;
    }

}
