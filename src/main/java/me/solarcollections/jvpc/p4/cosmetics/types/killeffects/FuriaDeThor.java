package me.solarcollections.jvpc.p4.cosmetics.types.killeffects;

import me.solarcollections.jvpc.p4.Main;
import me.solarcollections.jvpc.p4.cosmetics.types.KillEffect;
import me.solarcollections.jvpc.player.Profile;
import me.solarcollections.jvpc.utils.enums.EnumRarity;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.List;

public class FuriaDeThor extends KillEffect {

    private LightningStrike raio;
    protected final List<Player> REMOVE_DAMAGE = new ArrayList<>();

    public FuriaDeThor(ConfigurationSection section) {
        super(section.getLong("id"), EnumRarity.fromName(section.getString("rarity")), section.getDouble("coins"), section.getInt("cash"), section.getString("permission"),
                section.getString("name"), section.getString("icon"));
    }

    @Override
    public void execute(Player viewer, Location location) {
        if (viewer == null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                this.raio = player.getWorld().spawn(player.getLocation(), LightningStrike.class);
                player.getLocation().getWorld().strikeLightning(player.getLocation());
                this.raio.setFireTicks(0);
                REMOVE_DAMAGE.add(player);
            }
            Bukkit.getPluginManager().registerEvents(new Listener() {
                @EventHandler
                public void onEntityDamage(EntityDamageEvent evt) {
                    if (evt.getEntity() instanceof Player) {
                        Player player = (Player) evt.getEntity();

                        Profile profile = Profile.getProfile(player.getName());
                        if (profile != null) {
                            if (evt.getCause() == EntityDamageEvent.DamageCause.LIGHTNING  && REMOVE_DAMAGE.contains(player)) {
                                evt.setCancelled(true);
                                REMOVE_DAMAGE.remove(player);
                            }
                        }
                    }
                }
            }, Main.getInstance());
        }
    }
}