package kz.kadyrbekov.studentapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class StudentApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentApiApplication.class, args);
    }

    /** Печатает имя студента при старте приложения (требование: приложение показывает имя). */
    @Bean
    CommandLineRunner banner(Environment env) {
        return args -> {
            System.out.println("==============================================");
            System.out.println(" Student API by " + env.getProperty("student.name", "Bek Kadyrbekov"));
            System.out.println(" Group: " + env.getProperty("student.group", "IT2-2306")
                    + " | Student ID: " + env.getProperty("student.id", "35534"));
            System.out.println("==============================================");
        };
    }
}
