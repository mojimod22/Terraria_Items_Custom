package me.carson.terrariaItems.weaponsFolder;

import me.carson.terrariaItems.TILangManager;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.FluidCollisionMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public abstract class Weapon {

    protected final Plugin plugin;
    protected final String name;
    protected final String rarity;
    protected final Material baseMaterial;
    protected final String texture;
    protected final String id;
    protected final int cooldown;
    protected final String lore;
    private final NamespacedKey unplaceableKey;
    private final NamespacedKey customItemKey;
    public final TILangManager lang =TILangManager.getInstance();


    public Weapon(Plugin plugin, String name, String rarity, Material baseMaterial, String texture, String id, int cooldown, String lore) {
        this.plugin = plugin;
        this.name = name;
        this.rarity = rarity;
        this.baseMaterial = baseMaterial;
        this.texture = texture;
        this.id = id;
        unplaceableKey=new NamespacedKey(plugin, "unplaceable");
        customItemKey=new NamespacedKey(plugin, "customItem");
        this.cooldown = cooldown;
        this.lore = lore;
    }

    public ItemStack createItem() {
    ItemStack weapon = new ItemStack(baseMaterial);
    ItemMeta meta = weapon.getItemMeta();

    // Custom durability untuk semua Terraria weapon.
    // Tidak bergantung pada durability material vanilla.
    if (meta instanceof Damageable damageable) {
    damageable.setMaxDamage(250);
    damageable.setDamage(0);
    meta = damageable;
    }

    meta.setDisplayName(
            net.md_5.bungee.api.ChatColor.of(rarity)
                    + lang.get("weapons", name)
    );

    meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);

    meta.setLore(
            new ArrayList<>(
                    lang.getList("weapons", lore)
            )
    );

    NamespacedKey key =
            new NamespacedKey(plugin, "custom_item_id");

    meta.getPersistentDataContainer().set(
            key,
            PersistentDataType.STRING,
            id
    );

    meta.setItemModel(
            new NamespacedKey("terraria", texture)
    );

    meta.getPersistentDataContainer().set(
            unplaceableKey,
            PersistentDataType.BYTE,
            (byte) 1
    );

    meta.getPersistentDataContainer().set(
            customItemKey,
            PersistentDataType.BYTE,
            (byte) 1
    );

    meta.setMaxStackSize(1);

    weapon.setItemMeta(meta);

    return weapon;
}

    public boolean isThisItem(ItemStack item) {
        if (item == null || !item.hasItemMeta()) return false;
        ItemMeta meta = item.getItemMeta();
        if (meta == null) return false;

        NamespacedKey key = new NamespacedKey(plugin, "custom_item_id");
        String storedId = meta.getPersistentDataContainer().get(key, PersistentDataType.STRING);
        return id.equals(storedId);
    }

    public List<Entity> raycastCone(Player player, double range, double angleDegrees, int numRays) {
        List<Entity> hit = new ArrayList<>();
        Location origin = player.getEyeLocation();

        // Center ray
        RayTraceResult centerResult = player.getWorld().rayTrace(
                origin,
                origin.getDirection(),
                range,
                FluidCollisionMode.NEVER,
                true,
                0.5,
                e -> !e.equals(player)
        );
        if (centerResult != null && centerResult.getHitEntity() != null) {
            hit.add(centerResult.getHitEntity());
        }

        for (int i = 0; i < numRays; i++) {
            double angle = (360.0 / numRays) * i;
            float yaw = origin.getYaw() + (float)(Math.cos(Math.toRadians(angle)) * angleDegrees);
            float pitch = origin.getPitch() + (float)(Math.sin(Math.toRadians(angle)) * angleDegrees);

            Location spreadLoc = origin.clone();
            spreadLoc.setYaw(yaw);
            spreadLoc.setPitch(pitch);

            RayTraceResult result = player.getWorld().rayTrace(
                    origin,
                    spreadLoc.getDirection(),
                    range,
                    FluidCollisionMode.NEVER,
                    true,
                    0.5,
                    e -> !e.equals(player)
            );

            if (result != null && result.getHitEntity() != null) {
                hit.add(result.getHitEntity());
            }
        }

        return hit;
    }

    public abstract void leftActivate(Player player);

    public abstract void rightActivate(Player player);

}
