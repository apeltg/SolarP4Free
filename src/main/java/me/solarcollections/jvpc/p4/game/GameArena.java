package me.solarcollections.jvpc.p4.game;

import me.solarcollections.jvpc.p4.API.KitAPI;
import me.solarcollections.jvpc.p4.Main;
import me.solarcollections.jvpc.player.Profile;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

public class GameArena {

    public static Set<Player> playernaarena = new HashSet<>();
    public static Set<Player> cooldawn = new HashSet<>();

    public static void EntrarArena(Profile profile) {
        Player player = profile.getPlayer();
        playernaarena.add(player);
        profile.setHotbar(null);
        for (Player ps : Bukkit.getOnlinePlayers()) {
            player.showPlayer(ps);
        }
        player.setAllowFlight(false);
        try {
            World w = Bukkit.getServer().getWorld(Main.getInstance().getConfig().getString("arena.world"));
            double x = Main.getInstance().getConfig().getDouble("arena.x");
            double y = Main.getInstance().getConfig().getDouble("arena.y");
            double z = Main.getInstance().getConfig().getDouble("arena.z");
            Location lobby = new Location(w, x, y, z);
            lobby.setPitch((float) Main.getInstance().getConfig().getDouble("arena.pitch"));
            lobby.setYaw((float) Main.getInstance().getConfig().getDouble("arena.yaw"));
            player.teleport(lobby);
        }  catch (Exception ex) {
            player.sendMessage("§cA arena ainda não foi setada.");
            return;
        }
        player.getInventory().clear();
        new BukkitRunnable() {
            @Override
            public void run() {
                KitAPI.darItens(player);
                cancel();
            }
        }.runTaskLater(Main.getInstance(), 10);
    }
}
