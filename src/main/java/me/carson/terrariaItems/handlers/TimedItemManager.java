package me.carson.terrariaItems.handlers;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;

public class TimedItemManager implements Listener {

    private static TimedItemManager instance;

    private final Plugin plugin;

    private final NamespacedKey expirationKey;
    private final NamespacedKey permanentKey;
    private final NamespacedKey customItemKey;

    private BukkitTask task;

    public TimedItemManager(Plugin plugin) {
        this.plugin = plugin;

        expirationKey = new NamespacedKey(plugin, "license_expiration");
        permanentKey = new NamespacedKey(plugin, "license_permanent");
        customItemKey = new NamespacedKey(plugin, "customItem");

        instance = this;

        Bukkit.getPluginManager().registerEvents(this, plugin);

        startTask();
    }

    public static TimedItemManager getInstance() {
        return instance;
    }

    private void startTask() {

        task = Bukkit.getScheduler().runTaskTimer(
                plugin,
                () -> {

                    for (Player player : Bukkit.getOnlinePlayers()) {

                        for (int slot = 0; slot < player.getInventory().getSize(); slot++) {

                            ItemStack item = player.getInventory().getItem(slot);

                            if (item == null || item.getType().isAir()) {
                                continue;
                            }

                            if (!isLicensed(item)) {
                                continue;
                            }

                            if (isExpired(item)) {

                                player.getInventory().setItem(slot, null);

                                player.sendMessage(
                                        ChatColor.RED +
                                        "Item lisensi kamu telah kedaluwarsa."
                                );

                                continue;
                            }

                            updateCountdownLore(item);
                        }
                    }

                },
                20L,
                20L
        );
    }

    /**
     * Berikan lisensi waktu kepada item.
     *
     * Contoh:
     * 7 hari = 7 * 24 * 60 * 60 * 1000
     */
    public void applyTimedLicense(ItemStack item, long durationMillis) {

        if (item == null || !item.hasItemMeta()) {
            return;
        }

        ItemMeta meta = item.getItemMeta();

        meta.getPersistentDataContainer().set(
                expirationKey,
                PersistentDataType.LONG,
                System.currentTimeMillis() + durationMillis
        );

        meta.getPersistentDataContainer().remove(permanentKey);

        /*
         * Item lisensi tidak memakai durability.
         * Jadi walaupun material dasarnya Netherite / Sword / dll,
         * item tetap tidak rusak selama lisensinya aktif.
         */
        if (meta instanceof Damageable damageable) {
            damageable.setUnbreakable(true);
            damageable.resetDamage();
        }

        item.setItemMeta(meta);

        updateCountdownLore(item);
    }

    /**
     * Jadikan item permanent.
     */
    public void applyPermanentLicense(ItemStack item) {

        if (item == null || !item.hasItemMeta()) {
            return;
        }

        ItemMeta meta = item.getItemMeta();

        meta.getPersistentDataContainer().remove(expirationKey);

        meta.getPersistentDataContainer().set(
                permanentKey,
                PersistentDataType.BYTE,
                (byte) 1
        );

        if (meta instanceof Damageable damageable) {
            damageable.setUnbreakable(true);
            damageable.resetDamage();
        }

        item.setItemMeta(meta);

        updatePermanentLore(item);
    }

    /**
     * Apakah item merupakan item berlisensi?
     */
    public boolean isLicensed(ItemStack item) {

        if (item == null || !item.hasItemMeta()) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();

        return meta.getPersistentDataContainer().has(
                expirationKey,
                PersistentDataType.LONG
        ) ||
        meta.getPersistentDataContainer().has(
                permanentKey,
                PersistentDataType.BYTE
        );
    }

    /**
     * Apakah item permanent?
     */
    public boolean isPermanent(ItemStack item) {

        if (item == null || !item.hasItemMeta()) {
            return false;
        }

        return item.getItemMeta()
                .getPersistentDataContainer()
                .has(permanentKey, PersistentDataType.BYTE);
    }

    /**
     * Apakah waktu item sudah habis?
     */
    public boolean isExpired(ItemStack item) {

        if (item == null || !item.hasItemMeta()) {
            return false;
        }

        if (isPermanent(item)) {
            return false;
        }

        Long expiration = item.getItemMeta()
                .getPersistentDataContainer()
                .get(expirationKey, PersistentDataType.LONG);

        if (expiration == null) {
            return false;
        }

        return System.currentTimeMillis() >= expiration;
    }

    /**
     * Mendapatkan sisa waktu dalam milliseconds.
     */
    public long getRemainingMillis(ItemStack item) {

        if (item == null || !item.hasItemMeta()) {
            return 0;
        }

        if (isPermanent(item)) {
            return Long.MAX_VALUE;
        }

        Long expiration = item.getItemMeta()
                .getPersistentDataContainer()
                .get(expirationKey, PersistentDataType.LONG);

        if (expiration == null) {
            return 0;
        }

        return Math.max(
                0,
                expiration - System.currentTimeMillis()
        );
    }

    private void updateCountdownLore(ItemStack item) {

        if (item == null || !item.hasItemMeta()) {
            return;
        }

        if (isPermanent(item)) {
            updatePermanentLore(item);
            return;
        }

        ItemMeta meta = item.getItemMeta();

        List<String> lore = meta.hasLore()
                ? new ArrayList<>(meta.getLore())
                : new ArrayList<>();

        removeLicenseLore(lore);

        long remaining = getRemainingMillis(item);

        lore.add(
                ChatColor.DARK_GRAY +
                "⏳ Sisa waktu: " +
                ChatColor.YELLOW +
                formatDuration(remaining)
        );

        meta.setLore(lore);

        item.setItemMeta(meta);
    }

    private void updatePermanentLore(ItemStack item) {

        if (item == null || !item.hasItemMeta()) {
            return;
        }

        ItemMeta meta = item.getItemMeta();

        List<String> lore = meta.hasLore()
                ? new ArrayList<>(meta.getLore())
                : new ArrayList<>();

        removeLicenseLore(lore);

        lore.add(
                ChatColor.DARK_GRAY +
                "⏳ Status: " +
                ChatColor.GOLD +
                "PERMANENT"
        );

        meta.setLore(lore);

        item.setItemMeta(meta);
    }

    private void removeLicenseLore(List<String> lore) {

        lore.removeIf(line ->
                line != null &&
                (
                        line.contains("Sisa waktu:") ||
                        line.contains("Status: PERMANENT")
                )
        );
    }

    private String formatDuration(long millis) {

        if (millis <= 0) {
            return "EXPIRED";
        }

        long totalSeconds = millis / 1000;

        long days = totalSeconds / 86400;

        long hours = (totalSeconds % 86400) / 3600;

        long minutes = (totalSeconds % 3600) / 60;

        long seconds = totalSeconds % 60;

        if (days > 0) {

            return days + " hari "
                    + hours + " jam";

        }

        if (hours > 0) {

            return hours + " jam "
                    + minutes + " menit";

        }

        if (minutes > 0) {

            return minutes + " menit "
                    + seconds + " detik";

        }

        return seconds + " detik";
    }

    /**
     * Jika item expired ditemukan ketika player mengambil item,
     * jangan izinkan item tersebut masuk inventory.
     */
    @EventHandler
    public void onPickup(EntityPickupItemEvent event) {

        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        ItemStack item = event.getItem().getItemStack();

        if (!isLicensed(item)) {
            return;
        }

        if (isExpired(item)) {

            event.setCancelled(true);

            event.getItem().remove();

            player.sendMessage(
                    ChatColor.RED +
                    "Item tersebut sudah kedaluwarsa."
            );
        }
    }

    /**
     * Dipakai WeaponManager untuk mengizinkan penggunaan.
     */
    public boolean canUse(Player player, ItemStack item) {

        if (!isLicensed(item)) {
            return true;
        }

        if (isExpired(item)) {

            player.getInventory().setItemInMainHand(null);

            player.sendMessage(
                    ChatColor.RED +
                    "Item ini sudah kedaluwarsa."
            );

            return false;
        }

        return true;
    }
}