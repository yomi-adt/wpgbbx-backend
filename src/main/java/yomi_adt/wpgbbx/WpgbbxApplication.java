package yomi_adt.wpgbbx;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WpgbbxApplication implements CommandLineRunner {
	@Value("${spring.data.mongodb.uri}")
	private String mongoUri;

	public static void main(String[] args) {
		SpringApplication.run(WpgbbxApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println("Mongo URI: " + mongoUri);
	}
}
