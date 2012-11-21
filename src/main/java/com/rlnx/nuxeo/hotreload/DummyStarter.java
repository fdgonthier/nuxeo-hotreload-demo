package com.rlnx.nuxeo.hotreload;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.event.Event;
import org.nuxeo.ecm.core.event.EventListener;
import org.nuxeo.runtime.api.Framework;

import com.rlnx.nuxeo.am.scanner.AmScannerStarter;

/**
 * @author François-Denis Gonthier <fdgonthier@rlnx.com>
 */
public class DummyStarter implements EventListener {

    private static final Log log = LogFactory.getLog(AmScannerStarter.class);

    @Override
    public void handleEvent(Event event) {
        DummyService dummyService;
        String ev;

        ev = event.getName();

        try {
            if (ev.equals("DummyStart")) {
                dummyService = Framework.getService(DummyService.class);
                dummyService.runThroughList();
            }
        } catch (Exception ex) {
            log.error("Could not start dummy service", ex);
        }
    }
}
