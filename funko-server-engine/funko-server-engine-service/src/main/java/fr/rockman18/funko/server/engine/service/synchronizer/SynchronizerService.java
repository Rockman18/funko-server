package fr.rockman18.funko.server.engine.service.synchronizer;

import java.io.IOException;

import fr.rockman18.funko.server.engine.service.InternalFunkoService;

public interface SynchronizerService extends InternalFunkoService {

    public void run() throws IOException;

}
