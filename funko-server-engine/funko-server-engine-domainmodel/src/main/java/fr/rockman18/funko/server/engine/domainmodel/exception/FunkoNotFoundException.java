package fr.rockman18.funko.server.engine.domainmodel.exception;

import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlRootElement;

import fr.rockman18.funko.server.api.domainmodel.exception.FunkoException;

@XmlRootElement
public class FunkoNotFoundException extends FunkoException {

    private static final long serialVersionUID = 4057071129781455335L;
    private static final String exceptionMessage = "Not Found.";
    private static final Integer exceptionStatusCode = Status.NOT_FOUND.getStatusCode();
    private static final String exceptionType = FunkoNotFoundException.class.getSimpleName();

    /**
     * Constructor
     */
    public FunkoNotFoundException() {
	super(exceptionStatusCode, exceptionMessage, exceptionType);
    }

    public FunkoNotFoundException(String message, Throwable throwable) {
	super(exceptionStatusCode, getExceptionMessage(message, exceptionMessage), throwable.getClass().getSimpleName(),
		throwable);
    }

    public FunkoNotFoundException(String message) {
	super(exceptionStatusCode, getExceptionMessage(message, exceptionMessage), exceptionType);
    }
}
