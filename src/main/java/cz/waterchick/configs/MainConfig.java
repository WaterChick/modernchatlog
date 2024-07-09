package cz.waterchick.configs;

import com.google.common.base.Enums;
import cz.waterchick.enums.ConfigValue;
import dev.dejvokep.boostedyaml.YamlDocument;
import dev.dejvokep.boostedyaml.block.implementation.Section;

import java.io.File;
import java.util.Set;

public class MainConfig extends Config{
    public MainConfig(File dataFolder) {
        super(dataFolder, "config.yml");
    }

    @Override
    public void onLoad() {
        YamlDocument config = getConfig();
        Set<Object> configSection = config.getKeys();
        for(Object configValueString : configSection){
            if (!Enums.getIfPresent(ConfigValue.class, configValueString.toString().toUpperCase()).isPresent()) continue;
            ConfigValue configValue = ConfigValue.valueOf(configValueString.toString().toUpperCase());
            String value = config.getString(configValueString.toString());
            configValue.setValue(value);
        }
    }

    @Override
    public void onSave() {

    }
}
