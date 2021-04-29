package server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        /*int l = 0, m = 0;
        for (int i = 425; i < 1000; i++) {
            int i2 = i;
            while (i > 0){
                l += 1;
                if (i % 2 != 0) m = m + i % 8;
                i = i / 8;
            }
            if (l == 3 && m == 6) System.out.println(i2);
        }*/


        SpringApplication.run(Application.class, args);
    }
}
