package gc.nicoarbelaez.utils;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class InventoryUtilsTest {

    @Test
    public void test1() {
        int inventorySize = 45;

        int[] result = InventoryUtils.generateSlotInventory(inventorySize);
        int[] expected = { 10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34 };

        checkInventorySlots(expected, result);
    }

    @Test
    public void test2() {
        int inventorySize = 18;

        int[] result = InventoryUtils.generateSlotInventory(inventorySize);
        int[] expected = { 1, 2, 3, 4, 5, 6, 7, 10, 11, 12, 13, 14, 15, 16 };

        checkInventorySlots(expected, result);
    }

    @Test
    public void test3() {
        int inventorySize = 100;
        List<String> slotRanges = Arrays.asList("1-5", "1-2-3", "20-15", "10", "test", "test-test", "2-3");

        int[] result = InventoryUtils.generateSlotInventory(slotRanges, inventorySize);
        int[] expected = { 1, 2, 3, 4, 5, 10, 15, 16, 17, 18, 19, 20 };

        checkInventorySlots(expected, result);
    }

    private void checkInventorySlots(int[] expected, int[] result) {
        System.out.print("[InventoryUtilsTest] Expected: \n" + generateDebugInfo(expected));
        System.out.print("[InventoryUtilsTest] Result: \n" + generateDebugInfo(result));
        assertEquals(expected.length, result.length);
        for (int i = 0; i < expected.length; i++) {
            assertEquals(expected[i], result[i]);
        }
    }

    @SuppressWarnings("unused")
    private String generateDebugInfo(int[] list) {
        StringBuilder debugInfo = new StringBuilder();
        debugInfo.append(Arrays.toString(list));
        return debugInfo.toString();
    }
}
