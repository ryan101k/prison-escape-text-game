package textgame;

public class ItemAcquisitionEvent implements Event {
    private String message;
    private String item;
    private Inventory inventory;

    public ItemAcquisitionEvent(String message, String item, Inventory inventory) {
        this.message = message;
        this.item = item;
        this.inventory = inventory;
    }

    @Override
    public void trigger() {
        System.out.println(message);
        inventory.addItem(item);
    }
}
