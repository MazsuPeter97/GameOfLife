package peter.mazsu.dolphio.game.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CoordinateNotFoundExceptionResponse {

	private String coordinateNotFound;

	public CoordinateNotFoundExceptionResponse(String coordinateNotFound) {
		this.coordinateNotFound = coordinateNotFound;
	}

}
