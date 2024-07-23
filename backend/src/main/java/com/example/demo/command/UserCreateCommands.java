package com.example.demo.command;

import com.example.demo.model.User;
import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserCreateCommands implements Command {

    @Autowired
    private UserService userService;

    public void run(String... args) throws Exception {
        if (args.length < 2) {
            System.err.println("Usage: user-create <username> <password>");
            return;
        }
        createUser(args[0], args[1]);
    }

    private void createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        userService.save(user);
        System.out.println("User created successfully: " + username);
    }
}
