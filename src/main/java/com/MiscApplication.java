package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.lc.web")
public class MiscApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiscApplication.class,args);

	}

}
