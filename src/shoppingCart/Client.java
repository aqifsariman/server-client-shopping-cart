package shoppingCart;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    private Socket clntConn;

    public Client(String host, String name, int port) throws UnknownHostException, IOException {
        if (host.equalsIgnoreCase("localhost")) {
            this.clntConn = new Socket("127.0.0.1", port);
            System.out.printf("Connected to server at %s on %s port %d.\n", host, name, port);

        } else {
            this.clntConn = new Socket("127.0.0.1", port);
            System.out.printf("Connected to server at %s on %s port %d.\n", host, name, port);
        }
    }

    public void closeClientConnection() throws IOException {
        this.clntConn.close();
    }

    public DataOutputStream clientOutputStream() throws IOException {
        OutputStream os = this.clntConn.getOutputStream();
        return new DataOutputStream(os);
    }

    public DataInputStream clientInputStream() throws IOException {
        InputStream is = this.clntConn.getInputStream();
        return new DataInputStream(is);
    }

    // int count = 4;
    // oos.writeInt(count);
    // oos.writeUTF(line);
    // oos.flush();

    // for(int i = 0; i < count; i++){
    // String input = ois.readUTF();
    // System.out.printf(">>> Server %s\n", input);

    // }

    // // Get input from user
    // Console cons = System.console();
    // String input = cons.readLine("Say something to the server ");

    // // Write to server
    // dos.writeUTF(input);
    // dos.flush();

    // // Wait for response from server
    // String response = dis.readUTF();
    // System.out.printf(">> %s\n", response);

    // // close the strams
    // is.close();
    // os.close();

    // // close the socket
    // sock.close();
}
