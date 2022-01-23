package me.solarcollections.jvpc.p4.API;

import me.solarcollections.jvpc.utils.BukkitUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class KitAPI {

    public static HashMap<String, String> kit = new HashMap<String, String>();


    public static String getKit(Player p) {
        if (kit.containsKey(p.getName())) {
            return kit.get(p.getName());
        } else {
            return "Nenhum";
        }
    }

    public static void darItens(Player p) {
        kit.put(p.getName(), "Padrão");

        Inventory inv = p.getInventory();

        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        {
            ItemStack item = BukkitUtils.deserializeItemStack("311 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().setChestplate(item);

            ItemStack item1 = BukkitUtils.deserializeItemStack("312 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item1.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item1.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().setLeggings(item1);

            ItemStack item2 = BukkitUtils.deserializeItemStack("313 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item2.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item2.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().setBoots(item2);

            ItemStack item3 = BukkitUtils.deserializeItemStack("310 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item3.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item3.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().setHelmet(item3);

            ItemStack item4 = BukkitUtils.deserializeItemStack("276 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item4.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
            item4.addUnsafeEnchantment(Enchantment.FIRE_ASPECT, 2);
            item4.getItemMeta().spigot().setUnbreakable(true);
            p.getInventory().setItem(0, item4);

            ItemStack item5 = BukkitUtils.deserializeItemStack("322:1 : 64 : nome>&b&ka &aP4 FREE &b&ka");
            p.getInventory().setItem(1, item5);

            ItemStack item6 = BukkitUtils.deserializeItemStack("POTION : 1 : efeito>SPEED:1:12000 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            p.getInventory().setItem(2, item6);

            ItemStack item7 = BukkitUtils.deserializeItemStack("POTION : 1 : efeito>INCREASE_DAMAGE:1:12000 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            p.getInventory().setItem(3, item7);
        }
        {
            ItemStack item = BukkitUtils.deserializeItemStack("311 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item);

            ItemStack item1 = BukkitUtils.deserializeItemStack("312 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item1.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item1.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item1);

            ItemStack item2 = BukkitUtils.deserializeItemStack("313 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item2.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item2.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item2);

            ItemStack item3 = BukkitUtils.deserializeItemStack("310 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item3.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item3.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item3);
        }
        {
            ItemStack item = BukkitUtils.deserializeItemStack("311 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item);

            ItemStack item1 = BukkitUtils.deserializeItemStack("312 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item1.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item1.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item1);

            ItemStack item2 = BukkitUtils.deserializeItemStack("313 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item2.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item2.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item2);

            ItemStack item3 = BukkitUtils.deserializeItemStack("310 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item3.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item3.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item3);
        }
        {
            ItemStack item = BukkitUtils.deserializeItemStack("311 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item);

            ItemStack item1 = BukkitUtils.deserializeItemStack("312 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item1.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item1.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item1);

            ItemStack item2 = BukkitUtils.deserializeItemStack("313 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item2.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item2.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item2);

            ItemStack item3 = BukkitUtils.deserializeItemStack("310 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item3.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item3.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item3);
        }
        {
            ItemStack item = BukkitUtils.deserializeItemStack("311 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item);

            ItemStack item1 = BukkitUtils.deserializeItemStack("312 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item1.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item1.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item1);

            ItemStack item2 = BukkitUtils.deserializeItemStack("313 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item2.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item2.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item2);

            ItemStack item3 = BukkitUtils.deserializeItemStack("310 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item3.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item3.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item3);
        }
        {
            ItemStack item = BukkitUtils.deserializeItemStack("311 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item);

            ItemStack item1 = BukkitUtils.deserializeItemStack("312 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item1.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item1.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item1);

            ItemStack item2 = BukkitUtils.deserializeItemStack("313 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item2.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item2.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item2);

            ItemStack item3 = BukkitUtils.deserializeItemStack("310 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item3.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item3.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item3);
        }
        {
            ItemStack item = BukkitUtils.deserializeItemStack("311 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item);

            ItemStack item1 = BukkitUtils.deserializeItemStack("312 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item1.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item1.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item1);

            ItemStack item2 = BukkitUtils.deserializeItemStack("313 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item2.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item2.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item2);

            ItemStack item3 = BukkitUtils.deserializeItemStack("310 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item3.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item3.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item3);
        }
        {
            ItemStack item = BukkitUtils.deserializeItemStack("311 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item);

            ItemStack item1 = BukkitUtils.deserializeItemStack("312 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item1.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item1.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item1);

            ItemStack item2 = BukkitUtils.deserializeItemStack("313 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item2.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item2.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item2);

            ItemStack item3 = BukkitUtils.deserializeItemStack("310 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item3.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item3.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item3);
        }
        {
            ItemStack item = BukkitUtils.deserializeItemStack("311 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item);

            ItemStack item1 = BukkitUtils.deserializeItemStack("312 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item1.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item1.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item1);

            ItemStack item2 = BukkitUtils.deserializeItemStack("313 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item2.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item2.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item2);

            ItemStack item3 = BukkitUtils.deserializeItemStack("310 : 1 : nome>&b&ka &aP4 FREE &b&ka");
            item3.addUnsafeEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 4);
            item3.addUnsafeEnchantment(Enchantment.DURABILITY, 5);
            p.getInventory().addItem(item3);
        }
    }
}
