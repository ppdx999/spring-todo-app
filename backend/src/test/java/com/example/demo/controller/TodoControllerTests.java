package com.example.demo.controller;

import com.example.demo.model.Todo;
import com.example.demo.service.TodoService;
import com.example.demo.service.exception.TodoNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
class TodoApiControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(new TodoController(todoService)).build();
    }

    @Test
    void testGetAllTodos() throws Exception {
        Todo todo1 = new Todo();
        todo1.setId(1L);
        todo1.setTitle("Test Todo 1");
        todo1.setCompleted(false);

        Todo todo2 = new Todo();
        todo2.setId(2L);
        todo2.setTitle("Test Todo 2");
        todo2.setCompleted(true);

        when(todoService.findAll()).thenReturn(Arrays.asList(todo1, todo2));

        mockMvc.perform(get("/api/todos")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Test Todo 1"))
                .andExpect(jsonPath("$[1].title").value("Test Todo 2"));
    }

    @Test
    void testGetTodoById() throws Exception {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Test Todo");
        todo.setCompleted(false);

        when(todoService.findById(1L)).thenReturn(Optional.of(todo));

        mockMvc.perform(get("/api/todos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test Todo"));
    }

    @Test
    void testGetTodoByIdNotFound() throws Exception {
        when(todoService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/todos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateTodo() throws Exception {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setTitle("New Todo");
        todo.setCompleted(false);

        when(todoService.save(any(Todo.class))).thenReturn(todo);

        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"New Todo\",\"completed\":false}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Todo"));
    }

    @Test
    void testUpdateTodo() throws Exception {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Updated Todo");
        todo.setCompleted(true);

        when(todoService.update(eq(1L), any(Todo.class))).thenReturn(todo);

        mockMvc.perform(put("/api/todos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Updated Todo\",\"completed\":true}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated Todo"))
                .andExpect(jsonPath("$.completed").value(true));
    }

    @Test
    void testUpdateTodoNotFound() throws Exception {
        when(todoService.update(eq(1L), any(Todo.class))).thenThrow(new TodoNotFoundException(1L));

        mockMvc.perform(put("/api/todos/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"title\":\"Updated Todo\",\"completed\":true}"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testDeleteTodo() throws Exception {
        doNothing().when(todoService).deleteById(1L);

        mockMvc.perform(delete("/api/todos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteTodoNotFound() throws Exception {
        doThrow(new TodoNotFoundException(1L)).when(todoService).deleteById(1L);

        mockMvc.perform(delete("/api/todos/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
