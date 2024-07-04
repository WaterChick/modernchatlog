package cz.waterchick;

import org.slf4j.Logger;

import java.io.File;

public interface Platform {

    String getPlatformName();
    String getServerVersion();

    File getFolder();
    Logger getLogger();
}
