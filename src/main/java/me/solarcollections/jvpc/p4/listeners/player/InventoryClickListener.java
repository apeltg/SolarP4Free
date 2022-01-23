package me.solarcollections.jvpc.p4.listeners.player;

import me.solarcollections.jvpc.game.GameState;
import me.solarcollections.jvpc.p4.command.p4.BuildCommand;
import me.solarcollections.jvpc.p4.game.GameArena;
import me.solarcollections.jvpc.player.Profile;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {
  
  @EventHandler(priority = EventPriority.LOWEST)
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getWhoClicked() instanceof Player) {
      Player player = (Player) evt.getWhoClicked();
      Profile profile = Profile.getProfile(player.getName());
      if (GameArena.playernaarena.contains(player)) {
        return;
      }
      if (profile != null) {
          evt.setCancelled(!BuildCommand.hasBuilder(player));
      }
    }
  }
}
