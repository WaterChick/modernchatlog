package cz.waterchick;

import cz.waterchick.configs.DataConfig;
import cz.waterchick.configs.MainConfig;
import cz.waterchick.interfaces.Platform;

import java.util.logging.Level;

public class Core {

    private final Platform platform;
    private DataConfig dataConfig;
    private MainConfig mainConfig;
    private final LogWrapper logWrapper;

    public static String coreVersion = "1.0.7";

    public Core(Platform platform){
        this.platform = platform;
        this.logWrapper = new LogWrapper(platform.getPluginLogger(), "[ChatLog-"+platform.getPlatformName()+"]");
    }

    public void start(){

        logWrapper.log(Level.INFO, "ChatLog-Core INFO: ");
        logWrapper.log(Level.INFO,"Core Version: " + coreVersion);
        logWrapper.log(Level.INFO,"Server Platform: " + platform.getPlatformName());
        logWrapper.log(Level.INFO,"Server Version: " + platform.getServerVersion());


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