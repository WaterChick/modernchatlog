package cz.waterchick.interfaces;


import java.io.File;
import java.util.logging.Logger;

public interface Platform {

    String getPlatformName();
    String getServerVersion();
    File getFolder();
    Logger getPluginLogger();
}
