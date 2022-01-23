package me.solarcollections.jvpc.p4.command;

import me.solarcollections.jvpc.p4.Main;
import me.solarcollections.jvpc.p4.game.GameArena;
import me.solarcollections.jvpc.player.Profile;
import me.solarcollections.jvpc.player.hotbar.Hotbar;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class SpawnCommand extends Commands{
    public SpawnCommand() {
        super("spawn");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return;
        }
        Player player = ((Player) sender).getPlayer();
        if (GameArena.playernaarena.contains(player)) {
            if (GameArena.cooldawn.contains(player)) {
                player.sendMessage("§cVocê está em combate no momento.");
                return;
            }
            GameArena.playernaarena.remove(player);
            Profile profile = Profile.getProfile(player.getName());
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), new BukkitRunnable() {
                @Override
                public void run() {
                    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), profile::refresh, 3);
                    profile.setHotbar(Hotbar.getHotbarById("lobby"));
                    profile.refresh();
                }
            }, 1*2L);
            player.sendMessage("§aTeleportado com sucesso.");
        } else {
            player.sendMessage("§cVocê já está no spawn.");
        }
    }
}
