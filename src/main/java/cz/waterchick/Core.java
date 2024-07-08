package cz.waterchick;

import cz.waterchick.configs.DataConfig;
import cz.waterchick.configs.MainConfig;
import cz.waterchick.interfaces.Platform;

public class Core {

    private final Platform platform;
    private DataConfig dataConfig;
    private MainConfig mainConfig;

    public static String coreVersion = "1.0.4";

    public Core(Platform platform){
        this.platform = platform;
    }

    public void start(){
        platform.getPluginLogger().info("ModernChatLog-Core INFO: ");
        platform.getPluginLogger().info("Core Version: " + coreVersion);
        platform.getPluginLogger().info("Server Platform: " + platform.getPlatformName());
        platform.getPluginLogger().info("Server Version: " + platform.getServerVersion());


        this.dataConfig = new DataConfig(platform.getFolder());
        this.dataConfig.loadConfig();

        this.mainConfig = new MainConfig(platform.getFolder());
        this.mainConfig.loadConfig();

    }

    public void stop(){
        saveConfigs();
    }

    private void saveConfigs(){
        dataConfig.saveConfig();
    }


    public Platform getPlatform() {
        return platform;
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }
}