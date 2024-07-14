package com.example.demo;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.demo.command.CommandManager;
import com.example.demo.config.RsaKeyProperties;

@SpringBootApplication
@EnableConfigurationProperties(RsaKeyProperties.class)
public class DemoApplication {
	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "cmd".equals(args[0])) {
			if (args.length < 1) {
				System.err.println("Usage: cmd <command> [<args>]");
				return;
			}
			SpringApplication app = new SpringApplication(DemoApplication.class);
			app.setWebApplicationType(WebApplicationType.NONE);
			CommandManager.run(
				app.run(args),
				Arrays.copyOfRange(args, 1, args.length)
			);
		}

		SpringApplication.run(DemoApplication.class, args);
	}
}
