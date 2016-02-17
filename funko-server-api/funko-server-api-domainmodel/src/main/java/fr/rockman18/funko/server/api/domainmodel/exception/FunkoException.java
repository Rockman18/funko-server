package fr.rockman18.funko.server.api.domainmodel.exception;

import javax.ws.rs.core.Response.Status;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

@XmlRootElement
public abstract class FunkoException extends Exception {

    private static final long serialVersionUID = 4542630136184611664L;
    private Integer statusCode = Status.INTERNAL_SERVER_ERROR.getStatusCode();
    private String exceptionType = Status.INTERNAL_SERVER_ERROR.toString();

    /**
     * Constructor
     */
    public FunkoException() {
	super();
    }

    public FunkoException(Integer statusCode, String message, String exceptionType) {
	super(message);
	this.statusCode = statusCode;
	this.exceptionType = exceptionType;
    }

    public FunkoException(Integer statusCode, String message, String exceptionType, Throwable throwable) {
	super(message, throwable);
	this.statusCode = statusCode;
	this.exceptionType = exceptionType;
    }

    /**
     * Getter
     */
    @XmlElement
    public Integer getStatusCode() {
	return statusCode;
    }

    @XmlElement
    public String getExceptionType() {
	return exceptionType;
    }

    @XmlElement
    public String getExceptionMessage() {
	return super.getMessage();
    }

    @XmlTransient
    public StackTraceElement[] getStackTrace() {
	return null; // hotfix to avoid backtrace displaying
    }

    protected static String getExceptionMessage(String message, String defaultMessage) {
	if (message == null || message.equals("")) {
	    return defaultMessage;
	}

	return message;
    }
}
