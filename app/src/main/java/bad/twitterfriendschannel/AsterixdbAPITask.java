package bad.twitterfriendschannel;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class AsterixdbAPITask extends Thread {

    /*String hostname;
    int port;
    FollowersType followers;
    private ObjectOutputStream outputStream = null;

    AsterixdbAPITask(String address, int port, FollowersType followers) {
        hostname = address;
        this.port = port;
        this.followers = followers;
    }

    Socket clientSocket = null;

    @Override
    public void start() {

        try {
            clientSocket = new Socket(hostname, port);
            System.out.println("Connected");

        } catch (UnknownHostException e) {
            System.err.println("Don't know about host: " + hostname);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: " + hostname);
        }

        if (clientSocket == null) {
            System.err.println( "Something is wrong. One variable is null." );
            return;
        }

        try {
            while ( true ) {
                outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                outputStream.writeObject(followers);

            }

        } catch (UnknownHostException e) {
            System.err.println("Trying to connect to unknown host: " + e);
        } catch (IOException e) {
            System.err.println("IOException:  " + e);
        }

    }*/

}