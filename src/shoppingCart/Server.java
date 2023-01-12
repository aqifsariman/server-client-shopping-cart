package shoppingCart;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) throws IOException {
        boolean madeChanges = false, listLoaded = false, disconnected = false;
        String input, output = "";
        StringBuilder userItems = new StringBuilder();
        int port = Integer.parseInt(args[0]);
        Client client = new Client();

        System.out.printf("Starting shopping cart server on %d.\n", port);
        ServerSocket server = new ServerSocket(port);

        while (true) {
            Socket conn = server.accept();
            System.err.println("Connection received.");
            DataInputStream dis = client.clientInputStream(conn);
            input = dis.readUTF();

            String[] argumentsArray = input.split("@|:");
            String clientUser = argumentsArray[0];
            FileHandling handler = new FileHandling(Constants.SHOPPINGCART);
            handler.load(clientUser);
            DataOutputStream dos = client.clientOutputStream(conn);

            input = dis.readUTF();
            if (input.contains(Constants.LIST)) {
                if (disconnected) {
                    listLoaded = false;
                    madeChanges = false;
                }
                if (!listLoaded) {
                    String inputString = handler.list(madeChanges);
                    if (inputString.equalsIgnoreCase("")) {
                        output = "Cart is empty.";
                        dos.writeUTF(output);
                        dos.flush();
                    }
                    String[] lineArray = inputString.trim().split(" ");
                    for (int i = 0; i < lineArray.length; i++) {
                        userItems.append(String.format("%s ", lineArray[i]));
                    }
                    String[] userItemsStringsArray = userItems.toString().trim().split(" ");
                    for (int j = 0; j < userItemsStringsArray.length; j++) {
                        output += String.format("%d. %s\n", j + 1, userItemsStringsArray[j]);
                    }
                    listLoaded = true;
                    dos.writeUTF(output);
                    dos.flush();
                    output = "";
                }
                String[] userItemsStringsArray = userItems.toString().trim().split(" ");
                for (int j = 0; j < userItemsStringsArray.length; j++) {
                    output += String.format("%d. %s\n", j + 1, userItemsStringsArray[j]);
                }
                dos.writeUTF(output);
                dos.flush();
                output = "";

            }
            if (input.contains(Constants.EXIT)) {
                System.out.println("Disconnecting from client.");
                userItems.setLength(0);
                disconnected = true;
                listLoaded = true;
            }

            if (input.contains(Constants.DELETE)) {
                int index = Integer.parseInt(input.split(" ")[1]);
                output = handler.delete(index - 1, userItems);
                int i = userItems.indexOf(output.split(" ")[1]);
                if (i != -1) {
                    userItems.delete(i, i + output.split(" ")[1].length());
                }
                madeChanges = true;
                dos.writeUTF(output);
                dos.flush();
                output = "";

            }
            if (input.contains(Constants.ADD)) {
                String[] items = input.split(" ");
                output = handler.add(items, userItems);
                madeChanges = true;
                dos.writeUTF(output);
                dos.flush();
                output = "";
            }
            if (input.contains(Constants.SAVE)) {
                handler.save(userItems);
                dos.writeUTF("Cart saved successfully.");
                dos.flush();
            }
            conn.close();
            output = "";
        }
    }
}
