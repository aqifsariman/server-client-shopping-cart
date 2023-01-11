package shoppingCart;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        boolean closeConn = false;
        int port = Integer.parseInt(args[0]);
        System.out.printf("Starting server on %d\n", port);

        while (!closeConn) {
            System.out.println("Waiting for connection on port 3000...");
            ServerSocket server = new ServerSocket(port);
            Socket conn = server.accept();
            System.out.println("Connection accepted");

            InputStream is = conn.getInputStream();
            DataInputStream dis = new DataInputStream(is);

            OutputStream os = conn.getOutputStream();
            DataOutputStream dos = new DataOutputStream(os);

            // Read request from client
            String request = dis.readUTF();
            System.out.printf("Received request: %s\n", request);

            conn.close();
        }
    }

    // // Get the input and output stream - bytes
    // // Get the input stream
    // InputStream is = sock.getInputStream();
    // DataInputStream dis = new DataInputStream(is);

    // // Get the output stream
    // OutputStream os = sock.getOutputStream();
    // DataOutputStream dos = new DataOutputStream(os);

    // // Read request from client
    // String request = dis.readUTF();

    // System.out.printf("Received request: %s\n", request);

    // // Perform some operation on the request
    // request = "From the server: " + request.toUpperCase();

    // // Write back the data to the client
    // dos.writeUTF(request);

    // // close the streams
    // is.close();
    // os.close();
}
