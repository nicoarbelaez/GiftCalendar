package gc.nicoarbelaez.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class InventoryUtils {

    public static void main(String[] args) {
        int size1 = calculateAdjustedInventorySize(100);
        int[] slots1 = generateSlotInventory(size1);
        printInventoryInfo(size1, slots1);

        int size2 = calculateAdjustedInventorySize(36);
        int[] slots2 = generateSlotInventory(size2);
        printInventoryInfo(size2, slots2);

        int size3 = calculateAdjustedInventorySize(18);
        int[] slots3 = generateSlotInventory(size3);
        printInventoryInfo(size3, slots3);

        List<String> slotList = Arrays.asList("1-5", "1-2-3", "20-15", "10", "test", "test-test", "2-3");
        int[] slots4 = generateSlotInventory(slotList, size1);
        printInventoryInfo(slotList, slots4);
    }

    public static int calculateAdjustedInventorySize(int size) {
        if (size >= 9 && size <= 54 && size % 9 == 0) {
            return size;
        }
        return Math.min(Math.max(9, Math.round(size / 9.0F) * 9), 54);
    }

    public static int[] generateSlotInventory(int size) {
        size = calculateAdjustedInventorySize(size);
        int slotsPerRow = 7;
        int rows = 9;
        int columns = size / rows;
        int startSlot = (size > 18) ? 10 : 1;
        columns = (columns > 2) ? columns - 2 : columns;
        int[] slots = new int[columns * slotsPerRow];
        int slot = startSlot;

        for (int i = 0; i < slots.length; i++) {
            slots[i] = slot;
            boolean isEndOfRow = (i + 1) % slotsPerRow == 0;

            if (isEndOfRow) {
                slot += 3;
            } else {
                slot++;
            }
        }
        return slots;
    }

    public static int[] generateSlotInventory(List<String> slotList, int size) {
        size = calculateAdjustedInventorySize(size);
        List<Integer> slotsList = expandSlotList(slotList);
        int[] slots = new int[Math.min(slotsList.size(), size)];
        for (int i = 0; i < slots.length; i++) {
            slots[i] = slotsList.get(i);
        }
        return slots;
    }

    public static List<Integer> expandSlotList(List<String> rangeList) {
        Set<Integer> result = new HashSet<>();
        for (String rangeString : rangeList) {
            if (rangeString.contains("-")) {
                try {
                    result.addAll(parseRange(rangeString));
                } catch (NumberFormatException e) {
                    System.err.print("Warning: invalid range format \"" + rangeString + "\"");
                    continue;
                }
            } else {
                try {
                    result.add(Integer.parseInt(rangeString));
                } catch (NumberFormatException e) {
                    System.err.print("Warning: invalid number \"" + rangeString + "\"");
                    continue;
                }
            }
        }
        return result.stream().sorted().collect(Collectors.toList());
    }

    private static List<Integer> parseRange(String rangeString) throws NumberFormatException {
        String[] slotParts = rangeString.split("-");

        if (slotParts.length != 2) {
            throw new NumberFormatException();
        }
        int start = Integer.parseInt(slotParts[1]);
        int end = Integer.parseInt(slotParts[0]);

        if (start < end) {
            int temp = start;
            start = end;
            end = temp;
        }
        List<Integer> slotRange = new ArrayList<>();

        for (int i = end; i <= start; i++) {
            slotRange.add(i);
        }
        return slotRange;
    }

    private static void printInventoryInfo(int size, int[] slots) { // TODO: Remove
        System.out.print("[InventoryUtils] ========================");
        System.out.print("[InventoryUtils] Inventory Size: " + size + " Slots: ");
        System.out.print("[InventoryUtils] " + Arrays.toString(slots));
        System.out.print("[InventoryUtils] ========================");
    }

    private static void printInventoryInfo(List<String> slotList, int[] slots) { // TODO: Remove
        System.out.print("[InventoryUtils] ========================");
        System.out.print("[InventoryUtils] Inventory Size: " + slots.length + " SlotList: " + slotList + " Slots: ");
        System.out.print("[InventoryUtils] " + String.join(", ", Arrays.toString(slots)));
        System.out.print("[InventoryUtils] ========================");
    }
}