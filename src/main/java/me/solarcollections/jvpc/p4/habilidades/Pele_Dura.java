package me.solarcollections.jvpc.p4.habilidades;

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
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

public class Pele_Dura extends Perk {
  
  private final int index;
  
  public Pele_Dura(int index, String key) {
    super(1, key, CONFIG.getString(key + ".permission"), CONFIG.getString(key + ".name"), CONFIG.getString(key + ".icon"), new ArrayList<>());
    this.index = index;
    this.setupLevels(key);
    Bukkit.getPluginManager().registerEvents(new Listener() {
      @EventHandler(priority = EventPriority.MONITOR)
      public void PlayerDeath(PlayerDeathEvent evt) {
        Profile profile = Profile.getProfile(evt.getEntity().getPlayer().getName());
        if (profile != null) {
          Player player = evt.getEntity().getPlayer();
          if (GameArena.playernaarena.contains(player) && isSelectedPerk(profile) && has(profile) && canBuy(player)) {
            if (ThreadLocalRandom.current().nextInt(100) < getCurrentLevel(profile).getValue("percentage", int.class, 0)) {
              player.sendMessage(String.valueOf(getCurrentLevel(profile).getValue("mensagem", int.class, 0)));
              // dar a maçã ao jogador.
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
