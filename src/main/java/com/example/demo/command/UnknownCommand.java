package com.example.demo.command;

public class UnknownCommand implements Command {
    public void run(String... args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: cmd <command> [<args>]");
        } else {
            System.err.println("Unknown command: " + args[0]);
        }
    }
}
