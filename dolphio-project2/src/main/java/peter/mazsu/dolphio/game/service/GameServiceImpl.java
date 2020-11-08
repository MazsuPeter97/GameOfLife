package peter.mazsu.dolphio.game.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import peter.mazsu.dolphio.game.entity.Coordinate;
import peter.mazsu.dolphio.game.web.CoordinatesWebVO;

/**
 * Implement GameService
 * 
 * @author Peti
 *
 */
@Component
public class GameServiceImpl implements GameService {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameServiceImpl.class);

	@Autowired
	private CoordinateServiceImpl coordinateServiceImpl;

	/**
	 * return new coordinatesWebVO
	 * 
	 * @return CoordinatesWebVO
	 */
	@Override
	public CoordinatesWebVO newCoordinates(CoordinatesWebVO coordinatesWebVO, Integer step, String fileName) {
		CoordinatesWebVO newCoordinateWebVO = new CoordinatesWebVO(new ArrayList<Coordinate>());
		List<Coordinate> newCoordinates = new ArrayList<>();

		boolean[][] oldCoordinateArray = new boolean[100][100];
		boolean[][] revievCoordinate = new boolean[100][100];

		// megjelöljük a tömbben az elemeket
		coordinatesWebVO.getCoordinates().forEach(coordinate -> {
			oldCoordinateArray[coordinate.getX()][coordinate.getY()] = coordinate.isRevieved();
		});

		// megnézzük hogy van e 2vagy 3 szomszédja vagy újraélesztik
		coordinatesWebVO.getCoordinates().forEach(coordinate -> {
			if (isSurvived(coordinate, oldCoordinateArray)) {
				newCoordinateWebVO.getCoordinates().add(coordinate);
			}

			// elemek élesztése adott elem körül
			isReviveElement(coordinate.getX(), coordinate.getY() + 1, oldCoordinateArray, revievCoordinate,
					newCoordinates, step, fileName);

			isReviveElement(coordinate.getX() + 1, coordinate.getY() + 1, oldCoordinateArray, revievCoordinate,
					newCoordinates, step, fileName);

			isReviveElement(coordinate.getX() + 1, coordinate.getY(), oldCoordinateArray, revievCoordinate,
					newCoordinates, step, fileName);

			isReviveElement(coordinate.getX() + 1, coordinate.getY() - 1, oldCoordinateArray, revievCoordinate,
					newCoordinates, step, fileName);

			isReviveElement(coordinate.getX(), coordinate.getY() - 1, oldCoordinateArray, revievCoordinate,
					newCoordinates, step, fileName);

			isReviveElement(coordinate.getX() - 1, coordinate.getY() - 1, oldCoordinateArray, revievCoordinate,
					newCoordinates, step, fileName);

			isReviveElement(coordinate.getX() - 1, coordinate.getY(), oldCoordinateArray, revievCoordinate,
					newCoordinates, step, fileName);

			isReviveElement(coordinate.getX() - 1, coordinate.getY() + 1, oldCoordinateArray, revievCoordinate,
					newCoordinates, step, fileName);
		});

		newCoordinateWebVO.setCoordinates(newCoordinates);
		return newCoordinateWebVO;
	}

	/**
	 * check 2,3 neighbors and return true or false
	 * 
	 * @param coordinate
	 * @param oldCoordinateArray
	 * @return
	 */
	private boolean isSurvived(Coordinate coordinate, boolean[][] oldCoordinateArray) {
		return countNeighbors(coordinate.getX(), coordinate.getY(), oldCoordinateArray) == 2
				|| countNeighbors(coordinate.getX(), coordinate.getY(), oldCoordinateArray) == 3;
	}

	/**
	 * add revieved coordinate to list and save h2-database
	 * 
	 * @param x
	 * @param y
	 * @param oldCoordinateArray
	 * @param revievCoordinate
	 * @param newCoordinates
	 * @param step
	 * @param fileName
	 */
	private void isReviveElement(int x, int y, boolean[][] oldCoordinateArray, boolean[][] revievCoordinate,
			List<Coordinate> newCoordinates, Integer step, String fileName) {
		int edgeLength = oldCoordinateArray.length;
		if (x >= 0 && x < edgeLength && y >= 0 && y < edgeLength) {
			if (!revievCoordinate[x][y]) {
				if (3 == countNeighbors(x, y, oldCoordinateArray)) {
					revievCoordinate[x][y] = true;
					Coordinate coordinate = new Coordinate();
					coordinate.setStep(step);
					coordinate.setX(x);
					coordinate.setY(y);
					coordinate.setRevieved(true);
					coordinate.setFileName(fileName);
					coordinateServiceImpl.saveCoordinate(coordinate);
					newCoordinates.add(coordinate);
				}
			}
		}
	}

	/**
	 * count neighbors boolean value
	 */
	private int countNeighbors(int x, int y, boolean[][] oldCoordinateArray) {
		int neighborsNumber = 0;

		if (x > 0) {
			if (oldCoordinateArray[x - 1][y]) {
				neighborsNumber++;
			}
			if (y > 0 && oldCoordinateArray[x - 1][y - 1]) {
				neighborsNumber++;
			}
			if (y < oldCoordinateArray.length - 1 && oldCoordinateArray[x - 1][y + 1]) {
				neighborsNumber++;
			}
		}
		if (y > 0) {
			if (oldCoordinateArray[x][y - 1]) {
				neighborsNumber++;
			}
		}
		if (y < oldCoordinateArray.length - 1) {
			if (oldCoordinateArray[x][y + 1]) {
				neighborsNumber++;
			}
		}
		if (x < oldCoordinateArray.length - 1) {
			if (oldCoordinateArray[x + 1][y]) {
				neighborsNumber++;
			}
			if (y > 0 && oldCoordinateArray[x + 1][y - 1]) {
				neighborsNumber++;
			}
			if (y < oldCoordinateArray.length - 1 && oldCoordinateArray[x + 1][y + 1]) {
				neighborsNumber++;
			}
		}
		return neighborsNumber;
	}
}
