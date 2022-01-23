package me.solarcollections.jvpc.p4.menus.cosmetics;

import me.solarcollections.jvpc.Core;
import me.solarcollections.jvpc.cash.CashManager;
import me.solarcollections.jvpc.libraries.menu.PagedPlayerMenu;
import me.solarcollections.jvpc.p4.container.SelectedContainer;
import me.solarcollections.jvpc.p4.cosmetics.Cosmetic;
import me.solarcollections.jvpc.p4.cosmetics.CosmeticType;
import me.solarcollections.jvpc.p4.cosmetics.object.AbstractPreview;
import me.solarcollections.jvpc.p4.cosmetics.object.preview.KillEffectPreview;
import me.solarcollections.jvpc.p4.cosmetics.types.CustomInput;
import me.solarcollections.jvpc.p4.cosmetics.types.DeathCry;
import me.solarcollections.jvpc.p4.cosmetics.types.DeathMessage;
import me.solarcollections.jvpc.p4.cosmetics.types.KillEffect;
import me.solarcollections.jvpc.p4.menus.MenuShop;
import me.solarcollections.jvpc.player.Profile;
import me.solarcollections.jvpc.player.role.Role;
import me.solarcollections.jvpc.utils.BukkitUtils;
import me.solarcollections.jvpc.utils.StringUtils;
import me.solarcollections.jvpc.utils.enums.EnumSound;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MenuCosmetics<T extends Cosmetic> extends PagedPlayerMenu {

    private Class<T> cosmeticClass;
    private Map<ItemStack, T> cosmetics = new HashMap<>();

    public MenuCosmetics(Profile profile, String name, Class<T> cosmeticClass) {
        super(profile.getPlayer(), "P4 Free - " + name, (Cosmetic.listByType(cosmeticClass).size() / 7) + 4);
        this.cosmeticClass = cosmeticClass;
        this.previousPage = (this.rows * 9) - 9;
        this.nextPage = (this.rows * 9) - 1;
        this.onlySlots(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34);

        String desc = "§7Para a Loja.";
        if (Objects.requireNonNull(Cosmetic.listByType(cosmeticClass).stream().findFirst().orElse(null)).getType().toString().contains("EFFECT")) {
            desc = "§7Para Animações.";
        }
        this.removeSlotsWith(BukkitUtils.deserializeItemStack("ARROW : 1 : nome>&cVoltar : desc>" + desc), (this.rows * 9) - 5);

        List<ItemStack> items = new ArrayList<>();
        List<T> cosmetics = Cosmetic.listByType(cosmeticClass);
        for (T cosmetic : cosmetics) {
            ItemStack icon = cosmetic.getIcon(profile);
            items.add(icon);
            final int[] indexSize = {1};
            this.cosmetics.put(icon, cosmetic);
            if (profile.getAbstractContainer("sCoreP4", "selected",
                    SelectedContainer.class).getSelected(cosmetic.getType(), cosmeticClass, indexSize[0]) != null) {
                this.removeSlotsWith(BukkitUtils.deserializeItemStack("" + profile.getAbstractContainer("sCoreP4", "selected",
                        SelectedContainer.class).getSelected(cosmetic.getType(), cosmeticClass, indexSize[0]).getIcon(profile).getType() + " : 1 : nome>&a" + profile.getAbstractContainer("sCoreP4", "selected",
                        SelectedContainer.class).getSelected(cosmetic.getType(), cosmeticClass, indexSize[0]).getName() + " : desc>\n&eClique para filtrar pelos adquiridos."), (this.rows * 9) - 4);
            } else {
                this.removeSlotsWith(BukkitUtils.deserializeItemStack("PAPER : 1 : nome>&cNenhum selecionado."), (this.rows * 9)- 4);
            }
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
                        if (evt.getSlot() == this.previousPage) {
                            EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                            this.openPrevious();
                        } else if (evt.getSlot() == this.nextPage) {
                            EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                            this.openNext();
                        } else if (evt.getSlot() == (this.rows * 9) - 4) {
                            new MenuCosmeticsAll<>(profile, this.name, this.cosmeticClass);
                            EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                        } else if (evt.getSlot() == (this.rows * 9) - 5) {
                            EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                            new MenuShop(profile);
                        } else {
                            T cosmetic = this.cosmetics.get(item);
                            if (cosmetic != null) {
                                if (evt.isRightClick()) {
                                    if (cosmetic.getType() == CosmeticType.DEATH_CRY) {
                                        ((DeathCry) cosmetic).getSound().play(this.player, ((DeathCry) cosmetic).getVolume(), ((DeathCry) cosmetic).getSpeed());
                                        return;
                                    } else if (cosmetic.getType() == CosmeticType.DEATH_MESSAGE) {
                                        player.sendMessage("\n§eMensagens que poderão ser exibidas ao abater seu oponente:\n  \n");
                                        ((DeathMessage) cosmetic).getMessages().forEach(message -> {
                                            player.sendMessage(" §8▪ " + StringUtils.formatColors(message.replace("{name}", "§7Jogador").replace("{killer}", Role.getColored(player.getName()))));
                                        });
                                        new MenuCosmetics<>(profile, "Mensagens de Morte", DeathMessage.class);
                                        return;
                                    //} else if (cosmetic.getType() == CosmeticType.DEATH_HOLOGRAM) {
                                    //loc = new Location(player.getLocation().getWorld(), player.getLocation().getX(), player.getLocation().getY() + 1, player.getLocation().getZ());

                                    //DeathHologram dh = ((DeathHologram) cosmetic);

                                    //<String> result = new ArrayList<>();
                                    //for (String line : dh.getHologram()) {
                                    //line = line.replace("&", "§");
                                    //line = line.replace("{name}", "§7Jogador");
                                    //line = line.replace("{killer}", Role.getColored(player.getName()));
                                    //result.add(line);
                                    //}
                                    //yHoloAPI h = new yHoloAPI(this.loc, result);

                                    //new BukkitRunnable(){ //pode exportar
                                    //@Override
                                    //public void run() {
                                    //h.destroy(player);
                                    //hologram.remove(player);
                                //}
                                //}.runTaskLater(Main.getInstance(), 2*80);
                                //if (!hologram.contains(player)){
                                //h.display(player);
                                //hologram.add(player);
                                //} else{
                                //nada
                                //}
                                //return;
                                } else if (cosmetic.getType() == CosmeticType.CUSTOM_INPUT) {
                                        player.sendMessage("\n§eMensagens que poderão ser exibidas ao você entrar no lobby:\n  \n");
                                        ((CustomInput) cosmetic).getMessages().forEach(message -> {
                                            player.sendMessage(" §8▪ " + StringUtils.formatColors(message.replace("{name}", "§7Jogador").replace("{killer}", Role.getPrefixed(player.getName()))));
                                        });
                                        player.sendMessage("");
                                        return;
                                    } else if (cosmetic.getType() == CosmeticType.KILL_EFFECT) {
                                        if (!AbstractPreview.canDoKillEffect()) {
                                            if (player.hasPermission("sskywars.cmd.skywars")) {
                                                EnumSound.VILLAGER_NO.play(player, 1.0F, 1.0F);
                                                player.sendMessage("§cSete as localizações da previsualização utilizando /sw preview killeffect");
                                            }
                                            return;
                                        }

                                        new KillEffectPreview(profile, (KillEffect) cosmetic);
                                        player.closeInventory();
                                        return;
                                    }
                                }

                                if (!cosmetic.has(profile)) {
                                    if (!cosmetic.canBuy(this.player) || (profile.getCoins("sCoreP4") < cosmetic.getCoins() && (CashManager.CASH && profile
                                            .getStats("sCoreProfile", "cash") < cosmetic.getCash()))) {
                                        EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
                                        return;
                                    }

                                    EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                                    if (!CashManager.CASH || cosmetic.getCash() == 0) {
                                        new MenuBuyCosmetic<>(profile, this.name.replace("P4 Free - ", ""), cosmetic, this.cosmeticClass);
                                    } else {
                                        new MenuBuyCashCosmetic<>(profile, this.name.replace("P4 Free - ", ""), cosmetic, this.cosmeticClass);
                                    }
                                    return;
                                }

                                if (!cosmetic.canBuy(this.player)) {
                                    EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
                                    this.player.sendMessage("§cVocê não possui permissão suficiente para continuar.");
                                    return;
                                }

                                EnumSound.ITEM_PICKUP.play(this.player, 0.5F, 2.0F);
                                if (cosmetic.isSelected(profile)) {
                                    profile.getAbstractContainer("sCoreP4", "selected", SelectedContainer.class).setSelected(cosmetic.getType(), 0);
                                } else {
                                    profile.getAbstractContainer("sCoreP4", "selected", SelectedContainer.class).setSelected(cosmetic);
                                }

                                new MenuCosmetics<>(profile, this.name.replace("P4 Free - ", ""), this.cosmeticClass);
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