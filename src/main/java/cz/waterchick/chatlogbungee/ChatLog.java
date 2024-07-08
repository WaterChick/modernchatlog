package cz.waterchick.chatlogbungee;

import cz.waterchick.Core;
import cz.waterchick.chatlogbungee.commands.Command;
import cz.waterchick.chatlogbungee.events.PlayerChat;
import cz.waterchick.interfaces.Platform;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;

import java.io.File;
import java.util.logging.Logger;

public final class ChatLog extends Plugin implements Platform {

    private ProxyServer proxyServer;
    private Logger logger;
    private File dataFolder;
    private String serverVersion;

    private Core core;
    @Override
    public void onEnable() {
        this.proxyServer = getProxy();
        this.logger = proxyServer.getLogger();
        this.dataFolder = getDataFolder();
        this.serverVersion = ProxyServer.getInstance().getVersion();

        this.core = new Core(this);
        core.start();

        ProxyServer.getInstance().getPluginManager().registerCommand(this, new Command(core, proxyServer));
        ProxyServer.getInstance().getPluginManager().registerListener(this, new PlayerChat());
    }

    @Override
    public void onDisable() {
        core.stop();
    }

    @Override
    public String getPlatformName() {
        return "BungeeCord";
    }

    @Override
    public String getServerVersion() {
        return this.serverVersion;
    }

    @Override
    public File getFolder() {
        return this.dataFolder;
    }

    @Override
    public Logger getLogger(){
        return this.logger;
    }
}
