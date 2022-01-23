package me.solarcollections.jvpc.p4.listeners.entity;

import me.solarcollections.jvpc.p4.Language;
import me.solarcollections.jvpc.p4.Main;
import me.solarcollections.jvpc.p4.game.GameArena;
import me.solarcollections.jvpc.player.Profile;
import me.solarcollections.jvpc.player.hotbar.Hotbar;
import me.solarcollections.jvpc.player.role.Role;
import org.bukkit.Bukkit;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;


public class EntityListener implements Listener {

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onEntityDamageByEntity(EntityDamageByEntityEvent evt) {
    if (evt.getEntity() instanceof Player) {
      if (GameArena.playernaarena.contains((Player) evt.getEntity())) {
        if (evt.getDamager() instanceof LightningStrike) {
          evt.setCancelled(true);
        }
        return;
      }
    }
    if (evt.isCancelled()) {
      return;
    }
    if (evt.getDamager() instanceof LightningStrike) {
      evt.setCancelled(true);
    }
    if (evt.getEntity() instanceof Player) {
      Player player = (Player) evt.getEntity();

      Profile profile = Profile.getProfile(player.getName());
      if (profile == null) {
        evt.setCancelled(true);
      }
    }
  }

  @EventHandler(priority = EventPriority.LOWEST)
  public void onEntityDamage(EntityDamageEvent evt) {
    if (evt.getEntity() instanceof Player) {
      if (GameArena.playernaarena.contains((Player) evt.getEntity())) {
        GameArena.cooldawn.add(((Player) evt.getEntity()).getPlayer());
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new BukkitRunnable() {
          @Override
          public void run() {
            GameArena.cooldawn.remove(((Player) evt.getEntity()).getPlayer());
          }
        }, 60*2L);
        return;
      }
    }
    if (evt.getEntity() instanceof Player) {
      Player player = (Player) evt.getEntity();

      Profile profile = Profile.getProfile(player.getName());
      if (profile != null) {
        evt.setCancelled(true);
      }
    }
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void onCreatureSpawn(CreatureSpawnEvent evt) {
    evt.setCancelled(evt.getSpawnReason() != SpawnReason.CUSTOM);
  }

  @EventHandler
  public void onFoodLevelChange(FoodLevelChangeEvent evt) {
    evt.setCancelled(true);
  }

  @EventHandler(priority = EventPriority.MONITOR)
  public void onFoodLevelChangeMonitor(FoodLevelChangeEvent evt) {
    if (!evt.isCancelled() && evt.getEntity() instanceof Player) {
      ((Player) evt.getEntity()).setSaturation(5.0f);
    }
  }
}