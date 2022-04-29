package app.netlify.nmhillusion.support_tester_app.runner;

import java.util.Iterator;

import static app.netlify.nmhillusion.support_tester_app.log.LogHelper.getLog;

/**
 * date: 2022-04-29
 * <p>
 * created-by: nmhillusion
 */

public class Boy {

    public void speak(Iterator<Integer> affectedGirls) {
        getLog(this).info("i has affected to: ");
        while (affectedGirls.hasNext()) {
            getLog(this).info("[girlId] " + affectedGirls.next());
        }
    }
}
