package cz.waterchick.configs;

import com.google.common.base.Enums;
import cz.waterchick.enums.ConfigValue;
import dev.dejvokep.boostedyaml.block.implementation.Section;

import java.io.File;

public class MainConfig extends Config{
    public MainConfig(File dataFolder) {
        super(dataFolder, "config.yml");
    }

    @Override
    public void onLoad() {
        Section configSection = getConfig().getSection("");
        if(configSection == null){
            return;
        }
        for(Object configValueString : configSection.getKeys()){
            if (!Enums.getIfPresent(ConfigValue.class, configValueString.toString().toUpperCase()).isPresent()) continue;
            ConfigValue configValue = ConfigValue.valueOf(configValueString.toString().toUpperCase());
            String value = configSection.getString(configValueString.toString());
            configValue.setValue(value);
        }
    }

    @Override
    public void onSave() {

    }
}
