package com.tcore.modules.settings;

import com.tcore.TCore;
import com.tcore.utils.YamlConfig;
import lombok.Data;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;

@Data
public class DataModule {

    private Map<String, Object> values = new HashMap<>();
    private TCore tCore;

    public DataModule(TCore tCore) {
        this.tCore = tCore;
    }

    public void enable() {

        FileConfiguration fileConfiguration = YamlConfig.getConfiguration(tCore, "data");

        for (String sections : fileConfiguration.getConfigurationSection("").getKeys(false)) {
            values.put(sections, fileConfiguration.get(sections));
        }

    }

    public boolean getBoolean(String value) {
        return Boolean.parseBoolean(getString(value));
    }

    public int getInt(String value) {
        return Integer.parseInt(getString(value));
    }

    public String getString(String value) {
        return String.valueOf(values.get(value));
    }

}

