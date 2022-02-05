package mygoogleserviceapi;

import mygoogleserviceapi.shared.config.FileStorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({
        FileStorageProperties.class
})
public class MyGoogleServiceApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyGoogleServiceApiApplication.class, args);
    }

}
