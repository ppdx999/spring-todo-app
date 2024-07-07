package com.example.demo;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.example.demo.command.UserCommands;

@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) throws Exception {
		if (args.length > 0 && "cmd".equals(args[0])) {
			if (args.length < 1) {
				System.err.println("Usage: cmd <command> [<args>]");
				return;
			}

			String cmdName = args[1];

			SpringApplication app = new SpringApplication(DemoApplication.class);
			app.setWebApplicationType(WebApplicationType.NONE);
			ConfigurableApplicationContext context = app.run(args);

			/*
			 * TODO: Create abstract Command class with run method and have all commands
			 * Should Keep main method clean and simple
			 */
			switch (cmdName) {
				case "create-user":
					UserCommands cmd = context.getBean(UserCommands.class);
					args = Arrays.copyOfRange(args, 2, args.length);
					cmd.run(args);
					break;
				default:
					System.err.println("Unknown command: " + cmdName);
					break;
			}

			return;
		}

		SpringApplication.run(DemoApplication.class, args);
	}
}
