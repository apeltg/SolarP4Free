package me.solarcollections.jvpc.p4.command;

import me.solarcollections.jvpc.cmd.Commands;
import me.solarcollections.jvpc.p4.game.GameArena;
import me.solarcollections.jvpc.player.Profile;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FlyCommand extends Commands {

    private static final List<String> VOAR = new ArrayList<>();

    public FlyCommand() {
        super("voar", "fly");
    }

    public void perform(CommandSender sender, String label, String[] args) {
        Player player = (Player) sender;
        Profile profile = Profile.getProfile(player.getName());
        if (!player.hasPermission("score.fly")) {
            player.sendMessage("§cVocê não possui permissão para utilizar este comando.");
            return;
        }
        if (FlyPlayer(player)) {
            if (!GameArena.playernaarena.contains(player)) {
                VOAR.remove(player.getName());
                player.sendMessage("§cModo voar desativado!");
                player.setAllowFlight(false);
            } else {
                player.sendMessage("§cVocê precisa está em um lobby, para executar este comando.");
            }
        } else {
            if (!GameArena.playernaarena.contains(player)) {
                VOAR.add(player.getName());
                player.sendMessage("§aModo voar ativado!");
                player.setAllowFlight(true);
            } else {
                player.sendMessage("§cVocê precisa está em um lobby, para executar este comando.");
            }
        }
    }

    public static void remove(Player player) {
        VOAR.remove(player.getName());
    }

    public static void set(Player player) {
        FlyCommand.remove(player);
        VOAR.add(player.getName());
    }

    public static boolean FlyPlayer (Player player){
        return VOAR.contains(player.getName());
    }
}
