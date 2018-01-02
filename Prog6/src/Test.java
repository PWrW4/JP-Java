/*
    Wojciech Wójcik 235621
    Aplikacja z sokecikami

    Klasa która testuje server i klient
 */

public class Test {

    public static void main(String[] args)
    {
        PhoneBookServer serwer = new PhoneBookServer();

        String name = "test1";
        String host = "localhost";


        new PhoneBookClient(name, host);
    }
}
