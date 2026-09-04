package me.carson.terrariaItems.enemiesFolder;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Objects;

public class EnemyManager implements Listener {

    private final NamespacedKey key;
    private final NamespacedKey statsAppliedKey;
    private final Plugin plugin;

    public EnemyManager(Plugin plugin) {
        this.plugin = plugin;

        key = new NamespacedKey(plugin, "custom_enemy");
        statsAppliedKey = new NamespacedKey(plugin, "custom_stats_applied");

        Bukkit.getPluginManager().registerEvents(this, plugin);

        Bukkit.getPluginManager().registerEvents(new CustomZombies(plugin), plugin);
        Bukkit.getPluginManager().registerEvents(new CustomSkeletons(plugin), plugin);
        Bukkit.getPluginManager().registerEvents(new CustomDrowned(plugin), plugin);
    }

    /**
     * ============================================================
     * TERRARIA CUSTOM MOB ×3 STATS
     * ============================================================
     *
     * Semua mob yang mempunyai PDC "custom_enemy" akan mendapatkan:
     *
     * HP              ×3
     * Attack Damage   ×3
     * Armor           ×3
     * Armor Toughness ×3
     * Movement Speed  ×3
     * Follow Range    ×3
     *
     * Hanya diproses satu kali per mob.
     */
    @EventHandler
    public void onCustomEnemySpawn(CreatureSpawnEvent event) {

        if (!(event.getEntity() instanceof LivingEntity entity)) {
            return;
        }

        /*
         * Custom mob dibuat oleh CustomZombies,
         * CustomSkeletons, atau CustomDrowned.
         *
         * Karena listener tersebut mengisi PDC selama
         * CreatureSpawnEvent, kita tunggu 1 tick agar
         * custom_enemy sudah terpasang.
         */
        Bukkit.getScheduler().runTask(plugin, () -> {

            if (!entity.isValid() || entity.isDead()) {
                return;
            }

            /*
             * Cek apakah entity merupakan Terraria custom enemy.
             */
            String customEnemy = entity.getPersistentDataContainer().get(
                    key,
                    PersistentDataType.STRING
            );

            if (customEnemy == null) {
                return;
            }

            /*
             * Jangan sampai stat dikali 3 dua kali.
             */
            if (entity.getPersistentDataContainer().has(
                    statsAppliedKey,
                    PersistentDataType.BYTE
            )) {
                return;
            }

            // =====================================================
            // HP ×3
            // =====================================================

            AttributeInstance health = entity.getAttribute(Attribute.MAX_HEALTH);

            if (health != null) {

                double oldMaxHealth = health.getBaseValue();

                health.setBaseValue(oldMaxHealth * 3.0);

                /*
                 * Current HP juga ikut dinaikkan ×3.
                 */
                double newHealth = entity.getHealth() * 3.0;

                entity.setHealth(
                        Math.min(newHealth, health.getValue())
                );
            }

            // =====================================================
            // ATTACK DAMAGE ×3
            // =====================================================

            multiplyAttribute(
                    entity,
                    Attribute.ATTACK_DAMAGE
            );

            // =====================================================
            // ARMOR ×3
            // =====================================================

            multiplyAttribute(
                    entity,
                    Attribute.ARMOR
            );

            // =====================================================
            // ARMOR TOUGHNESS ×3
            // =====================================================

            multiplyAttribute(
                    entity,
                    Attribute.ARMOR_TOUGHNESS
            );

            // =====================================================
            // MOVEMENT SPEED ×3
            // =====================================================

            multiplyAttribute(
                    entity,
                    Attribute.MOVEMENT_SPEED
            );

            // =====================================================
            // FOLLOW RANGE ×3
            // =====================================================

            multiplyAttribute(
                    entity,
                    Attribute.FOLLOW_RANGE
            );

            /*
             * Tandai bahwa mob ini sudah mendapatkan multiplier.
             */
            entity.getPersistentDataContainer().set(
                    statsAppliedKey,
                    PersistentDataType.BYTE,
                    (byte) 1
            );

        });
    }

    /**
     * Mengalikan base attribute ×3.
     */
    private void multiplyAttribute(
            LivingEntity entity,
            Attribute attribute
    ) {

        AttributeInstance instance = entity.getAttribute(attribute);

        if (instance == null) {
            return;
        }

        instance.setBaseValue(
                instance.getBaseValue() * 3.0
        );
    }

    /**
     * Possessed Armor tidak menerima damage FIRE_TICK.
     */
    @EventHandler
    public void onPossessedArmor(EntityDamageEvent event) {

        if (event.getCause() != EntityDamageEvent.DamageCause.FIRE_TICK) {
            return;
        }

        if (!Objects.equals(
                event.getEntity()
                        .getPersistentDataContainer()
                        .get(key, PersistentDataType.STRING),
                "PossessedArmor"
        )) {
            return;
        }

        event.setCancelled(true);
    }

}