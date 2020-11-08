package peter.mazsu.dolphio.game.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CoordinateNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -914940115526301405L;

	public CoordinateNotFoundException(String message) {
        super(message);
    }
}
