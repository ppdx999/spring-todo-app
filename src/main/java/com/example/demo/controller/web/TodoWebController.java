package com.example.demo.controller.web;

import com.example.demo.model.Todo;
import com.example.demo.model.User;
import com.example.demo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/todos")
public class TodoWebController {

    @Autowired
    private TodoService todoService;

    @GetMapping
    public String index(@AuthenticationPrincipal User user, Model model) {
        List<Todo> todos = todoService.findAllByOwner(user);
        model.addAttribute("todos", todos);
        model.addAttribute("username", user.getUsername());
        return "todos/index";
    }
}
