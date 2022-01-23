package me.solarcollections.jvpc.p4.hook;

import com.comphenix.protocol.ProtocolLibrary;
import me.clip.placeholderapi.PlaceholderAPI;
import me.solarcollections.jvpc.Core;
import me.solarcollections.jvpc.achievements.Achievement;
import me.solarcollections.jvpc.achievements.types.SkyWarsAchievement;
import me.solarcollections.jvpc.p4.API.KitAPI;
import me.solarcollections.jvpc.p4.Language;
import me.solarcollections.jvpc.p4.Main;
import me.solarcollections.jvpc.p4.hook.hotbar.P4HotbarActionType;
import me.solarcollections.jvpc.p4.hook.protocollib.HologramAdapter;
import me.solarcollections.jvpc.player.Profile;
import me.solarcollections.jvpc.player.hotbar.Hotbar;
import me.solarcollections.jvpc.player.hotbar.HotbarAction;
import me.solarcollections.jvpc.player.hotbar.HotbarActionType;
import me.solarcollections.jvpc.player.hotbar.HotbarButton;
import me.solarcollections.jvpc.player.role.Role;
import me.solarcollections.jvpc.player.scoreboard.SScoreboard;
import me.solarcollections.jvpc.player.scoreboard.scroller.ScoreboardScroller;
import me.solarcollections.jvpc.plugin.config.SConfig;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class P4CoreHook {
  
  public static void setupHook() {
    Core.minigame = "P4 Free";
    
    setupHotbars();
    new BukkitRunnable() {
      @Override
      public void run() {
        Profile.listProfiles().forEach(profile -> {
          if (profile.getScoreboard() != null) {
            profile.getScoreboard().scroll();
          }
        });
      }
    }.runTaskTimerAsynchronously(Main.getInstance(), 0, Language.scoreboards$scroller$every_tick);
    
    new BukkitRunnable() {
      @Override
      public void run() {
        Profile.listProfiles().forEach(profile -> {
          if (!profile.playingGame() && profile.getScoreboard() != null) {
            profile.update();
          }
        });
      }
    }.runTaskTimerAsynchronously(Main.getInstance(), 0, 20);
    
    ProtocolLibrary.getProtocolManager().addPacketListener(new HologramAdapter());
  }
  
  public static void checkAchievements(Profile profile) {
    Bukkit.getScheduler().runTaskAsynchronously(Main.getInstance(), () -> {
      Achievement.listAchievements(SkyWarsAchievement.class).stream().filter(swa -> swa.canComplete(profile)).forEach(swa -> {
        swa.complete(profile);
        profile.getPlayer().sendMessage(Language.lobby$achievement.replace("{name}", swa.getName()));
      });
    });
  }
  
  public static void reloadScoreboard(Profile profile) {
    if (!profile.playingGame()) {
      checkAchievements(profile);
    }
    Player player = profile.getPlayer();
    List<String> lines = new ArrayList<>(Language.scoreboards$p4);
    
    profile.setScoreboard(new SScoreboard() {
      @Override
      public void update() {
        for (int index = 0; index < Math.min(lines.size(), 15); index++) {
          String line = lines.get(index);
            line =   line.replace("{map}", profile.getPlayer().getWorld().getName())
                    .replace("{date}", new SimpleDateFormat("dd/MM/YY").format(System.currentTimeMillis()))
                    .replace("{nick}", Role.getColored(profile.getPlayer().getName()))
                    .replace("{kit}", KitAPI.getKit(profile.getPlayer()));
          line = PlaceholderAPI.setPlaceholders(player, line);

          this.add(15 - index, line);
        }
      }
    }.scroller(new ScoreboardScroller(Language.scoreboards$scroller$titles)).to(player).build());
    profile.getScoreboard().health().updateHealth();
    profile.update();
    profile.getScoreboard().scroll();
  }

  private static void setupHotbars() {
    HotbarActionType.addActionType("p4", new P4HotbarActionType());

    SConfig config = Main.getInstance().getConfig("hotbar");
    for (String id : new String[]{"lobby"}) {
      Hotbar hotbar = new Hotbar(id);

      ConfigurationSection hb = config.getSection(id);
      for (String button : hb.getKeys(false)) {
        try {
          hotbar.getButtons().add(new HotbarButton(hb.getInt(button + ".slot"), new HotbarAction(hb.getString(button + ".execute")), hb.getString(button + ".icon")));
        } catch (Exception ex) {
          Main.getInstance().getLogger().log(Level.WARNING, "Falha ao carregar o botao \"" + button + "\" da hotbar \"" + id + "\": ", ex);
        }
      }

      Hotbar.addHotbar(hotbar);
    }
  }
}
