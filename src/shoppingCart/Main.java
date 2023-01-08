package shoppingCart;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        boolean ended = false;
        String name;
        FileHandling handler = new FileHandling(Constants.SHOPPINGCART);
        handler.run();
        try (Scanner scan = new Scanner(System.in)) {
            while (!ended) {
                System.out.print("> ");
                String command = scan.next();

                if (command.equalsIgnoreCase(Constants.LOAD)) {
                    name = scan.nextLine().trim();
                    handler.load(name);
                }
                if (command.equalsIgnoreCase(Constants.EXIT)) {
                    handler.exit();
                }
                if (command.equalsIgnoreCase(Constants.LIST)) {
                    handler.list();
                }

            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }
}
