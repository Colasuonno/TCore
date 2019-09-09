package com.tcore.modules;


import com.maxmind.geoip2.DatabaseReader;
import com.tcore.TCore;
import com.tcore.managers.TitansManager;
import com.tcore.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.io.File;

public class GeoModule extends TitansManager {

    private DatabaseReader reader;
    private TCore tCore;

    public GeoModule(TCore tCore) {
        this.tCore = tCore;
    }

    @Override
    public void enable() {
        try {

            if (!new File(tCore.getDataFolder(), "GeoIP2-City.mmdb").exists()) {
                StringUtils.m(ChatColor.RED + "GeoIP2-City Database not found! upload it", Bukkit.getConsoleSender());
            } else {
                File database = new File(tCore.getDataFolder(), "GeoIP2-City.mmdb");
                reader = new DatabaseReader.Builder(database).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disable() {
        super.disable();
    }

    public DatabaseReader getReader() {
        return reader;
    }
}
