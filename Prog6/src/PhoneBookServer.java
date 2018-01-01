import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class PhoneBookServer extends JFrame implements ActionListener, Runnable {

    private static final long serialVersionUID = 1L;

    static final int SERVER_PORT = 25000;

    public static void main(String[] args)
    {
        PhoneBookServer serwer = new PhoneBookServer();
        //serwer.printSentMessage("lol");
    }

    private ArrayList<ClientThread> listaOdbiorcow = new ArrayList<ClientThread>();//lista przechowująca aktualnych odbiorców
    private String odbiorca = "unknown";//pole pokazująca odbiorcę, który wszedł w interakcję
    private String messageSend = " - - - - - - - - ";//pole pokazujące ostatnią wysłaną wiadomość
    private boolean czyZamknacNasluchiwanie = false; //czy nadal zezwalać nowym klientom na porozumiewanie się z serwerem
    private PhoneBook phoneBook = new PhoneBook();//to ma w sobie kolekcję

    private JPanel panel = new JPanel();

    private JLabel labelOdbiorca = new JLabel("Odbiorca: ");
    private JLabel labelWyslijKomunikat = new JLabel("Wysyłany komunikat: ");
    private JLabel labelCoSieDzieje = new JLabel("Wejścia i wyjścia serwera: ");

    private JTextField textFieldWysylanyKomunikat = new JTextField(20); // wyświetla to co się wysyła
    private JTextField textFieldOdbiorca = new JTextField(20);

    private JTextArea textAreaCoSieDzieje = new JTextArea(20, 30);

    private JScrollPane scroll = new JScrollPane(textAreaCoSieDzieje, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

    public PhoneBookServer()
    {
        super("PhoneBookServer");

        textFieldOdbiorca.setText(odbiorca);
        textFieldOdbiorca.setEditable(false);

        textFieldWysylanyKomunikat.setText(messageSend);
        textFieldWysylanyKomunikat.setEditable(false);

        panel.add(labelOdbiorca);
        panel.add(textFieldOdbiorca);
        panel.add(labelWyslijKomunikat);
        panel.add(textFieldWysylanyKomunikat);
        panel.add(labelCoSieDzieje);
        panel.add(scroll);

        textAreaCoSieDzieje.setLineWrap(true);
        textAreaCoSieDzieje.setWrapStyleWord(true);
        textAreaCoSieDzieje.setEditable(false);

        setResizable(false);
        setContentPane(panel);
        setVisible(true);
        setSize(380, 460);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        new Thread(this).start();//wyjątek czekający na klientów
    }

    @Override
    public void run() {
        boolean socketCreated = false;

        try (ServerSocket serwer = new ServerSocket(SERVER_PORT))
        {
            String host = InetAddress.getLocalHost().getHostName();//nowe połączenie sieciowe
            System.out.println("Serwer został uruchomiony na hoście " + host);
            socketCreated = true;

            while(true)//czeka na połączenia przychodzące
            {
                Socket socket = serwer.accept();

                if(socket != null && !czyZamknacNasluchiwanie)
                {
                    new ClientThread(this, socket);//nowy wątek dla nowego klienta
                }
            }

        }
        catch (Exception e)
        {
            System.out.println(e);

            if(!socketCreated)
            {
                JOptionPane.showMessageDialog(null, "Gniazdko dla serwera nie może zostać utworzone.");
                System.exit(0);
            }
            else
            {
                JOptionPane.showMessageDialog(null, "BŁĄD SERWERA: Nie można połączyć się z klientem.");
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object window = e.getSource();
        String message;
    }

    synchronized public void printReceivedMessage(ClientThread client,String message)//wiadomość otrzymana
    {
        String text = textAreaCoSieDzieje.getText();//to wypisuje jako otrzymaną wiadomość od klienta
        String[] splitedMessage = message.split(" ");

        try
        {
            //tutaj serwer analizuje wejście i wykonuje określone zadania
            //zostało przekazanie do klienta
            if(splitedMessage[0].equals("LOAD"))
            {
                textAreaCoSieDzieje.setText(client.getName() + " > " + message + "\n" + text);// + text);
                phoneBook.LOAD(splitedMessage[1]);
                client.sendMessage("OK LOAD");
                //ładuje
            }
            else if(splitedMessage[0].equals("SAVE"))
            {
                textAreaCoSieDzieje.setText(client.getName() + " > " + message + "\n" + text);// + text);
                phoneBook.SAVE(splitedMessage[1]);
                client.sendMessage("OK SAVE");
            }
            else if (splitedMessage[0].equals("GET"))
            {
                textAreaCoSieDzieje.setText(client.getName() + " > " + message + "\n" + text);// + text);
                client.sendMessage("OK GET " + phoneBook.GET(splitedMessage[1]));
            }
            else if(splitedMessage[0].equals("PUT"))
            {
                textAreaCoSieDzieje.setText(client.getName() + " > " + message + "\n" + text);// + text);
                phoneBook.PUT(splitedMessage[1], splitedMessage[2]);
                client.sendMessage("OK PUT");
            }
            else if (splitedMessage[0].equals("REPLACE"))
            {
                textAreaCoSieDzieje.setText(client.getName() + " > " + message + "\n" + text);// + text);
                phoneBook.REPLACE(splitedMessage[1], splitedMessage[2]);
                client.sendMessage("OK REPLACE");

            }
            else if (splitedMessage[0].equals("DELETE"))
            {
                textAreaCoSieDzieje.setText(client.getName() + " > " + message + "\n" + text);// + text);
                phoneBook.DELETE(splitedMessage[1]);
                client.sendMessage("OK DELETE");
            }
            else if(message.equals("BYE"))
            {
                textAreaCoSieDzieje.setText(client.getName() + " > " + message + "\n" + text);// + text);
                client.sendMessage("OK BYE");
            }
            else if(message.equals("CLOSE"))
            {
                textAreaCoSieDzieje.setText(client.getName() + " > " + message + "\n" + text);// + text);
                //serwer nie chce więcej klientów
                this.czyZamknacNasluchiwanie = true;
                client.sendMessage("OK CLOSE");
            }
            else if(message.equals("LIST"))
            {
                textAreaCoSieDzieje.setText(client.getName() + " > " + message + "\n" + text);// + text);
                //przesyła listy imion które są zapamiętane w kolekcji
                client.sendMessage("OK " + phoneBook.LIST());
            }
            else
            {
                textAreaCoSieDzieje.setText(client.getName() + " > ERROR podczas wprowadzania polecenia wystąpił błąd, najprawdopodobnie złe polecenie.\n" + text);
            }
            repaint();
        }
        catch (Exception e)
        {

        }

    }

    synchronized public void printSentMessage(ClientThread client,String message)//wiadomość wysłana do klienta wprowadzana przez użytkownika
    //to chyba można usunąć, zakomentować i sprawdzić
    {
        String text = textAreaCoSieDzieje.getText();
        messageSend = message;
        //textAreaCoSieDzieje.setText(client.getName() + " < " + message + "\n" + text);
        try
        {
            if(message.equals("LOAD nazwa"))
            {
                textAreaCoSieDzieje.setText(client.getName() + "s < " + message + "\n" + text);// + text);

                client.sendMessage("działa load");
            }

        }
        catch (Exception e)
        {
            textAreaCoSieDzieje.setText(client.getName() + "s < ERROR " + e + "\n" + text);
        }
    }

    synchronized void addClient(ClientThread client)
    {
        listaOdbiorcow.add(client);
    }
    synchronized void removeClient(ClientThread client)
    {
        listaOdbiorcow.remove(client);
    }
}
