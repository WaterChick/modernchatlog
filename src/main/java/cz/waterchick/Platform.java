package cz.waterchick;

import java.io.File;

public interface Platform {

    String getPlatformName();
    String getServerVersion();

    File getFolder();
}
