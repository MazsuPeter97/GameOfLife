package peter.mazsu.dolphio.game.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages = "peter.mazsu.dolphio.game")
@EntityScan(basePackages = "peter.mazsu.dolphio.game.entity")
public class GameOfLifeApplication {

	public static void main(String[] args) {
		SpringApplication.run(GameOfLifeApplication.class, args);
	}

}
