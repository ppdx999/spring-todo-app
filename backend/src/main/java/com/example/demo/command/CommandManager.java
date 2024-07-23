package com.example.demo.command;

import java.util.Arrays;

import org.springframework.context.ConfigurableApplicationContext;

public class CommandManager {
    public static void run(ConfigurableApplicationContext context, String... args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: cmd <command> [<args>]");
            return;
        }
        String cmdName = args[0];
        args = Arrays.copyOfRange(args, 1, args.length);

        Command cmd = null;
        switch (cmdName) {
            case "user-create":
                cmd = context.getBean(UserCreateCommands.class);
                break;
            default:
                cmd = context.getBean(UnknownCommand.class);
                break;
        }
        cmd.run(args);
    }
}
