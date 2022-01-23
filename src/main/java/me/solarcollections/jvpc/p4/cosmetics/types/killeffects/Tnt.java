package me.solarcollections.jvpc.p4.cosmetics.types.killeffects;

import me.solarcollections.jvpc.p4.cosmetics.types.KillEffect;
import me.solarcollections.jvpc.utils.enums.EnumRarity;
import me.solarcollections.jvpc.utils.particles.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class Tnt extends KillEffect {

    public Tnt(ConfigurationSection section) {
        super(section.getLong("id"), EnumRarity.fromName(section.getString("rarity")), section.getDouble("coins"), (long) section.getInt("cash"), section.getString("permission"), section.getString("name"), section.getString("icon"));
    }

    @Override
    public void execute(Player viewer, Location location) {
        if (viewer == null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                ParticleEffect.EXPLOSION_LARGE.display((float) 0.0, 1.0F, 0.0F, 0.0F, 5, location, player);
            }
        } else {
            ParticleEffect.EXPLOSION_LARGE.display(0.0F,
                    1.0F, 0.0F, ThreadLocalRandom.current().nextFloat() * 2.0F, 5, location, viewer);
        }
    }
}

