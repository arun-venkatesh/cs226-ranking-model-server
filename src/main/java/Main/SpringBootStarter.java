package Main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import Utils.SolrDataParser;
import api.Handler;

@SpringBootApplication
@ComponentScan(basePackageClasses = Handler.class)
@ComponentScan(basePackageClasses = SolrRanker.class)
@ComponentScan(basePackageClasses = SolrDataParser.class)
public class SpringBootStarter {
	
	
	public static void main(String[] args) throws Exception {

		
    	SpringApplication.run(SpringBootStarter.class, args);
    	
    }
	
}
