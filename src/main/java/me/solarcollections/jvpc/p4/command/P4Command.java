package me.solarcollections.jvpc.p4.command;

import me.solarcollections.jvpc.p4.Main;
import me.solarcollections.jvpc.p4.command.p4.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class P4Command extends Commands {

  private final List<SubCommand> commands = new ArrayList<>();

  public P4Command() {
    super("solarp4free", "p4");

    this.commands.add(new UpdateCommand());
    this.commands.add(new BuildCommand());
    this.commands.add(new NPCDeliveryCommand());
    this.commands.add(new NPCStatsCommand());
    this.commands.add(new GiveCommand());
    this.commands.add(new SetSpawnCommand());
    this.commands.add(new ArenaSeter());
  }

  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (!sender.hasPermission("solarp4.cmd.p4")) {
      sender.sendMessage("§6SolarP4Free §7[" + Main.getInstance().getDescription().getVersion() + "] §f- §7Criado por §bJVPC_YouTube§7.");
      return;
    }

    if (args.length == 0) {
      this.sendHelp(sender, 1);
      return;
    }

    try {
      this.sendHelp(sender, Integer.parseInt(args[0]));
    } catch (Exception ex) {
      SubCommand subCommand = this.commands.stream().filter(sc -> sc.getName().equalsIgnoreCase(args[0])).findFirst().orElse(null);
      if (subCommand == null) {
        this.sendHelp(sender, 1);
        return;
      }

      List<String> list = new ArrayList<>(Arrays.asList(args));
      list.remove(0);
      if (subCommand.onlyForPlayer()) {
        if (!(sender instanceof Player)) {
          sender.sendMessage("§cEsse comando pode ser utilizado apenas pelos jogadores.");
          return;
        }

        subCommand.perform((Player) sender, list.toArray(new String[list.size()]));
      } else {
        subCommand.perform(sender, list.toArray(new String[list.size()]));
      }
    }
  }

  private void sendHelp(CommandSender sender, int page) {
    List<SubCommand> commands = this.commands.stream().filter(subcommand -> sender instanceof Player || !subcommand.onlyForPlayer()).collect(Collectors.toList());
    Map<Integer, StringBuilder> pages = new HashMap<>();

    int pagesCount = (commands.size() + 6) / 7;
    for (int index = 0; index < commands.size(); index++) {
      int currentPage = (index + 7) / 7;
      if (!pages.containsKey(currentPage)) {
        pages.put(currentPage, new StringBuilder(" \n§eAjuda - " + currentPage + "/" + pagesCount + "\n \n"));
      }

      pages.get(currentPage).append("§3/p4 ").append(commands.get(index).getUsage()).append(" §f- §7").append(commands.get(index).getDescription()).append("\n");
    }

    StringBuilder sb = pages.get(page);
    if (sb == null) {
      sender.sendMessage("§cPágina não encontrada.");
      return;
    }

    sb.append(" ");
    sender.sendMessage(sb.toString());
  }
}
