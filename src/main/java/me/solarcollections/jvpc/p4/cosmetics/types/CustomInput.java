package me.solarcollections.jvpc.p4.cosmetics.types;

import me.solarcollections.jvpc.cash.CashManager;
import me.solarcollections.jvpc.p4.Language;
import me.solarcollections.jvpc.p4.Main;
import me.solarcollections.jvpc.p4.container.SelectedContainer;
import me.solarcollections.jvpc.p4.cosmetics.Cosmetic;
import me.solarcollections.jvpc.p4.cosmetics.CosmeticType;
import me.solarcollections.jvpc.player.Profile;
import me.solarcollections.jvpc.player.role.Role;
import me.solarcollections.jvpc.plugin.config.SConfig;
import me.solarcollections.jvpc.plugin.logger.SLogger;
import me.solarcollections.jvpc.utils.BukkitUtils;
import me.solarcollections.jvpc.utils.StringUtils;
import me.solarcollections.jvpc.utils.enums.EnumRarity;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class CustomInput extends Cosmetic {

    public static final SLogger LOGGER = ((SLogger) Main.getInstance().getLogger()).getModule("CUSTOM_INPUT");
    private final String name;
    private final String icon;
    private final List<String> messages;

    public CustomInput(long id, EnumRarity rarity, double coins, long cash, String permission, String name, String icon, List<String> messages) {
        super(id, CosmeticType.CUSTOM_INPUT, coins, permission);
        this.name = name;
        this.icon = icon;
        this.messages = messages;
        this.rarity = rarity;
        this.cash = cash;
    }

    public static void setupCustomInput() {
        SConfig config = Main.getInstance().getConfig("cosmetics", "custominput");

        for (String key : config.getKeys(false)) {
            long id = config.getInt(key + ".id");
            double coins = config.getDouble(key + ".coins");
            if (!config.contains(key + ".cash")) {
                config.set(key + ".cash", getAbsentProperty("custominput", key + ".cash"));
            }
            long cash = config.getInt(key + ".cash", 0);
            String permission = config.getString(key + ".permission");
            String name = config.getString(key + ".name");
            String icon = config.getString(key + ".icon");
            if (!config.contains(key + ".rarity")) {
                config.set(key + ".rarity", getAbsentProperty("custominput", key + ".rarity"));
            }
            List<String> sound = config.getStringList(key + ".messages");

            new CustomInput(id, EnumRarity.fromName(config.getString(key + ".rarity")), coins, cash, permission, name, icon, sound);
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    public List<String> getMessages() {
        return messages;
    }

    public String getRandomMessage() {
        return StringUtils.formatColors(messages.get(ThreadLocalRandom.current().nextInt(messages.size())));
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
                Language.cosmetics$custuminput$icon$has_desc$start.replace("{has_desc_status}", isSelected ? Language.cosmetics$icon$has_desc$selected : Language.cosmetics$icon$has_desc$select) :
                canBuy ?
                        Language.cosmetics$custuminput$icon$buy_desc$start
                                .replace("{buy_desc_status}", (coins >= this.getCoins() || (CashManager.CASH && cash >= this.getCash())) ? Language.cosmetics$icon$buy_desc$click_to_buy : Language.cosmetics$icon$buy_desc$enough) :
                        Language.cosmetics$custuminput$icon$perm_desc$start
                                .replace("{perm_desc_status}", (role == null ? Language.cosmetics$icon$perm_desc$common : Language.cosmetics$icon$perm_desc$role.replace("{role}", role.getName()))))
                .replace("{name}", this.name).replace("{rarity}", this.getRarity().getName()).replace("{coins}", StringUtils.formatNumber(this.getCoins())).replace("{cash}", StringUtils.formatNumber(this.getCash()));
        ItemStack item = BukkitUtils.deserializeItemStack(this.icon + " : nome>" + color + this.name + " : desc>" + desc);
        if (isSelected) {
            BukkitUtils.putGlowEnchantment(item);
        }

        return item;
    }
}
