package org.tix.lab4;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@EnableProcessApplication
public class Lab4Application  {

    public static void main(String[] args) {
        SpringApplication.run(Lab4Application.class, args);
    }

}
