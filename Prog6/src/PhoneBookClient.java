/*
    Wojciech Wójcik 235621
    Aplikacja z sokecikami
 */

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PhoneBookClient extends JFrame implements ActionListener, Runnable {

    private static final long serialVersionUID = 1L;

    public static void main(String[] args)
    {
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


    static final int SERVER_PORT = 25000;
    private String name;
    private Socket socket;
    private String serverHost;
    private ObjectOutputStream outputStream;
    private ObjectInputStream inputStream;




    private JPanel panel = new JPanel();
    private JTextField textFieldInput = new JTextField(20);
    private JTextArea textAreaOutput = new JTextArea(20,25);
    private JLabel labelClientInput = new JLabel("Wprowadz zlecenie dla serwera: ");
    private JLabel labelSerwerOutput = new JLabel("Odpowiedz serwera: ");
    private JScrollPane scrollPane= new JScrollPane(textAreaOutput, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);


    public PhoneBookClient(String name, String host)
    {
        super(name);
        this.name = name;
        this.serverHost = host;

        setSize(200, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e)
            {
                try {
                    outputStream.close();
                    inputStream.close();
                    socket.close();
                } catch (Exception e2) {
                    System.out.println(e2);
                }
            }
            @Override
            public void windowClosed(WindowEvent event) {
                windowClosing(event);
            }
        });

        panel.add(labelClientInput);
        panel.add(textFieldInput);
        panel.add(labelSerwerOutput);
        panel.add(scrollPane);

        textAreaOutput.setLineWrap(true);
        textAreaOutput.setWrapStyleWord(true);
        textFieldInput.addActionListener(this);

        textAreaOutput.setEditable(false);

        setResizable(false);
        setVisible(true);
        setLocation(450, 250);
        setContentPane(panel);
        setSize(330, 450);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        new Thread(this).start(); //wątek odpowiedzi serwera
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        Object window = e.getSource();
        String message;

        if(window == textFieldInput)
        {
            try
            {
                message = textFieldInput.getText();

                outputStream.writeObject(message);
                printSendMessage(message);

                if(message.equals("BYE"))
                {
                    inputStream.close();
                    outputStream.close();
                    socket.close();
                    setVisible(false);
                    dispose();
                    return;
                }

            } catch (Exception exception)
            {
                System.out.println("Wyjątek klienta " + exception);
            }
            textFieldInput.setText("");
        }
        repaint();
    }

    @Override
    public void run()
    {
        if(serverHost.equals(""))
        {
            serverHost = "localhost";//domyślnie łączy z lokalnym hostem
        }

        try //próba nawiązania połączenia z serwerem
        {
            socket = new Socket(serverHost, SERVER_PORT);

            inputStream = new ObjectInputStream(socket.getInputStream());

            outputStream = new ObjectOutputStream(socket.getOutputStream());
            outputStream.writeObject(name);
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Połączenie z serwerem nie może być utworzone.");

            setVisible(false);
            dispose();

            return;
        }

        try
        {
            while(true)
            {
                String message = (String)inputStream.readObject();
                printReceivedMessage(message);

                if(message.equals("BYE"))
                {
                    inputStream.close();
                    outputStream.close();
                    socket.close();

                    setVisible(false);
                    dispose();

                    break;
                }
            }
        }
        catch (Exception e)
        {
            JOptionPane.showMessageDialog(null, "Polaczenie zostalo przerwane");

            setVisible(false);
            dispose();
        }
    }

    //wypisywanie odpowiedzi i komend w oknie
    synchronized public void printReceivedMessage(String receivedMessage)
    {
        String textShow = textAreaOutput.getText();
        textAreaOutput.setText(">" + receivedMessage + "\n" + textShow);
    }
    synchronized public void printSendMessage(String sendMessage)
    {
        String textShow = textAreaOutput.getText();
        textAreaOutput.setText("<" + sendMessage + "\n" + textShow);
    }
}

