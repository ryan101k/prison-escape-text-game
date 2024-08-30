package textgame;

import java.util.HashSet;
import java.util.Set;

public class Inventory {
    private Set<String> items = new HashSet<>();

    public void addItem(String item) {
        items.add(item);
    }

    public void removeItem(String item) {
        items.remove(item);
    }

    public boolean hasItem(String item) {
        return items.contains(item);
    }

    public void showInventory() {
        if (items.isEmpty()) {
            System.out.println("인벤토리가 비어 있습니다.");
        } else {
            System.out.println("인벤토리:");
            for (String item : items) {
                System.out.println("- " + item);
            }
        }
    }
}
