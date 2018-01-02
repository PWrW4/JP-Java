import javax.swing.*;

public class Test {

    public static void main(String[] args)
    {
        PhoneBookServer serwer = new PhoneBookServer();

        String name = "test1";
        String host = "localhost";


        new PhoneBookClient(name, host);
    }
}
