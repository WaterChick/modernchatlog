package cz.waterchick.configs;

import com.google.common.base.Enums;
import cz.waterchick.enums.ConfigValue;
import org.bspfsystems.yamlconfiguration.configuration.ConfigurationSection;

import java.io.File;

public class MainConfig extends Config{
    public MainConfig(File dataFolder) {
        super(dataFolder, "config.yml");
    }

    @Override
    public void onLoad() {
        ConfigurationSection configSection = getConfig().getConfigurationSection("");
        if(configSection == null){
            return;
        }
        for(String configValueString : configSection.getKeys(false)){
            if (!Enums.getIfPresent(ConfigValue.class, configValueString.toUpperCase()).isPresent()) continue;
            ConfigValue configValue = ConfigValue.valueOf(configValueString.toUpperCase());
            String value = configSection.getString(configValueString);
            configValue.setValue(value);
        }
    }

    @Override
    public void onSave() {

    }
}
