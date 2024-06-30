package com.example.demo.service;

import com.example.demo.model.Todo;
import com.example.demo.repository.TodoRepository;
import com.example.demo.service.exception.TodoNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<Todo> findAll() {
        return todoRepository.findAll();
    }

    public Optional<Todo> findById(Long id) {
        return todoRepository.findById(id);
    }

    public Todo save(Todo todo) {
        return todoRepository.save(todo);
    }

    public void deleteById(Long id) {
        try {
            todoRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new TodoNotFoundException(id);
        }
    }

    public Todo update(Long id, Todo newTodoData) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException(id));

        todo.setTitle(newTodoData.getTitle());
        todo.setCompleted(newTodoData.isCompleted());

        return todoRepository.save(todo);
    }
}
