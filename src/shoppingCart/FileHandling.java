package shoppingCart;

import java.io.FileWriter;
import java.io.IOException;

public class FileHandling {
    private String path;
    private String nameChosen;
    private ReaderWriter readerWriter;
    private FileWriter fileWriter;
    private StringBuilder userItems = new StringBuilder();

    public FileHandling() {
    }

    public FileHandling(String path) {
        this.path = path;
    }

    public void load(String name) throws IOException {
        this.readerWriter = new ReaderWriter(this.path);
        this.nameChosen = name;
        if (!readerWriter.checkExistence(name)) {
            readerWriter.createFile(name + ".cart");
        }

    }

    public String list(boolean madeChanges) throws IOException {
        this.readerWriter = new ReaderWriter(this.path);
        readerWriter.readFile(nameChosen);
        String line;
        String outputLine = "";

        if (!madeChanges) {
            while (null != (line = readerWriter.bufferedFile())) {
                outputLine += line.trim();
                String[] lineArray = line.trim().split(" ");
                for (int i = 0; i < lineArray.length; i++) {
                    this.userItems.append(String.format("%s ", lineArray[i]));
                }
            }
            readerWriter.closeBuffer();
            readerWriter.closeReader();
        } else {
            outputLine = userItems.toString().trim();
        }
        return outputLine;
    }

    public String add(String[] items, StringBuilder userItemsFromServer) throws IOException {
        String outputString = "";
        for (int i = 1; i < items.length; i++) {
            for (int j = 0; j < userItemsFromServer.length(); j++) {
                if (userItemsFromServer.indexOf(items[i]) == -1) {
                    userItemsFromServer.append(String.format("%s ", items[i]));
                    outputString += items[i] + " ";
                }
            }
        }
        if (outputString.equalsIgnoreCase("")) {
            return ("Nothing new added to the cart.");
        }
        return (outputString += "added to the cart.");
    }

    public String delete(int index, StringBuilder userItemsFromServer) throws IOException {
        String userItemsString = "";
        String itemRemoved = "", outputMessage = "";
        String[] userItemsArr = userItemsFromServer.toString().split(" ");
        for (int i = 0; i < userItemsArr.length; i++) {
            if (index > userItemsArr.length || index < 0) {
                outputMessage = String.format("Incorrect item index.");
            }
            if (i == index) {
                itemRemoved = userItemsArr[i];
                userItemsString += "";
                outputMessage = String.format("Removed %s from cart.\n", userItemsArr[i]);
                continue;
            }
            userItemsString += userItemsArr[i] + " ";
        }
        this.userItems.replace(0, (userItemsString.length() + itemRemoved.length()), userItemsString);

        return outputMessage;
    }

    public void save(StringBuilder userItems) throws IOException {
        fileWriter = readerWriter.fileWriter(nameChosen);
        fileWriter.write(userItems.toString());
        fileWriter.flush();
        fileWriter.close();
    }

}