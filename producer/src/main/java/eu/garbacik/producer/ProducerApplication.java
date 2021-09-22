package eu.garbacik.producer;

import eu.garbacik.producer.services.Producer;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Bean
    public ApplicationRunner runner(Producer producer) {
        return args -> {
            for(Integer i = 0; i < 10; i++){
                producer.sendMessageWithCallback("test" + i);
            }
        };
    }

}
