package me.carson.terrariaItems.weaponsFolder;

import me.carson.terrariaItems.weaponsFolder.weapons.bowFolder.bows.*;
import me.carson.terrariaItems.weaponsFolder.weapons.gunFolder.guns.*;
import me.carson.terrariaItems.weaponsFolder.weapons.magicFolder.magicWeapons.*;
import me.carson.terrariaItems.weaponsFolder.weapons.meleeFolder.melee.*;
import me.carson.terrariaItems.weaponsFolder.weapons.rougeFolder.rouge.*;
import me.carson.terrariaItems.weaponsFolder.weapons.throwableFolder.throwablesFolder.*;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.UUID;

public class WeaponManager implements Listener {
    private final HashMap<String, Weapon> weaponList = new HashMap<>();
    private final NamespacedKey weaponKey;;
    private final HashMap<UUID, Long> lastClickTime = new HashMap<>();

    public WeaponManager(Plugin plugin) {
        weaponKey = new NamespacedKey(plugin, "custom_item_id");

        weaponList.put("MoltenFury",new MoltenFury(plugin));
        weaponList.put("LightsBane",new LightsBane(plugin));
        weaponList.put("Volcano",new Volcano(plugin));
        weaponList.put("SnowballCannon",new SnowballCannon(plugin));
        weaponList.put("Excalibur",new Excalibur(plugin));
        weaponList.put("HallowedRepeater",new HallowedRepeater(plugin));
        weaponList.put("BladeOfGrass",new BladeOfGrass(plugin));
        weaponList.put("IceBlade",new IceBlade(plugin));
        weaponList.put("Blowpipe",new Blowpipe(plugin));
        weaponList.put("Minishark",new Minishark(plugin));
        weaponList.put("Handgun",new Handgun(plugin));
        weaponList.put("Shotgun",new Shotgun(plugin));
        weaponList.put("Needler",new Needler(plugin));
        weaponList.put("ChristmasTreeSword",new ChristmasTreeSword(plugin));
        weaponList.put("Megashark",new Megashark(plugin));
        weaponList.put("PhoenixBlaster",new PhoenixBlaster(plugin));
        weaponList.put("SniperRifle",new SniperRifle(plugin));
        weaponList.put("AmethystStaff",new AmethystStaff(plugin));
        weaponList.put("RubyStaff",new RubyStaff(plugin));
        weaponList.put("MeteorStaff",new MeteorStaff(plugin));
        weaponList.put("BubbleGun",new BubbleGun(plugin));
        weaponList.put("WaterBolt",new WaterBolt(plugin));
        weaponList.put("IcicleStaff",new IcicleStaff(plugin));
        weaponList.put("StarCannon",new StarCannon(plugin));
        weaponList.put("SuperStarShooter",new SuperStarShooter(plugin));
        weaponList.put("DaedalusStormbow",new DaedalusStormbow(plugin));
        weaponList.put("EnchantedSword",new EnchantedSword(plugin));
        weaponList.put("FalconBlade",new FalconBlade(plugin));
        weaponList.put("Boomstick",new Boomstick(plugin));
        weaponList.put("Starfury",new Starfury(plugin));
        weaponList.put("ThunderZapper",new ThunderZapper(plugin));
        weaponList.put("MagicalHarp",new MagicalHarp(plugin));
        weaponList.put("CrystalStorm",new CrystalStorm(plugin));
        weaponList.put("OnyxBlaster",new OnyxBlaster(plugin));
        weaponList.put("HoarfrostBow",new HoarfrostBow(plugin));
        weaponList.put("PulseBow",new PulseBow(plugin));
        weaponList.put("SandGun",new SandGun(plugin));
        weaponList.put("VampireKnives",new VampireKnives(plugin));
        weaponList.put("TaintedBlade",new TaintedBlade(plugin));
        weaponList.put("CausticEdge",new CausticEdge(plugin));
        weaponList.put("IceSickle",new IceSickle(plugin));
        weaponList.put("BreakerBlade",new BreakerBlade(plugin));
        weaponList.put("LaserRifle",new LaserRifle(plugin));
        weaponList.put("ClockworkAssaultRifle",new ClockworkAssaultRifle(plugin));
        weaponList.put("MagicDagger",new MagicDagger(plugin));
        weaponList.put("BloodRainBow",new BloodRainBow(plugin));
        weaponList.put("ThrowingKnife",new ThrowingKnife(plugin));
        weaponList.put("PoisonedKnife",new PoisonedKnife(plugin));
        weaponList.put("BoneThrowingKnife",new BoneThrowingKnife(plugin));
        weaponList.put("Shuriken",new Shuriken(plugin));
        weaponList.put("Grenade",new Grenade(plugin));
        weaponList.put("StickyGrenade",new StickyGrenade(plugin));
        weaponList.put("BouncyGrenade",new BouncyGrenade(plugin));
        weaponList.put("Bomb",new Bomb(plugin));
        weaponList.put("StickyBomb",new StickyBomb(plugin));
        weaponList.put("BouncyBomb",new BouncyBomb(plugin));
        weaponList.put("Dynamite",new Dynamite(plugin));
        weaponList.put("StickyDynamite",new StickyDynamite(plugin));
        weaponList.put("BouncyDynamite",new BouncyDynamite(plugin));
        weaponList.put("SpikyBall",new SpikyBall(plugin));
        weaponList.put("WandOfSparking",new WandOfSparking(plugin));
        weaponList.put("IronFrancisca",new IronFrancisca(plugin));
        weaponList.put("Glaive",new Glaive(plugin));
        weaponList.put("ConsecratedWater",new ConsecratedWater(plugin));
        weaponList.put("DesecratedWater",new DesecratedWater(plugin));
        weaponList.put("Exorcism",new Exorcism(plugin));
        weaponList.put("LifeDrain",new LifeDrain(plugin));
        weaponList.put("BlazingStarProjectile",new BlazingStar(plugin));
        weaponList.put("EnchantedAxe",new EnchantedAxe(plugin));
        weaponList.put("Brimlash",new Brimlash(plugin));
        weaponList.put("BrimstoneFury",new BrimstoneFury(plugin));
        weaponList.put("DaybloomStaff",new DaybloomStaff(plugin));

        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onLeftClick(PlayerInteractEvent event) {
        if (!(event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK)) return;

        Player player = event.getPlayer();

        ItemStack heldItem= event.getItem();
        if(heldItem==null){return;}

        Weapon weapon= getWeapon(heldItem);
        if(weapon==null){return;}

        long currentTime = System.currentTimeMillis();
        long lastTime = lastClickTime.getOrDefault(player.getUniqueId(), 0L);
        if (currentTime - lastTime < 10) {
            return;
        }
        lastClickTime.put(player.getUniqueId(), currentTime);

        if(!player.hasCooldown(heldItem)){
    weapon.leftActivate(player);
    damageWeapon(heldItem);
    player.setCooldown(heldItem, weapon.cooldown);
}

event.setCancelled(true);
}

        @EventHandler
    public void onRightClick(PlayerInteractEvent event) {
        if (!(event.getAction() == Action.RIGHT_CLICK_AIR
                || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            return;
        }

        if (event.getClickedBlock() != null) {
            if (event.getClickedBlock().getType().isInteractable()) {
                return;
            }
        }

        Player player = event.getPlayer();
        ItemStack heldItem = event.getItem();

        if (heldItem == null) {
            return;
        }

        Weapon weapon = getWeapon(heldItem);

        if (weapon == null) {
            return;
        }

        long currentTime = System.currentTimeMillis();
        long lastTime = lastClickTime.getOrDefault(player.getUniqueId(), 0L);

        if (currentTime - lastTime < 10) {
            return;
        }

        lastClickTime.put(player.getUniqueId(), currentTime);

        if (!player.hasCooldown(heldItem)) {
            weapon.rightActivate(player);
            damageWeapon(heldItem);
            player.setCooldown(heldItem, weapon.cooldown);
        }

        event.setCancelled(true);
    }

    private void damageWeapon(ItemStack item) {
        if (item == null || !item.hasItemMeta()) {
            return;
        }

        if (!(item.getItemMeta() instanceof Damageable damageable)) {
            return;
        }

        int maxDurability = item.getType().getMaxDurability();

        if (maxDurability <= 0) {
            return;
        }

        int newDamage = damageable.getDamage() + 1;

        if (newDamage >= maxDurability) {
            item.setAmount(0);
            return;
        }

        damageable.setDamage(newDamage);
        item.setItemMeta(damageable);
    }
    public Weapon getWeapon(ItemStack item){
        if(item==null|| !item.hasItemMeta()){return null;}
        String weaponId= item.getItemMeta().getPersistentDataContainer().get(weaponKey, PersistentDataType.STRING);
        return weaponList.get(weaponId);
    }



    /*
    @EventHandler
    public void onDamage(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Player player)){return;}
        player.sendMessage(""+event.getDamage());
    }


    /*
    @EventHandler
    public void onRangedDamage(EntityDamageByEntityEvent event){
        if(!(event.getDamager() instanceof Projectile projectile)){return;}
        ProjectileSource source = projectile.getShooter();
        if(source instanceof Player player){
            player.sendMessage(""+event.getDamage());
        }
    }
    */

}
