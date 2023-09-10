package gc.nicoarbelaez.utils;

import java.lang.reflect.Field;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

import gc.nicoarbelaez.models.CustomItemModel;

public class ItemUtils {

    @SuppressWarnings("deprecation")
    public static ItemStack createItemFromId(String idMaterial) {
        ItemStack itemStack = null;
        Material material = null;
        idMaterial = idMaterial.toUpperCase();

        if (idMaterial == null || idMaterial.isEmpty()) {
            return null;
        }

        try {
            if (idMaterial.contains(":")) {
                String[] split = idMaterial.split(":");
                material = Material.valueOf(split[0]);
                itemStack = new ItemStack(material, 1, Short.parseShort(split[1]));
            } else {
                material = Material.valueOf(idMaterial);
                itemStack = new ItemStack(material, 1);
            }
        } catch (IllegalArgumentException e) {
            String message = "Error when creating the object from the ID: " + idMaterial + "\n";
            message += "Server version <1.13 (Ex: SKULL_ITEM:3):\n\t";
            message += "https://hub.spigotmc.org/nexus/service/local/repositories/snapshots/archive/org/spigotmc/spigot-api/1.8.8-R0.1-SNAPSHOT/spigot-api-1.8.8-R0.1-20160221.082514-43-javadoc.jar/!/org/bukkit/Material.html\n";
            message += "Server version >=1.13 (Ex: PLAYER_HEAD):\n\thttps://hub.spigotmc.org/javadocs/bukkit/org/bukkit/Material.html";
            Bukkit.getLogger().log(Level.SEVERE, message + idMaterial, e);
            return null;
        }
        return itemStack;
    }

    public static void setSkullData(ItemStack item, String value) {
        String itemType = item.getType().name();

        if (!itemType.equals("PLAYER_HEAD") && !itemType.equals("SKULL_ITEM")) {
            return;
        }

        SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);

        if (value != null) {
            profile.getProperties().put("textures", new Property("textures", value));
            try {
                Field profileField = skullMeta.getClass().getDeclaredField("profile");
                profileField.setAccessible(true);
                profileField.set(skullMeta, profile);
            } catch (IllegalArgumentException | NoSuchFieldException | SecurityException
                    | IllegalAccessException e) {
                Bukkit.getLogger().log(Level.SEVERE, "An error occurred when establishing the skull data: ",
                        e.getMessage());
            }
        }

        item.setItemMeta(skullMeta);
    }

    public static ItemStack createItemFromProperties(CustomItemModel customItem) {
        ItemStack item = createItemFromId(customItem.getMaterial());
        if (item == null) {
            return null;
        }

        // Item
        item.setAmount(customItem.getAmmount());

        // Meta
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(customItem.getName());
        meta.setLore(customItem.getLore());
        if (customItem.isGlow()) {
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(meta);

        // Meta skull
        if (customItem.getValue() != null) {
            setSkullData(item, customItem.getValue());
        }

        return item;
    }

}
