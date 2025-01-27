package com.rdproject.waterping.plugin;

import com.rdproject.waterping.utils.*;
import lombok.extern.java.*;
import net.md_5.bungee.api.*;
import net.md_5.bungee.api.plugin.*;
import net.md_5.bungee.config.*;

import java.io.*;
import java.nio.file.*;

import static com.rdproject.waterping.utils.ConstantsUtil.*;

@Log
public class LoaderUtil {

    public static void GetConfig() {
        if (!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdir();
        File file = new File(plugin.getDataFolder(), "config.yml");
        if (!file.exists())
            try {
                InputStream in = plugin.getResourceAsStream("config.yml");
                Files.copy(in, file.toPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public static void LoadConfig() {
        try {
            cg = ConfigurationProvider.getProvider(YamlConfiguration.class)
                    .load(new File(plugin.getDataFolder(), "config.yml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void LoadUtils() {
        new Metrics(plugin, 14225);
        Boolean checkupdates = cg.getBoolean("Check-Updates");
        if (Boolean.TRUE.equals(checkupdates)) {
            UpdateChecker updateChecker = new UpdateChecker(plugin, 99861);
            try {
                if (updateChecker.checkForUpdates()) {
                    log.info(LIST);
                    log.info("    §8• §bWaterPing §8•");
                    log.info("");
                    log.info("§8× §7Update Available!");
                    log.info("§8× §7Download it from Spigot!");
                    log.info("");
                    log.info(LIST);
                } else {
                    log.info(LIST);
                    log.info("    §8• §bWaterPing §8•");
                    log.info("");
                    log.info("§8× §7You are using the Latest Version!");
                    log.info("");
                    log.info(LIST);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void LoadMessage() {
        log.info(STARTUP_MESSAGE);
    }

    public static void LoadListeners() {
        NOPERM = cg.getString("Prefix") + cg.getString("NoPermission");
        PREFIX = cg.getString("Prefix");
    }

    public static void LoadCommands() {
        PluginManager pm = ProxyServer.getInstance().getPluginManager();
        pm.registerCommand(plugin, new PingReload());
        pm.registerCommand(plugin, new Ping());
    }

    public static void LoadFeatures() {
        GetConfig();
        LoadConfig();
        LoadListeners();
        LoadCommands();
        LoadMessage();
        LoadUtils();
    }


}

