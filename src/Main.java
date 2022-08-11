import config.AppConfigParser;
import sorter.KWaySort;

import java.io.IOException;
import java.util.Arrays;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Main {

    private static final String logName = "log";
    private static final String logPath = "log.txt";

    public static void main(String[] args) {
        Logger logger = loggerInit();
        logger.info("Session '" + Arrays.toString(args) + "'");

        AppConfigParser appConfigParser = new AppConfigParser(args);

        KWaySort sorter = appConfigParser.sorterInit();
        sorter.merge();
    }

    private static Logger loggerInit() {
        Logger logger = Logger.getLogger(logName);
        logger.setUseParentHandlers(false);

        try {
            FileHandler fh = new FileHandler(logPath);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (SecurityException | IOException e) {
            throw new RuntimeException("The logger could not be initialized", e);
        }

        return logger;
    }
}