package gc.nicoarbelaez.models.calendar;

import java.util.List;

public class ItemModel {

    private String path;
    private String id;
    private String material;
    private String value; // Only if the material is a skull
    private String owner; // Only if the material is a skull
    private String name;
    private List<String> lore;
    private List<String> commands;
    private List<Integer> slots;
    private int ammount;
    private int priority;
    private boolean glow;

    public ItemModel(String path, String id, String material) {
        this.path = path;
        this.id = id;
        this.material = material;
        this.glow = false;
        this.ammount = 1;
        this.priority = 0;
    }

    public ItemModel(ItemModel itemModel) {
        this.path = itemModel.getPath();
        this.id = itemModel.getId();
        this.material = itemModel.getMaterial();
        this.value = itemModel.getValue();
        this.owner = itemModel.getOwner();
        this.name = itemModel.getName();
        this.lore = itemModel.getLore();
        this.commands = itemModel.getCommands();
        this.slots = itemModel.getSlots();
        this.ammount = itemModel.getAmmount();
        this.priority = itemModel.getPriority();
        this.glow = itemModel.isGlow();
    }

    public String toString() {
        return "ItemModel{" +
                "path='" + path + '\'' +
                ", id='" + id + '\'' +
                ", material='" + material + '\'' +
                ", value='" + value + '\'' +
                ", owner='" + owner + '\'' +
                ", name='" + name + '\'' +
                ", lore=" + lore +
                ", commands=" + commands +
                ", slots=" + slots +
                ", ammount=" + ammount +
                ", glow=" + glow +
                '}';
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public List<String> getCommands() {
        return commands;
    }

    public void setCommands(List<String> commands) {
        this.commands = commands;
    }

    public List<Integer> getSlots() {
        return slots;
    }

    public void setSlots(List<Integer> slots) {
        this.slots = slots;
    }

    public int getAmmount() {
        return ammount;
    }

    public void setAmmount(int ammount) {
        this.ammount = ammount;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isGlow() {
        return glow;
    }

    public void setGlow(boolean glow) {
        this.glow = glow;
    }
}
