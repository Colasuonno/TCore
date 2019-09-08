package com.tcore;

import com.tcore.api.TManager;
import com.tcore.commands.CommandManager;
import com.tcore.gui.listeners.InventoryListener;
import com.tcore.listener.PlayerListener;
import com.tcore.managers.LangManager;
import com.tcore.managers.PlayersManager;
import com.tcore.managers.TitansManager;
import com.tcore.modules.PlayerModule;
import com.tcore.utils.StringUtils;
import com.tcore.utils.YamlConfig;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class TCore extends JavaPlugin {

    @Getter private CommandManager commandManager;
    @Getter private PlayersManager playersManager;
    @Getter private PlayerModule playerModule;
    @Getter private LangManager langManager;

    @Override
    public void onEnable() {

        YamlConfig.create(this, "lang/en_US", true);

        this.langManager = new LangManager(this);
        this.commandManager = new CommandManager(this);
        this.playersManager = new PlayersManager(this);
        this.playerModule = new PlayerModule(this);

        new PlayerListener(this);
        new InventoryListener(this);

        // Loading managers modules
        TitansManager.getManagers()
                .forEach(TManager::enable);

    }

}
