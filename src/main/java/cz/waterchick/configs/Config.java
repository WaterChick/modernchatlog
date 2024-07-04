package cz.waterchick.configs;

import org.bspfsystems.yamlconfiguration.file.YamlConfiguration;
import org.yaml.snakeyaml.Yaml;


import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;


public abstract class Config {
    private final String name;
    private final File dataFolder;
    private final File file;
    private YamlConfiguration config;

    public Config(File dataFolder, String name){
        this.name = name;
        this.dataFolder = dataFolder;
        this.file = new File(dataFolder, name);
    }

    public void loadConfig(){
        if(!dataFolder.exists()){
            dataFolder.mkdirs();
        }
        if(!file.exists()){
            //dodelat save z resources
        }
        this.config = YamlConfiguration.loadConfiguration(file);

        onLoad();
    }

    public void reloadConfig(){
        loadConfig();
    }

    public void saveConfig(){
        onSave();
        if(config != null) {
            try {
                config.save(file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public YamlConfiguration getConfig() {
        return config;
    }

    public abstract void onLoad();

    public abstract void onSave();
}
