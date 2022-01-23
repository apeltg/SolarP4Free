package me.solarcollections.jvpc.p4.command.p4;

import me.solarcollections.jvpc.p4.Main;
import me.solarcollections.jvpc.p4.command.SubCommand;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class ArenaSeter extends SubCommand {
    public ArenaSeter() {
        super("setarena", "setarena", "Setar a arena", true);
    }
    public static Location location;

    @Override
    public void perform(Player player, String[] args) {
        Main.getInstance().getConfig().set("arena.x", Double.valueOf(player.getLocation().getX()));
        Main.getInstance().getConfig().set("arena.y", Double.valueOf(player.getLocation().getY()));
        Main.getInstance().getConfig().set("arena.z", Double.valueOf(player.getLocation().getZ()));
        Main.getInstance().getConfig().set("arena.pitch", Double.valueOf(player.getLocation().getPitch()));
        Main.getInstance().getConfig().set("arena.yaw", Double.valueOf(player.getLocation().getYaw()));
        Main.getInstance().getConfig().set("arena.world", player.getLocation().getWorld().getName());
        Main.getInstance().saveConfig();
        player.sendMessage("§aArena setada!");
    }
}
