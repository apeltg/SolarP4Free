package me.solarcollections.jvpc.p4.cosmetics;

import me.solarcollections.jvpc.p4.Language;
import me.solarcollections.jvpc.p4.Main;
import me.solarcollections.jvpc.p4.container.CosmeticsContainer;
import me.solarcollections.jvpc.p4.container.SelectedContainer;
import me.solarcollections.jvpc.player.Profile;
import me.solarcollections.jvpc.p4.cosmetics.types.*;
import me.solarcollections.jvpc.utils.enums.EnumRarity;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
public abstract class Cosmetic {

  private static final List<Cosmetic> COSMETICS = new ArrayList<>();
  protected long cash;
  protected EnumRarity rarity;
  private final long id;
  private final double coins;
  private final String permission;
  private final CosmeticType type;
  
  public Cosmetic(long id, CosmeticType type, double coins, String permission) {
    this.id = id;
    this.coins = coins;
    this.permission = permission;
    this.type = type;
    COSMETICS.add(this);
  }
  
  public static void setupCosmetics() {
    DeathCry.setupDeathCries();
    KillEffect.setupEffects();
    FallEffect.setupFallEffects();
    DeathMessage.setupDeathMessages();
    CustomInput.setupCustomInput();
    Perk.setupPerks();
  }
  
  public static void removeCosmetic(Cosmetic cosmetic) {
    COSMETICS.remove(cosmetic);
  }
  
  public static <T extends Cosmetic> T findById(Class<T> cosmeticClass, long id) {
    return COSMETICS.stream()
        .filter(cosmetic -> (cosmetic.getClass().isAssignableFrom(cosmeticClass) || cosmetic.getClass().getSuperclass().equals(cosmeticClass)) && cosmetic.getId() == id)
        .map(cosmetic -> (T) cosmetic).findFirst().orElse(null);
  }
  
  public static Cosmetic findById(String lootChestID) {
    return COSMETICS.stream().filter(cosmetic -> cosmetic.getLootChestsID().equals(lootChestID)).findFirst().orElse(null);
  }
  
  public static List<Cosmetic> listCosmetics() {
    return COSMETICS;
  }
  
  public static <T extends Cosmetic> List<T> listByType(Class<T> cosmeticClass) {
    return COSMETICS.stream().filter(cosmetic -> cosmetic.getClass().isAssignableFrom(cosmeticClass) || cosmetic.getClass().getSuperclass().equals(cosmeticClass))
        .sorted(Comparator.comparingLong(Cosmetic::getId)).map(cosmetic -> (T) cosmetic).collect(Collectors.toList());
  }
  
  protected static Object getAbsentProperty(String file, String property) {
    InputStream stream = Main.getInstance().getResource(file + ".yml");
    if (stream == null) {
      return null;
    }
    
    return YamlConfiguration.loadConfiguration(new InputStreamReader(stream, StandardCharsets.UTF_8)).get(property);
  }
  
  public void give(Profile profile) {
    profile.getAbstractContainer("sCoreP4", "cosmetics", CosmeticsContainer.class).addCosmetic(this);
  }
  
  public boolean has(Profile profile) {
    return profile.getAbstractContainer("sCoreP4", "cosmetics", CosmeticsContainer.class).hasCosmetic(this);
  }
  
  public boolean isSelected(Profile profile) {
    return profile.getAbstractContainer("sCoreP4", "selected", SelectedContainer.class).isSelected(this);
  }
  
  public boolean isSelectedPerk(Profile profile) {
    int checkIndex = profile.getPlayer().hasPermission("role.mvpplus") ? Language.habilidade$selecionar$mvpplus : profile.getPlayer().hasPermission("role.mvp") ? Language.habilidade$selecionar$mvp
            : profile.getPlayer().hasPermission("role.vip") ? Language.habilidade$selecionar$vip : Language.habilidade$selecionar$membro;
    boolean isSelected = false;
    for (int i = 1; i < checkIndex + 1; i++) {
      if (isSelected) {
        continue;
      }
    }
    return isSelected;
  }
  
  public int getAvailableSlot(Profile profile) {
    int checkIndex = profile.getPlayer().hasPermission("role.mvpplus") ? Language.habilidade$selecionar$mvpplus : profile.getPlayer().hasPermission("role.mvp") ? Language.habilidade$selecionar$mvp
            : profile.getPlayer().hasPermission("role.vip") ? Language.habilidade$selecionar$vip : Language.habilidade$selecionar$membro;
    int isSelected = 1;
    for (int i = 1; i < checkIndex + 1; i++) {
    }
    return isSelected;
  }
  
  public long getId() {
    return this.id;
  }
  
  public String getLootChestsID() {
    return "p4" + this.type.ordinal() + "-" + this.id;
  }
  
  public long getIndex() {
    return 1;
  }
  
  public EnumRarity getRarity() {
    return this.rarity;
  }
  
  public double getCoins() {
    return this.coins;
  }
  
  public long getCash() {
    return this.cash;
  }
  
  public String getPermission() {
    return this.permission;
  }
  
  public CosmeticType getType() {
    return this.type;
  }
  
  public boolean canBuy(Player player) {
    return this.permission.isEmpty() || player.hasPermission(this.permission);
  }
  
  public abstract String getName();
  
  public abstract ItemStack getIcon(Profile profile);
}
