package me.solarcollections.jvpc.p4.listeners;

import me.solarcollections.jvpc.p4.Main;
import me.solarcollections.jvpc.p4.listeners.entity.EntityListener;
import me.solarcollections.jvpc.p4.listeners.player.*;
import me.solarcollections.jvpc.p4.listeners.server.ServerListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

public class Listener {

    public static void setupListeners() {
        try {
            PluginManager pm = Bukkit.getPluginManager();

            pm.getClass().getDeclaredMethod("registerEvents", org.bukkit.event.Listener.class, Plugin.class)
                    .invoke(pm, new PlayerJoinListener(), Main.getInstance());
            pm.getClass().getDeclaredMethod("registerEvents", org.bukkit.event.Listener.class, Plugin.class)
                    .invoke(pm, new PlayerQuitEvt(), Main.getInstance());
            pm.getClass().getDeclaredMethod("registerEvents", org.bukkit.event.Listener.class, Plugin.class)
                    .invoke(pm, new AsyncPlayerChatListener(), Main.getInstance());
            pm.getClass().getDeclaredMethod("registerEvents", org.bukkit.event.Listener.class, Plugin.class)
                    .invoke(pm, new InventoryClickListener(), Main.getInstance());
            pm.getClass().getDeclaredMethod("registerEvents", org.bukkit.event.Listener.class, Plugin.class)
                    .invoke(pm, new PlayerDeathListener(), Main.getInstance());
            pm.getClass().getDeclaredMethod("registerEvents", org.bukkit.event.Listener.class, Plugin.class)
                    .invoke(pm, new PlayerInteractListener(), Main.getInstance());
            pm.getClass().getDeclaredMethod("registerEvents", org.bukkit.event.Listener.class, Plugin.class)
                    .invoke(pm, new PlayerRestListener(), Main.getInstance());

            pm.getClass().getDeclaredMethod("registerEvents", org.bukkit.event.Listener.class, Plugin.class)
                    .invoke(pm, new EntityListener(), Main.getInstance());
            pm.getClass().getDeclaredMethod("registerEvents", org.bukkit.event.Listener.class, Plugin.class)
                    .invoke(pm, new ServerListener(), Main.getInstance());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
