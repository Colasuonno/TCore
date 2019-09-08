package com.tcore.commands;

import com.tcore.TCore;
import com.tcore.api.objects.TPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.defaults.BukkitCommand;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class FineCommand extends BukkitCommand {

    private CommandManager.CommandType type;
    private TCore plugin;

    /**
     * Auto-Register commands throw FineCommand constructor
     *
     * @param name       of the command
     * @param permission to execute this command
     * @param type       who can execute this command
     * @param aliases    of the command
     */
    public FineCommand(TCore plugin, String name, String permission, CommandManager.CommandType type, List<String> aliases) {
        super(name, "", "", aliases);
        super.setPermission(permission);
        this.plugin = plugin;
        this.type = type;
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.register(name, this);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * Auto-Register commands throw FineCommand constructor
     *
     * @param name       of the command
     * @param permission to execute this command
     * @param type       who can execute this command
     * @param aliases    of the command
     */
    public FineCommand(TCore plugin, String name, String permission, CommandManager.CommandType type, String... aliases) {
        this(plugin, name, permission, type, Arrays.asList(aliases));
    }

    public FineCommand(TCore plugin, String name, String permission, CommandManager.CommandType type) {
        this(plugin, name, permission, type, new ArrayList<>());
    }

    public FineCommand(TCore plugin, String name, String permission) {
        this(plugin, name, permission, CommandManager.CommandType.ALL);
    }

    public FineCommand(TCore plugin, String name) {
        this(plugin, name, null);
    }

    /**
     * Command Handler for each FineCommand
     *
     * @param commandSender who perform the command
     * @param s             label
     * @param strings       command arguments
     * @return true if the command was successful
     */
    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {

        if ((type.equals(CommandManager.CommandType.CONSOLE) && commandSender instanceof Player) || (type.equals(CommandManager.CommandType.PLAYER) && commandSender instanceof ConsoleCommandSender))
            commandSender.sendMessage(ChatColor.RED + "You cannot execute this command");
        else {
            if (isAllowed(commandSender)) {
                if (commandSender instanceof Player) {
                    TPlayer tPlayer = plugin.getPlayersManager().fromPlayer((Player)commandSender);
                    run(tPlayer, commandSender, s, strings);
                } else run(null, commandSender, s, strings);
            } else commandSender.sendMessage(ChatColor.RED + "You do not have permission");
        }
        return true;
    }


    /**
     * General run for each fine command, CALLED only if authorized
     *
     * @param sender who perform the command
     * @param label  command name
     * @param args   command arguments
     */
    public abstract void run(TPlayer tPlayer, CommandSender sender, String label, String[] args);

    private boolean isAllowed(CommandSender commandSender) {
        String perm = this.getPermission();
        return perm == null || commandSender.hasPermission(perm);
    }

    public CommandManager.CommandType getType() {
        return type;
    }
}
