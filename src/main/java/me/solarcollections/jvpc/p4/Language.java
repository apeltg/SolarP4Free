package me.solarcollections.jvpc.p4;

import me.solarcollections.jvpc.plugin.config.SConfig;
import me.solarcollections.jvpc.plugin.config.SWriter;
import me.solarcollections.jvpc.plugin.config.SWriter.YamlEntry;
import me.solarcollections.jvpc.plugin.logger.SLogger;
import me.solarcollections.jvpc.utils.StringUtils;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

public class Language {


    public static long scoreboards$scroller$every_tick = 1;
    public static List<String> scoreboards$scroller$titles = Arrays
            .asList("§6§lP4 FREE");
    public static List<String> scoreboards$p4 = Arrays
            .asList("             {date}       ", "", "Online: §a%sCore_online%", "Grupo: §a%sCore_role%", "Nick: {nick}", "", "Kit: §a{kit}", "Kills: §a%sCore_P4_kills%", "Mortes: §a%sCore_P4_deaths%", "", "Coins: §e%sCore_P4_coins%", "Cash: §b%sCore_cash%",
                    "", "§esolarcollections.com");
    public static boolean lobby$tab$enabled = true;
    public static String lobby$tab$header = " \n§b§lSOLAR COLLECTIONS\n  §fsolarcollections.com\n ";
    public static String lobby$tab$footer =
            " \n \n§aForúm: §cNenhum\n§aTwitter: §cNenhum\n§aDiscord: §fhttps://discord.gg/zmDdPaQ2UG\n \n                                          §bAdquira VIP acessando: §fhttps://discord.gg/zmDdPaQ2UG                                          \n ";

    public static String chat$delay = "§cAguarde mais {time}s para falar novamente.";
    public static String chat$color$default = "§7";
    public static String chat$color$custom = "§f";
    public static String chat$format$lobby = "{player}{color}: {message}";

    public static String lobby$achievement = " \n§aVocê completou o desafio §f{name}\n ";
    public static String lobby$broadcast = "{player} §6entrou no lobby!";

    public static String lobby$npc$deliveries$deliveries = "§c{deliveries} Entrega{s}";
    public static List<String> lobby$npc$deliveries$hologram = Arrays
            .asList("{deliveries}", "§bEntregador", "§e§lCLIQUE DIREITO");
    public static List<String> lobby$npc$stats$hologram = Arrays
            .asList("§6Suas Estatísticas", "§e§lCLIQUE DIREITO");

    public static String lobby$npc$deliveries$skin$value =
            "eyJ0aW1lc3RhbXAiOjE1ODM0NTc4OTkzMTksInByb2ZpbGVJZCI6IjIxMWNhN2E4ZWFkYzQ5ZTVhYjBhZjMzMTBlODY0M2NjIiwicHJvZmlsZU5hbWUiOiJNYXh0ZWVyIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS85MWU0NTc3OTgzZjEzZGI2YTRiMWMwNzQ1MGUyNzQ2MTVkMDMyOGUyNmI0MGQ3ZDMyMjA3MjYwOWJmZGQ0YTA4IiwibWV0YWRhdGEiOnsibW9kZWwiOiJzbGltIn19fX0=";
    public static String lobby$npc$deliveries$skin$signature =
            "SXnMF3f9x90fa+FdP2rLk/V6/zvMNuZ0sC4RQpPHF9JxdVWYRZm/+DhxkfjCHWKXV/4FSTN8LPPsxXd0XlYSElpi5OaT9/LGhITSK6BbeBfaYhLZnoD0cf9jG9nl9av38KipnkNXI+cO3wttB27J7KHznAmfrJd5bxdO/M0aGQYtwpckchYUBG6pDzaxN7tr4bFxDdxGit8Tx+aow/YtYSQn4VilBIy2y/c2a4PzWEpWyZQ94ypF5ZojvhaSPVl88Fbh+StdgfJUWNN3hNWt31P68KT4Jhx+SkT2LTuDj0jcYsiuxHP6AzZXtOtPPARqM0/xd53CUHCK+TEF5mkbJsG/PZYz/JRR1B1STk4D2cgbhunF87V4NLmCBtF5WDQYid11eO0OnROSUbFduCLj0uJ6QhNRRdhSh54oES7vTi0ja3DftTjdFhPovDAXQxCn+ROhTeSxjW5ZvP6MpmJERCSSihv/11VGIrVRfj2lo9MaxRogQE3tnyMNKWm71IRZQf806hwSgHp+5m2mhfnjYeGRZr44j21zqnSKudDHErPyEavLF83ojuMhNqTTO43ri3MVbMGix4TbIOgB2WDwqlcYLezENBIIkRsYO/Y1r5BWCA7DJ5IlpxIr9TCu39ppVmOGReDWA/Znyox5GP6JIM53kQoTOFBM3QWIQcmXll4=";

    public static int options$coins$kills = 5;
    public static int options$coins$clan$kills = 15;
    public static int options$coins$morte = 5;
    public static int options$coins$emkillstreak = 60;
    public static int habilidade$selecionar$membro = 4;
    public static int habilidade$selecionar$vip = 5;
    public static int habilidade$selecionar$mvp = 6;
    public static int habilidade$selecionar$mvpplus = 7;

    public static String cosmetics$color$locked = "§a";
    public static String cosmetics$color$canbuy = "§e";
    public static String cosmetics$color$unlocked = "§a";
    public static String cosmetics$color$selected = "§6";
    public static String cosmetics$icon$perm_desc$common = "§cVocê não possui permissão.";
    public static String cosmetics$icon$perm_desc$role = "§7Exclusivo para {role} §7ou superior.";
    public static String cosmetics$icon$buy_desc$enough = "§cVocê não possui saldo suficiente.";
    public static String cosmetics$icon$buy_desc$click_to_buy = "§eClique para comprar!";
    public static String cosmetics$icon$has_desc$select = "§eClique para selecionar!";
    public static String cosmetics$icon$has_desc$selected = "§eClique para deselecionar!";

    public static String cosmetics$perk$icon$perm_desc$start = "\n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
    public static String cosmetics$perk$icon$buy_desc$start = "\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n{buy_desc_status}";
    public static String cosmetics$perk$icon$has_desc$start = "\n \n§fRaridade: {rarity}\n \n§eAdquirido!\n§eClique para evoluir!";

    public static String cosmetics$kit$icon$perm_desc$start = "\n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
    public static String cosmetics$kit$icon$buy_desc$start = "\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n{buy_desc_status}";
    public static String cosmetics$kit$icon$has_desc$start = "\n \n§fRaridade: {rarity}\n \n§eAdquirido!\n§eClique para customizar ou evoluir!";

    public static String cosmetics$kill_effect$icon$perm_desc$start = "\n \n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
    public static String cosmetics$kill_effect$icon$buy_desc$start =
            "\n \n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n{buy_desc_status}";
    public static String cosmetics$kill_effect$icon$has_desc$start = "\n \n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n \n{has_desc_status}";

    public static String cosmetics$fall_effect$icon$perm_desc$start =
            "§7Quando você levar dano de queda\n§7sairá partículas de {name}.\n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
    public static String cosmetics$fall_effect$icon$buy_desc$start =
            "§7Quando você levar dano de queda\n§7sairá partículas de {name}.\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n{buy_desc_status}";
    public static String cosmetics$fall_effect$icon$has_desc$start =
            "§7Quando você levar dano de queda\n§7sairá partículas de {name}.\n \n§fRaridade: {rarity}\n \n{has_desc_status}";

    public static String cosmetics$deathcry$icon$perm_desc$start =
            "§7Quando você morrer tocará\n§7o grito de morte {name}.\n \n§6Clique direito para escutar!\n  \n§fRaridade: {rarity}\n \n{perm_desc_status}";
    public static String cosmetics$deathcry$icon$buy_desc$start =
            "§7Quando você morrer tocará\n§7o grito de morte {name}.\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n§6Clique direito para escutar!\n \n{buy_desc_status}";
    public static String cosmetics$deathcry$icon$has_desc$start =
            "§7Quando você morrer tocará\n§7o grito de morte {name}.\n \n§6Clique direito para escutar!\n \n§fRaridade: {rarity}\n \n{has_desc_status}";

    public static String cosmetics$deathmessage$icon$perm_desc$start =
            "\n§6Clique direito para ver!\n \n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
    public static String cosmetics$deathmessage$icon$buy_desc$start =
            "\n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n{buy_desc_status}";
    public static String cosmetics$deathmessage$icon$has_desc$start =
            "\n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n \n{has_desc_status}";

    public static String cosmetics$custuminput$icon$perm_desc$start =
            "\n§6Clique direito para ver!\n \n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
    public static String cosmetics$custuminput$icon$buy_desc$start =
            "\n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n{buy_desc_status}";
    public static String cosmetics$custuminput$icon$has_desc$start =
            "\n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n \n{has_desc_status}";

    public static String ingame$broadcast$suicide = "{name} §emorreu sozinho.";
    public static String ingame$broadcast$default_killed_message = "{name} §efoi abatido por {killer}";

    public static final SLogger LOGGER = ((SLogger) Main.getInstance().getLogger())
            .getModule("LANGUAGE");
    private static final SConfig CONFIG = Main.getInstance().getConfig("language");

    public static void setupLanguage() {
        boolean save = false;
        SWriter writer = Main.getInstance().getWriter(CONFIG.getFile(),
                "SolarP4Free - Criado por JVPC_YouTube\nVersão da configuração: " + Main.getInstance()
                        .getDescription().getVersion());
        for (Field field : Language.class.getDeclaredFields()) {
            if (field.getName().contains("$") && !Modifier.isFinal(field.getModifiers())) {
                String nativeName = field.getName().replace("$", ".").replace("_", "-");

                try {
                    Object value;
                    SWriter.YamlEntryInfo entryInfo = field.getAnnotation(SWriter.YamlEntryInfo.class);

                    if (CONFIG.contains(nativeName)) {
                        value = CONFIG.get(nativeName);
                        if (value instanceof String) {
                            value = StringUtils.formatColors((String) value).replace("\\n", "\n");
                        } else if (value instanceof List) {
                            List l = (List) value;
                            List<Object> list = new ArrayList<>(l.size());
                            for (Object v : l) {
                                if (v instanceof String) {
                                    list.add(StringUtils.formatColors((String) v).replace("\\n", "\n"));
                                } else {
                                    list.add(v);
                                }
                            }

                            value = list;
                        }

                        field.set(null, value);
                        writer.set(nativeName, new YamlEntry(
                                new Object[]{entryInfo == null ? "" : entryInfo.annotation(),
                                        CONFIG.get(nativeName)}));
                    } else {
                        value = field.get(null);
                        if (value instanceof String) {
                            value = StringUtils.deformatColors((String) value).replace("\n", "\\n");
                        } else if (value instanceof List) {
                            List l = (List) value;
                            List<Object> list = new ArrayList<>(l.size());
                            for (Object v : l) {
                                if (v instanceof String) {
                                    list.add(StringUtils.deformatColors((String) v).replace("\n", "\\n"));
                                } else {
                                    list.add(v);
                                }
                            }

                            value = list;
                        }

                        save = true;
                        writer.set(nativeName, new YamlEntry(
                                new Object[]{entryInfo == null ? "" : entryInfo.annotation(), value}));
                    }
                } catch (ReflectiveOperationException e) {
                    LOGGER.log(Level.WARNING, "Unexpected error on settings file: ", e);
                }
            }
        }

        if (save) {
            writer.write();
            CONFIG.reload();
            Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),
                    () -> LOGGER.info("A config §6language.yml §afoi modificada ou criada."));
        }
    }
}
