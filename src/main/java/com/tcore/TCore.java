package com.tcore;

import com.tcore.api.TManager;
import com.tcore.commands.CommandManager;
import com.tcore.gui.listeners.InventoryListener;
import com.tcore.listener.PlayerListener;
import com.tcore.managers.LangManager;
import com.tcore.managers.PlayersManager;
import com.tcore.managers.TitansManager;
import com.tcore.modules.GeoModule;
import com.tcore.modules.PlayerModule;
import com.tcore.modules.scoreboard.ScoreboardModule;
import com.tcore.modules.settings.DataModule;
import com.tcore.modules.settings.SettingsModule;
import com.tcore.task.AsyncScoreboardPlacer;
import com.tcore.utils.YamlConfig;
import lombok.Getter;
import me.lucko.luckperms.api.LuckPermsApi;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class TCore extends JavaPlugin {


    @Getter private CommandManager commandManager;
    @Getter private PlayersManager playersManager;
    @Getter private PlayerModule playerModule;
    @Getter private LangManager langManager;
    @Getter private GeoModule geoModule;
    @Getter private SettingsModule settingsModule;
    @Getter private DataModule dataModule;
    @Getter private ScoreboardModule scoreboardModule;

    @Getter
    private LuckPermsApi luckPerms;


    @Override
    public void onEnable() {

        YamlConfig.create(this, "lang/en_US", true);
        YamlConfig.create(this, "settings", true);
        YamlConfig.create(this, "data", true);

        // First module to load
        this.dataModule = new DataModule(this);
        this.settingsModule = new SettingsModule(this);
        this.settingsModule.enable();
        this.dataModule.enable();

        this.scoreboardModule = new ScoreboardModule();
        this.langManager = new LangManager(this);
        this.commandManager = new CommandManager(this);
        this.playersManager = new PlayersManager(this);
        this.playerModule = new PlayerModule(this);
        this.geoModule = new GeoModule(this);

        new PlayerListener(this);
        new InventoryListener(this);

        // Loading managers modules
        TitansManager.getManagers()
                .forEach(TManager::enable);

        // Tasks
        new AsyncScoreboardPlacer(this).runTaskTimerAsynchronously(this, 20L, 20L);

        RegisteredServiceProvider<LuckPermsApi> provider = Bukkit.getServicesManager().getRegistration(LuckPermsApi.class);
        if (provider != null) {
            luckPerms = provider.getProvider();
        }
    }
}
