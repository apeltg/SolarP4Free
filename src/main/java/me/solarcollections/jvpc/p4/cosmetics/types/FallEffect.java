package me.solarcollections.jvpc.p4.cosmetics.types;

import me.solarcollections.jvpc.cash.CashManager;
import me.solarcollections.jvpc.game.GameState;
import me.solarcollections.jvpc.libraries.npclib.NPCLibrary;
import me.solarcollections.jvpc.libraries.npclib.api.npc.NPC;
import me.solarcollections.jvpc.p4.Language;
import me.solarcollections.jvpc.p4.Main;
import me.solarcollections.jvpc.p4.container.SelectedContainer;
import me.solarcollections.jvpc.p4.cosmetics.Cosmetic;
import me.solarcollections.jvpc.p4.cosmetics.CosmeticType;
import me.solarcollections.jvpc.p4.game.GameArena;
import me.solarcollections.jvpc.player.Profile;
import me.solarcollections.jvpc.player.role.Role;
import me.solarcollections.jvpc.plugin.config.SConfig;
import me.solarcollections.jvpc.plugin.logger.SLogger;
import me.solarcollections.jvpc.utils.BukkitUtils;
import me.solarcollections.jvpc.utils.StringUtils;
import me.solarcollections.jvpc.utils.enums.EnumRarity;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import java.util.logging.Level;

import static org.bukkit.Bukkit.getPluginManager;

public class FallEffect extends Cosmetic implements Listener {

    public static final SLogger LOGGER = ((SLogger) Main.getInstance().getLogger()).getModule("FALL_EFFECT");
    private final String name;
    private final String icon;
    private final EnumParticle particle;

    public FallEffect(long id, EnumRarity rarity, double coins, long cash, String permission, String name, String icon, EnumParticle particle) {
        super(id, CosmeticType.FALL_EFFECT, coins, permission);
        this.name = name;
        this.icon = icon;
        this.particle = particle;
        this.rarity = rarity;
        this.cash = cash;

        try {
            getPluginManager().getClass().getDeclaredMethod("registerEvents", Listener.class, Plugin.class)
                    .invoke(getPluginManager(), this, Main.getInstance());
        } catch (Exception exception) {
            LOGGER.log(Level.WARNING, "Ocorreu um erro ao registar o evento: ", exception);
        }
    }

    public static void setupFallEffects() {
        SConfig config = Main.getInstance().getConfig("cosmetics", "falleffects");

        for (String key : config.getKeys(false)) {
            long id = config.getInt(key + ".id");
            double coins = config.getDouble(key + ".coins");
            if (!config.contains(key + ".cash")) {
                config.set(key + ".cash", getAbsentProperty("falleffects", key + ".cash"));
            }
            long cash = config.getInt(key + ".cash", 0);
            String permission = config.getString(key + ".permission");
            String name = config.getString(key + ".name");
            String icon = config.getString(key + ".icon");
            if (!config.contains(key + ".rarity")) {
                config.set(key + ".rarity", getAbsentProperty("falleffects", key + ".rarity"));
            }
            EnumParticle particle;
            try {
                particle = EnumParticle.valueOf(config.getString(key + ".particle"));
            } catch (Exception ex) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> LOGGER.warning("A partÃ­cula \"" + config.getString(key + ".particle") + "\" nao foi encontrada."));
                continue;
            }

            new FallEffect(id, EnumRarity.fromName(config.getString(key + ".rarity")), coins, cash, permission, name, icon, particle);
        }
    }

    @Override
    public String getName() {
        return this.name;
    }
    SConfig config = Main.getInstance().getConfig("cosmetics", "falleffects");
    @EventHandler(priority = EventPriority.MONITOR)
    public void onEntityDamageEvent(EntityDamageEvent evt) {
        if (!(evt.getEntity() instanceof Player)) {
            return;
        }
        NPC npc = NPCLibrary.getNPC(evt.getEntity());
        if (npc != null) {
            return;
        }
        Player player = (Player) evt.getEntity();
        Location loc = player.getLocation();
        if (GameArena.playernaarena.contains(player)) {
            for (float grau = 0; grau < 360; grau++) {
                double cos = Math.cos(Math.toRadians(grau));
                double sin = Math.sin(Math.toRadians(grau));


                double x = loc.getX() + (cos);
                double x2 = loc.getX() + (cos) * 2;
                double z = loc.getZ() + (sin);
                double z2 = loc.getZ() + (sin) * 2;
                Profile profile = Profile.getProfile(player.getName());
                if (player.getNoDamageTicks() < 1
                        && isSelected(profile) && canBuy(player) && has(profile)
                        && evt.getCause() != null && evt.getCause() == EntityDamageEvent.DamageCause.FALL) {
                    for (String key : config.getKeys(false)) {
                        PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(getParticle(), true, (float) x, (float) loc.getY(), (float) z, 0, 0, 0, 0, 1);
                        PacketPlayOutWorldParticles packet2 = new PacketPlayOutWorldParticles(getParticle(), true, (float) x2, (float) loc.getY(), (float) z2, 0, 0, 0, 0, 1);
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
                            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet2);
                        }
                    }
                }
            }
        }
    }

    public EnumParticle getParticle() {
        return this.particle;
    }

    @Override
    public EnumRarity getRarity() {
        return this.rarity;
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
                Language.cosmetics$fall_effect$icon$has_desc$start.replace("{has_desc_status}", isSelected ? Language.cosmetics$icon$has_desc$selected : Language.cosmetics$icon$has_desc$select) :
                canBuy ?
                        Language.cosmetics$fall_effect$icon$buy_desc$start
                                .replace("{buy_desc_status}", (coins >= this.getCoins() || (CashManager.CASH && cash >= this.getCash())) ? Language.cosmetics$icon$buy_desc$click_to_buy : Language.cosmetics$icon$buy_desc$enough) :
                        Language.cosmetics$fall_effect$icon$perm_desc$start
                                .replace("{perm_desc_status}", (role == null ? Language.cosmetics$icon$perm_desc$common : Language.cosmetics$icon$perm_desc$role.replace("{role}", role.getName()))))
                .replace("{name}", this.name).replace("{rarity}", this.getRarity().getName()).replace("{coins}", StringUtils.formatNumber(this.getCoins())).replace("{cash}", StringUtils.formatNumber(this.getCash()));
        ItemStack item = BukkitUtils.deserializeItemStack(this.icon + " : nome>" + color + this.name + " : desc>" + desc);
        if (isSelected) {
            BukkitUtils.putGlowEnchantment(item);
        }

        return item;
    }
}