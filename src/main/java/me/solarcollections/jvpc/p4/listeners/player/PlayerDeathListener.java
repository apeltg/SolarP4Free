package me.solarcollections.jvpc.p4.listeners.player;

import me.solarcollections.jvpc.clans.api.ClanAPI;
import me.solarcollections.jvpc.p4.API.KitAPI;
import me.solarcollections.jvpc.p4.Language;
import me.solarcollections.jvpc.p4.Main;
import me.solarcollections.jvpc.p4.container.SelectedContainer;
import me.solarcollections.jvpc.p4.cosmetics.CosmeticType;
import me.solarcollections.jvpc.p4.cosmetics.types.DeathCry;
import me.solarcollections.jvpc.p4.cosmetics.types.DeathMessage;
import me.solarcollections.jvpc.p4.cosmetics.types.KillEffect;
import me.solarcollections.jvpc.p4.game.GameArena;
import me.solarcollections.jvpc.player.Profile;
import me.solarcollections.jvpc.player.hotbar.Hotbar;
import me.solarcollections.jvpc.player.role.Role;
import me.solarcollections.jvpc.utils.enums.EnumSound;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;

public class PlayerDeathListener implements Listener {

  @EventHandler
  public void onPlayerDeath(PlayerDeathEvent evt) {
    Player player = evt.getEntity().getPlayer();
    Profile profile = Profile.getProfile(player.getName());
    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new BukkitRunnable() {
      @Override
      public void run() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), profile::refresh, 3);

        List<Profile> hitters = profile.getLastHitters();
        Profile killer = hitters.size() > 0 ? hitters.get(0) : null;

        for (Profile hitter : hitters) {
          if (!hitter.equals(killer)) {
            hitter.addStats("sCoreP4", 1, "assists");

          }
          hitters.clear();
        }
        profile.setHotbar(Hotbar.getHotbarById("lobby"));
        profile.refresh();

      }//o
    }, 2L);
    evt.setDroppedExp(0);
    player.setHealth(20.0);
    evt.setDeathMessage(null);
    if (GameArena.playernaarena.contains(evt.getEntity().getPlayer())) {
      GameArena.playernaarena.remove(evt.getEntity().getPlayer());
      if (KitAPI.kit.containsKey(player.getName())) {
        KitAPI.kit.remove(player.getName());
      }
      if (evt.getEntity().getKiller() != null) {
        Player killer = evt.getEntity().getKiller();
        Profile profilekiller = Profile.getProfile(killer.getName());
        if (profilekiller != null) {
          EnumSound.ORB_PICKUP.play(killer, 1.0F, 1.0F);
          profilekiller.addCoins("sCoreP4", Language.options$coins$kills);
          if (Main.SolarClans) {
            if (ClanAPI.getClanByPlayerName(killer.getName()) != null) {
              ClanAPI.addCoins(ClanAPI.getClanByPlayerName(killer.getName()), Language.options$coins$clan$kills);
            }
          }
          profilekiller.addStats("sCoreP4", "kills");
          profilekiller.addStats("sCoreP4", "monthlykills");

          profile.addStats("sCoreP4", "deaths");
          profile.addStats("sCoreP4", "monthlydeaths");

          DeathMessage dm = profilekiller.getAbstractContainer("sCoreP4", "selected", SelectedContainer.class).getSelected(CosmeticType.DEATH_MESSAGE, DeathMessage.class);
          if (dm != null) {
            Bukkit.broadcastMessage(dm.getRandomMessage().replace("{name}", Role.getColored(player.getName())).replace("{killer}", Role.getColored(killer.getName())));
          } else {
            Bukkit.broadcastMessage(Language.ingame$broadcast$default_killed_message.replace("{name}", Role.getColored(player.getName())).replace("{killer}", Role.getColored(killer.getName())));
          }
          DeathCry dc = profile.getAbstractContainer("sCoreP4", "selected", SelectedContainer.class).getSelected(CosmeticType.DEATH_CRY, DeathCry.class);
          if (dc != null) {
            dc.getSound().play(player.getWorld(), player.getLocation(), dc.getVolume(), dc.getSpeed());
          }
          KillEffect ke = profilekiller.getAbstractContainer("sCoreP4", "selected", SelectedContainer.class).getSelected(CosmeticType.KILL_EFFECT, KillEffect.class);
          if (ke != null) {
            ke.execute(player.getLocation());
          }
        }
      } else {
        Bukkit.broadcastMessage(Language.ingame$broadcast$suicide.replace("{name}", Role.getColored(player.getName())));
        profile.addStats("sCoreP4", "deaths");
        profile.addStats("sCoreP4", "monthlydeaths");
      }

      if (profile.getCoins("sCoreP4") >= 5) {
        profile.removeCoins("sCoreP4", Language.options$coins$morte);
        player.sendMessage("§cVocê perdeu §e" + Language.options$coins$morte + " §ccoins por ter morrido.");
      }

      //Para caso o coins ficar negativo.
      if (profile.getCoins("sCoreP4") < 0) {
        profile.addCoins("sCoreP4", 1);
      }
    }
  }
}

