package cz.waterchick;


import java.util.logging.Level;
import java.util.logging.Logger;

public class LogWrapper {

    private final Logger logger;
    private final String prefix;

    public LogWrapper(Logger logger, String prefix){
        this.logger = logger;
        this.prefix = prefix;
    }

    public void log(Level level, String message){
        logger.info(prefix + " " + level + ": " + message);
    }
}
