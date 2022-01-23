package me.solarcollections.jvpc.p4.command;

import me.solarcollections.jvpc.player.role.Role;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatCleaner extends Commands {

    public ChatCleaner() {
        super("clearchat", "cc");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cSomente um jogador pode utilizar este comando.");
            return;
        }

        Player player = (Player) sender;
        if (player.hasPermission("solarp4.cmd.p4")) {
            int i = 0;
            while (i <= 100) {
                Bukkit.broadcastMessage(" ");
                i++;
            }

            Bukkit.broadcastMessage("§aChat limpo por " + Role.getColored(player.getName()));
            player.sendMessage("§aChat limpo com sucesso!");
        }
    }
}
