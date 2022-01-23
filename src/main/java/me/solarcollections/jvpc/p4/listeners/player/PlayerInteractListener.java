package me.solarcollections.jvpc.p4.listeners.player;

import me.solarcollections.jvpc.Core;
import me.solarcollections.jvpc.libraries.npclib.api.event.NPCLeftClickEvent;
import me.solarcollections.jvpc.libraries.npclib.api.event.NPCRightClickEvent;
import me.solarcollections.jvpc.libraries.npclib.api.npc.NPC;
import me.solarcollections.jvpc.menus.MenuDeliveries;
import me.solarcollections.jvpc.p4.command.p4.BuildCommand;
import me.solarcollections.jvpc.p4.game.GameArena;
import me.solarcollections.jvpc.p4.menus.MenuStatsNPC;
import me.solarcollections.jvpc.player.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerInteractListener implements Listener {
  
  @EventHandler
  public void onNPCLeftClick(NPCLeftClickEvent evt) {
    Player player = evt.getPlayer();
  }
  
  @EventHandler
  public void onNPCRightClick(NPCRightClickEvent evt) {
    Player player = evt.getPlayer();
    Profile profile = Profile.getProfile(player.getName());
    
    if (profile != null) {
      if (GameArena.playernaarena.contains(player)) {
        return;
      }
      NPC npc = evt.getNPC();
      if (npc.data().has("delivery-npc")) {
        new MenuDeliveries(profile);
      } else if (npc.data().has("stats-npc")) {
        new MenuStatsNPC(profile);
      }
    }
  }
  
  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerInteract(PlayerInteractEvent evt) {
    Player player = evt.getPlayer();
    Profile profile = Profile.getProfile(player.getName());
    if (GameArena.playernaarena.contains(player)) {
      return;
    }
    if (profile != null) {
      if (!BuildCommand.hasBuilder(player)) {
        evt.setCancelled(true);
      }
    }
  }
  
  @EventHandler
  public void onPlayerMove(PlayerMoveEvent evt) {
    if (evt.getTo().getBlockY() != evt.getFrom().getBlockY() && evt.getTo().getBlockY() < 0) {
      Player player = evt.getPlayer();
      Profile profile = Profile.getProfile(player.getName());
      
      if (profile != null) {
        player.teleport(Core.getLobby());
      }
    }
  }
}
