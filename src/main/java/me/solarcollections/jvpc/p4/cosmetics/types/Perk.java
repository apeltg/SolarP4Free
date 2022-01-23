package me.solarcollections.jvpc.p4.cosmetics.types;

import me.solarcollections.jvpc.cash.CashManager;
import me.solarcollections.jvpc.p4.Language;
import me.solarcollections.jvpc.p4.Main;
import me.solarcollections.jvpc.p4.container.CosmeticsContainer;
import me.solarcollections.jvpc.p4.container.SelectedContainer;
import me.solarcollections.jvpc.p4.cosmetics.Cosmetic;
import me.solarcollections.jvpc.p4.cosmetics.CosmeticType;
import me.solarcollections.jvpc.p4.cosmetics.object.perk.PerkLevel;
import me.solarcollections.jvpc.p4.cosmetics.types.perk.Pele_Dura;
import me.solarcollections.jvpc.player.Profile;
import me.solarcollections.jvpc.player.role.Role;
import me.solarcollections.jvpc.plugin.config.SConfig;
import me.solarcollections.jvpc.utils.BukkitUtils;
import me.solarcollections.jvpc.utils.StringUtils;
import me.solarcollections.jvpc.utils.enums.EnumRarity;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

public abstract class Perk extends Cosmetic {
  
  protected static final SConfig CONFIG = Main.getInstance().getConfig("cosmetics", "perks");
  protected List<PerkLevel> levels;
  private final String name;
  private final String icon;
  
  public Perk(long id, String key, String permission, String name, String icon, List<PerkLevel> levels) {
    super(id, CosmeticType.PERK, 0.0, permission);
    this.name = name;
    this.icon = icon;
    this.levels = levels;
    this.rarity = this.getRarity(key);
  }
  
  public static void setupPerks() {
    checkIfAbsent("pele_dura");

    // Se a habilidade estiver como ativada registra-lá.
    if (CONFIG.getBoolean("pele_dura.enabled")) {
      new Pele_Dura(1, "pele_dura");
    }
  }
  
  private static void checkIfAbsent(String key) {
    if (CONFIG.contains(key)) {
      return;
    }
    
    FileConfiguration config = YamlConfiguration.loadConfiguration(new InputStreamReader(Main.getInstance().getResource("perks.yml"), StandardCharsets.UTF_8));
    for (String dataKey : config.getConfigurationSection(key).getKeys(false)) {
      CONFIG.set(key + "." + dataKey, config.get(key + "." + dataKey));
    }
  }
  
  protected EnumRarity getRarity(String key) {
    if (!CONFIG.contains(key + ".rarity")) {
      CONFIG.set(key + ".rarity", getAbsentProperty("perks", key + ".rarity"));
    }
    
    return EnumRarity.fromName(CONFIG.getString(key + ".rarity"));
  }
  
  protected void setupLevels(String key) {
    ConfigurationSection section = CONFIG.getSection(key);
    for (String level : section.getConfigurationSection("levels").getKeys(false)) {
      if (!section.contains("levels." + level + ".cash")) {
        CONFIG.set(key + ".levels." + level + ".cash", getAbsentProperty("perks", key + ".levels." + level + ".cash"));
      }
      
      PerkLevel perkLevel =
          new PerkLevel(section.getDouble("levels." + level + ".coins"), section.getInt("levels." + level + ".cash"), section.getString("levels." + level + ".description"),
              new HashMap<>());
      for (String property : section.getConfigurationSection("levels." + level).getKeys(false)) {
        if (!property.equals("coins") && !property.equals("cash") && !property.equals("description")) {
          perkLevel.getValues().put(property, section.get("levels." + level + "." + property));
        }
      }
      
      this.levels.add(perkLevel);
    }
  }
  
  @Override
  public String getName() {
    return this.name;
  }
  
  @Override
  public double getCoins() {
    return this.getFirstLevel().getCoins();
  }
  
  @Override
  public long getCash() {
    return this.getFirstLevel().getCash();
  }
  
  public PerkLevel getFirstLevel() {
    return this.levels.get(0);
  }
  
  public PerkLevel getCurrentLevel(Profile profile) {
    return this.levels.get((int) (profile.getAbstractContainer("sCoreP4", "cosmetics", CosmeticsContainer.class).getLevel(this) - 1));
  }
  
  public List<PerkLevel> getLevels() {
    return this.levels;
  }
  
  @Override
  public ItemStack getIcon(Profile profile) {
    return this.getIcon(profile, true);
  }
  
  public ItemStack getIcon(Profile profile, boolean select) {
    return this.getIcon(profile, true, select);
  }
  
  public ItemStack getIcon(Profile profile, boolean useDesc, boolean select) {
    double coins = profile.getCoins("sCoreP4");
    long cash = profile.getStats("sCoreProfile", "cash");
    boolean has = this.has(profile);
    boolean canBuy = this.canBuy(profile.getPlayer());
    boolean isSelected = this.isSelectedPerk(profile);
    if (isSelected && !canBuy) {
      isSelected = false;
      profile.getAbstractContainer("sCoreP4", "selected", SelectedContainer.class).setSelected(getType(), 0);
    }
    
    Role role = Role.getRoleByPermission(this.getPermission());
    int currentLevel = (int) profile.getAbstractContainer("sCoreP4", "cosmetics", CosmeticsContainer.class).getLevel(this);
    PerkLevel perkLevel = this.levels.get(currentLevel - 1);
    String levelName = " " + (currentLevel > 3 ? currentLevel == 4 ? "IV" : "V" : StringUtils.repeat("I", currentLevel));
    String color = has ?
        Language.cosmetics$color$unlocked :
        (coins >= this.getCoins() || (CashManager.CASH && cash >= this.getCash())) && canBuy ? Language.cosmetics$color$canbuy : Language.cosmetics$color$locked;
    String desc = "";
    if (useDesc) {
      desc = (has && canBuy ?
          (select ? "\n \n" + (isSelected ? Language.cosmetics$icon$has_desc$selected : Language.cosmetics$icon$has_desc$select) : Language.cosmetics$kit$icon$has_desc$start) :
          select ?
              "" :
              canBuy ?
                  Language.cosmetics$kit$icon$buy_desc$start.replace("{buy_desc_status}", (coins >= this.getCoins() || (CashManager.CASH && cash >= this.getCash())) ?
                      Language.cosmetics$icon$buy_desc$click_to_buy :
                      Language.cosmetics$icon$buy_desc$enough) :
                  Language.cosmetics$kit$icon$perm_desc$start
                      .replace("{perm_desc_status}", role == null ? Language.cosmetics$icon$perm_desc$common : Language.cosmetics$icon$perm_desc$role.replace("{role}", role.getName())))
          .replace("{name}", this.name).replace("{rarity}", this.getRarity().getName()).replace("{coins}", StringUtils.formatNumber(this.getCoins()))
          .replace("{cash}", StringUtils.formatNumber(this.getCash()));
    }
    ItemStack item = BukkitUtils.deserializeItemStack(this.icon.replace("{description}", perkLevel.getDescription()) + desc + " : nome>" + color + this.name + levelName);
    if (select && isSelected) {
      BukkitUtils.putGlowEnchantment(item);
    }
    
    return item;
  }
}
