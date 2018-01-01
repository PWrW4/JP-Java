import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientThread implements Runnable
{
    private Socket socket;
    private String name;
    private PhoneBookServer phoneBookServer;

    private ObjectOutputStream outputStream = null;

    ClientThread(String prototypeDisplayValue)
    {
        name = prototypeDisplayValue;
    }

    public ClientThread(PhoneBookServer server, Socket socket)
    {
        phoneBookServer = server;
        this.socket = socket;
        new Thread(this).start();//wątek do obsługi dodatkowego wątka komunikacji sieciowej
    }

    @Override
    public void run() {

        String message;

        try(ObjectOutputStream output = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream input = new ObjectInputStream(socket.getInputStream()); )
        {
            outputStream = output;
            name = (String)input.readObject();
            phoneBookServer.addClient(this);

            while(true)
            {
                message = (String)input.readObject();
                phoneBookServer.printReceivedMessage(this, message);

                if(message.equals("BYE")) //tutaj dostaje wiadomość od serwera
                {
                    phoneBookServer.removeClient(this);
                    break;
                }
                if(message.equals("serwer"))/// tutaj wykonuje polecenia na książce telefonicznej
                {
                    System.out.println("tutaj dostaje wiadomość od serwera!");
                    //this.p
                }
            }
            socket.close();
            socket = null;
        }
        catch (Exception e)
        {
            phoneBookServer.removeClient(this);
        }
    }

    public String getName()
    {
        return name;
    }
    public String tooString()
    {
        return name;
    }

    public void sendMessage(String message)
    {
        try
        {
            outputStream.writeObject(message);

            if(message.equals("BYE"))//albo tutaj dopisać
            {
                phoneBookServer.removeClient(this);
                socket.close();
                socket = null;
            }
			/*else if(message.equals("serwer"))
			{
				//tutaj chyba dostaje wiadomości od serwera bo tam wysyła do serwera
			}*/
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}