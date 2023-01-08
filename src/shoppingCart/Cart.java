package shoppingCart;

import java.util.LinkedList;
import java.util.List;

public class Cart {
    private String commands;
    private String items;
    private List<String> list = new LinkedList<String>();

    public Cart() {
    }

    public Cart(String commands, String items) {
        this.commands = commands;
        this.items = items;
    }

    public String getCommands() {
        return commands;
    }

    public void setCommands(String commands) {
        this.commands = commands;
    }

    public String getItems() {
        return items;
    }

    public void setItems(String items) {
        this.items = items;
    }

}
