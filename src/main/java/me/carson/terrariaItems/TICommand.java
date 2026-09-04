package me.carson.terrariaItems;

import me.carson.terrariaItems.accesoryFolder.AccessoryManager;
import me.carson.terrariaItems.accesoryFolder.accessories.*;
import me.carson.terrariaItems.armorFolder.armors.desertProwlerArmor.DesertProwlerHat;
import me.carson.terrariaItems.armorFolder.armors.desertProwlerArmor.DesertProwlerLeggings;
import me.carson.terrariaItems.armorFolder.armors.desertProwlerArmor.DesertProwlerPants;
import me.carson.terrariaItems.armorFolder.armors.desertProwlerArmor.DesertProwlerShirt;
import me.carson.terrariaItems.armorFolder.armors.forbiddenArmor.*;
import me.carson.terrariaItems.armorFolder.armors.frostArmor.*;
import me.carson.terrariaItems.armorFolder.armors.jungleArmor.JungleHat;
import me.carson.terrariaItems.armorFolder.armors.jungleArmor.JungleLeggings;
import me.carson.terrariaItems.armorFolder.armors.jungleArmor.JunglePants;
import me.carson.terrariaItems.armorFolder.armors.jungleArmor.JungleShirt;
import me.carson.terrariaItems.armorFolder.armors.necroArmor.NecroBreastplate;
import me.carson.terrariaItems.armorFolder.armors.necroArmor.NecroGreaves;
import me.carson.terrariaItems.armorFolder.armors.necroArmor.NecroHelmet;
import me.carson.terrariaItems.armorFolder.armors.necroArmor.NecroLeggings;
import me.carson.terrariaItems.armorFolder.armors.timArmor.WizardHat;
import me.carson.terrariaItems.handlers.*;
import me.carson.terrariaItems.materialsFolder.materials.*;
import me.carson.terrariaItems.miscFolder.BasicItems.BonePickaxe;
import me.carson.terrariaItems.miscFolder.fishingRods.*;
import me.carson.terrariaItems.miscFolder.hats.GoldenCrown;
import me.carson.terrariaItems.armorFolder.armors.cactusArmor.CactusBoots;
import me.carson.terrariaItems.armorFolder.armors.cactusArmor.CactusChestplate;
import me.carson.terrariaItems.armorFolder.armors.cactusArmor.CactusHelmet;
import me.carson.terrariaItems.armorFolder.armors.cactusArmor.CactusLeggings;
import me.carson.terrariaItems.armorFolder.armors.hallowedArmor.*;
import me.carson.terrariaItems.armorFolder.armors.moltenArmor.*;
import me.carson.terrariaItems.armorFolder.armors.shadowArmor.*;
import me.carson.terrariaItems.blocksFolder.blocks.Hellforge;
import me.carson.terrariaItems.materialsFolder.materials.souls.*;
import me.carson.terrariaItems.miscFolder.BasicItems.PickaxeAxe;
import me.carson.terrariaItems.toolFolder.tools.*;
import me.carson.terrariaItems.toolFolder.tools.crates.*;
import me.carson.terrariaItems.toolFolder.tools.hooks.*;
import me.carson.terrariaItems.toolFolder.tools.potions.*;
import me.carson.terrariaItems.toolFolder.tools.summons.BloodyTear;
import me.carson.terrariaItems.toolFolder.tools.summons.MechanicalEgg;
import me.carson.terrariaItems.toolFolder.tools.summons.MechanicalShrieker;
import me.carson.terrariaItems.toolFolder.tools.summons.MechanicalSkull;
import me.carson.terrariaItems.weaponsFolder.weapons.bowFolder.bows.*;
import me.carson.terrariaItems.weaponsFolder.weapons.gunFolder.guns.*;
import me.carson.terrariaItems.weaponsFolder.weapons.magicFolder.magicWeapons.*;
import me.carson.terrariaItems.weaponsFolder.weapons.meleeFolder.melee.*;
import me.carson.terrariaItems.weaponsFolder.weapons.rougeFolder.rouge.*;
import me.carson.terrariaItems.weaponsFolder.weapons.throwableFolder.throwablesFolder.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TICommand implements CommandExecutor, TabCompleter {

    private final TerrariaItems plugin;
    private final AccessoryManager accessoryManagerInstance = AccessoryManager.getInstance();
    private final PlayerDataHandler playerInstance = PlayerDataHandler.getInstance();
    private final ResetHandler resetInstance = ResetHandler.getInstance();
    private final WorldDataHandler worldDataHandler = WorldDataHandler.getInstance();
    private final VanityManager vanityManagerInstance = VanityManager.getInstance();
    private final BloodMoonManager bloodMoonManagerInstance = BloodMoonManager.getInstance();
    private final CustomPotionHandler customPotionHandler = CustomPotionHandler.getInstance();
    private final TILangManager lang = TILangManager.getInstance();

    public TICommand(TerrariaItems plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(
            @NotNull CommandSender sender,
            @NotNull Command command,
            @NotNull String label,
            String[] args
    ) {

        if (args.length == 0) {
            sender.sendMessage("§eUsage: /ti <subcommand>");
            return true;
        }

        /*
         * ============================================================
         * ADMIN / OP COMMANDS
         * ============================================================
         */

        switch (args[0].toLowerCase()) {

            case "toggle_blood_moon" -> {
                if (sender.isOp()) {
                    worldDataHandler.setBloodMoonEnabled(
                            !worldDataHandler.getBloodMoonEnabled()
                    );

                    sender.sendMessage(
                            "Blood Moon Enabled: "
                                    + worldDataHandler.getBloodMoonEnabled()
                    );

                    worldDataHandler.save();

                } else {
                    sender.sendMessage(
                            ChatColor.RED
                                    + "You do not have permission to use this command"
                    );
                }
            }

            case "toggle_prehardmode" -> {
                if (sender.isOp()) {
                    worldDataHandler.setPreHardmodeEnabled(
                            !worldDataHandler.getPreHardmodeEnabled()
                    );

                    sender.sendMessage(
                            "Pre-Hardmode Enabled: "
                                    + worldDataHandler.getPreHardmodeEnabled()
                    );

                    worldDataHandler.save();

                } else {
                    sender.sendMessage(
                            ChatColor.RED
                                    + "You do not have permission to use this command"
                    );
                }
            }

            case "toggle_hardmode" -> {
                if (sender.isOp()) {
                    worldDataHandler.setHardmodeEnabled(
                            !worldDataHandler.getHardmodeEnabled()
                    );

                    sender.sendMessage(
                            "Hardmode Enabled: "
                                    + worldDataHandler.getHardmodeEnabled()
                    );

                    worldDataHandler.save();

                } else {
                    sender.sendMessage(
                            ChatColor.RED
                                    + "You do not have permission to use this command"
                    );
                }
            }

            case "summon_blood_moon" -> {
                if (sender.isOp()) {

                    for (World world : sender.getServer().getWorlds()) {

                        if (world.getEnvironment() == World.Environment.NORMAL) {
                            bloodMoonManagerInstance.startBloodMoon(world);
                        }
                    }

                } else {
                    sender.sendMessage(
                            ChatColor.RED
                                    + "You do not have permission to use this command"
                    );
                }
            }
        }

        /*
         * ============================================================
         * PLAYER COMMANDS
         * ============================================================
         */

        if (sender instanceof Player player) {

            switch (args[0].toLowerCase()) {

                /*
                 * ====================================================
                 * GIVE
                 * ====================================================
                 *
                 * OLD:
                 * /ti give <item>
                 *
                 * NEW:
                 * /ti give <player> <item> <amount> <duration>
                 *
                 * Examples:
                 * /ti give Steve minishark 1 7d
                 * /ti give Steve minishark 1 14d
                 * /ti give Steve minishark 1 permanent
                 *
                 * ====================================================
                 */

                case "give" -> {

                    if (!player.hasPermission("terrariaitems.sub.give")) {
                        player.sendMessage(
                                ChatColor.RED
                                        + "You do not have permission to use /ti give."
                        );
                        return true;
                    }

                    Player targetPlayer = player;

                    boolean licensedGive = false;
        int requestedAmount = 1;

        String duration = null;
        Integer customDurability = null;

                    String itemName;

                    /*
                     * =================================================
                     * DETEKSI FORMAT COMMAND
                     * =================================================
                     *
                     * Kalau args hanya 2:
                     *
                     * /ti give minishark
                     *
                     * maka gunakan MODE LAMA.
                     *
                     * Kalau args >= 3:
                     *
                     * /ti give Steve minishark 1 7d
                     *
                     * maka wajib menggunakan format lengkap.
                     */

                    if (args.length >= 3) {

                        if (args.length < 5) {

                            player.sendMessage("§cUsage:");
                            player.sendMessage(
                                    "§7/ti give <item>"
                            );
                            player.sendMessage(
                                    "§7/ti give <player> <item> <amount> <duration>"
                            );
                            player.sendMessage(
                                    "§7Contoh: §e/ti give Steve minishark 1 7d"
                            );
                            player.sendMessage(
                                    "§7Contoh: §e/ti give Steve minishark 1 permanent"
                            );

                            return true;
                        }

                        /*
                         * PLAYER
                         */

                        targetPlayer = Bukkit.getPlayerExact(args[1]);

                        if (targetPlayer == null) {

                            player.sendMessage(
                                    "§cPlayer tidak ditemukan: §e"
                                            + args[1]
                            );

                            return true;
                        }

                        /*
                         * ITEM
                         */

                        itemName = args[2].toLowerCase();

                        /*
                         * AMOUNT
                         */

                        try {

                            requestedAmount = Integer.parseInt(args[3]);

                            if (requestedAmount <= 0) {

                                player.sendMessage(
                                        "§cAmount harus lebih dari 0."
                                );

                                return true;
                            }

                            if (requestedAmount > 64) {

                                player.sendMessage(
                                        "§cAmount maksimal adalah 64."
                                );

                                return true;
                            }

                        } catch (NumberFormatException exception) {

                            player.sendMessage(
                                    "§cAmount tidak valid."
                            );

                            return true;
                        }

                        /*
 * ============================================================
 * DURATION / CUSTOM DURABILITY
 * ============================================================
 *
 * 7d / 14d / 30d / permanent
 * = LICENSE
 *
 * 100 - 2000
 * = CUSTOM DURABILITY
 * ============================================================
 */

String finalArgument = args[4].toLowerCase();

/*
 * LICENSE MODE
 */
if (finalArgument.equals("permanent")
        || finalArgument.matches("\\d+d")) {

    duration = finalArgument;
    licensedGive = true;

    if (!duration.equals("permanent")) {

        try {

            long days = Long.parseLong(
                    duration.substring(
                            0,
                            duration.length() - 1
                    )
            );

            if (days <= 0) {

                player.sendMessage(
                        "§cDurasi harus lebih dari 0 hari."
                );

                return true;
            }

        } catch (NumberFormatException exception) {

            player.sendMessage(
                    "§cDurasi tidak valid."
            );

            return true;
        }
    }

/*
 * CUSTOM DURABILITY MODE
 */
} else {

    try {

        customDurability = Integer.parseInt(finalArgument);

        if (customDurability < 100
                || customDurability > 2000) {

            player.sendMessage(
                    "§cDurability harus antara §e100 §cdan §e2000."
            );

            return true;
        }

    } catch (NumberFormatException exception) {

        player.sendMessage(
                "§cNilai terakhir harus berupa:"
        );

        player.sendMessage(
                "§7Durability: §e100-2000"
        );

        player.sendMessage(
                "§7Duration: §e7d, 14d, 30d, permanent"
        );

        return true;
    }
}

                        licensedGive = true;

                        /*
                         * PERMANENT
                         */

                        if (!duration.equals("permanent")
                                && !duration.matches("\\d+d")) {

                            player.sendMessage(
                                    "§cDurasi tidak valid."
                            );

                            player.sendMessage(
                                    "§7Contoh: §e7d §7/ §e14d §7/ §e30d §7/ §epermanent"
                            );

                            return true;
                        }

                        /*
                         * CHECK DAYS
                         */

                        if (!duration.equals("permanent")) {

                            try {

                                long days = Long.parseLong(
                                        duration.substring(
                                                0,
                                                duration.length() - 1
                                        )
                                );

                                if (days <= 0) {

                                    player.sendMessage(
                                            "§cDurasi harus lebih dari 0 hari."
                                    );

                                    return true;
                                }

                            } catch (NumberFormatException exception) {

                                player.sendMessage(
                                        "§cDurasi tidak valid."
                                );

                                return true;
                            }
                        }

                    } else {

                        /*
                         * =================================================
                         * MODE LAMA
                         * =================================================
                         *
                         * /ti give <item>
                         */

                        if (args.length < 2) {

                            player.sendMessage(
                                    "§cUsage: /ti give <item>"
                            );

                            return true;
                        }

                        itemName = args[1].toLowerCase();
                    }

                    /*
                     * ====================================================
                     * SIMPAN INVENTORY SEBELUM GIVE
                     * ====================================================
                     *
                     * Digunakan untuk mengetahui item mana yang baru
                     * dimasukkan sehingga license bisa ditempelkan.
                     */

                    ItemStack[] inventoryBefore =
                            targetPlayer.getInventory()
                                    .getContents()
                                    .clone();

                    /*
                     * ====================================================
                     * ITEM SWITCH
                     * ====================================================
                     */

                    switch (itemName) {

                        case "cosmolight" -> {
                            targetPlayer.getInventory().addItem(
                                    Cosmolight.getItem(plugin)
                            );
                        }

                        case "rod_of_discord" -> {
                            targetPlayer.getInventory().addItem(
                                    RodOfDiscord.getItem(plugin)
                            );
                        }

                        case "momentum_capacitor" -> {
                            targetPlayer.getInventory().addItem(
                                    MomentumCapacitor.getItem(plugin)
                            );
                        }

                        case "stormbow" -> {
                            targetPlayer.getInventory().addItem(
                                    DaedalusStormbow.getItem(plugin)
                            );
                        }

                        case "cloud_bottle" -> {
                            targetPlayer.getInventory().addItem(
                                    CloudInABottle.getItem(plugin)
                            );
                        }

                        case "aglet" -> {
                            targetPlayer.getInventory().addItem(
                                    Aglet.getItem(plugin)
                            );
                        }

                        case "obsidian_skull" -> {
                            targetPlayer.getInventory().addItem(
                                    ObsidianSkull.getItem(plugin)
                            );
                        }

                        case "band_of_regeneration" -> {
                            targetPlayer.getInventory().addItem(
                                    BandOfRegeneration.getItem(plugin)
                            );
                        }

                        case "red_balloon" -> {
                            targetPlayer.getInventory().addItem(
                                    RedBalloon.getItem(plugin)
                            );
                        }

                        case "lucky_horseshoe" -> {
                            targetPlayer.getInventory().addItem(
                                    LuckyHorseshoe.getItem(plugin)
                            );
                        }

                        case "magic_mirror" -> {
                            targetPlayer.getInventory().addItem(
                                    MagicMirror.getItem(plugin)
                            );
                        }

                        case "cobalt_shield" -> {
                            targetPlayer.getInventory().addItem(
                                    CobaltShield.getItem(plugin)
                            );
                        }

                        case "golden_crown" -> {
                            targetPlayer.getInventory().addItem(
                                    GoldenCrown.getItem(plugin)
                            );
                        }

                        case "demonite_bar" -> {
                            targetPlayer.getInventory().addItem(
                                    DemoniteBar.getItem(plugin)
                            );
                        }

                        case "lights_bane" -> {
                            targetPlayer.getInventory().addItem(
                                    LightsBane.getItem(plugin)
                            );
                        }

                        case "shadow_armour" -> {
                            targetPlayer.getInventory().addItem(
                                    ShadowHelmet.getItem(plugin)
                            );
                            targetPlayer.getInventory().addItem(
                                    ShadowScalemail.getItem(plugin)
                            );
                            targetPlayer.getInventory().addItem(
                                    ShadowLeggings.getItem(plugin)
                            );
                            targetPlayer.getInventory().addItem(
                                    ShadowGreaves.getItem(plugin)
                            );
                        }

                        case "molten_armour" -> {
                            targetPlayer.getInventory().addItem(
                                    MoltenHelmet.getItem(plugin)
                            );
                            targetPlayer.getInventory().addItem(
                                    MoltenChestplate.getItem(plugin)
                            );
                            targetPlayer.getInventory().addItem(
                                    MoltenLeggings.getItem(plugin)
                            );
                            targetPlayer.getInventory().addItem(
                                    MoltenBoots.getItem(plugin)
                            );
                        }

                        case "counter_scarf" -> {
                            targetPlayer.getInventory().addItem(
                                    CounterScarf.getItem(plugin)
                            );
                        }

                        case "molten_fury" -> {
                            targetPlayer.getInventory().addItem(
                                    MoltenFury.getItem(plugin)
                            );
                        }

                        case "volcano" -> {
                            targetPlayer.getInventory().addItem(
                                    Volcano.getItem(plugin)
                            );
                        }

                        case "hellforge" -> {
                            targetPlayer.getInventory().addItem(
                                    Hellforge.getItem(plugin)
                            );
                        }

                        case "bezoar" -> {
                            targetPlayer.getInventory().addItem(
                                    Bezoar.getItem(plugin)
                            );
                        }

                        case "blindfold" -> {
                            targetPlayer.getInventory().addItem(
                                    Blindfold.getItem(plugin)
                            );
                        }

                        case "fast_clock" -> {
                            targetPlayer.getInventory().addItem(
                                    FastClock.getItem(plugin)
                            );
                        }

                        case "vitamins" -> {
                            targetPlayer.getInventory().addItem(
                                    Vitamins.getItem(plugin)
                            );
                        }

                        case "molten_elytra" -> {
                            targetPlayer.getInventory().addItem(
                                    MoltenElytra.getItem(plugin)
                            );
                        }

                        case "shadow_elytra" -> {
                            targetPlayer.getInventory().addItem(
                                    ShadowElytra.getItem(plugin)
                            );
                        }

                        case "ranger_emblem" -> {
                            targetPlayer.getInventory().addItem(
                                    RangerEmblem.getItem(plugin)
                            );
                        }

                        case "warrior_emblem" -> {
                            targetPlayer.getInventory().addItem(
                                    WarriorEmblem.getItem(plugin)
                            );
                        }

                        case "shackle" -> {
                            targetPlayer.getInventory().addItem(
                                    Shackle.getItem(plugin)
                            );
                        }

                        case "might" -> {
                            targetPlayer.getInventory().addItem(
                                    SoulOfMight.getItem(plugin)
                            );
                        }

                        case "excalibur" -> {
                            targetPlayer.getInventory().addItem(
                                    Excalibur.getItem(plugin)
                            );
                        }

                        case "snowball_cannon" -> {
                            targetPlayer.getInventory().addItem(
                                    SnowballCannon.getItem(plugin)
                            );
                        }

                        case "hallowed_repeater" -> {
                            targetPlayer.getInventory().addItem(
                                    HallowedRepeater.getItem(plugin)
                            );
                        }

                        case "pickaxe_axe" -> {
                            targetPlayer.getInventory().addItem(
                                    PickaxeAxe.getItem(plugin)
                            );
                        }

                        case "hallowed_armour" -> {
                            targetPlayer.getInventory().addItem(
                                    HallowedMask.getItem(plugin)
                            );
                            targetPlayer.getInventory().addItem(
                                    HallowedHelmet.getItem(plugin)
                            );
                            targetPlayer.getInventory().addItem(
                                    HallowedHeadgear.getItem(plugin)
                            );
                            targetPlayer.getInventory().addItem(
                                    HallowedChestplate.getItem(plugin)
                            );
                            targetPlayer.getInventory().addItem(
                                    HallowedLeggings.getItem(plugin)
                            );
                            targetPlayer.getInventory().addItem(
                                    HallowedBoots.getItem(plugin)
                            );
                        }

                        case "hallowed_elytra" -> {
                            targetPlayer.getInventory().addItem(
                                    HallowedElytra.getItem(plugin)
                            );
                        }

                        case "avenger_emblem" -> {
                            targetPlayer.getInventory().addItem(
                                    AvengerEmblem.getItem(plugin)
                            );
                        }

                        case "blade_of_grass" -> {
                            targetPlayer.getInventory().addItem(
                                    BladeOfGrass.getItem(plugin)
                            );
                        }

                        case "ice_blade" -> {
                            targetPlayer.getInventory().addItem(
                                    IceBlade.getItem(plugin)
                            );
                        }

                        case "blowpipe" -> {
                            targetPlayer.getInventory().addItem(
                                    Blowpipe.getItem(plugin)
                            );
                        }

                        case "minishark" -> {
                            targetPlayer.getInventory().addItem(
                                    Minishark.getItem(plugin)
                            );
                        }

                        case "handgun" -> {
                            targetPlayer.getInventory().addItem(
                                    Handgun.getItem(plugin)
                            );
                        }

                        case "shotgun" -> {
                            targetPlayer.getInventory().addItem(
                                    Shotgun.getItem(plugin)
                            );
                        }

                        case "needler" -> {
                            targetPlayer.getInventory().addItem(
                                    Needler.getItem(plugin)
                            );
                        }

                        case "christmastreesword" -> {
                            targetPlayer.getInventory().addItem(
                                    ChristmasTreeSword.getItem(plugin)
                            );
                        }

                        case "mega_shark" -> {
                            targetPlayer.getInventory().addItem(
                                    Megashark.getItem(plugin)
                            );
                        }

                        case "sniper_rifle" -> {
                            targetPlayer.getInventory().addItem(
                                    SniperRifle.getItem(plugin)
                            );
                        }

                        case "phoenix_blaster" -> {
                            targetPlayer.getInventory().addItem(
                                    PhoenixBlaster.getItem(plugin)
                            );
                        }

                        case "torrential_tear" -> {
                            targetPlayer.getInventory().addItem(
                                    TorrentialTear.getItem(plugin)
                            );
                        }

                        case "amethyst_staff" -> {
                            targetPlayer.getInventory().addItem(
                                    AmethystStaff.getItem(plugin)
                            );
                        }

                        case "ruby_staff" -> {
                            targetPlayer.getInventory().addItem(
                                    RubyStaff.getItem(plugin)
                            );
                        }

                        case "mana_crystal" -> {
                            targetPlayer.getInventory().addItem(
                                    ManaCrystal.getItem(plugin)
                            );
                        }

                        case "meteor_staff" -> {
                            targetPlayer.getInventory().addItem(
                                    MeteorStaff.getItem(plugin)
                            );
                        }

                        case "bubble_gun" -> {
                            targetPlayer.getInventory().addItem(
                                    BubbleGun.getItem(plugin)
                            );
                        }

                        case "water_bolt" -> {
                            targetPlayer.getInventory().addItem(
                                    WaterBolt.getItem(plugin)
                            );
                        }

                        case "icicle_staff" -> {
                            targetPlayer.getInventory().addItem(
                                    IcicleStaff.getItem(plugin)
                            );
                        }

                        case "neptunes_shell" -> {
                            targetPlayer.getInventory().addItem(
                                    NeptunesShell.getItem(plugin)
                            );
                        }

                        case "ancient_fossil" -> {
                            targetPlayer.getInventory().addItem(
                                    AncientFossil.getItem(plugin)
                            );
                        }

                        case "cactus_armor" -> {
                            targetPlayer.getInventory().addItem(
                                    CactusHelmet.getItem(plugin)
                            );
                            targetPlayer.getInventory().addItem(
                                    CactusChestplate.getItem(plugin)
                            );
                            targetPlayer.getInventory().addItem(
                                    CactusLeggings.getItem(plugin)
                            );
                            targetPlayer.getInventory().addItem(
                                    CactusBoots.getItem(plugin)
                            );
                        }

                        case "terra_blade" -> {
                            targetPlayer.getInventory().addItem(
                                    TerraBlade.getItem(plugin)
                            );
                        }

                        case "star_cannon" -> {
                            targetPlayer.getInventory().addItem(
                                    StarCannon.getItem(plugin)
                            );
                        }

                        case "fallen_star" -> {
                            ItemStack itemStack =
                                    FallenStar.getItem(plugin);

                            itemStack.setAmount(20);

                            targetPlayer.getInventory().addItem(
                                    itemStack
                            );
                        }

                        case "super_star_shooter" -> {
                            targetPlayer.getInventory().addItem(
                                    SuperStarShooter.getItem(plugin)
                            );
                        }

                        case "sorcerer_emblem" -> {
                            targetPlayer.getInventory().addItem(
                                    SorcererEmblem.getItem(plugin)
                            );
                        }

                        case "wooden_crate" -> {
                            targetPlayer.getInventory().addItem(
                                    WoodenCrate.getItem(plugin)
                            );
                        }

                        case "iron_crate" -> {
                            targetPlayer.getInventory().addItem(
                                    IronCrate.getItem(plugin)
                            );
                        }

                        case "golden_crate" -> {
                            targetPlayer.getInventory().addItem(
                                    GoldenCrate.getItem(plugin)
                            );
                        }

                        case "frozen_crate" -> {
                            targetPlayer.getInventory().addItem(
                                    FrozenCrate.getItem(plugin)
                            );
                        }

                        case "sky_crate" -> {
                            targetPlayer.getInventory().addItem(
                                    SkyCrate.getItem(plugin)
                            );
                        }

                        case "jungle_crate" -> {
                            targetPlayer.getInventory().addItem(
                                    JungleCrate.getItem(plugin)
                            );
                        }

                        case "oasis_crate" -> {
                            targetPlayer.getInventory().addItem(
                                    OasisCrate.getItem(plugin)
                            );
                        }

                        case "ocean_crate" -> {
                            targetPlayer.getInventory().addItem(
                                    OceanCrate.getItem(plugin)
                            );
                        }

                        case "falcon_blade" -> {
                            targetPlayer.getInventory().addItem(
                                    FalconBlade.getItem(plugin)
                            );
                        }

                        case "enchanted_sword" -> {
                            targetPlayer.getInventory().addItem(
                                    EnchantedSword.getItem(plugin)
                            );
                        }

                        case "tsunami_in_a_bottle" -> {
                            targetPlayer.getInventory().addItem(
                                    TsunamiInABottle.getItem(plugin)
                            );
                        }

                        case "anklet_of_the_wind" -> {
                            targetPlayer.getInventory().addItem(
                                    AnkletOfTheWind.getItem(plugin)
                            );
                        }

                        case "blizzard_in_a_bottle" -> {
                            targetPlayer.getInventory().addItem(
                                    BlizzardInABottle.getItem(plugin)
                            );
                        }

                        case "sandstorm_in_a_bottle" -> {
                            targetPlayer.getInventory().addItem(
                                    SandstormInABottle.getItem(plugin)
                            );
                        }

                        case "thunder_zapper" -> {
                            targetPlayer.getInventory().addItem(
                                    ThunderZapper.getItem(plugin)
                            );
                        }

                        case "magical_harp" -> {
                            targetPlayer.getInventory().addItem(
                                    MagicalHarp.getItem(plugin)
                            );
                        }

                        case "crystal_storm" -> {
                            targetPlayer.getInventory().addItem(
                                    CrystalStorm.getItem(plugin)
                            );
                        }

                        case "onyx_blaster" -> {
                            targetPlayer.getInventory().addItem(
                                    OnyxBlaster.getItem(plugin)
                            );
                        }

                        case "hoarfrost_bow" -> {
                            targetPlayer.getInventory().addItem(
                                    HoarfrostBow.getItem(plugin)
                            );
                        }

                        case "mechanical_shrieker" -> {
                            targetPlayer.getInventory().addItem(
                                    MechanicalShrieker.getItem(plugin)
                            );
                        }

                        case "mechanical_egg" -> {
                            targetPlayer.getInventory().addItem(
                                    MechanicalEgg.getItem(plugin)
                            );
                        }

                        case "mechanical_skull" -> {
                            targetPlayer.getInventory().addItem(
                                    MechanicalSkull.getItem(plugin)
                            );
                        }

                        case "pulse_bow" -> {
                            targetPlayer.getInventory().addItem(
                                    PulseBow.getItem(plugin)
                            );
                        }

                        case "bone_pickaxe" -> {
                            targetPlayer.getInventory().addItem(
                                    BonePickaxe.getItem(plugin)
                            );
                        }

                        case "night_vision_helmet" -> {
                            targetPlayer.getInventory().addItem(
                                    NightVisionHelmet.getItem(plugin)
                            );
                        }

                        case "sand_gun" -> {
                            targetPlayer.getInventory().addItem(
                                    SandGun.getItem(plugin)
                            );
                        }

                        case "souls" -> {

                            ItemStack light =
                                    SoulOfLight.getItem(plugin);

                            light.setAmount(4);

                            targetPlayer.getInventory().addItem(light);

                            ItemStack night =
                                    SoulOfNight.getItem(plugin);

                            night.setAmount(4);

                            targetPlayer.getInventory().addItem(night);

                            ItemStack might =
                                    SoulOfMight.getItem(plugin);

                            might.setAmount(4);

                            targetPlayer.getInventory().addItem(might);

                            ItemStack fright =
                                    SoulOfFright.getItem(plugin);

                            fright.setAmount(4);

                            targetPlayer.getInventory().addItem(fright);

                            ItemStack sight =
                                    SoulOfSight.getItem(plugin);

                            sight.setAmount(4);

                            targetPlayer.getInventory().addItem(sight);
                        }

                        case "jungle_armor" -> {

                            targetPlayer.getInventory().addItem(
                                    JungleHat.getItem(plugin)
                            );

                            targetPlayer.getInventory().addItem(
                                    JungleShirt.getItem(plugin)
                            );

                            targetPlayer.getInventory().addItem(
                                    JungleLeggings.getItem(plugin)
                            );

                            targetPlayer.getInventory().addItem(
                                    JunglePants.getItem(plugin)
                            );
                        }

                        case "necro_armor" -> {

                            targetPlayer.getInventory().addItem(
                                    NecroHelmet.getItem(plugin)
                            );

                            targetPlayer.getInventory().addItem(
                                    NecroBreastplate.getItem(plugin)
                            );

                            targetPlayer.getInventory().addItem(
                                    NecroLeggings.getItem(plugin)
                            );

                            targetPlayer.getInventory().addItem(
                                    NecroGreaves.getItem(plugin)
                            );
                        }

                        case "forbidden_armor" -> {

                            targetPlayer.getInventory().addItem(
                                    ForbiddenCirclet.getItem(plugin)
                            );

                            targetPlayer.getInventory().addItem(
                                    ForbiddenMask.getItem(plugin)
                            );

                            targetPlayer.getInventory().addItem(
                                    ForbiddenRobes.getItem(plugin)
                            );

                            targetPlayer.getInventory().addItem(
                                    ForbiddenLeggings.getItem(plugin)
                            );

                            targetPlayer.getInventory().addItem(
                                    ForbiddenTreads.getItem(plugin)
                            );
                        }

                        case "forbidden_fragment" -> {
                            targetPlayer.getInventory().addItem(
                                    ForbiddenFragment.getItem(plugin)
                            );
                        }

                        case "frost_core" -> {
                            targetPlayer.getInventory().addItem(
                                    FrostCore.getItem(plugin)
                            );
                        }

                        case "frost_armor" -> {

                            targetPlayer.getInventory().addItem(
                                    FrostHelmet.getItem(plugin)
                            );

                            targetPlayer.getInventory().addItem(
                                    FrostBreastplate.getItem(plugin)
                            );

                            targetPlayer.getInventory().addItem(
                                    FrostLeggings.getItem(plugin)
                            );

                            targetPlayer.getInventory().addItem(
                                    FrostBoots.getItem(plugin)
                            );
                        }

                        case "magic_quiver" -> {
                            targetPlayer.getInventory().addItem(
                                    MagicQuiver.getItem(plugin)
                            );
                        }

                        case "wizard_hat" -> {
                            targetPlayer.getInventory().addItem(
                                    WizardHat.getItem(plugin)
                            );
                        }

                        case "vampire_knives" -> {
                            targetPlayer.getInventory().addItem(
                                    VampireKnives.getItem(plugin)
                            );
                        }

                        case "forbidden_elytra" -> {
                            targetPlayer.getInventory().addItem(
                                    ForbiddenElytra.getItem(plugin)
                            );
                        }

                        case "frost_elytra" -> {
                            targetPlayer.getInventory().addItem(
                                    FrostElytra.getItem(plugin)
                            );
                        }

                        case "tainted_blade" -> {
                            targetPlayer.getInventory().addItem(
                                    TaintedBlade.getItem(plugin)
                            );
                        }

                        case "caustic_edge" -> {
                            targetPlayer.getInventory().addItem(
                                    CausticEdge.getItem(plugin)
                            );
                        }

                        case "ice_sickle" -> {
                            targetPlayer.getInventory().addItem(
                                    IceSickle.getItem(plugin)
                            );
                        }

                        case "breaker_blade" -> {
                            targetPlayer.getInventory().addItem(
                                    BreakerBlade.getItem(plugin)
                            );
                        }

                        case "slap_hand" -> {
                            targetPlayer.getInventory().addItem(
                                    SlapHand.getItem(plugin)
                            );
                        }

                        case "laser_rifle" -> {
                            targetPlayer.getInventory().addItem(
                                    LaserRifle.getItem(plugin)
                            );
                        }

                        case "clockwork_assault_rifle" -> {
                            targetPlayer.getInventory().addItem(
                                    ClockworkAssaultRifle.getItem(plugin)
                            );
                        }

                        case "lesser_mana_potion" -> {
                            targetPlayer.getInventory().addItem(
                                    LesserManaPotion.getItem(plugin)
                            );
                        }

                        case "mana_potion" -> {
                            targetPlayer.getInventory().addItem(
                                    ManaPotion.getItem(plugin)
                            );
                        }

                        case "greater_mana_potion" -> {
                            targetPlayer.getInventory().addItem(
                                    GreaterManaPotion.getItem(plugin)
                            );
                        }

                        case "super_mana_potion" -> {
                            targetPlayer.getInventory().addItem(
                                    SuperManaPotion.getItem(plugin)
                            );
                        }

                        case "feral_claws" -> {
                            targetPlayer.getInventory().addItem(
                                    FeralClaws.getItem(plugin)
                            );
                        }

                        case "panic_necklace" -> {
                            targetPlayer.getInventory().addItem(
                                    PanicNecklace.getItem(plugin)
                            );
                        }

                        case "band_of_starpower" -> {
                            targetPlayer.getInventory().addItem(
                                    BandOfStarpower.getItem(plugin)
                            );
                        }

                        case "magic_cuffs" -> {
                            targetPlayer.getInventory().addItem(
                                    MagicCuffs.getItem(plugin)
                            );
                        }

                        case "honey_comb" -> {
                            targetPlayer.getInventory().addItem(
                                    HoneyComb.getItem(plugin)
                            );
                        }

                        case "honey_balloon" -> {
                            targetPlayer.getInventory().addItem(
                                    HoneyBalloon.getItem(plugin)
                            );
                        }

                        case "sweetheart_necklace" -> {
                            targetPlayer.getInventory().addItem(
                                    SweetheartNecklace.getItem(plugin)
                            );
                        }

                        case "obsidian_shield" -> {
                            targetPlayer.getInventory().addItem(
                                    ObsidianShield.getItem(plugin)
                            );
                        }

                        case "ankh_charm" -> {
                            targetPlayer.getInventory().addItem(
                                    AnkhCharm.getItem(plugin)
                            );
                        }

                        case "ankh_shield" -> {
                            targetPlayer.getInventory().addItem(
                                    AnkhShield.getItem(plugin)
                            );
                        }

                        case "titan_glove" -> {
                            targetPlayer.getInventory().addItem(
                                    TitanGlove.getItem(plugin)
                            );
                        }

                        case "power_glove" -> {
                            targetPlayer.getInventory().addItem(
                                    PowerGlove.getItem(plugin)
                            );
                        }

                        case "mechanical_glove" -> {
                            targetPlayer.getInventory().addItem(
                                    MechanicalGlove.getItem(plugin)
                            );
                        }

                        case "cross_necklace" -> {
                            targetPlayer.getInventory().addItem(
                                    CrossNecklace.getItem(plugin)
                            );
                        }

                        case "magic_dagger" -> {
                            targetPlayer.getInventory().addItem(
                                    MagicDagger.getItem(plugin)
                            );
                        }

                        case "star_cloak" -> {
                            targetPlayer.getInventory().addItem(
                                    StarCloak.getItem(plugin)
                            );
                        }

                        case "star_veil" -> {
                            targetPlayer.getInventory().addItem(
                                    StarVeil.getItem(plugin)
                            );
                        }

                        case "life_crystal" -> {
                            targetPlayer.getInventory().addItem(
                                    LifeCrystal.getItem(plugin)
                            );
                        }

                        case "blood_rain_bow" -> {
                            targetPlayer.getInventory().addItem(
                                    BloodRainBow.getItem(plugin)
                            );
                        }

                        case "bloody_tear" -> {
                            targetPlayer.getInventory().addItem(
                                    BloodyTear.getItem(plugin)
                            );
                        }

                        case "shark_tooth_necklace" -> {
                            targetPlayer.getInventory().addItem(
                                    SharkToothNecklace.getItem(plugin)
                            );
                        }

                        case "stinger_necklace" -> {
                            targetPlayer.getInventory().addItem(
                                    StingerNecklace.getItem(plugin)
                            );
                        }

                        case "fisher_of_souls" -> {
                            targetPlayer.getInventory().addItem(
                                    FisherOfSouls.getItem(plugin)
                            );
                        }

                        case "golden_fishing_rod" -> {
                            targetPlayer.getInventory().addItem(
                                    GoldenFishingRod.getItem(plugin)
                            );
                        }

                        case "mechanics_rod" -> {
                            targetPlayer.getInventory().addItem(
                                    MechanicsRod.getItem(plugin)
                            );
                        }

                        case "natures_gift" -> {
                            targetPlayer.getInventory().addItem(
                                    NaturesGift.getItem(plugin)
                            );
                        }

                        case "mana_flower" -> {
                            targetPlayer.getInventory().addItem(
                                    ManaFlower.getItem(plugin)
                            );
                        }

                        case "mana_cloak" -> {
                            targetPlayer.getInventory().addItem(
                                    ManaCloak.getItem(plugin)
                            );
                        }

                        case "chum_caster" -> {
                            targetPlayer.getInventory().addItem(
                                    ChumCaster.getItem(plugin)
                            );
                        }

                        case "scarab_fishing_rod" -> {
                            targetPlayer.getInventory().addItem(
                                    ScarabFishingRod.getItem(plugin)
                            );
                        }

                        case "fiberglass_fishing_pole" -> {
                            targetPlayer.getInventory().addItem(
                                    FiberglassFishingPole.getItem(plugin)
                            );
                        }

                        case "sitting_ducks_fishing_pole" -> {
                            targetPlayer.getInventory().addItem(
                                    SittingDucksFishingPole.getItem(plugin)
                            );
                        }

                        case "grenade" -> {
                            targetPlayer.getInventory().addItem(
                                    Grenade.getItem(plugin)
                            );
                        }

                        case "sticky_grenade" -> {
                            targetPlayer.getInventory().addItem(
                                    StickyGrenade.getItem(plugin)
                            );
                        }

                        case "bouncy_grenade" -> {
                            targetPlayer.getInventory().addItem(
                                    BouncyGrenade.getItem(plugin)
                            );
                        }

                        case "bomb" -> {
                            targetPlayer.getInventory().addItem(
                                    Bomb.getItem(plugin)
                            );
                        }

                        case "sticky_bomb" -> {
                            targetPlayer.getInventory().addItem(
                                    StickyBomb.getItem(plugin)
                            );
                        }

                        case "bouncy_bomb" -> {
                            targetPlayer.getInventory().addItem(
                                    BouncyBomb.getItem(plugin)
                            );
                        }

                        case "dynamite" -> {
                            targetPlayer.getInventory().addItem(
                                    Dynamite.getItem(plugin)
                            );
                        }

                        case "bouncy_dynamite" -> {
                            targetPlayer.getInventory().addItem(
                                    BouncyDynamite.getItem(plugin)
                            );
                        }

                        case "sticky_dynamite" -> {
                            targetPlayer.getInventory().addItem(
                                    StickyDynamite.getItem(plugin)
                            );
                        }

                        case "spiky_ball" -> {

                            ItemStack item =
                                    SpikyBall.getItem(plugin);

                            item.setAmount(11);

                            targetPlayer.getInventory().addItem(item);
                        }

                        case "cloud_in_a_balloon" -> {
                            targetPlayer.getInventory().addItem(
                                    CloudInABalloon.getItem(plugin)
                            );
                        }

                        case "sandstorm_in_a_balloon" -> {
                            targetPlayer.getInventory().addItem(
                                    SandstormInABalloon.getItem(plugin)
                            );
                        }

                        case "blizzard_in_a_balloon" -> {
                            targetPlayer.getInventory().addItem(
                                    BlizzardInABalloon.getItem(plugin)
                            );
                        }

                        case "amber_horseshoe_balloon" -> {
                            targetPlayer.getInventory().addItem(
                                    AmberHorseshoeBalloon.getItem(plugin)
                            );
                        }

                        case "blue_horseshoe_balloon" -> {
                            targetPlayer.getInventory().addItem(
                                    BlueHorseshoeBalloon.getItem(plugin)
                            );
                        }

                        case "white_horseshoe_balloon" -> {
                            targetPlayer.getInventory().addItem(
                                    WhiteHorseshoeBalloon.getItem(plugin)
                            );
                        }

                        case "yellow_horseshoe_balloon" -> {
                            targetPlayer.getInventory().addItem(
                                    YellowHorseshoeBalloon.getItem(plugin)
                            );
                        }

                        case "bundle_of_balloons" -> {
                            targetPlayer.getInventory().addItem(
                                    BundleOfBalloons.getItem(plugin)
                            );
                        }

                        case "bundle_of_horseshoe_balloons" -> {
                            targetPlayer.getInventory().addItem(
                                    BundleOfHorseshoeBalloons.getItem(plugin)
                            );
                        }

                        case "grappling_hook" -> {
                            targetPlayer.getInventory().addItem(
                                    GrapplingHook.getItem(plugin)
                            );
                        }

                        case "amethyst_hook" -> {
                            targetPlayer.getInventory().addItem(
                                    AmethystHook.getItem(plugin)
                            );
                        }

                        case "emerald_hook" -> {
                            targetPlayer.getInventory().addItem(
                                    EmeraldHook.getItem(plugin)
                            );
                        }

                        case "diamond_hook" -> {
                            targetPlayer.getInventory().addItem(
                                    DiamondHook.getItem(plugin)
                            );
                        }

                        case "ruby_hook" -> {
                            targetPlayer.getInventory().addItem(
                                    RubyHook.getItem(plugin)
                            );
                        }

                        case "wand_of_sparking" -> {
                            targetPlayer.getInventory().addItem(
                                    WandOfSparking.getItem(plugin)
                            );
                        }

                        case "step_stool" -> {
                            targetPlayer.getInventory().addItem(
                                    StepStool.getItem(plugin)
                            );
                        }

                        case "iron_francisca" -> {
                            targetPlayer.getInventory().addItem(
                                    IronFrancisca.getItem(plugin)
                            );
                        }

                        case "glaive" -> {
                            targetPlayer.getInventory().addItem(
                                    Glaive.getItem(plugin)
                            );
                        }

                        case "consecrated_water" -> {
                            targetPlayer.getInventory().addItem(
                                    ConsecratedWater.getItem(plugin)
                            );
                        }

                        case "desecrated_water" -> {
                            targetPlayer.getInventory().addItem(
                                    DesecratedWater.getItem(plugin)
                            );
                        }

                        case "coin_of_deceit" -> {
                            targetPlayer.getInventory().addItem(
                                    CoinOfDeceit.getItem(plugin)
                            );
                        }

                        case "exorcism" -> {
                            targetPlayer.getInventory().addItem(
                                    Exorcism.getItem(plugin)
                            );
                        }

                        case "unholy_core" -> {

                            ItemStack item =
                                    UnholyCore.getItem(plugin);

                            item.setAmount(2);

                            targetPlayer.getInventory().addItem(item);
                        }

                        case "ruin_medallion" -> {
                            targetPlayer.getInventory().addItem(
                                    RuinMedallion.getItem(plugin)
                            );
                        }

                        case "rogue_emblem" -> {
                            targetPlayer.getInventory().addItem(
                                    RougeEmblem.getItem(plugin)
                            );
                        }

                        case "silencing_sheath" -> {
                            targetPlayer.getInventory().addItem(
                                    SilencingSheath.getItem(plugin)
                            );
                        }

                        case "desert_prowler_armor" -> {

                            targetPlayer.getInventory().addItem(
                                    DesertProwlerHat.getItem(plugin)
                            );

                            targetPlayer.getInventory().addItem(
                                    DesertProwlerShirt.getItem(plugin)
                            );

                            targetPlayer.getInventory().addItem(
                                    DesertProwlerLeggings.getItem(plugin)
                            );

                            targetPlayer.getInventory().addItem(
                                    DesertProwlerPants.getItem(plugin)
                            );
                        }

                        case "life_drain" -> {
                            targetPlayer.getInventory().addItem(
                                    LifeDrain.getItem(plugin)
                            );
                        }

                        case "blazing_star" -> {
                            targetPlayer.getInventory().addItem(
                                    BlazingStar.getItem(plugin)
                            );
                        }

                        case "enchanted_axe" -> {
                            targetPlayer.getInventory().addItem(
                                    EnchantedAxe.getItem(plugin)
                            );
                        }

                        case "brimlash" -> {
                            targetPlayer.getInventory().addItem(
                                    Brimlash.getItem(plugin)
                            );
                        }

                        case "ironskin_potion" -> {
                            targetPlayer.getInventory().addItem(
                                    IronSkinPotion.getItem(plugin)
                            );
                        }

                        case "builder_potion" -> {
                            targetPlayer.getInventory().addItem(
                                    BuilderPotion.getItem(plugin)
                            );
                        }

                        case "titan_potion" -> {
                            targetPlayer.getInventory().addItem(
                                    TitanPotion.getItem(plugin)
                            );
                        }

                        case "mining_potion" -> {
                            targetPlayer.getInventory().addItem(
                                    MiningPotion.getItem(plugin)
                            );
                        }

                        case "endurance_potion" -> {
                            targetPlayer.getInventory().addItem(
                                    EndurancePotion.getItem(plugin)
                            );
                        }

                        case "wrath_potion" -> {
                            targetPlayer.getInventory().addItem(
                                    WrathPotion.getItem(plugin)
                            );
                        }

                        case "rage_potion" -> {
                            targetPlayer.getInventory().addItem(
                                    RagePotion.getItem(plugin)
                            );
                        }

                        case "magic_power_potion" -> {
                            targetPlayer.getInventory().addItem(
                                    MagicPowerPotion.getItem(plugin)
                            );
                        }

                        case "seafood_dinner" -> {
                            targetPlayer.getInventory().addItem(
                                    SeafoodDinner.getItem(plugin)
                            );
                        }

                        case "armored_cavefish" -> {
                            targetPlayer.getInventory().addItem(
                                    ArmoredCavefish.getItem(plugin)
                            );
                        }

                        case "hemopiranha" -> {
                            targetPlayer.getInventory().addItem(
                                    Hemopiranha.getItem(plugin)
                            );
                        }

                        case "ebonkoi" -> {
                            targetPlayer.getInventory().addItem(
                                    Ebonkoi.getItem(plugin)
                            );
                        }

                        case "brimstone_fury" -> {
                            targetPlayer.getInventory().addItem(
                                    BrimstoneFury.getItem(plugin)
                            );
                        }

                        case "crossed_heart_necklace" -> {
                            targetPlayer.getInventory().addItem(
                                    CrossedHeartNecklace.getItem(plugin)
                            );
                        }

                        case "golden_delight" -> {
                            targetPlayer.getInventory().addItem(
                                    GoldenDelight.getItem(plugin)
                            );
                        }

                        case "daybloom_staff" -> {
                            targetPlayer.getInventory().addItem(
                                    DaybloomStaff.getItem(plugin)
                            );
                        }

                        default -> {
                            player.sendMessage(
                                    "§cUnknown item: " + itemName
                            );

                            return true;
                        }
                    }

                    /*
                     * ====================================================
                     * LICENSE PROCESSING
                     * ====================================================
                     *
                     * Kalau command lama:
                     *
                     * /ti give minishark
                     *
                     * tidak melakukan license.
                     *
                     * Kalau command baru:
                     *
                     * /ti give Steve minishark 1 7d
                     *
                     * maka item baru akan diberi license 7 hari.
                     */

                    if (licensedGive) {

                        TimedItemManager timedItemManager =
                                TimedItemManager.getInstance();

                        if (timedItemManager == null) {

                            player.sendMessage(
                                    "§cTimedItemManager belum aktif."
                            );

                            return true;
                        }

                        long durationMillis = 0;

                        /*
                         * PERMANENT
                         */

                        if (!duration.equals("permanent")) {

                            long days = Long.parseLong(
                                    duration.substring(
                                            0,
                                            duration.length() - 1
                                    )
                            );

                            durationMillis =
                                    days
                                            * 24L
                                            * 60L
                                            * 60L
                                            * 1000L;
                        }

                        /*
                         * Cari item baru di inventory target.
                         */

                        for (int slot = 0;
                             slot < targetPlayer.getInventory().getSize();
                             slot++) {

                            ItemStack before =
                                    inventoryBefore[slot];

                            ItemStack after =
                                    targetPlayer.getInventory()
                                            .getItem(slot);

                            if (after == null
                                    || after.getType().isAir()) {
                                continue;
                            }

                            boolean changed;

                            if (before == null
                                    || before.getType().isAir()) {

                                changed = true;

                            } else {

                                changed =
                                        !before.isSimilar(after)
                                                || before.getAmount()
                                                != after.getAmount();
                            }

                            if (!changed) {
                                continue;
                            }

                            /*
                             * Kalau item normal hanya dibuat 1 buah,
                             * gunakan amount dari command.
                             *
                             * Contoh:
                             *
                             * /ti give Steve minishark 5 7d
                             *
                             * menjadi 5 Minishark.
                             */

                            if (after.getAmount() == 1
                                    && requestedAmount > 1) {

                                after.setAmount(requestedAmount);
                            }

                            /*
                             * APPLY LICENSE
                             */

                            if (duration.equals("permanent")) {

                                timedItemManager.applyPermanentLicense(
                                        after
                                );

                            } else {

                                timedItemManager.applyTimedLicense(
                                        after,
                                        durationMillis
                                );
                            }

                            targetPlayer.getInventory().setItem(
                                    slot,
                                    after
                            );
                        }

                        /*
                         * MESSAGE
                         */

                        if (duration.equals("permanent")) {

                            player.sendMessage(
                                    "§aBerhasil memberikan §e"
                                            + itemName
                                            + " §ake §e"
                                            + targetPlayer.getName()
                                            + " §adengan license §6PERMANENT§a."
                            );

                            if (!targetPlayer.equals(player)) {

                                targetPlayer.sendMessage(
                                        "§aKamu menerima §e"
                                                + itemName
                                                + " §adengan license §6PERMANENT§a."
                                );
                            }

                        } else {

                            player.sendMessage(
                                    "§aBerhasil memberikan §e"
                                            + itemName
                                            + " §ake §e"
                                            + targetPlayer.getName()
                                            + " §aselama §e"
                                            + duration
                                            + "§a."
                            );

                            if (!targetPlayer.equals(player)) {

                                targetPlayer.sendMessage(
                                        "§aKamu menerima §e"
                                                + itemName
                                                + " §aselama §e"
                                                + duration
                                                + "§a."
                                );
                            }
                        }

                    } else {

                        /*
                         * MODE LAMA
                         */

                        if (!targetPlayer.equals(player)) {

                            player.sendMessage(
                                    "§aBerhasil memberikan §e"
                                            + itemName
                                            + " §ake §e"
                                            + targetPlayer.getName()
                                            + "§a."
                            );
                        }
                    }
                }

                case "toggle_message" -> {
                    playerInstance.toggleMsg(
                            player.getUniqueId()
                    );

                    playerInstance.save();
                }

                case "toggle_potion_list" -> {

                    playerInstance.toggleSidebar(
                            player.getUniqueId()
                    );

                    playerInstance.save();

                    if (playerInstance.getShowSidebar(
                            player.getUniqueId()
                    )) {

                        customPotionHandler.updateSidebar(
                                player
                        );

                    } else {

                        customPotionHandler.removeSidebar(
                                player
                        );
                    }
                }

                case "accessory" -> {
                    accessoryManagerInstance.openMenu(player);
                }

                case "vanity" -> {
                    vanityManagerInstance.openVanity(player);
                }

                case "reset_bonuses" -> {
                    resetInstance.resetBonuses(player);
                }

                case "show_stats" -> {
                    playerInstance.showStats(player);
                }

                case "undiscover" -> {
                    CustomRecipeDiscoverManager
                            .getInstance()
                            .undiscoverAll(player);
                }
            }

            return true;
        }

        return true;
    }

    /*
     * ================================================================
     * TAB COMPLETE
     * ================================================================
     */

    @Override
    public List<String> onTabComplete(
            CommandSender sender,
            Command command,
            String alias,
            String[] args
    ) {

        List<String> completions = new ArrayList<>();

        /*
         * ============================================================
         * SUBCOMMAND
         * ============================================================
         */

        if (args.length == 1) {

            List<String> subCommands =
                    new ArrayList<>();

            subCommands.add("toggle_message");
            subCommands.add("toggle_potion_list");
            subCommands.add("accessory");
            subCommands.add("vanity");
            subCommands.add("show_stats");
            subCommands.add("reset_bonuses");
            subCommands.add("undiscover");

            if (sender.isOp()) {

                subCommands.add("give");
                subCommands.add("toggle_prehardmode");
                subCommands.add("toggle_hardmode");
                subCommands.add("toggle_blood_moon");
                subCommands.add("summon_blood_moon");
            }

            StringUtil.copyPartialMatches(
                    args[0],
                    subCommands,
                    completions
            );

        } else if (
                args[0].equalsIgnoreCase("give")
        ) {

            /*
             * ========================================================
             * ITEM LIST
             * ========================================================
             */

            List<String> items = Arrays.asList(
                    "cosmolight",
                    "warrior_emblem",
                    "daybloom_staff",
                    "golden_delight",
                    "crossed_heart_necklace",
                    "brimstone_fury",
                    "seafood_dinner",
                    "ebonkoi",
                    "hemopiranha",
                    "armored_cavefish",
                    "rage_potion",
                    "magic_power_potion",
                    "wrath_potion",
                    "endurance_potion",
                    "mining_potion",
                    "titan_potion",
                    "builder_potion",
                    "ironskin_potion",
                    "brimlash",
                    "blazing_star",
                    "enchanted_axe",
                    "life_drain",
                    "desert_prowler_armor",
                    "silencing_sheath",
                    "rogue_emblem",
                    "ruin_medallion",
                    "unholy_core",
                    "exorcism",
                    "desecrated_water",
                    "coin_of_deceit",
                    "consecrated_water",
                    "glaive",
                    "iron_francisca",
                    "diamond_hook",
                    "step_stool",
                    "wand_of_sparking",
                    "ruby_hook",
                    "emerald_hook",
                    "grappling_hook",
                    "amethyst_hook",
                    "bundle_of_horseshoe_balloons",
                    "bundle_of_balloons",
                    "yellow_horseshoe_balloon",
                    "bouncy_grenade",
                    "white_horseshoe_balloon",
                    "amber_horseshoe_balloon",
                    "blue_horseshoe_balloon",
                    "sandstorm_in_a_balloon",
                    "blizzard_in_a_balloon",
                    "cloud_in_a_balloon",
                    "spiky_ball",
                    "sticky_dynamite",
                    "bouncy_dynamite",
                    "dynamite",
                    "bomb",
                    "sticky_bomb",
                    "bouncy_bomb",
                    "grenade",
                    "sticky_grenade",
                    "sitting_ducks_fishing_pole",
                    "fiberglass_fishing_pole",
                    "mana_flower",
                    "scarab_fishing_rod",
                    "chum_caster",
                    "mana_cloak",
                    "mechanics_rod",
                    "natures_gift",
                    "fisher_of_souls",
                    "golden_fishing_rod",
                    "stinger_necklace",
                    "ice_sickle",
                    "shark_tooth_necklace",
                    "blood_rain_bow",
                    "bloody_tear",
                    "star_veil",
                    "life_crystal",
                    "star_cloak",
                    "magic_dagger",
                    "cross_necklace",
                    "titan_glove",
                    "mechanical_glove",
                    "obsidian_shield",
                    "power_glove",
                    "ankh_shield",
                    "ankh_charm",
                    "honey_comb",
                    "sweetheart_necklace",
                    "honey_balloon",
                    "magic_cuffs",
                    "band_of_starpower",
                    "panic_necklace",
                    "mana_potion",
                    "feral_claws",
                    "greater_mana_potion",
                    "super_mana_potion",
                    "laser_rifle",
                    "lesser_mana_potion",
                    "clockwork_assault_rifle",
                    "slap_hand",
                    "wizard_hat",
                    "breaker_blade",
                    "frost_elytra",
                    "caustic_edge",
                    "tainted_blade",
                    "forbidden_elytra",
                    "vampire_knives",
                    "magic_quiver",
                    "mechanical_skull",
                    "frost_armor",
                    "frost_core",
                    "forbidden_fragment",
                    "forbidden_armor",
                    "necro_armor",
                    "jungle_armor",
                    "sand_gun",
                    "night_vision_helmet",
                    "mechanical_egg",
                    "bone_pickaxe",
                    "pulse_bow",
                    "golden_crown",
                    "mechanical_shrieker",
                    "souls",
                    "hoarfrost_bow",
                    "onyx_blaster",
                    "enchanted_sword",
                    "crystal_storm",
                    "magical_harp",
                    "sandstorm_in_a_bottle",
                    "thunder_zapper",
                    "blizzard_in_a_bottle",
                    "anklet_of_the_wind",
                    "tsunami_in_a_bottle",
                    "wooden_crate",
                    "falcon_blade",
                    "iron_crate",
                    "golden_crate",
                    "oasis_crate",
                    "sky_crate",
                    "ocean_crate",
                    "jungle_crate",
                    "frozen_crate",
                    "sorcerer_emblem",
                    "super_star_shooter",
                    "star_cannon",
                    "fallen_star",
                    "cactus_armor",
                    "terra_blade",
                    "icicle_staff",
                    "bubble_gun",
                    "ancient_fossil",
                    "neptunes_shell",
                    "water_bolt",
                    "mana_crystal",
                    "meteor_staff",
                    "christmastreesword",
                    "ruby_staff",
                    "amethyst_staff",
                    "torrential_tear",
                    "phoenix_blaster",
                    "sniper_rifle",
                    "mega_shark",
                    "needler",
                    "minishark",
                    "shotgun",
                    "handgun",
                    "ice_blade",
                    "blowpipe",
                    "blade_of_grass",
                    "avenger_emblem",
                    "hallowed_elytra",
                    "pickaxe_axe",
                    "hallowed_armour",
                    "hallowed_repeater",
                    "excalibur",
                    "snowball_cannon",
                    "might",
                    "shackle",
                    "molten_elytra",
                    "ranger_emblem",
                    "shadow_elytra",
                    "blindfold",
                    "vitamins",
                    "fast_clock",
                    "rod_of_discord",
                    "bezoar",
                    "hellforge",
                    "molten_fury",
                    "volcano",
                    "counter_scarf",
                    "molten_armour",
                    "lights_bane",
                    "shadow_armour",
                    "momentum_capacitor",
                    "stormbow",
                    "demonite_bar",
                    "cloud_bottle",
                    "aglet",
                    "obsidian_skull",
                    "red_balloon",
                    "band_of_regeneration",
                    "lucky_horseshoe",
                    "magic_mirror",
                    "cobalt_shield"
            );

            /*
             * ========================================================
             * /ti give <item>
             *
             * ATAU
             *
             * /ti give <player> <item> <amount> <duration>
             * ========================================================
             */

            if (args.length == 2) {

                /*
                 * Tampilkan player + item.
                 *
                 * Jadi format lama tetap bisa:
                 *
                 * /ti give minishark
                 *
                 * dan format baru:
                 *
                 * /ti give Steve
                 */

                List<String> suggestions =
                        new ArrayList<>();

                for (Player onlinePlayer :
                        Bukkit.getOnlinePlayers()) {

                    suggestions.add(
                            onlinePlayer.getName()
                    );
                }

                suggestions.addAll(items);

                StringUtil.copyPartialMatches(
                        args[1],
                        suggestions,
                        completions
                );

            } else if (args.length == 3) {

                /*
                 * ARGUMENT 3 = ITEM
                 *
                 * /ti give Steve <item>
                 */

                StringUtil.copyPartialMatches(
                        args[2],
                        items,
                        completions
                );

            } else if (args.length == 4) {

                /*
                 * ARGUMENT 4 = AMOUNT
                 */

                List<String> amounts = Arrays.asList(
                        "1",
                        "2",
                        "5",
                        "10",
                        "16",
                        "32",
                        "64"
                );

                StringUtil.copyPartialMatches(
                        args[3],
                        amounts,
                        completions
                );

            } else if (args.length == 5) {

                /*
                 * ARGUMENT 5 = DURATION
                 */

                List<String> durations = Arrays.asList(
                        "1d",
                        "3d",
                        "7d",
                        "14d",
                        "30d",
                        "60d",
                        "90d",
                        "365d",
                        "permanent"
                );

                StringUtil.copyPartialMatches(
                        args[4],
                        durations,
                        completions
                );
            }
        }

        return completions;
    }
}