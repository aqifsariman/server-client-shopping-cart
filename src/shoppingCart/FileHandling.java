package shoppingCart;

import java.io.FileWriter;
import java.io.IOException;

public class FileHandling {
    private String path;
    private int cartsCount;
    private String nameChosen;
    private ReaderWriter readerWriter;
    private FileWriter fileWriter;

    public FileHandling(String path) {
        this.path = path;
    }

    public void run() throws IOException {
        this.readerWriter = new ReaderWriter(this.path);
        cartsCount = readerWriter.getNumberOfCarts(this.path);
        System.out.printf("There are %d carts in the shopping directory.\n", cartsCount);
    }

    public void exit() {
        System.out.println("Thank you for using Shopping Cart On-The-Go!\n");
        System.exit(1);
    }

    public void load(String name) throws IOException {
        this.readerWriter = new ReaderWriter(this.path);
        this.nameChosen = name;
        if (readerWriter.checkExistence(name)) {
            System.out.printf("%s's shopping cart loaded.\n",
                    (name.substring(0, 1).toUpperCase() + name.substring(1)));
        } else {
            readerWriter.createFile(name + ".cart");
            System.out.printf("%s's shopping cart loaded.\n",
                    (name.substring(0, 1).toUpperCase() + name.substring(1)));
        }

    }

    public void list() throws IOException {
        this.readerWriter = new ReaderWriter(this.path);
        readerWriter.readFile(nameChosen);
        String line;

        // READS ALL THE LINE IN THE FILE
        while (null != (line = readerWriter.bufferedFile())) {
            String[] lineArray = line.trim().split(" ");
            for (int i = 1; i < lineArray.length; i++) {
                System.out.printf("%d. %s\n", i, lineArray[i]);

            }
        }
        readerWriter.closeBuffer();
        readerWriter.closeReader();
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

    public void delete(int index) throws IOException {
        this.readerWriter = new ReaderWriter(this.path);
        String line;
        while (null != (line = readerWriter.bufferedFile())) {
            String finalLine = String.format("", nameChosen);
            if (line.contains(nameChosen)) {
                String[] itemArr = line.split(" ");
                this.fileWriter = new FileWriter(Constants.SHOPPINGCART, false);
                for (int i = 0; i < itemArr.length; i++) {
                    if (index < 0 || index > itemArr.length) {
                        System.out.println();
                    } else if ((index) == i) {
                        System.out.printf("%s removed from cart.\n", itemArr[i]);
                        continue;
                    } else {
                        finalLine += itemArr[i] + " ";
                    }
                }
                fileWriter.append(finalLine);
            }
        }

        readerWriter.closeBuffer();
        readerWriter.closeReader();
        // CLOSE WRITER
        fileWriter.flush();
        fileWriter.close();
    }

}