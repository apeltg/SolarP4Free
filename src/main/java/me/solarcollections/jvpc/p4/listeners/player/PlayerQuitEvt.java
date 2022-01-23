package me.solarcollections.jvpc.p4.listeners.player;

import me.solarcollections.jvpc.p4.command.p4.BuildCommand;
import me.solarcollections.jvpc.p4.game.GameArena;
import me.solarcollections.jvpc.p4.tagger.TagUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitEvt implements Listener {

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent evt) {
        evt.setQuitMessage(null);
        BuildCommand.remove(evt.getPlayer());
        TagUtils.reset(evt.getPlayer().getName());
        GameArena.playernaarena.remove(evt.getPlayer());
    }
}
