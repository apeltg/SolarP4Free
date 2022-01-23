package me.solarcollections.jvpc.p4.menus.cosmetics;

import me.solarcollections.jvpc.Core;
import me.solarcollections.jvpc.libraries.menu.PagedPlayerMenu;
import me.solarcollections.jvpc.p4.container.SelectedContainer;
import me.solarcollections.jvpc.p4.cosmetics.Cosmetic;
import me.solarcollections.jvpc.p4.cosmetics.CosmeticType;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuCosmeticsAll<T extends Cosmetic> extends PagedPlayerMenu {

    private Class<T> cosmeticClass;
    private Map<ItemStack, T> cosmetics = new HashMap<>();

    public MenuCosmeticsAll(Profile profile, String name, Class<T> cosmeticClass) {
        super(profile.getPlayer(), "Desbloqueados", 6);
        this.cosmeticClass = cosmeticClass;
        this.previousPage = (this.rows * 9) - 9;
        this.nextPage = (this.rows * 9) - 1;
        this.onlySlots(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34);

        this.removeSlotsWith(BukkitUtils.deserializeItemStack("ARROW : 1 : nome>&cVoltar : desc>&7Voltar"), (this.rows * 9) - 5);

        List<ItemStack> items = new ArrayList<>();
        List<T> cosmetics = Cosmetic.listByType(cosmeticClass);
        final int[] indexSize = {1};
        for (T cosmetic : cosmetics) {
            ItemStack icon = cosmetic.getIcon(profile);
            if (cosmetic.has(profile)) {
                items.add(icon);
            }
            this.cosmetics.put(icon, cosmetic);
            if (profile.getAbstractContainer("sCoreP4", "selected",
                    SelectedContainer.class).getSelected(cosmetic.getType(), cosmeticClass, indexSize[0]) != null) {
                this.removeSlotsWith(BukkitUtils.deserializeItemStack("" + profile.getAbstractContainer("sCoreP4", "selected",
                        SelectedContainer.class).getSelected(cosmetic.getType(), cosmeticClass, indexSize[0]).getIcon(profile).getType() + " : 1 : nome>&a" + profile.getAbstractContainer("sCoreP4", "selected",
                        SelectedContainer.class).getSelected(cosmetic.getType(), cosmeticClass, indexSize[0]).getName() + " : desc>\n&eClique para filtrar para todos."), (this.rows * 9) - 4);
            } else {
                this.removeSlotsWith(BukkitUtils.deserializeItemStack("PAPER : 1 : nome>&cNenhum selecionado."), (this.rows * 9) - 4);
            }
                this.removeSlotsWith(BukkitUtils.deserializeItemStack("BARRIER : 1 : nome>&cRemover Cosmético."), (this.rows * 9) - 6);
        }

        this.setItems(items);
        cosmetics.clear();
        items.clear();

        this.register(Core.getInstance());
        this.open();
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent evt) {
        if (evt.getInventory().equals(this.getCurrentInventory())) {
            evt.setCancelled(true);

            if (evt.getWhoClicked().equals(this.player)) {
                Profile profile = Profile.getProfile(this.player.getName());
                if (profile == null) {
                    this.player.closeInventory();
                    return;
                }

                if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(this.getCurrentInventory())) {
                    ItemStack item = evt.getCurrentItem();

                    if (item != null && item.getType() != Material.AIR) {
                        T cosmetic = this.cosmetics.get(item);
                        if (evt.getSlot() == this.previousPage) {
                            EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                            this.openPrevious();
                        } else if (evt.getSlot() == this.nextPage) {
                            EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                            this.openNext();
                        } else if (evt.getSlot() == (this.rows * 9) - 4) {
                            EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                            new me.solarcollections.jvpc.p4.menus.cosmetics.MenuCosmetics<>(profile, this.name, this.cosmeticClass);
                        } else if (evt.getSlot() == (this.rows * 9) - 6) {
                            EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                            this.player.sendMessage("§cEm Breve.");
                            new me.solarcollections.jvpc.p4.menus.cosmetics.MenuCosmeticsAll<>(profile, this.name, this.cosmeticClass);
                        } else if (evt.getSlot() == (this.rows * 9) - 5) {
                            EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                            new me.solarcollections.jvpc.p4.menus.cosmetics.MenuCosmetics<>(profile, this.name, this.cosmeticClass);
                        } else {
                            if (cosmetic != null) {
                                if (cosmetic.has(profile)) {
                                    EnumSound.ITEM_PICKUP.play(this.player, 0.5F, 2.0F);
                                    if (cosmetic.isSelected(profile)) {
                                        profile.getAbstractContainer("sCoreP4", "selected", SelectedContainer.class).setSelected(cosmetic.getType(), 0);
                                    } else {
                                        profile.getAbstractContainer("sCoreP4", "selected", SelectedContainer.class).setSelected(cosmetic);
                                    }

                                    new MenuCosmeticsAll<>(profile, this.name, this.cosmeticClass);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    public void cancel() {
        HandlerList.unregisterAll(this);
        this.cosmeticClass = null;
        this.cosmetics.clear();
        this.cosmetics = null;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent evt) {
        if (evt.getPlayer().equals(this.player)) {
            this.cancel();
        }
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent evt) {
        if (evt.getPlayer().equals(this.player) && evt.getInventory().equals(this.getCurrentInventory())) {
            this.cancel();
        }
    }
}