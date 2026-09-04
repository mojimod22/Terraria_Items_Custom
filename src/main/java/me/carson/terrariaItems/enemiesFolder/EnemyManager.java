package me.carson.terrariaItems.enemiesFolder;

import me.carson.terrariaItems.enemiesFolder.enemies.CustomDrowned;
import me.carson.terrariaItems.enemiesFolder.enemies.CustomSkeletons;
import me.carson.terrariaItems.enemiesFolder.enemies.CustomZombies;
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

public class EnemyManager implements Listener {

    private final NamespacedKey key;
    private final NamespacedKey statsAppliedKey;
    private final Plugin plugin;

    public EnemyManager(Plugin plugin) {
        this.plugin = plugin;

        key = new NamespacedKey(plugin, "custom_enemy");
        statsAppliedKey = new NamespacedKey(plugin, "custom_stats_applied");

        Bukkit.getPluginManager().registerEvents(this, plugin);

        Bukkit.getPluginManager().registerEvents(
                new CustomZombies(plugin),
                plugin
        );

        Bukkit.getPluginManager().registerEvents(
                new CustomSkeletons(plugin),
                plugin
        );

        Bukkit.getPluginManager().registerEvents(
                new CustomDrowned(plugin),
                plugin
        );
    }

    @EventHandler
    public void onCustomEnemySpawn(CreatureSpawnEvent event) {

        LivingEntity entity = event.getEntity();

        Bukkit.getScheduler().runTask(plugin, () -> {

            if (entity.isDead() || !entity.isValid()) {
                return;
            }

            String customEnemy = entity.getPersistentDataContainer()
                    .get(key, PersistentDataType.STRING);

            if (customEnemy == null) {
                return;
            }

            /*
             * Prevent stats from being multiplied more than once.
             */
            if (entity.getPersistentDataContainer()
                    .has(statsAppliedKey, PersistentDataType.BYTE)) {
                return;
            }

            // =========================
            // MAX HEALTH ×3
            // =========================

            AttributeInstance health =
                    entity.getAttribute(Attribute.MAX_HEALTH);

            if (health != null) {

                double baseHealth = health.getBaseValue();
                double newHealth = baseHealth * 3.0;

                health.setBaseValue(newHealth);

                entity.setHealth(
                        Math.min(
                                entity.getHealth() * 3.0,
                                health.getValue()
                        )
                );
            }

            // =========================
            // ATTACK DAMAGE ×3
            // =========================

            AttributeInstance attack =
                    entity.getAttribute(Attribute.ATTACK_DAMAGE);

            if (attack != null) {

                double baseAttack = attack.getBaseValue();

                attack.setBaseValue(
                        baseAttack * 3.0
                );
            }

            // =========================
            // ARMOR ×3
            // =========================

            AttributeInstance armor =
                    entity.getAttribute(Attribute.ARMOR);

            if (armor != null) {

                double baseArmor = armor.getBaseValue();

                armor.setBaseValue(
                        baseArmor * 3.0
                );
            }

            // =========================
            // ARMOR TOUGHNESS ×3
            // =========================

            AttributeInstance armorToughness =
                    entity.getAttribute(Attribute.ARMOR_TOUGHNESS);

            if (armorToughness != null) {

                double baseToughness =
                        armorToughness.getBaseValue();

                armorToughness.setBaseValue(
                        baseToughness * 3.0
                );
            }

            // =========================
            // MOVEMENT SPEED ×3
            // =========================

            AttributeInstance speed =
                    entity.getAttribute(Attribute.MOVEMENT_SPEED);

            if (speed != null) {

                double baseSpeed =
                        speed.getBaseValue();

                speed.setBaseValue(
                        baseSpeed * 3.0
                );
            }

            // =========================
            // FOLLOW RANGE ×3
            // =========================

            AttributeInstance followRange =
                    entity.getAttribute(Attribute.FOLLOW_RANGE);

            if (followRange != null) {

                double baseFollowRange =
                        followRange.getBaseValue();

                followRange.setBaseValue(
                        baseFollowRange * 3.0
                );
            }

            // =========================
            // MARK AS APPLIED
            // =========================

            entity.getPersistentDataContainer().set(
                    statsAppliedKey,
                    PersistentDataType.BYTE,
                    (byte) 1
            );

        });
    }

    @EventHandler
    public void onPossessedArmor(EntityDamageEvent event) {

        if (event.getCause()
                != EntityDamageEvent.DamageCause.FIRE_TICK) {
            return;
        }

        String customEnemy = event.getEntity()
                .getPersistentDataContainer()
                .get(
                        key,
                        PersistentDataType.STRING
                );

        if (!"PossessedArmor".equals(customEnemy)) {
            return;
        }

        event.setCancelled(true);
    }
}