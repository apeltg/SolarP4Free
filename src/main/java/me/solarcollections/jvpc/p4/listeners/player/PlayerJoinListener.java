package me.solarcollections.jvpc.p4.listeners.player;

import me.solarcollections.jvpc.Core;
import me.solarcollections.jvpc.nms.NMS;
import me.solarcollections.jvpc.p4.Language;
import me.solarcollections.jvpc.p4.Main;
import me.solarcollections.jvpc.p4.container.SelectedContainer;
import me.solarcollections.jvpc.p4.cosmetics.CosmeticType;
import me.solarcollections.jvpc.p4.cosmetics.types.CustomInput;
import me.solarcollections.jvpc.p4.hook.P4CoreHook;
import me.solarcollections.jvpc.p4.lobby.Lobby;
import me.solarcollections.jvpc.p4.tagger.TagUtils;
import me.solarcollections.jvpc.player.Profile;
import me.solarcollections.jvpc.player.enums.Fly;
import me.solarcollections.jvpc.player.hotbar.Hotbar;
import me.solarcollections.jvpc.player.role.Role;
import me.solarcollections.jvpc.titles.TitleManager;
import me.solarcollections.jvpc.utils.enums.EnumSound;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {
  
  @EventHandler
  public void onPlayerJoin(PlayerJoinEvent evt) {
    evt.setJoinMessage(null);

    Player player = evt.getPlayer();
    Profile profile = Profile.getProfile(player.getName());
    TagUtils.sendTeams(player);

    if (profile != null) {
      profile.setHotbar(Hotbar.getHotbarById("lobby"));
      profile.refresh();
      P4CoreHook.reloadScoreboard(profile);

      if (player.hasPermission("score.fly")) {
        if (profile.getPreferencesContainer().getFly() == Fly.ATIVADO) {
          player.setAllowFlight(true);
        } else if (profile.getPreferencesContainer().getFly() == Fly.DESATIVADO) {
          player.setAllowFlight(false);
        }
      }

      NMS.sendTitle(player, "", "", 0, 1, 0);
      if (Language.lobby$tab$enabled) {
        NMS.sendTabHeaderFooter(player, Language.lobby$tab$header, Language.lobby$tab$footer);
      }

      Bukkit.getScheduler().runTaskLaterAsynchronously(Main.getInstance(), () -> {
        TagUtils.setTag(evt.getPlayer());
        if (Role.getPlayerRole(player).isBroadcast()) {
          String broadcast = Language.lobby$broadcast
                  .replace("{player}", Role.getPrefixed(player.getName()));
          Profile.listProfiles().forEach(pf -> {
            if (!pf.playingGame()) {
              Player players = pf.getPlayer();
              if (players != null) {
                CustomInput ci = profile.getAbstractContainer("sCoreP4", "selected", SelectedContainer.class).getSelected(CosmeticType.CUSTOM_INPUT, CustomInput.class);
                if (ci != null) {
                  players.sendMessage(ci.getRandomMessage().replace("{name}", Role.getPrefixed(player.getName())));
                } else {
                  players.sendMessage(broadcast);
                }
              }
            }
          });
        }
      }, 5);

      Bukkit.getScheduler().runTaskLaterAsynchronously(Core.getInstance(), () -> {
        TitleManager.joinLobby(profile);
      }, 10);
      if (player.hasPermission("solarp4.cmd.p4")) {
        if (!Lobby.WARNINGS.isEmpty()) {
          TextComponent component = new TextComponent("");
          for (BaseComponent components : TextComponent.fromLegacyText(
                  " \n §6§lAVISO IMPORTANTE\n \n §7O sistema de servidores do SolarP4Free foi alterado nessa nova versão e, aparentemente você utiliza a versão antiga!\n §7O novo padrão de 'servername' na lobbies.yml é 'IP:PORTA ; BungeeServerName' e você utiliza o antigo padrão 'BungeeServerName' nas seguintes entradas:")) {
            component.addExtra(components);
          }
          for (String warning : Lobby.WARNINGS) {
            for (BaseComponent components : TextComponent.fromLegacyText("\n§f" + warning)) {
              component.addExtra(components);
            }
          }
          for (BaseComponent components : TextComponent.fromLegacyText("\n ")) {
            component.addExtra(components);
          }

          player.spigot().sendMessage(component);
          EnumSound.ORB_PICKUP.play(player, 1.0F, 1.0F);
        }
      }
    }
  }
}
