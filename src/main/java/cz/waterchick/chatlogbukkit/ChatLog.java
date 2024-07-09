package cz.waterchick.chatlogbukkit;

import cz.waterchick.Core;
import cz.waterchick.chatlogbukkit.TabCompleter.CommandTabComplete;
import cz.waterchick.chatlogbukkit.commands.Command;
import cz.waterchick.chatlogbukkit.events.PlayerChat;
import cz.waterchick.interfaces.Platform;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public final class ChatLog extends JavaPlugin implements Platform {

    private Core core;

    @Override
    public void onEnable() {
        this.core = new Core(this);
        core.start();

        getServer().getPluginManager().registerEvents(new PlayerChat(), this);
        PluginCommand command = getCommand("chatlog");
        if(command != null){
            command.setExecutor(new Command(core));
            command.setTabCompleter(new CommandTabComplete());
        }


    }

    @Override
    public void onDisable() {
        core.stop();
    }

    @Override
    public String getPlatformName() {
        return "Bukkit";
    }

    @Override
    public String getServerVersion() {
        return getServer().getVersion();
    }

    @Override
    public File getFolder() {
        return getDataFolder();
    }

    @Override
    public Logger getPluginLogger() {
        return getServer().getLogger();
    }
}
