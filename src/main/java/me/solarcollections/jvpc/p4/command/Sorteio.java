package me.solarcollections.jvpc.p4.command;

import me.solarcollections.jvpc.cash.CashException;
import me.solarcollections.jvpc.cash.CashManager;
import me.solarcollections.jvpc.p4.Main;
import me.solarcollections.jvpc.player.Profile;
import me.solarcollections.jvpc.player.role.Role;
import me.solarcollections.jvpc.plugin.config.SConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Sorteio extends Commands {

    List<Player> playersparticipantes = new ArrayList<Player>();
    private Boolean ativar = true;
    private static final SConfig CONFIG = Main.getInstance().getConfig("sorteio");

    public Sorteio() {
        super("sortear");
    }

    @Override
    public void perform(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cSomente um jogador pode utilizar este comando.");
            return;
        }
        Player player = (Player) sender;
        if (args.length == 0) {
            player.sendMessage("§cUso correto: /sortear vip/coins/cash");
            return;
        }

        String action = args[0];
        if (player.hasPermission(CONFIG.getString("sorteio.permissao"))) {
            if (ativar) {
                for (Player players : Bukkit.getOnlinePlayers()) {
                    playersparticipantes.addAll(Collections.singleton(players));
                    players.sendMessage(CONFIG.getString("sorteio.mensagemaoanunciar"));
                    int random = new Random().nextInt(playersparticipantes.size());
                    Player ganhador = playersparticipantes.get(random);
                    if (action.equalsIgnoreCase("vip")) {
                        Bukkit.broadcastMessage(CONFIG.getString("sorteio.vip.aoanunciar"));
                        ativar = false;


                        //Givar vip
                        final BukkitTask runnable = new BukkitRunnable() {
                            @Override
                            public void run() {
                                ativar = true;
                                Bukkit.broadcastMessage(CONFIG.getString("sorteio.mensagemganhador").replace("{playerganhador}", Role.getColored(ganhador.getName())));
                                Bukkit.getServer().dispatchCommand(Bukkit.getServer().getConsoleSender(), CONFIG.getString("sorteio.vip").replace("{playerganhador}", Role.getColored(ganhador.getName())));
                                cancel();
                            }
                        }.runTaskLater(Main.getInstance(), 60);


                    } else if (action.equalsIgnoreCase("coins")) {
                        Bukkit.broadcastMessage(CONFIG.getString("sorteio.coins.aoanunciar"));
                        ativar = false;


                        //Givar Coins
                        final BukkitTask runnable = new BukkitRunnable() {
                            @Override
                            public void run() {
                                ativar = true;
                                Bukkit.broadcastMessage(CONFIG.getString("sorteio.mensagemganhador").replace("{playerganhador}", Role.getColored(ganhador.getName())));
                                player.performCommand("p4 dar " + ganhador.getName() + " coins " + CONFIG.getInt("sorteio.coins.quantia"));
                                cancel();
                            }
                        }.runTaskLater(Main.getInstance(), CONFIG.getInt("sorteio.tempo"));


                    } else if (action.equalsIgnoreCase("cash")) {
                        Bukkit.broadcastMessage(CONFIG.getString("sorteio.cash.aoanunciar"));
                        ativar = false;


                        //Givar Cash
                        final BukkitTask runnable = new BukkitRunnable() {
                            @Override
                            public void run() {
                                ativar = true;
                                Bukkit.broadcastMessage(CONFIG.getString("sorteio.mensagemganhador").replace("{playerganhador}", Role.getColored(ganhador.getName())));
                                try {
                                    CashManager.addCash(ganhador, + CONFIG.getInt("sorteio.cash.quantia"));
                                } catch (CashException e) {
                                    e.printStackTrace();
                                }
                                cancel();
                            }
                        }.runTaskLater(Main.getInstance(), 60);
                    }
                }
            } else {
                player.sendMessage("§cUm sorteio já foi iniciado anteriormente.");
            }
        } else {
            player.sendMessage("§cVocê não possui permissão para fazer isso.");
        }
    }
}
