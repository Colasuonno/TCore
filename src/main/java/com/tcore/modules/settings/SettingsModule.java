package com.tcore.modules.settings;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.tcore.TCore;
import com.tcore.managers.TitansManager;
import com.tcore.utils.YamlConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class SettingsModule  {

    private Map<String, Object> values = new HashMap<>();
    private TCore tCore;

    public SettingsModule(TCore tCore) {
        this.tCore = tCore;
    }

    public void enable() {

        FileConfiguration fileConfiguration = YamlConfig.getConfiguration(tCore, "settings");

        for (String sections : fileConfiguration.getConfigurationSection("").getKeys(false)) {
            values.put(sections, fileConfiguration.get(sections));
        }

        System.out.println(values);

    }

    public boolean getBoolean(String value){
        return Boolean.parseBoolean(getString(value));
    }

    public int getInt(String value){
        return Integer.parseInt(getString(value));
    }

    public List<String> getStringList(String value){
        return (List<String>)values.get(value);
    }

    public String getString(String value){
        return String.valueOf(values.get(value));
    }

}
