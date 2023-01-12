package shoppingCart;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private Socket clntConn;

    public Client() {
    }

    public Client(String host, String name, int port) throws UnknownHostException, IOException {
        if (host.equalsIgnoreCase("localhost")) {
            this.clntConn = new Socket("127.0.0.1", port);
            System.out.printf("Connected to server at %s on %s port %d.", host, name, port);

        } else {
            this.clntConn = new Socket("127.0.0.1", port);
            System.out.printf("Connected to server at %s on %s port %d.", host, name, port);
        }
    }

    public void closeClientConnection() throws IOException {
        this.clntConn.close();
    }

    public DataOutputStream clientOutputStream(Socket clientConn) throws IOException {
        OutputStream os = clientConn.getOutputStream();
        BufferedOutputStream bos = new BufferedOutputStream(os);
        return new DataOutputStream(bos);
    }

    public DataInputStream clientInputStream(Socket clientConn) throws IOException {
        InputStream is = clientConn.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(is);
        return new DataInputStream(bis);
    }

}
