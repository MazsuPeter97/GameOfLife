package peter.mazsu.dolphio.game.service;

import peter.mazsu.dolphio.game.web.CoordinatesWebVO;

public interface GameService {

	public CoordinatesWebVO newCoordinates(CoordinatesWebVO coordinatesWebVO, Integer step, String fileName);
}	
