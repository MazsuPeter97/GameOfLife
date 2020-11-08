package peter.mazsu.dolphio.game.web;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.Getter;
import lombok.Setter;
import peter.mazsu.dolphio.game.entity.Coordinate;
import peter.mazsu.dolphio.game.service.CoordinateServiceImpl;
import peter.mazsu.dolphio.game.service.GameServiceImpl;

@RestController
@RequestMapping("/api/project")
@CrossOrigin("*")
public class GameOfLifeWeb implements Serializable {

	private static final long serialVersionUID = -1771987748019553046L;

	private static final Logger LOGGER = LoggerFactory.getLogger(GameOfLifeWeb.class);

	@Autowired
	private GameServiceImpl gameServiceImpl;

	@Autowired
	private CoordinateServiceImpl coordinateServiceImpl;

	@Getter
	@Setter
	private Map<Integer, List<String>> readedStringMap;

	@Getter
	@Setter
	private Integer stepOfGame;

	@Getter
	@Setter
	private String uploadedFileName;

	/**
	 * method for file upload and put data to readedStringMap
	 * 
	 * @param file
	 * @return
	 */
	@PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
		stepOfGame = 0;
		uploadedFileName = file.getOriginalFilename();
		readedStringMap = new HashMap<Integer, List<String>>();

		try (FileInputStream input = (FileInputStream) file.getInputStream();
				BufferedReader buff = new BufferedReader(new InputStreamReader(input))) {

			List<String> uploadedFileLineList = new ArrayList<>();

			int keyToMap = 0;

			String uploadedFileLine = null;

			while ((uploadedFileLine = buff.readLine()) != null) {

				if (keyToMap != 0 && uploadedFileLine.contains("#P")) {
					readedStringMap.put(keyToMap, uploadedFileLineList);
					uploadedFileLineList = new ArrayList<>();
				}
				if (uploadedFileLine.startsWith("#P")) {
					keyToMap++;
					uploadedFileLine = buff.readLine();
				}
				if (keyToMap != 0 && !uploadedFileLine.startsWith("#P")) {
					uploadedFileLineList.add(uploadedFileLine);
				}
			}

			readedStringMap.put(keyToMap, uploadedFileLineList);

		} catch (Exception e) {
			LOGGER.error(String.format("File name '%s' file read error.", file.getOriginalFilename()));
		}

		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug(String.format("File is uploaded with '%s' name", uploadedFileName));
		}

		return new ResponseEntity<CoordinatesWebVO>(createCoordinate(readedStringMap), HttpStatus.OK);
	}

	/**
	 * create CoordinateWebVO from readedStringMap
	 * 
	 * @param readedMap
	 * @return
	 */
	private CoordinatesWebVO createCoordinate(Map<Integer, List<String>> readedStringMap) {
		int y = 49;
		List<Coordinate> coordinates = new ArrayList<>();

		for (String string : readedStringMap.get(1)) {

			for (int i = 0; i < string.length(); i++) {
				if (string.charAt(i) == '*') {
					Coordinate coordinate = new Coordinate();
					coordinate.setX(49 + i);
					coordinate.setY(y);
					coordinate.setStep(stepOfGame);
					coordinate.setRevieved(true);
					coordinate.setFileName(uploadedFileName);

					coordinates.add(coordinate);

					coordinateServiceImpl.saveCoordinate(coordinate);
				}
			}
			y++;
		}

		CoordinatesWebVO coordinatesWebVO = new CoordinatesWebVO();
		coordinatesWebVO.setCoordinates(coordinates);

		return coordinatesWebVO;
	}

	/**
	 * generate new CoordinateWebVO
	 * 
	 * @param coordinatesWebVO
	 * @return
	 */
	@PostMapping(value = "/game", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CoordinatesWebVO generateNewCoordinateWebVO(@RequestBody CoordinatesWebVO coordinatesWebVO) {
		stepOfGame++;

		CoordinatesWebVO calculatedStructure = gameServiceImpl.newCoordinates(coordinatesWebVO, stepOfGame,
				uploadedFileName);

		return calculatedStructure;
	}

	/**
	 * get last step
	 * 
	 * @param step
	 * @return
	 */
	@PostMapping(value = "/step", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public CoordinatesWebVO stepOfGame(@RequestBody Integer step) {
		return new CoordinatesWebVO(coordinateServiceImpl.getLastCoordinates(step - 1));
	}

}
