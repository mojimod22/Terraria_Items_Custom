package me.carson.terrariaItems.enemyProjectilesFolder;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.damage.DamageSource;
import org.bukkit.damage.DamageType;
import org.bukkit.entity.*;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

public abstract class EnemyProjectile implements Listener {

    protected final Plugin plugin;
    protected final int damage;
    protected final float projSpeed;
    protected final String texture;
    protected final String id;
    protected final int peirce;
    protected final int bounces;
    protected final DamageType damageType;
    protected final Particle.DustOptions particle;

    public EnemyProjectile(
            Plugin plugin,
            int damage,
            float projSpeed,
            String texture,
            String id,
            int peirce,
            int bounces,
            DamageType damageType,
            Particle.DustOptions particle
    ) {
        this.plugin = plugin;
        this.damage = damage;
        this.projSpeed = projSpeed;
        this.texture = texture;
        this.id = id;
        this.peirce = peirce;
        this.bounces = bounces;
        this.damageType = damageType;
        this.particle = particle;
    }

    public void createBossProjectile(
            LivingEntity shooter,
            Player target,
            float speed,
            float weaponDamage,
            float spread,
            float duration
    ) {

        Location loc = shooter.getEyeLocation();
        Location targetLoc = target.getEyeLocation();

        loc.add(
                loc.getDirection()
                        .normalize()
                        .multiply(0.1)
        );

        Vector dir = targetLoc.toVector()
                .subtract(loc.toVector())
                .normalize();

        dir.add(new Vector(
                (Math.random() - 0.5) * spread,
                (Math.random() - 0.5) * spread,
                (Math.random() - 0.5) * spread
        ));

        dir.normalize().multiply(speed + projSpeed);

        loc.setDirection(dir);

        ItemDisplay proj = (ItemDisplay) shooter.getWorld()
                .spawnEntity(
                        loc,
                        EntityType.ITEM_DISPLAY
                );

        ItemStack item = new ItemStack(
                Material.IRON_NUGGET
        );

        ItemMeta meta = item.getItemMeta();

        meta.setItemModel(
                new NamespacedKey(
                        "terraria",
                        texture
                )
        );

        item.setItemMeta(meta);

        proj.setItemStack(item);

        NamespacedKey key = new NamespacedKey(
                plugin,
                id
        );

        proj.getPersistentDataContainer().set(
                key,
                PersistentDataType.INTEGER,
                1
        );

        proj.setInterpolationDuration(3);
        proj.setTeleportDuration(1);

        faceDirection(proj, dir);

        moveProj(
                shooter,
                weaponDamage,
                duration,
                proj,
                dir
        );
    }

    private void moveProj(
            LivingEntity shooter,
            float weaponDamage,
            float duration,
            ItemDisplay proj,
            Vector dir
    ) {

        final int[] tick = {0};
        final int[] enemiesHit = {0};
        final int[] blocksBounced = {0};
        final Vector[] direction = {dir};

        Bukkit.getScheduler().runTaskTimer(
                plugin,
                task -> {

                    if (proj.isDead()) {
                        task.cancel();
                        return;
                    }

                    tick[0]++;

                    if (tick[0] >= duration) {
                        proj.remove();
                        task.cancel();
                        return;
                    }

                    // =========================
                    // BLOCK HANDLING
                    // =========================

                    Location now = proj.getLocation();

                    Location next = now.clone()
                            .add(direction[0]);

                    float dist =
                            (float) now.distance(next);

                    RayTraceResult result =
                            shooter.getWorld().rayTrace(
                                    now,
                                    now.getDirection(),
                                    dist,
                                    FluidCollisionMode.NEVER,
                                    true,
                                    0.1,
                                    e ->
                                            (e.getType() != proj.getType())
                                                    && (e != shooter)
                            );

                    if (result != null) {

                        if (result.getHitBlock() != null) {

                            if (!result.getHitBlock().isPassable()
                                    && result.getHitBlockFace() != null) {

                                hitBlockEffect(
                                        result.getHitBlock()
                                );

                                if (blocksBounced[0] >= bounces) {

                                    proj.remove();
                                    task.cancel();
                                    return;

                                } else {

                                    blocksBounced[0]++;

                                    direction[0] = bounce(
                                            direction[0],
                                            result.getHitBlockFace()
                                    );

                                    next = now.clone()
                                            .add(direction[0]);
                                }
                            }
                        }

                        // =========================
                        // ENTITY HANDLING
                        // =========================

                        if (result.getHitEntity() != null) {

                            if (result.getHitEntity()
                                    instanceof LivingEntity target) {

                                int temp =
                                        target.getMaximumNoDamageTicks();

                                target.setMaximumNoDamageTicks(0);

                                DamageSource source =
                                        DamageSource
                                                .builder(damageType)
                                                .withCausingEntity(shooter)
                                                .withDirectEntity(shooter)
                                                .build();

                                // =================================
                                // TERRARIA PROJECTILE DAMAGE ×3
                                // =================================

                                double finalDamage =
                                        (damage + weaponDamage) * 3.0;

                                target.damage(
                                        finalDamage,
                                        source
                                );

                                hitEntityEffect(target);

                                target.setMaximumNoDamageTicks(
                                        temp
                                );
                            }

                            if (enemiesHit[0] >= peirce) {

                                proj.remove();
                                task.cancel();
                                return;

                            } else {

                                enemiesHit[0]++;
                            }
                        }
                    }

                    // =========================
                    // PROJECTILE ROTATION
                    // =========================

                    Vector norm =
                            direction[0]
                                    .clone()
                                    .normalize();

                    float yaw =
                            (float) Math.toDegrees(
                                    Math.atan2(
                                            -norm.getX(),
                                            norm.getZ()
                                    )
                            );

                    float pitch =
                            (float) Math.toDegrees(
                                    Math.asin(
                                            -norm.getY()
                                    )
                            );

                    next.setYaw(yaw);
                    next.setPitch(pitch);

                    proj.teleport(next);

                    // =========================
                    // PARTICLE
                    // =========================

                    if (particle != null
                            && tick[0] > 2) {

                        proj.getWorld().spawnParticle(
                                Particle.DUST,
                                now,
                                1,
                                0,
                                0,
                                0,
                                0,
                                particle
                        );
                    }

                },
                1L,
                1L
        );
    }

    private Vector bounce(
            Vector currentDir,
            BlockFace face
    ) {

        Vector v = currentDir.clone();

        switch (face) {

            case EAST, WEST ->
                    v.setX(-v.getX());

            case UP, DOWN ->
                    v.setY(-v.getY());

            case NORTH, SOUTH ->
                    v.setZ(-v.getZ());
        }

        return v;
    }

    private void faceDirection(
            ItemDisplay proj,
            Vector dir
    ) {

        Vector norm =
                dir.clone().normalize();

        float yaw =
                (float) Math.toDegrees(
                        Math.atan2(
                                -norm.getX(),
                                norm.getZ()
                        )
                );

        float pitch =
                (float) Math.toDegrees(
                        Math.asin(
                                -norm.getY()
                        )
                );

        Location loc =
                proj.getLocation();

        loc.setYaw(yaw);
        loc.setPitch(pitch);

        proj.teleport(loc);
    }

    public abstract void hitEntityEffect(
            LivingEntity entity
    );

    public abstract void hitBlockEffect(
            Block block
    );
}