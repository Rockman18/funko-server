package fr.rockman18.funko.server.api.service;

import java.util.Collection;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import fr.rockman18.funko.server.api.domainmodel.exception.FunkoException;
import fr.rockman18.funko.server.api.domainmodel.line.FunkoLine;
import fr.rockman18.funko.server.api.service.authentication.Authenticated;

@Path("lines")
public interface FunkoLineService extends FunkoServerPublicService {

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
	FunkoLine get(@PathParam("id") Integer id) throws FunkoException;

	/**
	 * Retrieve all patchs
	 *
	 * @return
	 * @throws FunkoException
	 */
	@Authenticated
	@GET
	Collection<FunkoLine> getAll() throws FunkoException;

}
