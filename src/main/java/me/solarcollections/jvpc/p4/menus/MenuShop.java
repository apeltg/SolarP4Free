package me.solarcollections.jvpc.p4.menus;

import me.solarcollections.jvpc.libraries.menu.PlayerMenu;
import me.solarcollections.jvpc.menus.profile.MenuBoosters;
import me.solarcollections.jvpc.p4.Main;
import me.solarcollections.jvpc.p4.cosmetics.Cosmetic;
import me.solarcollections.jvpc.p4.cosmetics.types.*;
import me.solarcollections.jvpc.p4.menus.animations.MenuAnimations;
import me.solarcollections.jvpc.p4.menus.cosmetics.MenuCosmetics;
import me.solarcollections.jvpc.p4.menus.cosmetics.perks.MenuPerks;
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
import java.util.List;

public class MenuShop extends PlayerMenu {
  
  public MenuShop(Profile profile) {
    super(profile.getPlayer(), "Loja - P4 FREE", 6);

    long max = 0;
    long owned = 0;
    long percentage = max == 0 ? 100 : (owned * 100) / max;
    String color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
    this.setItem(13, BukkitUtils.deserializeItemStack(
        "IRON_SWORD : 1 : esconder>tudo : nome>&aKits : desc>&7Um verdadeiro guerreiro sempre estará\n&7preparado para o combate.\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar!"));

    List<Perk> perks = Cosmetic.listByType(Perk.class);
    max = perks.size();
    owned = perks.stream().filter(perk -> perk.has(profile)).count();
    percentage = max == 0 ? 100 : (owned * 100) / max;
    color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
    perks.clear();
    this.setItem(22, BukkitUtils.deserializeItemStack(
        "EXP_BOTTLE : 1 : nome>&aHabilidades : desc>&7Aproveite de vantagens únicas\n&7para lhe auxiliar nas partidas.\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar ou evoluir!"));

    List<DeathCry> deathcries = Cosmetic.listByType(DeathCry.class);
    max = deathcries.size();
    owned = deathcries.stream().filter(deathcry -> deathcry.has(profile)).count();
    percentage = max == 0 ? 100 : (owned * 100) / max;
    color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
    deathcries.clear();
    this.setItem(37, BukkitUtils.deserializeItemStack(
        "GHAST_TEAR : 1 : nome>&aGritos de Morte : desc>&7Sons que irão ser reproduzidos\n&7toda vez que você morrer.\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar ou selecionar!"));

    List<DeathMessage> deathmessages = Cosmetic.listByType(DeathMessage.class);
    max = deathmessages.size();
    owned = deathmessages.stream().filter(cage -> cage.has(profile)).count();
    percentage = max == 0 ? 100 : (owned * 100) / max;
    color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
    deathmessages.clear();
    this.setItem(38, BukkitUtils.deserializeItemStack(
        "BOOK_AND_QUILL : 1 : nome>&aMensagens de Morte : desc>&7Anuncie o abate do seu inimigo de\n&7uma forma estilosa com mensagens de morte.\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar ou selecionar!"));

    List<CustomInput> custominput = Cosmetic.listByType(CustomInput.class);
    max = custominput.size();
    owned = custominput.stream().filter(customInput -> customInput.has(profile)).count();
    percentage = max == 0 ? 100 : (owned * 100) / max;
    color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
    custominput.clear();
    this.setItem(39, BukkitUtils.deserializeItemStack(
            "NETHER_STAR : 1 : nome>&aMensagens de Entrada : desc>&7Em nosso servidor existe um sistema\n&7aonde você poderá mudar\n&7sua mensagem de entrada!\n&7Deixando nossos lobbies muito mais divertidos!\\n \\n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\\n \\n&eClique para comprar ou selecionar!"));

    this.setItem(41, BukkitUtils.deserializeItemStack(
        "POTION:8194 : 1 : esconder>tudo : nome>&aBoosters de Coins : desc>&7Adquira um Booster de Coins e receba\n&7mais coins durante as partidas.\n \n&eClique para comprar ou ativar um booster!"));

    this.setItem(43, BukkitUtils.deserializeItemStack(
        "397:1 : 1 : nome>&aItens Místicos : desc>&7Compre itens místicos\n&7que possuem mágias negras obscuras...\n \n&eClique para comprar!"));

    List<Cosmetic> totaleffects = new ArrayList<>();
    totaleffects.addAll(Cosmetic.listByType(KillEffect.class));
    totaleffects.addAll(Cosmetic.listByType(FallEffect.class));;
    max = totaleffects.size();
    owned = totaleffects.stream().filter(killEffect -> killEffect.has(profile)).count();
    percentage = max == 0 ? 100 : (owned * 100) / max;
    color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
    this.setItem(42, BukkitUtils.deserializeItemStack(
        "321 : 1 : nome>&aAnimações : desc>&7Adicione animações às suas ações\n&7para se destacar dentro do jogo\\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar ou selecionar!"));
    
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
            if (evt.getSlot() == 37) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuCosmetics<>(profile, "Gritos de Morte", DeathCry.class);
            } else if (evt.getSlot() == 22) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuPerks<>(profile, "Habilidade", Perk.class);
            } else if (evt.getSlot() == 39) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuCosmetics<>(profile, "Mensagens de Entrada", CustomInput.class);
            } else if (evt.getSlot() == 41) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuBoosters(profile);
            } else if (evt.getSlot() == 43) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
            } else if (evt.getSlot() == 13) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
            } else if (evt.getSlot() == 38) {
              new MenuCosmetics<>(profile, "Mensagens de Morte", DeathMessage.class);
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
            } else if (evt.getSlot() == 42) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuAnimations(profile);
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
