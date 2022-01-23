package me.solarcollections.jvpc.p4.listeners.player;

import me.solarcollections.jvpc.database.data.DataContainer;
import me.solarcollections.jvpc.nms.NMS;
import me.solarcollections.jvpc.p4.Language;
import me.solarcollections.jvpc.player.Profile;
import me.solarcollections.jvpc.player.enums.Mention;
import me.solarcollections.jvpc.player.role.Role;
import me.solarcollections.jvpc.utils.StringUtils;
import me.solarcollections.jvpc.utils.enums.EnumSound;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class AsyncPlayerChatListener implements Listener {

  private static final Map<String, Long> flood = new HashMap<>();

  private static final DecimalFormat df = new DecimalFormat("###.#");

  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    flood.remove(evt.getPlayer().getName());
  }

  @EventHandler(priority = EventPriority.HIGHEST)
  public void AsyncPlayerChat(AsyncPlayerChatEvent evt) {
    if (evt.isCancelled()) {
      return;
    }
    Player player = evt.getPlayer();
    Profile profile = Profile.getProfile(player.getName());
    if (!player.hasPermission("solarp4.chat.delay")) {
      long start = flood.containsKey(player.getName()) ? flood.get(player.getName()) : 0;
      if (start > System.currentTimeMillis()) {
        double time = (start - System.currentTimeMillis()) / 1000.0;
        if (time > 0.1) {
          evt.setCancelled(true);
          String timeString = df.format(time).replace(",", ".");
          if (timeString.endsWith("0")) {
            timeString = timeString.substring(0, timeString.lastIndexOf("."));
          }

          player.sendMessage(Language.chat$delay.replace("{time}", timeString));
          return;
        }
      }

      flood.put(player.getName(), System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(3));
    }

    Role role = Role.getPlayerRole(player);
    if (player.hasPermission("solarp4.chat.color")) {
      evt.setMessage(StringUtils.formatColors(evt.getMessage()));
    }
    if (!profile.playingGame()) {
      for (Player players : Bukkit.getOnlinePlayers()) {
        Profile profile1 = Profile.getProfile(players.getName());
        if (profile1.getPreferencesContainer().getMention().equals(Mention.ATIVADO)) {
          if (evt.getMessage().contains(players.getName())) {
            NMS.sendActionBar(players, Role.getColored(player.getName()) + " §emencionou você no chat!");
            EnumSound.ORB_PICKUP.play(players, 1.0F, 1.0F);
          }
        }
      }
    }

    String suffix = "";
    DataContainer container = profile.getDataContainer("sCoreProfile", "clan");
    if (container != null) {
      suffix = container.getAsString().replace(" ", "") + " ";
      if (suffix.contains("§8")) {
        suffix = "";
      }
    }
    evt.setFormat(
            Language.chat$format$lobby.replace("{player}", role.getPrefix() + "%s").replace("{color}", role.isDefault() ? Language.chat$color$default : Language.chat$color$custom)
                    .replace("{message}", "%s"));

    evt.getRecipients().clear();
    for (Player players : player.getWorld().getPlayers()) {
      Profile profiles = Profile.getProfile(players.getName());
      if (profiles != null) {
        if (!profiles.playingGame()) {
          evt.getRecipients().add(players);
        }
      }
    }
  }
}
