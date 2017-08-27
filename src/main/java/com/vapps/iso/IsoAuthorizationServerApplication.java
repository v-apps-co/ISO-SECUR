package com.vapps.iso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.vapps.iso.configuration.boot.CreateDefaultUserListener;

@SpringBootApplication
public class IsoAuthorizationServerApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(IsoAuthorizationServerApplication.class, args);
		ctx.getBean(CreateDefaultUserListener.class);
	}
}
