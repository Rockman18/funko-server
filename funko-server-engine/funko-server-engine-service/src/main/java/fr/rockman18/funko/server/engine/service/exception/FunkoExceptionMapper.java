/**
 * CloudOMC PMS
 * Copyright 2015 Thales Services SAS - All rights reserved
 */
package fr.rockman18.funko.server.engine.service.exception;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

import fr.rockman18.funko.server.api.domainmodel.exception.FunkoException;

/**
 * Javadoc TO BE COMPLETED
 */
public class FunkoExceptionMapper implements ExceptionMapper<FunkoException> {

    @Override
    public Response toResponse(FunkoException e) {
	// return
	// Response.status(e.getStatus()).type(MediaType.TEXT_PLAIN).entity(entityToPut.toString()).build();
	return Response.status(e.getStatusCode()).entity(e).build();
    }

}
