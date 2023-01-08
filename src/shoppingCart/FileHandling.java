package shoppingCart;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class FileHandling {
    private String path;
    private int cartsCount;
    private List<String> itemsArray = new LinkedList<>();
    private HashMap<String, List<String>> carts = new HashMap<>();
    private String nameChosen;
    private ReaderWriter readerWriter;

    public FileHandling(String path) {
        this.path = path;
    }

    public void run() throws IOException {

        this.readerWriter = new ReaderWriter(this.path);

        // READS ALL THE LINE IN THE FILE
        while (null != (readerWriter.bufferedFile())) {
            cartsCount++;
        }
        System.out.printf("There are %d carts in the shopping directory.\n", cartsCount);
        // CLOSE THE READER
        readerWriter.closeBuffer();
        readerWriter.closeReader();
    }

    public void exit() {
        System.out.println("Thank you for using Shopping Cart On-The-Go!\n");
        System.exit(1);
    }

    public void load(String name) throws IOException {
        boolean nameExist = false;
        String line;
        this.readerWriter = new ReaderWriter(this.path);

        // READS ALL THE LINE IN THE FILE
        while (null != (line = readerWriter.bufferedFile())) {
            if (Arrays.asList(line.split(",")).contains(name)) {
                this.nameChosen = name;
                System.out.printf("%s's shopping cart loaded.\n",
                        name.substring(0, 1).toUpperCase() + name.substring(1));
                nameExist = true;
                break;
            } else {
                nameExist = false;
            }
        }
        if (!nameExist) {
            System.out.printf("User %s not found.\n", name.substring(0, 1).toUpperCase() + name.substring(1));
        }
        readerWriter.closeBuffer();
        readerWriter.closeReader();
    }

    public void list() throws IOException {
        String emptyString = "";
        String line;
        this.readerWriter = new ReaderWriter(this.path);

        // READS ALL THE LINE IN THE FILE
        while (null != (line = readerWriter.bufferedFile())) {
            String[] lineArray = line.trim().split(",");
            if (!lineArray[0].equalsIgnoreCase(nameChosen)) {
                continue;
            } else {
                emptyString = "filled";
                for (int i = 1; i < lineArray.length; i++) {
                    itemsArray.add(lineArray[i]);
                }
                carts.put(nameChosen, itemsArray);
            }
        }

        if (emptyString.equalsIgnoreCase("")) {
            System.out.println("Your cart is empty.");
        }
        for (Map.Entry<String, List<String>> entry : carts.entrySet()) {
            int index = 1;
            for (String item : entry.getValue()) {
                System.out.printf("%d. %s\n", index, item);
                index++;
            }
        }
        readerWriter.closeBuffer();
        readerWriter.closeReader();
    }
}