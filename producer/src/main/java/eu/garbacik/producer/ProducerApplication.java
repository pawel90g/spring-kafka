package eu.garbacik.producer;

import eu.garbacik.producer.services.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ProducerApplication implements CommandLineRunner {

    @Autowired
    private ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        var producer = applicationContext.getBean(Producer.class);

        for (Integer i = 0; i < 10; i++) {
            producer.sendMessageAndReadResponse("test" + i);
        }
    }

//    @Bean
//    public ApplicationRunner runner(Producer producer) {
//        return args -> {
//            for(Integer i = 0; i < 10; i++){
//                producer.sendMessageAndReadResponse("test" + i);
//            }
//        };
//    }
}
