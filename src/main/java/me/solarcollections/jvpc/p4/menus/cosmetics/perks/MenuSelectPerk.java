package me.solarcollections.jvpc.p4.menus.cosmetics.perks;

import me.solarcollections.jvpc.Core;
import me.solarcollections.jvpc.libraries.menu.PagedPlayerMenu;
import me.solarcollections.jvpc.p4.Language;
import me.solarcollections.jvpc.p4.container.SelectedContainer;
import me.solarcollections.jvpc.p4.cosmetics.Cosmetic;
import me.solarcollections.jvpc.p4.cosmetics.types.Perk;
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
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuSelectPerk<T extends Perk> extends PagedPlayerMenu {
  
  private Class<T> cosmeticClass;
  private Map<ItemStack, T> cosmetics = new HashMap<>();
  public MenuSelectPerk(Profile profile, Class<T> cosmeticClass) {
    super(profile.getPlayer(), "Habilidades", 6);
    this.cosmeticClass = cosmeticClass;
    this.previousPage = 45;
    this.nextPage = 53;
    this.onlySlots(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34);

    StringBuilder sb = new StringBuilder();
    final int[] selectedSize = {0};
    final int[] indexSize = {1};
    Cosmetic.listByType(Perk.class).forEach(f -> {
      if (profile.getAbstractContainer("sCoreP4", "selected",
              SelectedContainer.class).getSelected(f.getType(), Perk.class, indexSize[0]) != null) {
        sb.append("\n").append(" &8▪ &7").append(profile.getAbstractContainer("sCoreSkyWars", "selected",
                SelectedContainer.class).getSelected(f.getType(), Perk.class, indexSize[0]).getName());
        selectedSize[0]++;
      }
      indexSize[0]++;
    });
    int checkIndex = profile.getPlayer().hasPermission("role.mvpplus") ? Language.habilidade$selecionar$mvpplus : profile.getPlayer().hasPermission("role.mvp") ? Language.habilidade$selecionar$mvp
            : profile.getPlayer().hasPermission("role.vip") ? Language.habilidade$selecionar$vip : Language.habilidade$selecionar$membro;
    this.removeSlotsWith(BukkitUtils.deserializeItemStack("175 : 1 : nome>&aPontos de Habilidade : desc>&7Utilize os seus pontos para\n&7selecionar diferentes habilidades\n&7nas suas partidas!\n \n&fHabilidades selecionadas:" +
            (sb.toString().isEmpty() ? "\n &8▪ &7Nenhuma" : sb.toString()) + (checkIndex - selectedSize[0] < 1 ? "" : "\n \n&fPontos restantes: &e" +
            (checkIndex - selectedSize[0]) + "" + (checkIndex - selectedSize[0] > 1 ? "" : "")) + "\n \n&aClique para gerenciar!"), (this.rows * 9) - 5);
    
    List<ItemStack> items = new ArrayList<>(), sub = new ArrayList<>();
    List<T> cosmetics = Cosmetic.listByType(cosmeticClass);
    for (T cosmetic : cosmetics) {
      ItemStack icon = cosmetic.getIcon(profile, true);
      if (cosmetic.has(profile)) {
        items.add(icon);
      } else {
        icon.setType(Material.STAINED_GLASS_PANE);
        ItemMeta meta = icon.getItemMeta();
        meta.getEnchants().forEach((key, value) -> meta.removeEnchant(key));
        icon.setDurability((short) 14);
        sub.add(icon);
      }
      this.cosmetics.put(icon, cosmetic);
    }
    
    items.addAll(sub);
    sub.clear();
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
            } else if (evt.getSlot() == 49) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
            } else if (evt.getSlot() == (this.rows * 9) - 5) {
              EnumSound.ITEM_PICKUP.play(this.player, 0.5F, 2.0F);
            } else {
              T cosmetic = this.cosmetics.get(item);
              if (cosmetic != null) {
                if (!cosmetic.has(profile)) {
                  EnumSound.NOTE_BASS_DRUM.play(this.player, 0.5F, 1.0F);
                  return;
                }
                
                if (!cosmetic.canBuy(this.player)) {
                  EnumSound.NOTE_BASS_DRUM.play(this.player, 0.5F, 1.0F);
                  this.player.sendMessage("§cVocê não possui permissão suficiente para continuar.");
                  return;
                }
                
                EnumSound.ITEM_PICKUP.play(this.player, 0.5F, 2.0F);
                if (cosmetic.isSelectedPerk(profile)) {
                  profile.getAbstractContainer("sCoreP4", "selected", SelectedContainer.class).
                      setSelected(cosmetic.getType(), 0,
                          profile.getAbstractContainer("sCoreP4", "selected", SelectedContainer.class).
                              getIndex(cosmetic));
                } else {
                  profile.getAbstractContainer("sCoreP4", "selected",
                      SelectedContainer.class).setSelected(cosmetic.getType(),
                      cosmetic.getId(), cosmetic.getAvailableSlot(profile));
                }
                
                new MenuSelectPerk<>(profile, this.cosmeticClass);
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
