package com.aktivis.dijkstra;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableAutoConfiguration
@ComponentScan(basePackages={"com.aktivis.dijkstra"})
@EnableJpaRepositories(basePackages="com.aktivis.dijkstra.repositories")
@EnableTransactionManagement
@EntityScan(basePackages="com.aktivis.dijkstra.entities")
public class DijkstraDemoApplication {

	 public static void main(String[] args) {        
			if (!Desktop.isDesktopSupported()) {
				System.out.println("App needs a desktop manager to run, exiting.");
				System.exit(1);
			}

			SpringApplicationBuilder builder = new SpringApplicationBuilder(DijkstraDemoApplication.class);
			builder.headless(false).run(args);
	    }    
	    
	    @EventListener(ApplicationReadyEvent.class)
	    public void openBrowserAfterStartup() throws IOException, URISyntaxException {
	        Desktop.getDesktop().browse(new URI("http://localhost:8080"));
	    }	    

}
