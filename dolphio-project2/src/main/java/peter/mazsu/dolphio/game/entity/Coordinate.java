package peter.mazsu.dolphio.game.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Entity
@Table(name = "COORDINATES")
@Getter
@Setter
@NoArgsConstructor
public class Coordinate {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "FILE_NAME", nullable = false)
	private String fileName;

	@Column(name = "STEP", nullable = false)
	private Integer step;

	@Column(name = "X", nullable = false)
	private Integer x;

	@Column(name = "Y", nullable = false)
	private Integer y;

	@Column(name = "IS_REVIEVED")
	private boolean isRevieved;

	public Coordinate(Integer x, Integer y) {
		super();
		this.x = x;
		this.y = y;
	}

	public Coordinate(Integer x, Integer y, boolean isRevieved) {
		super();
		this.x = x;
		this.y = y;
		this.isRevieved = isRevieved;
	}

}
