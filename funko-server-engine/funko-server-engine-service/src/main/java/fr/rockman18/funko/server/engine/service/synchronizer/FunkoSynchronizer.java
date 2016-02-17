package fr.rockman18.funko.server.engine.service.synchronizer;

import java.util.Set;
import java.util.concurrent.Callable;

import fr.rockman18.funko.server.api.domainmodel.FunkoObject;

public interface FunkoSynchronizer extends Callable<Set<FunkoObject>> {

    void setParent(FunkoObject parent);

}
