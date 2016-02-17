package fr.rockman18.funko.server.engine.service;

import java.util.Collection;
import java.util.HashSet;

import fr.rockman18.funko.server.api.domainmodel.exception.FunkoException;
import fr.rockman18.funko.server.api.domainmodel.line.FunkoLine;
import fr.rockman18.funko.server.api.service.FunkoLineService;

public class FunkoLineServiceImpl extends AbstractBaseFunkoService implements FunkoLineService {

    @Override
    public FunkoLine get(Integer id) throws FunkoException {
	// TODO Auto-generated method stub
	return null;
    }

    @Override
    public Collection<FunkoLine> getAll() throws FunkoException {
	return new HashSet<>();
    }

}
