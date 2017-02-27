/*
 * Author: Rio
 * Date: 2017/02/26
 */

package net.rio;

import org.bukkit.entity.*;
import org.bukkit.event.*;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class MinecartShooter extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getLogger().info("Resgistering events");

        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
    }

    @EventHandler
    public void onEntitySpawned(EntityShootBowEvent eve) {
        Entity arrow = eve.getProjectile();
        Entity shooter = eve.getEntity();

        if(arrow.getType() == EntityType.ARROW &&
                shooter.getType() == EntityType.PLAYER) {
            Minecart minecart = (Minecart) shooter.getWorld().spawnEntity(
                    arrow.getLocation(), EntityType.MINECART);

            if(minecart != null) {
                Vector mod = new Vector(.9, .9, .9);

                minecart.setMaxSpeed(10);
                minecart.setDerailedVelocityMod(mod);
                minecart.setFlyingVelocityMod(mod);
                minecart.setVelocity(arrow.getVelocity());

                getServer().getScheduler().runTaskLater(this, () -> {
                    minecart.getWorld().createExplosion(minecart.getLocation(), 10);
                }, 50);
            }
            arrow.remove();
        }
    }

}
