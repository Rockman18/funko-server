package fr.rockman18.funko.server.engine.domainmodel.exception;

import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlRootElement;

import fr.rockman18.funko.server.api.domainmodel.exception.FunkoException;

@XmlRootElement
public class FunkoPreconditionFailedException extends FunkoException {

    private static final long serialVersionUID = 4057071129781455335L;
    private static final String exceptionMessage = "Precondition failed.";
    private static final Integer exceptionStatusCode = Status.PRECONDITION_FAILED.getStatusCode();
    private static final String exceptionType = FunkoPreconditionFailedException.class.getSimpleName();
    
    /**
     * Constructor
     */
    public FunkoPreconditionFailedException() {
	super(exceptionStatusCode, exceptionMessage, exceptionType);
    }

    public FunkoPreconditionFailedException(String message, Throwable throwable) {
	super(exceptionStatusCode, getExceptionMessage(message, exceptionMessage), throwable.getClass().getSimpleName(),
		throwable);
    }

    public FunkoPreconditionFailedException(String message) {
	super(exceptionStatusCode, getExceptionMessage(message, exceptionMessage), exceptionType);
    }
}
