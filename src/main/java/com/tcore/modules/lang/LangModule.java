package com.tcore.modules.lang;

import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

public class LangModule {

    @Getter
    private String prefix;
    @Getter
    private boolean usingPrefix;
    @Getter
    private Map<String, String> messages = new HashMap<>();

    public void load(FileConfiguration fileConfiguration) {

        this.prefix = fileConfiguration.getString("settings.prefix");
        this.usingPrefix = fileConfiguration.getBoolean("settings.display-prefix");

        // Load all messages
        for (String sections : fileConfiguration.getConfigurationSection("lang").getKeys(false)) {
            messages.put(sections, fileConfiguration.getString("lang." + sections));
        }

    }

}


