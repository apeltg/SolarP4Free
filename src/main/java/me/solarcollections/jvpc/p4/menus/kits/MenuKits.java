package me.solarcollections.jvpc.p4.menus.kits;

import me.solarcollections.jvpc.libraries.menu.PlayerMenu;
import me.solarcollections.jvpc.p4.Main;
import me.solarcollections.jvpc.p4.game.GameArena;
import me.solarcollections.jvpc.player.Profile;
import me.solarcollections.jvpc.utils.BukkitUtils;
import me.solarcollections.jvpc.utils.enums.EnumSound;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class MenuKits extends PlayerMenu {

    public MenuKits(Profile profile) {
        super(profile.getPlayer(), "Kits Desbloquados", 6);

        this.setItem(10, BukkitUtils.deserializeItemStack(
                "DIAMOND_SWORD : 1 : esconder>tudo : nome>&aKit Padrão : desc>&7Conteúdo:\n \n &f* &7Armadura completa de diamante (P4)\n &f* &7Espada de diamante (AF5 e AF2)\n &f* &7Poção de Velocidade 2 e Força 2\n \n&eClique para selecionar!"));

        this.register(Main.getInstance());
        this.open();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent evt) {
        if (evt.getInventory().equals(this.getInventory())) {
            evt.setCancelled(true);

            if (evt.getWhoClicked().equals(this.player)) {
                Profile profile = Profile.getProfile(this.player.getName());
                if (profile == null) {
                    this.player.closeInventory();
                    return;
                }

                if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(this.getInventory())) {
                    ItemStack item = evt.getCurrentItem();
                    if (item != null && item.getType() != Material.AIR) {
                        if (evt.getSlot() == 10) {
                            EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                            new GameArena();
                            GameArena.EntrarArena(profile);
                        }
                    }
                }
            }
        }
    }

    public void cancel() {
        HandlerList.unregisterAll(this);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent evt) {
        if (evt.getPlayer().equals(this.player)) {
            this.cancel();
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent evt) {
        if (evt.getPlayer().equals(this.player) && evt.getInventory().equals(this.getInventory())) {
            this.cancel();
        }
    }
}
