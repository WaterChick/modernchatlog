package cz.waterchick.chatlogvelocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.command.SimpleCommand;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyShutdownEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import cz.waterchick.Core;
import cz.waterchick.chatlogvelocity.commands.Command;
import cz.waterchick.chatlogvelocity.events.Event;
import cz.waterchick.interfaces.Platform;

import java.io.File;
import java.nio.file.Path;
import java.util.logging.Logger;

@Plugin(
        id = "chatlog-velocity",
        name = "chatlog-velocity",
        version = "1.0.3",
        authors = "Water_Chick"
)
public class ChatLog implements Platform {

    @Inject
    private Logger logger;
    private final Core core;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event)
    {
        proxyServer.getEventManager().register(this, new Event());
    }

    @Subscribe
    public void onProxyShutDown(ProxyShutdownEvent event){
        core.stop();
    }

    private final Path dataDirectory;
    private final ProxyServer proxyServer;

    @Inject
    public ChatLog(ProxyServer proxyServer, Logger logger, @DataDirectory Path dataDirectory) {
        this.proxyServer = proxyServer;
        this.logger = logger;
        this.dataDirectory = dataDirectory;

        this.core =  new Core(this);
        core.start();

        registerCommands();
    }

    @Override
    public String getPlatformName() {
        return "Velocity";
    }

    @Override
    public String getServerVersion() {
        return proxyServer.getVersion().getVersion();
    }

    @Override
    public File getFolder() {
        return dataDirectory.toFile();
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    private void registerCommands() {
        CommandManager commandManager = proxyServer.getCommandManager();
        // Here you can add meta for the command, as aliases and the plugin to which it belongs (RECOMMENDED)
        CommandMeta commandMeta = commandManager.metaBuilder("chatlog")
                // This will create a new alias for the command "/test"
                // with the same arguments and functionality
                .plugin(this)
                .build();
        SimpleCommand simpleCommand = new Command(core, proxyServer);

        commandManager.register(commandMeta, simpleCommand);
    }
}
