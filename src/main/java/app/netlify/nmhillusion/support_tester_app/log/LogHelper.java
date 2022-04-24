package app.netlify.nmhillusion.support_tester_app.log;

import app.netlify.nmhillusion.pi_logger.PiLogger;
import app.netlify.nmhillusion.pi_logger.PiLoggerHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * date: 2022-04-24
 * <p>
 * created-by: nmhillusion
 */

public class LogHelper {
    private static final Map<Object, PiLogger> logFactory = new HashMap<>();

    public static PiLogger getLog(Object instance) {
        if (logFactory.containsKey(instance)) {
            return logFactory.get(instance);
        } else {
            final PiLogger piLogger = PiLoggerHelper.getLog(instance);
            logFactory.put(instance, piLogger);
            return piLogger;
        }
    }

}
