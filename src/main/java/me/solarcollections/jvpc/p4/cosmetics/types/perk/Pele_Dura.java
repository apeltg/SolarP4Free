package me.solarcollections.jvpc.p4.cosmetics.types.perk;

import me.solarcollections.jvpc.p4.API.P4Event;
import me.solarcollections.jvpc.p4.Main;
import me.solarcollections.jvpc.p4.cosmetics.types.Perk;
import me.solarcollections.jvpc.p4.game.GameArena;
import me.solarcollections.jvpc.player.Profile;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Pele_Dura extends Perk {
  
  private final int index;
  
  public Pele_Dura(int index, String key) {
    super(1, key, CONFIG.getString(key + ".permission"), CONFIG.getString(key + ".name"), CONFIG.getString(key + ".icon"), new ArrayList<>());
    this.index = index;
    this.setupLevels(key);
    Bukkit.getPluginManager().registerEvents(new Listener() {
      @EventHandler(priority = EventPriority.MONITOR)
      public void onEntityDamageByEntity(EntityDamageByEntityEvent evt) {
        if (evt.getDamager() instanceof Player) {
          Profile profile = Profile.getProfile(evt.getDamager().getName());
          if (profile != null) {
            Player player = (Player) evt.getDamager();
            if (GameArena.playernaarena.contains(player) && isSelectedPerk(profile) && has(profile) && canBuy(player)) {
              player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
            }
          }
        }
      }
    }, Main.getInstance());
  }
  
  @Override
  public long getIndex() {
    return this.index;
  }
}
