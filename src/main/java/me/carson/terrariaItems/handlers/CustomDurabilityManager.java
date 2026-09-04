package me.carson.terrariaItems.handlers;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;

public class CustomDurabilityManager {

    private final NamespacedKey durabilityKey;
    private final NamespacedKey maxDurabilityKey;

    public CustomDurabilityManager(JavaPlugin plugin) {
        durabilityKey = new NamespacedKey(plugin, "custom_durability");
        maxDurabilityKey = new NamespacedKey(plugin, "custom_max_durability");
    }

    public void setDurability(ItemStack item, int durability, int maxDurability) {
        if (item == null || !item.hasItemMeta()) {
            return;
        }

        ItemMeta meta = item.getItemMeta();

        meta.getPersistentDataContainer().set(
                durabilityKey,
                PersistentDataType.INTEGER,
                durability
        );

        meta.getPersistentDataContainer().set(
                maxDurabilityKey,
                PersistentDataType.INTEGER,
                maxDurability
        );

        item.setItemMeta(meta);

        updateLore(item);
    }

    public boolean hasDurability(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return false;
        }

        return item.getItemMeta()
                .getPersistentDataContainer()
                .has(durabilityKey, PersistentDataType.INTEGER);
    }

    public int getDurability(ItemStack item) {
        if (!hasDurability(item)) {
            return 0;
        }

        return item.getItemMeta()
                .getPersistentDataContainer()
                .getOrDefault(
                        durabilityKey,
                        PersistentDataType.INTEGER,
                        0
                );
    }

    public int getMaxDurability(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return 0;
        }

        return item.getItemMeta()
                .getPersistentDataContainer()
                .getOrDefault(
                        maxDurabilityKey,
                        PersistentDataType.INTEGER,
                        0
                );
    }

    public boolean damage(ItemStack item, int amount) {
        if (!hasDurability(item)) {
            return false;
        }

        int current = getDurability(item);
        int max = getMaxDurability(item);

        current -= amount;

        if (current <= 0) {
            return true;
        }

        setDurability(item, current, max);
        return false;
    }

    public void updateLore(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return;
        }

        ItemMeta meta = item.getItemMeta();

        int durability = getDurability(item);
        int max = getMaxDurability(item);

        List<String> oldLore = meta.hasLore()
                ? meta.getLore()
                : new ArrayList<>();

        List<String> newLore = new ArrayList<>();

        for (String line : oldLore) {
            if (!ChatColor.stripColor(line)
                    .toLowerCase()
                    .startsWith("durability:")) {
                newLore.add(line);
            }
        }

        newLore.add(
                ChatColor.GRAY + "Durability: "
                        + ChatColor.YELLOW + durability
                        + ChatColor.GRAY + "/"
                        + ChatColor.YELLOW + max
        );

        meta.setLore(newLore);
        item.setItemMeta(meta);
    }
}