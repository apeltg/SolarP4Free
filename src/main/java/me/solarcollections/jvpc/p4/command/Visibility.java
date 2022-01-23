package me.solarcollections.jvpc.p4.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class Visibility extends Commands {

    public Visibility() {
        super("v");
    }

    private static final List<String> VISIVEL = new ArrayList<>();

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cSomente um jogador pode utilizar este comando.");
            return;
        }

        Player player = (Player) sender;
        if (player.hasPermission("solarp4.cmd.p4")) {
            if (Visivel(player)) {
                player.sendMessage("§aAgora você está aparecendo para todos os jogadores!");
                player.setAllowFlight(false);
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (!players.hasPermission("solarp4.cmd.p4")) {
                        players.showPlayer(player);
                    }
                }
                VISIVEL.remove(player.getName());
            } else {
                player.sendMessage("§aAgora você está escondido de todos os jogadores!");
                player.setAllowFlight(true);
                for (Player players : Bukkit.getOnlinePlayers()) {
                    if (!players.hasPermission("solarp4.cmd.p4")) {
                        players.hidePlayer(player);
                    }
                }
                VISIVEL.add(player.getName());
            }
        } else {
            player.sendMessage("§cVocê não possui permissão para fazer isso.");
        }
    }
    public static boolean Visivel (Player player) {
        return VISIVEL.contains(player.getName());
    }
}
