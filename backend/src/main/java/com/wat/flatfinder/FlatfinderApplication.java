package com.wat.flatfinder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("com.wat.flatfinder.entities")
public class FlatfinderApplication {

    public static void main(String[] args) {
        //String a = "ala";
        SpringApplication.run(FlatfinderApplication.class, args);
    }

}
