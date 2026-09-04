package me.carson.terrariaItems.enemiesFolder.enemies;

import me.carson.terrariaItems.armorFolder.armors.bloodZombieArmor.BloodZombieBoots;
import me.carson.terrariaItems.armorFolder.armors.bloodZombieArmor.BloodZombieChestplate;
import me.carson.terrariaItems.armorFolder.armors.bloodZombieArmor.BloodZombieLeggings;
import me.carson.terrariaItems.armorFolder.armors.frozenZombieArmor.FrozenZombieChestplate;
import me.carson.terrariaItems.armorFolder.armors.possessedArmor.*;
import me.carson.terrariaItems.armorFolder.armors.raincoatZombieArmor.RaincoatZombieChestplate;
import me.carson.terrariaItems.armorFolder.armors.werewolfArmor.WerewolfBoots;
import me.carson.terrariaItems.armorFolder.armors.werewolfArmor.WerewolfChestplate;
import me.carson.terrariaItems.armorFolder.armors.werewolfArmor.WerewolfLeggings;
import me.carson.terrariaItems.enemiesFolder.CustomEnemy;
import me.carson.terrariaItems.miscFolder.hats.*;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.block.Biome;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public class CustomZombies extends CustomEnemy implements Listener {

    private static final Set<Biome> snowyBiomes = Set.of(
            Biome.SNOWY_TAIGA,
            Biome.JAGGED_PEAKS,
            Biome.FROZEN_PEAKS,
            Biome.GROVE,
            Biome.SNOWY_SLOPES,
            Biome.FROZEN_RIVER,
            Biome.SNOWY_PLAINS,
            Biome.ICE_SPIKES,
            Biome.SNOWY_BEACH
    );

    private static final Set<Biome> gnomeBiomes = Set.of(
            Biome.OLD_GROWTH_BIRCH_FOREST,
            Biome.OLD_GROWTH_PINE_TAIGA,
            Biome.OLD_GROWTH_SPRUCE_TAIGA,
            Biome.DARK_FOREST,
            Biome.PALE_GARDEN
    );

    private static final Set<Biome> jungleBiomes = Set.of(
            Biome.JUNGLE,
            Biome.OLD_GROWTH_PINE_TAIGA,
            Biome.SPARSE_JUNGLE,
            Biome.BAMBOO_JUNGLE
    );

    // =========================================================
    // SPAWN RATIO
    // =========================================================
    // 1 Terraria : 2 Vanilla
    // 33.33% Terraria
    // 66.67% Vanilla
    // =========================================================

    private static final double TERRARIA_SPAWN_CHANCE = 1.0 / 3.0;

    public CustomZombies(Plugin plugin) {
        super(plugin);
    }

    @EventHandler
    public void onZombieSpawn(CreatureSpawnEvent event) {

        if (event.getEntityType() != EntityType.ZOMBIE) {
            return;
        }

        Zombie zombie = (Zombie) event.getEntity();
        Location location = zombie.getLocation();

        /*
         * Zombie di bawah Y60 tidak diproses oleh
         * sistem custom Terraria.
         */
        if (location.getY() < 60) {
            return;
        }

        /*
         * GLOBAL TERRARIA SPAWN RATE
         *
         * 33.33% = Terraria custom mob
         * 66.67% = tetap Zombie vanilla
         */
        if (ThreadLocalRandom.current().nextDouble() >= TERRARIA_SPAWN_CHANCE) {
            return;
        }

        boolean isRaining = zombie.getWorld().hasStorm();
        double rand = Math.random();

        // =====================================================
        // HARDMODE
        // =====================================================

        if (instance.getHardmode() && instance.getHardmodeEnabled()) {

            // -------------------------------------------------
            // Doctor Bones
            // 1%
            // -------------------------------------------------

            if ((rand < 0.01)
                    && jungleBiomes.contains(
                    location.getBlock().getBiome())) {

                spawnDoctorBones(zombie);
                return;
            }

            // -------------------------------------------------
            // Gnome
            // 5%
            // -------------------------------------------------

            if ((rand < 0.05)
                    && gnomeBiomes.contains(
                    location.getBlock().getBiome())) {

                spawnGnome(zombie);
                return;
            }

            // -------------------------------------------------
            // Werewolf
            // 70% saat Full Moon
            // -------------------------------------------------

            if ((getMoonPhase(zombie.getWorld()) == 0)
                    && rand < 0.7) {

                spawnWerewolf(zombie);
                return;
            }

            // -------------------------------------------------
            // Blood Moon
            // -------------------------------------------------

            if (instance.getBloodMoon()) {

                // The Groom - 1%
                if (Math.random() < 0.01) {
                    spawnGroomZombie(zombie);
                    return;
                }

                // The Bride - 1%
                if (Math.random() < 0.01) {
                    spawnBrideZombie(zombie);
                    return;
                }

                // Blood Zombie - 50%
                if (Math.random() < 0.5) {
                    spawnBloodZombie(zombie);
                    return;
                }
            }

            // -------------------------------------------------
            // Possessed Armor
            // 50%
            // -------------------------------------------------

            if (rand < 0.5) {
                spawnPossessedArmor(zombie);
                return;
            }

            // -------------------------------------------------
            // Frozen Zombie
            // -------------------------------------------------

            if (snowyBiomes.contains(
                    location.getBlock().getBiome())) {

                spawnFrozenZombie(zombie);
                return;
            }

            // -------------------------------------------------
            // Raincoat Zombie
            // -------------------------------------------------

            if (isRaining) {
                spawnRaincoatZombie(zombie);
                return;
            }
        }

        // =====================================================
        // PRE-HARDMODE
        // =====================================================

        else if (instance.getPreHardmodeEnabled()) {

            // -------------------------------------------------
            // Doctor Bones
            // 1%
            // -------------------------------------------------

            if ((rand < 0.01)
                    && jungleBiomes.contains(
                    location.getBlock().getBiome())) {

                spawnDoctorBones(zombie);
                return;
            }

            // -------------------------------------------------
            // Gnome
            // 5%
            // -------------------------------------------------

            if ((rand < 0.05)
                    && gnomeBiomes.contains(
                    location.getBlock().getBiome())) {

                spawnGnome(zombie);
                return;
            }

            // -------------------------------------------------
            // Blood Moon
            // -------------------------------------------------

            if (instance.getBloodMoon()) {

                // The Groom - 1%
                if (Math.random() < 0.01) {
                    spawnGroomZombie(zombie);
                    return;
                }

                // The Bride - 1%
                if (Math.random() < 0.01) {
                    spawnBrideZombie(zombie);
                    return;
                }

                // Blood Zombie
                spawnBloodZombie(zombie);
                return;
            }

            // -------------------------------------------------
            // Frozen Zombie
            // -------------------------------------------------

            if (snowyBiomes.contains(
                    location.getBlock().getBiome())) {

                spawnFrozenZombie(zombie);
                return;
            }

            // -------------------------------------------------
            // Raincoat Zombie
            // -------------------------------------------------

            if (isRaining) {
                spawnRaincoatZombie(zombie);
                return;
            }
        }
    }

    // =========================================================
    // MOON PHASE
    // =========================================================

    public int getMoonPhase(World world) {

        long days = world.getFullTime() / 24000;

        return (int) (days % 8);
    }

    // =========================================================
    // POSSESSED ARMOR
    // =========================================================

    public void spawnPossessedArmor(Zombie zombie) {

        zombie.setCustomName(
                lang.get("enemies", "possessed_armor.name")
        );

        NamespacedKey key = new NamespacedKey(
                plugin,
                "custom_enemy"
        );

        zombie.getPersistentDataContainer().set(
                key,
                PersistentDataType.STRING,
                "PossessedArmor"
        );

        zombie.setCanPickupItems(false);

        zombie.getAttribute(Attribute.ATTACK_DAMAGE)
                .setBaseValue(12);

        EntityEquipment equipment = zombie.getEquipment();

        equipment.setHelmet(
                PossessedHelmet.getItem(plugin)
        );

        equipment.setChestplate(
                PossessedChestplate.getItem(plugin)
        );

        equipment.setLeggings(
                PossessedLeggings.getItem(plugin)
        );

        equipment.setBoots(
                PossessedBoots.getItem(plugin)
        );

        equipment.setItemInMainHand(null);

        equipment.setHelmetDropChance(0f);
        equipment.setChestplateDropChance(0f);
        equipment.setLeggingsDropChance(0f);
        equipment.setBootsDropChance(0f);

        zombie.setInvisible(true);
    }

    // =========================================================
    // FROZEN ZOMBIE
    // =========================================================

    public void spawnFrozenZombie(Zombie zombie) {

        zombie.setCustomName(
                lang.get("enemies", "frozen_zombie.name")
        );

        NamespacedKey key = new NamespacedKey(
                plugin,
                "custom_enemy"
        );

        zombie.getPersistentDataContainer().set(
                key,
                PersistentDataType.STRING,
                "FrozenZombie"
        );

        zombie.setCanPickupItems(false);

        EntityEquipment equipment = zombie.getEquipment();

        equipment.setHelmet(
                FrozenZombieHat.getItem(plugin)
        );

        equipment.setChestplate(
                FrozenZombieChestplate.getItem(plugin)
        );

        equipment.setHelmetDropChance(0f);
        equipment.setChestplateDropChance(0f);
    }

    // =========================================================
    // RAINCOAT ZOMBIE
    // =========================================================

    public void spawnRaincoatZombie(Zombie zombie) {

        zombie.setCustomName(
                lang.get("enemies", "raincoat_zombie.name")
        );

        NamespacedKey key = new NamespacedKey(
                plugin,
                "custom_enemy"
        );

        zombie.getPersistentDataContainer().set(
                key,
                PersistentDataType.STRING,
                "RaincoatZombie"
        );

        zombie.setCanPickupItems(false);

        EntityEquipment equipment = zombie.getEquipment();

        equipment.setHelmet(
                RaincoatZombieHat.getItem(plugin)
        );

        equipment.setChestplate(
                RaincoatZombieChestplate.getItem(plugin)
        );

        equipment.setHelmetDropChance(0f);
        equipment.setChestplateDropChance(0f);
    }

    // =========================================================
    // GNOME
    // =========================================================

    public void spawnGnome(Zombie zombie) {

        zombie.setCustomName(
                lang.get("enemies", "gnome.name")
        );

        NamespacedKey key = new NamespacedKey(
                plugin,
                "custom_enemy"
        );

        zombie.getPersistentDataContainer().set(
                key,
                PersistentDataType.STRING,
                "Gnome"
        );

        zombie.setCanPickupItems(false);
        zombie.setInvisible(true);

        zombie.getAttribute(Attribute.SCALE)
                .setBaseValue(0.4);

        zombie.getAttribute(Attribute.MOVEMENT_SPEED)
                .setBaseValue(0.3);

        zombie.getAttribute(Attribute.MAX_HEALTH)
                .setBaseValue(15);

        zombie.setHealth(15);

        EntityEquipment equipment = zombie.getEquipment();

        equipment.setHelmet(
                GnomeHat.getItem(plugin)
        );

        equipment.setChestplate(null);
        equipment.setLeggings(null);
        equipment.setBoots(null);

        equipment.setHelmetDropChance(0f);
    }

    // =========================================================
    // WEREWOLF
    // =========================================================

    public void spawnWerewolf(Zombie zombie) {

        zombie.setCustomName(
                lang.get("enemies", "werewolf.name")
        );

        NamespacedKey key = new NamespacedKey(
                plugin,
                "custom_enemy"
        );

        zombie.getPersistentDataContainer().set(
                key,
                PersistentDataType.STRING,
                "Werewolf"
        );

        zombie.setCanPickupItems(false);

        zombie.getAttribute(Attribute.MOVEMENT_SPEED)
                .setBaseValue(0.3);

        zombie.getAttribute(Attribute.MAX_HEALTH)
                .setBaseValue(60);

        zombie.getAttribute(Attribute.ATTACK_DAMAGE)
                .setBaseValue(10);

        zombie.setHealth(60);

        EntityEquipment equipment = zombie.getEquipment();

        equipment.setItemInMainHand(null);

        equipment.setHelmet(
                WerewolfHat.getItem(plugin)
        );

        equipment.setChestplate(
                WerewolfChestplate.getItem(plugin)
        );

        equipment.setLeggings(
                WerewolfLeggings.getItem(plugin)
        );

        equipment.setBoots(
                WerewolfBoots.getItem(plugin)
        );

        equipment.setHelmetDropChance(0f);
        equipment.setChestplateDropChance(0f);
        equipment.setLeggingsDropChance(0f);
        equipment.setBootsDropChance(0f);
    }

    // =========================================================
    // DOCTOR BONES
    // =========================================================

    public void spawnDoctorBones(Zombie zombie) {

        zombie.setCustomName(
                lang.get("enemies", "doctor_bones.name")
        );

        NamespacedKey key = new NamespacedKey(
                plugin,
                "custom_enemy"
        );

        zombie.getPersistentDataContainer().set(
                key,
                PersistentDataType.STRING,
                "DoctorBones"
        );

        zombie.setCanPickupItems(false);

        zombie.getAttribute(Attribute.MAX_HEALTH)
                .setBaseValue(35);

        zombie.getAttribute(Attribute.ATTACK_DAMAGE)
                .setBaseValue(4);

        zombie.setHealth(35);

        EntityEquipment equipment = zombie.getEquipment();

        equipment.setItemInMainHand(null);

        equipment.setHelmet(
                ArchaeologistsHat.getItem(plugin)
        );

        equipment.setChestplate(null);
        equipment.setLeggings(null);
        equipment.setBoots(null);

        equipment.setHelmetDropChance(1f);
    }

    // =========================================================
    // BLOOD ZOMBIE
    // =========================================================

    public void spawnBloodZombie(Zombie zombie) {

        zombie.setCustomName(
                lang.get("enemies", "blood_zombie.name")
        );

        NamespacedKey key = new NamespacedKey(
                plugin,
                "custom_enemy"
        );

        zombie.getPersistentDataContainer().set(
                key,
                PersistentDataType.STRING,
                "BloodZombie"
        );

        zombie.setCanPickupItems(false);

        zombie.getAttribute(Attribute.MAX_HEALTH)
                .setBaseValue(30);

        zombie.getAttribute(Attribute.ATTACK_DAMAGE)
                .setBaseValue(6);

        zombie.setHealth(30);

        EntityEquipment equipment = zombie.getEquipment();

        equipment.setItemInMainHand(null);

        equipment.setHelmet(
                BloodZombieHat.getItem(plugin)
        );

        equipment.setChestplate(
                BloodZombieChestplate.getItem(plugin)
        );

        equipment.setLeggings(
                BloodZombieLeggings.getItem(plugin)
        );

        equipment.setBoots(
                BloodZombieBoots.getItem(plugin)
        );

        equipment.setHelmetDropChance(0f);
        equipment.setChestplateDropChance(0f);
        equipment.setLeggingsDropChance(0f);
        equipment.setBootsDropChance(0f);
    }

    // =========================================================
    // THE GROOM
    // =========================================================

    public void spawnGroomZombie(Zombie zombie) {

        zombie.setCustomName(
                lang.get("enemies", "the_groom.name")
        );

        NamespacedKey key = new NamespacedKey(
                plugin,
                "custom_enemy"
        );

        zombie.getPersistentDataContainer().set(
                key,
                PersistentDataType.STRING,
                "TheGroom"
        );

        zombie.setCanPickupItems(false);

        zombie.getAttribute(Attribute.MAX_HEALTH)
                .setBaseValue(75);

        zombie.getAttribute(Attribute.ATTACK_DAMAGE)
                .setBaseValue(11);

        zombie.setHealth(75);

        EntityEquipment equipment = zombie.getEquipment();

        equipment.setItemInMainHand(null);

        equipment.setHelmet(
                TopHat.getItem(plugin)
        );

        equipment.setChestplate(null);
        equipment.setLeggings(null);
        equipment.setBoots(null);

        equipment.setHelmetDropChance(1f);
        equipment.setChestplateDropChance(0f);
        equipment.setLeggingsDropChance(0f);
    }

    // =========================================================
    // THE BRIDE
    // =========================================================

    public void spawnBrideZombie(Zombie zombie) {

        zombie.setCustomName(
                lang.get("enemies", "the_bride.name")
        );

        NamespacedKey key = new NamespacedKey(
                plugin,
                "custom_enemy"
        );

        zombie.getPersistentDataContainer().set(
                key,
                PersistentDataType.STRING,
                "TheBride"
        );

        zombie.setCanPickupItems(false);

        zombie.getAttribute(Attribute.MAX_HEALTH)
                .setBaseValue(75);

        zombie.getAttribute(Attribute.ATTACK_DAMAGE)
                .setBaseValue(11);

        zombie.setHealth(75);

        EntityEquipment equipment = zombie.getEquipment();

        equipment.setItemInMainHand(null);

        equipment.setHelmet(
                WeddingVeil.getItem(plugin)
        );

        equipment.setChestplate(null);
        equipment.setLeggings(null);
        equipment.setBoots(null);

        equipment.setHelmetDropChance(1f);
        equipment.setChestplateDropChance(0f);
        equipment.setLeggingsDropChance(0f);
    }
}