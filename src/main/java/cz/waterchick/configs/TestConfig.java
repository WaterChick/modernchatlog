package cz.waterchick.configs;

import java.io.File;

public class TestConfig extends Config{
    public TestConfig(File dataFolder) {
        super(dataFolder, "test.yml");
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onSave() {

    }
}
