package shoppingCart;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        // Taking in arguments for USER, HOST AND PORT
        String arguments = args[0];
        String[] argumentsArray = arguments.split("@|:");
        String user = argumentsArray[0];
        String host = argumentsArray[1];
        int port = Integer.parseInt(argumentsArray[2]);

        // Keep progam runnning till exit command given
        boolean ended = false;
        // String name;
        String item;
        int index;

        Client client = new Client(host, user, port);
        ReaderWriter readerWriter = new ReaderWriter(Constants.SHOPPINGCART);
        

        FileHandling handler = new FileHandling(Constants.SHOPPINGCART, user, host, port);
        handler.run();
        handler.load(user, host, port);
        try (Scanner scan = new Scanner(System.in)) {
            while (!ended) {
                System.out.print("> ");
                String command = scan.next();
                if (command.equalsIgnoreCase(Constants.LIST)) {
                    handler.list();
                }
                if (command.equalsIgnoreCase(Constants.EXIT)) {
                    handler.exit();
                }

                if (command.equalsIgnoreCase(Constants.DELETE)) {
                    index = scan.nextInt();
                    handler.delete(index - 1);
                }
                if (command.equalsIgnoreCase(Constants.ADD)) {
                    item = scan.nextLine();
                    handler.add(item);
                }
                if (command.equalsIgnoreCase(Constants.SAVE)) {
                    handler.save();
                }

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
