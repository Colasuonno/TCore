package com.tcore.utils;

import org.apache.commons.lang.Validate;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;

public class YamlConfig {

    private static FileConfiguration configuration;
    private static File file;

    public static void create(Plugin plugin, String filename, boolean saveResource) {
        init(plugin, filename);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            plugin.saveResource(filename + ".yml", saveResource);
        }
    }

    public static void createNewFile(Plugin plugin, String filename) {
        init(plugin, filename);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static FileConfiguration getConfiguration(Plugin plugin, String filename) {
        init(plugin,filename);
        if (file.exists()) {
            loadConfiguration();
            return configuration;
        } else {
            return null;
        }
    }

    public static FileConfiguration getConfiguration(File file) {
        init(file);
        if (file.exists()) {
            loadConfiguration();
            return configuration;
        } else {
            return null;
        }
    }

    public static void saveConfig(Plugin plugin, FileConfiguration configuration, String filename) {
        init(plugin, filename);
        if (file.exists()) {
            saveConfig(configuration);
        }
    }

    private static void init(Plugin plugin, String filename) {
        Validate.notNull(plugin, "Plugin cannot be null");
        file = new File(plugin.getDataFolder(), filename + ".yml");
        configuration = new YamlConfiguration();
    }

    private static void init(File filez) {
        file = filez;
        configuration = new YamlConfiguration();
    }

    public static void fastModify(Plugin plugin, String filename, String path, Object object) {
        FileConfiguration fileConfiguration = getConfiguration(plugin,filename);
        fileConfiguration.set(path, object);
        saveConfig(plugin,fileConfiguration, filename);
    }

    /**
     * You have to init configuration && file too
     */
    private static void loadConfiguration(FileConfiguration fileConfiguration) {
        try {
            fileConfiguration.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void loadConfiguration() {
        loadConfiguration(configuration);
    }

    /**
     * You have to init and load configuration && file too
     */
    private static void saveConfig(FileConfiguration fileConfiguration) {
        try {
            fileConfiguration.save(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void saveConfig() {
        saveConfig(configuration);
    }
}
