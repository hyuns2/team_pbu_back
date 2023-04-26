package projectbuildup.mivv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MivvApplication {

	public static void main(String[] args) {
		SpringApplication.run(MivvApplication.class, args);
	}

}
