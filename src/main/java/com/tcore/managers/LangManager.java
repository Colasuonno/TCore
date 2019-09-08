package com.tcore.managers;

import com.tcore.TCore;
import com.tcore.exception.TCoreException;
import com.tcore.lang.LangModule;
import com.tcore.utils.YamlConfig;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.logging.Level;

public class LangManager extends TitansManager {

    private TCore plugin;
    private static final String CURRENT_LANG = "en_US";
    @Getter private LangModule langModule;

    public LangManager(TCore plugin) {
        this.plugin = plugin;
        this.langModule = new LangModule();
    }

    @Override
    public void enable() {

        try {

            File lang = new File(plugin.getDataFolder(), "lang");

            if (lang.isDirectory()) {
                if (lang.listFiles() != null) {
                    for (File files : lang.listFiles()) {
                        if (files.getName().contains(CURRENT_LANG)) {
                            FileConfiguration fileConfiguration = YamlConfig.getConfiguration(files);
                            if (fileConfiguration != null) this.langModule.load(fileConfiguration);
                            else throw new TCoreException("FileConfiguration cannot be null!");
                        }
                    }
                }
            } else throw new TCoreException("Lang must be a directory!");
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void disable() {
        super.disable();
    }
}
