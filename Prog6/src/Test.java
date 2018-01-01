import javax.swing.*;

public class Test {

    public static void main(String[] args)
    {
        PhoneBookServer serwer = new PhoneBookServer();


        //pobieranie danych do połączenia z serwerem
        String name;
        String host;

        host = JOptionPane.showInputDialog("Podaj host serwera: ");
        name = JOptionPane.showInputDialog("Podaj nazwa użytkownika: ");

        if(name != null && !name.equals(""))//czy wszystko zostało wprowadzone
        {
            new PhoneBookClient(name, host);
        }
    }
}
