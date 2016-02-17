package fr.rockman18.funko.server.engine.service.synchronizer;

import java.io.IOException;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.util.Assert;

import fr.rockman18.funko.server.api.domainmodel.FunkoObject;
import fr.rockman18.funko.server.engine.service.AbstractBaseInternalFunkoService;
import fr.rockman18.funko.server.engine.service.ApplicationContextUtils;

public class SynchronizerServiceImpl extends AbstractBaseInternalFunkoService implements SynchronizerService {

    private ApplicationContextUtils applicationContextUtils;
    private AsyncTaskExecutor funkoSynchronizerExecutor;

    public void setApplicationContextUtils(ApplicationContextUtils applicationContextUtils) {
	this.applicationContextUtils = applicationContextUtils;
    }

    public void setFunkoSynchronizerExecutor(AsyncTaskExecutor funkoSynchronizerExecutor) {
	this.funkoSynchronizerExecutor = funkoSynchronizerExecutor;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
	Assert.notNull(applicationContextUtils);
	Assert.notNull(funkoSynchronizerExecutor);
    }

    @Override
    public void run() throws IOException {
	FunkoSynchronizer funkoSynchronizer = (FunkoSynchronizer) applicationContextUtils.getApplicationContext()
		.getBean("funkoSynchronizer");
	Future<Set<FunkoObject>> future = funkoSynchronizerExecutor.submit(funkoSynchronizer);
	Set<FunkoObject> discoveredObjects = null;
	try {
	    discoveredObjects = future.get();
	} catch (InterruptedException | ExecutionException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

	if (discoveredObjects != null) {
	    for (FunkoObject discoveredObject : discoveredObjects) {
		funkoSynchronizer = (FunkoSynchronizer) applicationContextUtils.getApplicationContext()
			.getBean("funkoSynchronizer");
		funkoSynchronizer.setParent(discoveredObject);
		funkoSynchronizerExecutor.submit(funkoSynchronizer);
	    }
	}
    }
}
