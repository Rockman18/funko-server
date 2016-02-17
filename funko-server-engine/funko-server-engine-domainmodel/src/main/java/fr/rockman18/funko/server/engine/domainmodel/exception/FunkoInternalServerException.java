package fr.rockman18.funko.server.engine.domainmodel.exception;

import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlRootElement;

import fr.rockman18.funko.server.api.domainmodel.exception.FunkoException;

@XmlRootElement
public class FunkoInternalServerException extends FunkoException {

    private static final long serialVersionUID = 4057071129781455335L;
    private static final String exceptionMessage = "Internal server error.";
    private static final Integer exceptionStatusCode = Status.INTERNAL_SERVER_ERROR.getStatusCode();
    private static final String exceptionType = FunkoInternalServerException.class.getSimpleName();

    /**
     * Constructor
     */
    public FunkoInternalServerException() {
	super(exceptionStatusCode, exceptionMessage, exceptionType);
    }

    public FunkoInternalServerException(String message, Throwable throwable) {
	super(exceptionStatusCode, getExceptionMessage(message, exceptionMessage), throwable.getClass().getSimpleName(),
		throwable);
    }

    public FunkoInternalServerException(String message) {
	super(exceptionStatusCode, getExceptionMessage(message, exceptionMessage), exceptionType);
    }
}
