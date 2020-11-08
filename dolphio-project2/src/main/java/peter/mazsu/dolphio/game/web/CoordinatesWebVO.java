package peter.mazsu.dolphio.game.web;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peter.mazsu.dolphio.game.entity.Coordinate;

@Getter
@Setter
@NoArgsConstructor
public class CoordinatesWebVO implements Serializable {

	private static final long serialVersionUID = -966596765923857652L;

	private List<Coordinate> coordinates;

	public CoordinatesWebVO(List<Coordinate> coordinates) {
		super();
		this.coordinates = coordinates;
	}

}
