package tr.org.iys.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class ApiException extends RuntimeException {
	
	private static final long serialVersionUID = 6155842946182046058L;
	private final int errorCode;
	public ApiException(String errorMessage, int errorCode) {
		super(errorMessage);
		this.errorCode = errorCode;
	}
	
	public int getErrorCode() {
		return errorCode;
	}

}
