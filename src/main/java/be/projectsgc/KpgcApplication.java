package be.projectsgc;

import org.apache.log4j.BasicConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
@ComponentScan
@Configuration
public class KpgcApplication {

	public static void main(String[] args) {
		BasicConfigurator.configure();
		SpringApplication.run(KpgcApplication.class, args);
	}

	@Override
	public String toString() {
		return "KpgcApplication{}";
	}
}
