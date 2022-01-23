package me.solarcollections.jvpc.p4.cosmetics.types.killeffects;

import me.solarcollections.jvpc.p4.cosmetics.types.KillEffect;
import me.solarcollections.jvpc.utils.enums.EnumRarity;
import me.solarcollections.jvpc.utils.particles.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import java.util.concurrent.ThreadLocalRandom;

public class Hearts extends KillEffect {

    public Hearts(ConfigurationSection section) {
        super(section.getLong("id"), EnumRarity.fromName(section.getString("rarity")), section.getDouble("coins"), section.getInt("cash"), section.getString("permission"),
                section.getString("name"), section.getString("icon"));
    }

    @Override
    public void execute(Player viewer, Location location) {
        if (viewer == null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                ParticleEffect.HEART.display((float) Math.floor(Math.random() * 2.0F), 0.0F, (float) Math.floor(Math.random() * 2.0F), 0.0F, 5, location, player);
                ParticleEffect.HEART.display((float) Math.floor(Math.random() * 2.0F), 1.0F, (float) Math.floor(Math.random() * 2.0F), 0.0F, 5, location, player);
                ParticleEffect.HEART.display((float) Math.floor(Math.random() * 2.0F), 1.5F, (float) Math.floor(Math.random() * 2.0F), 0.0F, 5, location, player);
            }
        } else {
            ParticleEffect.HEART.display(ThreadLocalRandom.current().nextFloat() * 2.0F,
                    0.0F, 0.0F, ThreadLocalRandom.current().nextFloat() * 2.0F, 5, location, viewer);
            ParticleEffect.HEART.display(ThreadLocalRandom.current().nextFloat() * 2.0F, 1.0F, 0.0F,
                    ThreadLocalRandom.current().nextFloat() * 2.0F, 5, location, viewer);
            ParticleEffect.HEART.display(ThreadLocalRandom.current().nextFloat() * 2.0F, 1.5F,
                    ThreadLocalRandom.current().nextFloat() * 2.0F, 0.0F, 5, location, viewer);
        }
    }
}