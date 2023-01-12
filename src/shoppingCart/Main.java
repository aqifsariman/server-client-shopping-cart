package shoppingCart;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException, EOFException {
        // Taking in arguments for USER, HOST AND PORT
        String item, input, output, arguments = args[0];
        String[] argumentsArray = arguments.split("@|:");
        String user = argumentsArray[0];
        int port = Integer.parseInt(argumentsArray[2]), index;
        boolean ended = false;

        Client client = new Client();

        System.out.printf("Connected to shopping cart server at localhost on %s port %d.\n",
                user.substring(0, 1).toUpperCase() + user.substring(1, user.length()), port);
        System.out.printf("%s's cart is loaded.\n",
                user.substring(0, 1).toUpperCase() + user.substring(1, user.length()));
        try (Scanner scan = new Scanner(System.in)) {
            while (!ended) {
                Socket clientConn = new Socket("127.0.0.1", port);
                if (arguments.contains("@")) {
                    DataOutputStream dos = client.clientOutputStream(clientConn);
                    dos.writeUTF(arguments);
                    dos.flush();
                }
                System.out.print("> ");
                output = scan.next();
                output += scan.nextLine();
                DataOutputStream dos = client.clientOutputStream(clientConn);
                dos.writeUTF(output);
                dos.flush();
                DataInputStream dis = client.clientInputStream(clientConn);
                input = dis.readUTF();
                System.out.println(input);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}