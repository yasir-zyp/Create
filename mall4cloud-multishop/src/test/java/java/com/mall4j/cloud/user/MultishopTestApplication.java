package java.com.mall4j.cloud.user;

import com.mall4j.cloud.multishop.MultishopApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.mall4j.cloud" })
public class MultishopTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(MultishopApplication.class, args);
    }
}
