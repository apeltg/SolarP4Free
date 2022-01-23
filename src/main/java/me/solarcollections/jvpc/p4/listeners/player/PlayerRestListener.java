package me.solarcollections.jvpc.p4.listeners.player;

import me.solarcollections.jvpc.p4.command.p4.BuildCommand;
import me.solarcollections.jvpc.p4.game.GameArena;
import me.solarcollections.jvpc.player.Profile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerRestListener implements Listener {
  
  @EventHandler
  public void onPlayerDropItem(PlayerDropItemEvent evt) {
    if (GameArena.playernaarena.contains(evt.getPlayer())) {
      return;
    }
    Profile profile = Profile.getProfile(evt.getPlayer().getName());
    if (profile != null) {
      evt.setCancelled(!BuildCommand.hasBuilder(evt.getPlayer()));
    }
  }
  
  @EventHandler
  public void onPlayerPickupItem(PlayerPickupItemEvent evt) {
    if (GameArena.playernaarena.contains(evt.getPlayer())) {
      return;
    }
    Profile profile = Profile.getProfile(evt.getPlayer().getName());
    if (profile != null) {
      evt.setCancelled(!BuildCommand.hasBuilder(evt.getPlayer()));
    }
  }
  
  @EventHandler
  public void onBlockBreak(BlockBreakEvent evt) {
    Profile profile = Profile.getProfile(evt.getPlayer().getName());
    if (profile != null) {
      evt.setCancelled(!BuildCommand.hasBuilder(evt.getPlayer()));
    }
  }
  
  @EventHandler
  public void onBlockPlace(BlockPlaceEvent evt) {
    Profile profile = Profile.getProfile(evt.getPlayer().getName());
    if (profile != null) {
      evt.setCancelled(!BuildCommand.hasBuilder(evt.getPlayer()));
    }
  }
}
