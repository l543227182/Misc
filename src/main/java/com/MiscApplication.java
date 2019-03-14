package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.EventListener;
import java.util.EventObject;

@SpringBootApplication(scanBasePackages = "com.lc.web")
public class MiscApplication {

	public static void main(String[] args) {
		SpringApplication.run(MiscApplication.class);
	}
}
