package fr.rockman18.funko.server.api.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import fr.rockman18.funko.server.api.domainmodel.exception.FunkoException;
import fr.rockman18.funko.server.api.domainmodel.product.FunkoProduct;
import fr.rockman18.funko.server.api.service.authentication.Authenticated;

@Path("products")
public interface FunkoPopProductService extends FunkoServerPublicService {

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
	FunkoProduct get(@PathParam("id") Integer id) throws FunkoException;

	/**
	 * JAVADOC TO BE COMPLETED
	 *
	 * @return
	 * @throws FunkoException
	 */
	@Authenticated
	@GET
	List<FunkoProduct> getAll() throws FunkoException;

}
