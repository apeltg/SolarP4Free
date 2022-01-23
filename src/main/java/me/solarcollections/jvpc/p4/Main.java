package me.solarcollections.jvpc.p4;

import me.solarcollections.jvpc.Core;
import me.solarcollections.jvpc.libraries.MinecraftVersion;
import me.solarcollections.jvpc.p4.command.Commands;
import me.solarcollections.jvpc.p4.cosmetics.Cosmetic;
import me.solarcollections.jvpc.p4.hook.P4CoreHook;
import me.solarcollections.jvpc.p4.listeners.Listener;
import me.solarcollections.jvpc.p4.lobby.DeliveryNPC;
import me.solarcollections.jvpc.p4.lobby.Lobby;
import me.solarcollections.jvpc.p4.lobby.StatsNPC;
import me.solarcollections.jvpc.plugin.SPlugin;
import me.solarcollections.jvpc.utils.BukkitUtils;
import org.bukkit.Bukkit;

public class Main extends SPlugin {

    private static Main instance;
    private static boolean validInit;
    public static String currentServerName;
    public static Boolean SolarCosmetics, SolarCaixaMisteriosa, SolarClans;

    @Override
    public void start() {
        instance = this;
    }

    @Override
    public void load() {}

    @Override
    public void enable() {
        if (MinecraftVersion.getCurrentVersion().getCompareId() != 183) {
            this.setEnabled(false);
            this.getLogger().warning("O plugin apenas funciona na versao 1_8_R3 (Atual: " + MinecraftVersion.getCurrentVersion().getVersion() + ")");
            return;
        }

        saveDefaultConfig();
        currentServerName = getConfig().getString("lobby");
        if (getConfig().getString("spawn") != null) {
            Core.setLobby(BukkitUtils.deserializeLocation(getConfig().getString("spawn")));
        }
        SolarCosmetics = Bukkit.getPluginManager().getPlugin("SolarCosmetics") != null;
        SolarCaixaMisteriosa = Bukkit.getPluginManager().getPlugin("SolarCaixaMisteriosa") != null;
        SolarClans = Bukkit.getPluginManager().getPlugin("SolarClans") != null;


        StatsNPC.setupNPCs();
        DeliveryNPC.setupNPCs();
        Language.setupLanguage();
        P4CoreHook.setupHook();
        Lobby.setupLobbies();
        Cosmetic.setupCosmetics();

        Commands.setupCommands();
        Listener.setupListeners();

        validInit = true;
        this.getLogger().warning("O plugin inicou com sucesso.");
    }

    @Override
    public void disable() {
        this.getLogger().warning("O plugin foi desativado.");
        if (validInit) {

        }
    }
    public static Main getInstance() {
        return instance;
    }
}
