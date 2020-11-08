package peter.mazsu.dolphio.game.service;

import java.util.List;

import peter.mazsu.dolphio.game.entity.Coordinate;

public interface CoordinateService {
	
	public void saveCoordinate(Coordinate coordinate);
	
	public List<Coordinate> getLastCoordinates(Integer step);
}
