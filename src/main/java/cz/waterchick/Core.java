package cz.waterchick;

import cz.waterchick.configs.TestConfig;

public class Core {

    private final Platform platform;

    public Core(Platform platform){
        this.platform = platform;
    }

    public void start(){
        platform.getLogger().info("Loaded ChatLog-"+platform.getPlatformName());
        /*
        TestConfig testConfig = new TestConfig(platform.getFolder());
        testConfig.loadConfig();

         */
    }

    public Platform getPlatform() {
        return platform;
    }
}