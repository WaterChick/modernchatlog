package cz.waterchick;

import cz.waterchick.configs.TestConfig;

public class ChatLog {

    private final Platform platform;

    public ChatLog(Platform platform){
        this.platform = platform;
    }
    private void start(){
        TestConfig testConfig = new TestConfig(platform.getFolder());
        testConfig.loadConfig();
    }

    public Platform getPlatform() {
        return platform;
    }
}