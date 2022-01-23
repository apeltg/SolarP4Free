package me.solarcollections.jvpc.p4.command.p4;

import me.solarcollections.jvpc.p4.command.SubCommand;
import org.bukkit.entity.Player;

public class UpdateCommand extends SubCommand {

  public UpdateCommand() {
    super("atualizar", "atualizar", "Atualizar o SolarP4Free.", true);
  }

  @Override
  public void perform(Player player, String[] args) {
    player.sendMessage("§aO plugin já se encontra em sua última versão.");
  }
}
