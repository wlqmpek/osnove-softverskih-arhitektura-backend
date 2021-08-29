package ftn.project.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogEvents {
    public static void log(Class cla, String msg) {
        Logger logger = LoggerFactory.getLogger(cla);
        logger.info(msg);
    }
}
