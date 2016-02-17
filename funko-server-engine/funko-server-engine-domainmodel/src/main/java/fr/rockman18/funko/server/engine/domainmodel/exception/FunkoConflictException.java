package fr.rockman18.funko.server.engine.domainmodel.exception;

import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlRootElement;

import fr.rockman18.funko.server.api.domainmodel.exception.FunkoException;

@XmlRootElement
public class FunkoConflictException extends FunkoException {

    private static final long serialVersionUID = 4057071129781455335L;
    private static final String exceptionMessage = "Conflict.";
    private static final Integer exceptionStatusCode = Status.CONFLICT.getStatusCode();
    private static final String exceptionType = FunkoConflictException.class.getSimpleName();

    /**
     * Constructor
     */
    public FunkoConflictException() {
	super(exceptionStatusCode, exceptionMessage, exceptionType);
    }

    public FunkoConflictException(String message, Throwable throwable) {
	super(exceptionStatusCode, getExceptionMessage(message, exceptionMessage), throwable.getClass().getSimpleName(),
		throwable);
    }

    public FunkoConflictException(String message) {
	super(exceptionStatusCode, getExceptionMessage(message, exceptionMessage), exceptionType);
    }

}
