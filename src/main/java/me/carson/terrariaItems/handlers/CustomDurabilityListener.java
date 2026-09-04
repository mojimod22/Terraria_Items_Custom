package me.carson.terrariaItems.handlers;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomDurabilityListener implements Listener {

    private final CustomDurabilityManager durabilityManager;

    public CustomDurabilityListener(JavaPlugin plugin) {

        this.durabilityManager =
                new CustomDurabilityManager(plugin);

        plugin.getServer()
                .getPluginManager()
                .registerEvents(this, plugin);
    }

    /*
     * MELEE
     *
     * Setiap kali player mengenai entity
     * dengan weapon yang memiliki custom durability,
     * durability berkurang 1.
     */
    @EventHandler
    public void onEntityHit(EntityDamageByEntityEvent event) {

        if (!(event.getDamager() instanceof Player player)) {
            return;
        }

        ItemStack item =
                player.getInventory()
                        .getItemInMainHand();

        if (!durabilityManager.hasDurability(item)) {
            return;
        }

        boolean broken =
                durabilityManager.damage(item, 1);

        if (broken) {

            player.getInventory()
                    .setItemInMainHand(null);

            player.sendMessage(
                    ChatColor.RED
                            + "Senjatamu telah habis durability!"
            );
        }
    }

    /*
     * RIGHT CLICK
     *
     * Untuk weapon yang digunakan dengan
     * klik kanan.
     *
     * Hanya MAIN HAND.
     */
    @EventHandler
    public void onRightClick(PlayerInteractEvent event) {

        if (event.getHand() == null) {
            return;
        }

        if (event.getHand()
                != org.bukkit.inventory.EquipmentSlot.HAND) {
            return;
        }

        Action action =
                event.getAction();

        if (action != Action.RIGHT_CLICK_AIR
                && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        Player player =
                event.getPlayer();

        ItemStack item =
                player.getInventory()
                        .getItemInMainHand();

        if (!durabilityManager.hasDurability(item)) {
            return;
        }

        /*
         * Jangan kurangi kalau item sedang
         * dalam cooldown.
         *
         * Ini mencegah durability berkurang
         * terlalu cepat pada weapon tertentu.
         */
        if (player.getCooldown(item.getType()) > 0) {
            return;
        }

        boolean broken =
                durabilityManager.damage(item, 1);

        if (broken) {

            player.getInventory()
                    .setItemInMainHand(null);

            player.sendMessage(
                    ChatColor.RED
                            + "Senjatamu telah habis durability!"
            );
        }
    }
}