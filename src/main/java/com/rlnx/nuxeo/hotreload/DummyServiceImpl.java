package com.rlnx.nuxeo.hotreload;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nuxeo.ecm.core.api.*;
import org.nuxeo.ecm.core.api.repository.RepositoryManager;
import org.nuxeo.ecm.platform.query.nxql.NXQLQueryBuilder;
import org.nuxeo.runtime.api.Framework;
import org.nuxeo.runtime.model.ComponentContext;
import org.nuxeo.runtime.model.DefaultComponent;

import com.rlnx.nuxeo.am.AmServiceImpl;
import com.rlnx.nuxeo.am.scanner.AmScannerServiceImpl;

/**
 * @author François-Denis Gonthier <fdgonthier@rlnx.com>
 */
public class DummyServiceImpl extends DefaultComponent implements DummyService {

    private static final Log log = LogFactory.getLog(AmScannerServiceImpl.class);

    @Override
    public void activate(ComponentContext context) throws Exception {
        super.activate(context);
        log.debug(AmServiceImpl.class + " activated");
    }

    @Override
    public void deactivate(ComponentContext context) throws Exception {
        super.deactivate(context);
        log.debug(AmServiceImpl.class + " deactivated");
    }

    protected String getDefaultRepositoryName() {
        RepositoryManager repoMgr = Framework.getLocalService(RepositoryManager.class);
        return repoMgr.getDefaultRepository().getName();
    }

    @Override
    public void runThroughList() {
        try {
            new UnrestrictedSessionRunner(getDefaultRepositoryName()) {
                @Override
                public void run() throws ClientException {
                    String nxql;
                    DocumentModelIterator docIt = null;

                    // List all classified documents.
                    nxql = NXQLQueryBuilder.getQuery("SELECT * FROM Dummy",
                            new Object[] {}, false, true);
                    docIt = session.queryIt(nxql, null, 0);

                    // Scan through all the returned ID.
                    log.info(String.format("Dummy loop started."));

                    for (DocumentModel docModel : docIt) {
                        log.info("Found document: " + docModel.getPathAsString());
                    }

                    log.info(String.format("Dummy loop ended."));
                }
            }.runUnrestricted();
        } catch (ClientException e) {
            log.error("Dummy loop failed", e);
        }
    }
}
