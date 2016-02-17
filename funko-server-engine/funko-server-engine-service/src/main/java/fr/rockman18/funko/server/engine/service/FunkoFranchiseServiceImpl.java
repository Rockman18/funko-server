package fr.rockman18.funko.server.engine.service;

import java.util.Collection;
import java.util.HashSet;

import fr.rockman18.funko.server.api.domainmodel.exception.FunkoException;
import fr.rockman18.funko.server.api.domainmodel.franchise.FunkoFranchise;
import fr.rockman18.funko.server.api.service.FunkoFranchiseService;

public class FunkoFranchiseServiceImpl extends AbstractBaseFunkoService implements FunkoFranchiseService {

    @Override
    public FunkoFranchise get(Integer id) throws FunkoException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Collection<FunkoFranchise> getAll() throws FunkoException {
	return new HashSet<>();
    }

}
