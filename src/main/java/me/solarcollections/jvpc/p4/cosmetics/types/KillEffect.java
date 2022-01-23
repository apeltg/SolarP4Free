package me.solarcollections.jvpc.p4.cosmetics.types;

import me.solarcollections.jvpc.cash.CashManager;
import me.solarcollections.jvpc.p4.Language;
import me.solarcollections.jvpc.p4.Main;
import me.solarcollections.jvpc.p4.container.SelectedContainer;
import me.solarcollections.jvpc.p4.cosmetics.Cosmetic;
import me.solarcollections.jvpc.p4.cosmetics.CosmeticType;
import me.solarcollections.jvpc.p4.cosmetics.types.killeffects.*;
import me.solarcollections.jvpc.player.Profile;
import me.solarcollections.jvpc.player.role.Role;
import me.solarcollections.jvpc.plugin.config.SConfig;
import me.solarcollections.jvpc.utils.BukkitUtils;
import me.solarcollections.jvpc.utils.StringUtils;
import me.solarcollections.jvpc.utils.enums.EnumRarity;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public abstract class KillEffect extends Cosmetic {
  
  private static final SConfig CONFIG = Main.getInstance().getConfig("cosmetics", "killeffects");
  private final String name;
  private final String icon;
  
  public KillEffect(long id, EnumRarity rarity, double coins, long cash, String permission, String name, String icon) {
    super(id, CosmeticType.KILL_EFFECT, coins, permission);
    this.name = name;
    this.icon = icon;
    this.rarity = rarity;
    this.cash = cash;
  }
  
  public static void setupEffects() {
    checkIfAbsent("blood_explosion");
    checkIfAbsent("firework");
    checkIfAbsent("humantorch");
    checkIfAbsent("headrocket");
    checkIfAbsent("finalsmash");
    checkIfAbsent("hearts");
    checkIfAbsent("explosao");
    checkIfAbsent("furia_de_thor");
    
    new BloodExplosion(CONFIG.getSection("blood_explosion"));
    new HumanTorch(CONFIG.getSection("humantorch"));
    new Firework(CONFIG.getSection("firework"));
    new HeadRocket(CONFIG.getSection("headrocket"));
    new FinalSmash(CONFIG.getSection("finalsmash"));
    new Hearts(CONFIG.getSection("hearts"));
    new Tnt(CONFIG.getSection("explosao"));
    new FuriaDeThor(CONFIG.getSection("furia_de_thor"));
  }
  
  private static void checkIfAbsent(String key) {
    if (CONFIG.contains(key)) {
      return;
    }
    
    FileConfiguration config = YamlConfiguration.loadConfiguration(new InputStreamReader(Main.getInstance().getResource("killeffects.yml"), StandardCharsets.UTF_8));
    for (String dataKey : config.getConfigurationSection(key).getKeys(false)) {
      CONFIG.set(key + "." + dataKey, config.get(key + "." + dataKey));
    }
  }
  
  public void execute(Location location) {
    this.execute(null, location);
  }
  
  public abstract void execute(Player viewer, Location location);
  
  @Override
  public String getName() {
    return this.name;
  }
  
  @Override
  public ItemStack getIcon(Profile profile) {
    double coins = profile.getCoins("sCoreP4");
    long cash = profile.getStats("sCoreProfile", "cash");
    boolean has = this.has(profile);
    boolean canBuy = this.canBuy(profile.getPlayer());
    boolean isSelected = this.isSelected(profile);
    if (isSelected && !canBuy) {
      isSelected = false;
      profile.getAbstractContainer("sCoreP4", "selected", SelectedContainer.class).setSelected(getType(), 0);
    }
    
    Role role = Role.getRoleByPermission(this.getPermission());
    String color = has ?
        (isSelected ? Language.cosmetics$color$selected : Language.cosmetics$color$unlocked) :
        (coins >= this.getCoins() || (CashManager.CASH && cash >= this.getCash())) && canBuy ? Language.cosmetics$color$canbuy : Language.cosmetics$color$locked;
    String desc = (has && canBuy ?
        Language.cosmetics$kill_effect$icon$has_desc$start.replace("{has_desc_status}", isSelected ? Language.cosmetics$icon$has_desc$selected : Language.cosmetics$icon$has_desc$select) :
        canBuy ?
            Language.cosmetics$kill_effect$icon$buy_desc$start
                .replace("{buy_desc_status}", (coins >= this.getCoins() || (CashManager.CASH && cash >= this.getCash())) ? Language.cosmetics$icon$buy_desc$click_to_buy : Language.cosmetics$icon$buy_desc$enough) :
            Language.cosmetics$kill_effect$icon$perm_desc$start
                .replace("{perm_desc_status}", (role == null ? Language.cosmetics$icon$perm_desc$common : Language.cosmetics$icon$perm_desc$role.replace("{role}", role.getName()))))
        .replace("{name}", this.name).replace("{rarity}", this.getRarity().getName()).replace("{coins}", StringUtils.formatNumber(this.getCoins())).replace("{cash}", StringUtils.formatNumber(this.getCash()));
    ItemStack item = BukkitUtils.deserializeItemStack(this.icon + desc + " : nome>" + (color + this.name));
    if (isSelected) {
      BukkitUtils.putGlowEnchantment(item);
    }
    
    return item;
  }
}
