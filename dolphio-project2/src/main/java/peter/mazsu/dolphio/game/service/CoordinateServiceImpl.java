package peter.mazsu.dolphio.game.service;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import peter.mazsu.dolphio.game.entity.Coordinate;

@Component
@Transactional
public class CoordinateServiceImpl implements CoordinateService {

	@PersistenceContext
	private EntityManager em;

	/**
	 * save Coordinate to h2 database
	 */
	@Override
	public void saveCoordinate(Coordinate coordinate) {
		em.persist(coordinate);
	}

	/**
	 * find coordinates for step and return Coordinate list
	 */
	@Override
	public List<Coordinate> getLastCoordinates(Integer step) {

		List<Coordinate> coordinates = em
				.createQuery("SELECT c FROM Coordinate c WHERE c.step =:step", Coordinate.class)
				.setParameter("step", step).getResultList();

		if (CollectionUtils.isEmpty(coordinates)) {
			return Collections.emptyList();
		}

		return coordinates;
	}

}
