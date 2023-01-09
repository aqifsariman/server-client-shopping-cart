package shoppingCart;

import java.io.FileWriter;
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
    private FileWriter fileWriter;
    private boolean ranOnce = false;

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
            if (Arrays.asList(line.split(" ")).contains(name)) {
                this.nameChosen = name;
                System.out.printf("\n%s's shopping cart loaded.\n",
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
        this.readerWriter = new ReaderWriter(this.path);
        String line;

        if (!ranOnce) {
            // READS ALL THE LINE IN THE FILE
            while (null != (line = readerWriter.bufferedFile())) {
                String[] lineArray = line.trim().split(" ");
                if (!lineArray[0].equalsIgnoreCase(nameChosen)) {
                    continue;
                } else {
                    emptyString = "filled";
                    for (int i = 1; i < lineArray.length; i++) {
                        itemsArray.add(lineArray[i]);
                    }
                    this.carts.put(nameChosen, itemsArray);
                }
            }
            if (emptyString.equalsIgnoreCase("")) {
                System.out.println("Your cart is empty.");
            }
        } else {
            if (itemsArray.size() != 0) {
                System.out.println("Running list again!");
                emptyString = "filled";
                this.carts.put(nameChosen, itemsArray);
            } else {
                emptyString = "";
                if (emptyString.equalsIgnoreCase("")) {
                    System.out.println("Your cart is empty.");
                }
            }
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
        itemsArray.clear();
    }

    public void add(String items) throws IOException {
        this.readerWriter = new ReaderWriter(this.path);
        String line;

        while (null != (line = readerWriter.bufferedFile())) {
            String itemsAdded = "";
            if (line.contains(nameChosen)) {
                String[] itemArr = items.split(" ");
                this.fileWriter = new FileWriter(Constants.SHOPPINGCART, true);
                for (int i = 0; i < itemArr.length; i++) {
                    if (line.contains(itemArr[i])) {
                        continue;
                    }
                    fileWriter.append(String.format(" %s", itemArr[i]));
                    if (i != itemArr.length - 1) {
                        itemsAdded += itemArr[i] + ", ";
                    } else {
                        itemsAdded += itemArr[i];
                    }
                }
                if (itemsAdded.equalsIgnoreCase("")) {
                    System.out.println("Nothing new added to cart.");
                } else {
                    System.out.printf("%s added to the cart.\n", itemsAdded);
                }
            }

        }

        readerWriter.closeBuffer();
        readerWriter.closeReader();

        // CLOSE WRITER
        fileWriter.flush();
        fileWriter.close();
    }

}
// public void delete(int index) throws IOException {
// System.out.println(carts);
// this.readerWriter = new ReaderWriter(this.path);
// String line;
// while (null != (line = readerWriter.bufferedFile())) {
// String[] lineArray = line.trim().split(" ");
// if (!lineArray[0].equalsIgnoreCase(nameChosen)) {
// continue;
// } else {
// if (index > lineArray.length || index < 0) {
// System.out.println("Incorrect item index.\n");
// throw new IndexOutOfBoundsException();
// } else {
// List<String> proxyArray = new LinkedList<>();
// for (int i = 1; i < lineArray.length; i++) {
// if (i == index) {
// continue;
// }
// proxyArray.add(lineArray[i]);
// }

// for (int j = 1; j < proxyArray.size(); j++) {
// this.itemsArray.add(proxyArray.get(j));
// }
// String itemString = String.join(" ", proxyArray).trim();
// System.out.println("Item String: " + itemString);
// line = line.replaceAll(line, String.format("%s, %s", nameChosen.trim(),
// itemString));
// System.out.println("Line here: " + line);
// ranOnce = true;
// System.out.printf("%s removed from the cart.\n",
// lineArray[index].substring(0, 1).toUpperCase()
// + lineArray[index].substring(1));
// }
// }
// }

// readerWriter.closeBuffer();
// readerWriter.closeReader();
// }