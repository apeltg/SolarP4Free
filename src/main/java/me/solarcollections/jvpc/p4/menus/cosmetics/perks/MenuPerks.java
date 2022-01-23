package me.solarcollections.jvpc.p4.menus.cosmetics.perks;

import me.solarcollections.jvpc.Core;
import me.solarcollections.jvpc.cash.CashManager;
import me.solarcollections.jvpc.libraries.menu.PagedPlayerMenu;
import me.solarcollections.jvpc.p4.Language;
import me.solarcollections.jvpc.p4.container.SelectedContainer;
import me.solarcollections.jvpc.p4.cosmetics.Cosmetic;
import me.solarcollections.jvpc.p4.cosmetics.types.Perk;
import me.solarcollections.jvpc.p4.menus.MenuShop;
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

public class MenuPerks<T extends Perk> extends PagedPlayerMenu {
  
  private String mode;
  private Class<T> cosmeticClass;
  private Map<ItemStack, T> cosmetics = new HashMap<>();
  public MenuPerks(Profile profile, String name, Class<T> cosmeticClass) {
    super(profile.getPlayer(), "Habilidades (" + name + ")", (Cosmetic.listByType(cosmeticClass).size() / 7) + 4);
    this.mode = name;
    this.cosmeticClass = cosmeticClass;
    this.previousPage = (this.rows * 9) - 9;
    this.nextPage = (this.rows * 9) - 1;
    this.onlySlots(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34);
    
    this.removeSlotsWith(BukkitUtils.deserializeItemStack("ARROW : 1 : nome>&cVoltar : desc>&7Para a Loja."), (this.rows * 9) - 5);
    
    StringBuilder sb = new StringBuilder();
    final int[] selectedSize = {0};
    final int[] indexSize = {1};
    Cosmetic.listByType(Perk.class).forEach(f -> {
      if (profile.getAbstractContainer("sCoreP4", "selected",
          SelectedContainer.class).getSelected(f.getType(), Perk.class, indexSize[0]) != null) {
        sb.append("\n").append(" &8▪ &7").append(profile.getAbstractContainer("sCoreP4", "selected",
            SelectedContainer.class).getSelected(f.getType(), Perk.class, indexSize[0]).getName());
        selectedSize[0]++;
      }
      indexSize[0]++;
    });
    int checkIndex = profile.getPlayer().hasPermission("role.mvpplus") ? Language.habilidade$selecionar$mvpplus : profile.getPlayer().hasPermission("role.mvp") ? Language.habilidade$selecionar$mvp
            : profile.getPlayer().hasPermission("role.vip") ? Language.habilidade$selecionar$vip : Language.habilidade$selecionar$membro;
    this.removeSlotsWith(BukkitUtils.deserializeItemStack("175 : 1 : nome>&aPontos de Habilidade : desc>&7Utilize os seus pontos para\n&7selecionar diferentes habilidades\n&7nas suas partidas!\n \n&fHabilidades selecionadas:" +
            (sb.toString().isEmpty() ? "\n &8▪ &7Nenhuma" : sb.toString()) + (checkIndex - selectedSize[0] < 1 ? "" : "\n \n&fPontos restantes: &e" +
            (checkIndex - selectedSize[0]) + "" + (checkIndex - selectedSize[0] > 1 ? "" : "")) + "\n \n&aClique para gerenciar!"), (this.rows * 9) - 4);
    
    List<ItemStack> items = new ArrayList<>();
    List<T> cosmetics = Cosmetic.listByType(cosmeticClass);
    for (T cosmetic : cosmetics) {
      ItemStack icon = cosmetic.getIcon(profile, true, false);
      items.add(icon);
      this.cosmetics.put(icon, cosmetic);
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
            } else if (evt.getSlot() == (this.rows * 9) - 5) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuShop(profile);
            } else if (evt.getSlot() == (this.rows * 9) - 4) {
              EnumSound.ITEM_PICKUP.play(this.player, 0.5F, 2.0F);
              new Gerenciamento<>(profile, Perk.class);
            } else {
              T cosmetic = this.cosmetics.get(item);
              if (cosmetic != null) {
                if (!cosmetic.has(profile)) {
                  if (!cosmetic.canBuy(this.player) || (profile.getCoins("sCoreP4") < cosmetic.getCoins() && (CashManager.CASH && profile
                      .getStats("sCoreProfile", "cash") < cosmetic.getCash()))) {
                    EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
                    return;
                  }
                  
                  EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                  if (!CashManager.CASH && cosmetic.getCash() == 0) {
                    new MenuBuyPerk<>(profile, this.mode, cosmetic, this.cosmeticClass);
                  } else {
                    new MenuBuyCashPerk<>(profile, this.mode, cosmetic, this.cosmeticClass);
                  }
                  return;
                }
                
                if (!cosmetic.canBuy(this.player)) {
                  EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
                  this.player.sendMessage("§cVocê não possui permissão suficiente para continuar.");
                  return;
                }
                
                EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                new MenuPerkUpgrade<>(profile, this.mode, cosmetic, this.cosmeticClass);
              }
            }
          }
        }
      }
    }
  }
  
  public void cancel() {
    HandlerList.unregisterAll(this);
    this.mode = null;
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
