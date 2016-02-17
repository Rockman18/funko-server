package fr.rockman18.funko.server.api.service;

import java.util.Collection;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import fr.rockman18.funko.server.api.domainmodel.exception.FunkoException;
import fr.rockman18.funko.server.api.domainmodel.franchise.FunkoFranchise;
import fr.rockman18.funko.server.api.service.authentication.Authenticated;

@Path("franchises")
public interface FunkoFranchiseService extends FunkoServerPublicService {

	/**
	 * JAVADOC TO BE COMPLETED
	 *
	 * @param id
	 * @return
	 * @throws FunkoException
	 */
	@Authenticated
	@GET
	@Path("{id}")
	FunkoFranchise get(@PathParam("id") Integer id) throws FunkoException;

	/**
	 * Retrieve all patchs
	 *
	 * @return
	 * @throws FunkoException
	 */
	@Authenticated
	@GET
    Collection<FunkoFranchise> getAll() throws FunkoException;

}
