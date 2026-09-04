package me.carson.terrariaItems.handlers;

import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class CustomDurabilityManager {

    private final NamespacedKey durabilityKey;
    private final NamespacedKey maxDurabilityKey;

    public CustomDurabilityManager(JavaPlugin plugin) {
        this.durabilityKey =
                new NamespacedKey(plugin, "custom_durability");

        this.maxDurabilityKey =
                new NamespacedKey(plugin, "custom_max_durability");
    }

    public void setDurability(
            ItemStack item,
            int durability,
            int maxDurability
    ) {
        if (item == null || item.getType().isAir()) return;

        ItemMeta meta = item.getItemMeta();
        if (meta == null) return;

        /*
         * MATIKAN DURABILITY VANILLA
         */
        if (meta instanceof Damageable damageable) {
            damageable.setUnbreakable(true);
            damageable.setDamage(0);
        }

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
                .has(
                        durabilityKey,
                        PersistentDataType.INTEGER
                );
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

    /**
     * Mengurangi custom durability.
     *
     * @return true jika item habis
     */
    public boolean damage(ItemStack item, int amount) {

        if (!hasDurability(item)) {
            return false;
        }

        int current = getDurability(item);
        int max = getMaxDurability(item);

        current -= amount;

        if (current <= 0) {

            item.setAmount(0);

            return true;
        }

        setDurability(
                item,
                current,
                max
        );

        return false;
    }

    private void updateLore(ItemStack item) {

        ItemMeta meta = item.getItemMeta();

        if (meta == null) return;

        int durability = getDurability(item);
        int max = getMaxDurability(item);

        List<String> oldLore =
                meta.hasLore()
                        ? meta.getLore()
                        : new ArrayList<>();

        List<String> newLore =
                new ArrayList<>();

        for (String line : oldLore) {

            String stripped =
                    ChatColor.stripColor(line);

            if (stripped == null) continue;

            if (!stripped
                    .toLowerCase()
                    .startsWith("durability:")) {

                newLore.add(line);
            }
        }

        newLore.add(
                ChatColor.GRAY
                        + "Durability: "
                        + ChatColor.YELLOW
                        + durability
                        + ChatColor.GRAY
                        + "/"
                        + ChatColor.YELLOW
                        + max
        );

        meta.setLore(newLore);

        item.setItemMeta(meta);
    }
}