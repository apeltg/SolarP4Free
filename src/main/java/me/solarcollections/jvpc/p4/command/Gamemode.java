package me.solarcollections.jvpc.p4.command;

import me.solarcollections.jvpc.player.Profile;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Gamemode extends Commands {

    public Gamemode() {
        super("gm");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Somente um jogador pode utilizar este comando.");
            return;
        }

        Player player = (Player) sender;
        Profile profile = Profile.getProfile(player.getName());

        if (profile != null) {
            if (player.hasPermission("solarp4.cmd.p4")) {
                if (args.length == 0) {
                    player.sendMessage("§cUso correto: /gamemode [1/2/3]");
                    return;
                }
                String action = args[0];
                if (action.equalsIgnoreCase("1")) {
                    player.sendMessage("§aGamemode alterado para: §eCreativo§a!");
                    player.setGameMode(GameMode.CREATIVE);
                } else if (action.equalsIgnoreCase("2")) {
                    player.sendMessage("§aGamemode alterado para: §eAventura§a!");
                    player.setGameMode(GameMode.ADVENTURE);
                } else if (action.equalsIgnoreCase("3")) {
                    player.sendMessage("§aGamemode alterado para: §eEspectador§a!");
                    player.setGameMode(GameMode.SPECTATOR);
                } else if (action.equalsIgnoreCase("0")) {
                    player.sendMessage("§aGamemode alterado para: §eSurvival§a!");
                    player.setGameMode(GameMode.SURVIVAL);
                }
            } else {
                player.sendMessage("§cVocê não possui permissão para fazer isso.");
            }
        }
    }
}
