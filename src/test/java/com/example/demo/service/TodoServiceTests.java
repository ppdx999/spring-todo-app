package com.example.demo.service;

import com.example.demo.service.exception.TodoNotFoundException;
import com.example.demo.model.Todo;
import com.example.demo.repository.TodoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoServiceTests {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoService todoService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Todo todo1 = new Todo();
        todo1.setId(1L);
        todo1.setTitle("Test Todo 1");
        todo1.setCompleted(false);

        Todo todo2 = new Todo();
        todo2.setId(2L);
        todo2.setTitle("Test Todo 2");
        todo2.setCompleted(true);

        when(todoRepository.findAll()).thenReturn(Arrays.asList(todo1, todo2));

        List<Todo> todos = todoService.findAll();
        assertEquals(2, todos.size());
        assertEquals("Test Todo 1", todos.get(0).getTitle());
        assertEquals("Test Todo 2", todos.get(1).getTitle());
    }

    @Test
    void testFindById() {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Test Todo");
        todo.setCompleted(false);

        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        Optional<Todo> foundTodo = todoService.findById(1L);
        assertTrue(foundTodo.isPresent());
        assertEquals("Test Todo", foundTodo.get().getTitle());
    }

    @Test
    void testFindByIdNotFound() {
        when(todoRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<Todo> foundTodo = todoService.findById(1L);
        assertFalse(foundTodo.isPresent());
    }

    @Test
    void testSave() {
        Todo todo = new Todo();
        todo.setTitle("New Todo");
        todo.setCompleted(false);

        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        Todo savedTodo = todoService.save(todo);
        assertNotNull(savedTodo);
        assertEquals("New Todo", savedTodo.getTitle());
    }

    @Test
    void testUpdate() {
        Todo existingTodo = new Todo();
        existingTodo.setId(1L);
        existingTodo.setTitle("Existing Todo");
        existingTodo.setCompleted(false);

        Todo newTodoData = new Todo();
        newTodoData.setTitle("Updated Todo");
        newTodoData.setCompleted(true);

        when(todoRepository.findById(1L)).thenReturn(Optional.of(existingTodo));
        when(todoRepository.save(any(Todo.class))).thenReturn(existingTodo);

        Todo updatedTodo = todoService.update(1L, newTodoData);
        assertEquals("Updated Todo", updatedTodo.getTitle());
        assertTrue(updatedTodo.isCompleted());
    }

    @Test
    void testUpdateThrowsExceptionWhenTodoNotFound() {
        when(todoRepository.findById(1L)).thenReturn(Optional.empty());

        Todo newTodoData = new Todo();
        newTodoData.setTitle("Updated Todo");
        newTodoData.setCompleted(true);

        assertThrows(TodoNotFoundException.class, () -> {
            todoService.update(1L, newTodoData);
        });
    }

    @Test
    void testDeleteById() {
        doNothing().when(todoRepository).deleteById(1L);
        todoService.deleteById(1L);
        verify(todoRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteByIdThrowsExceptionWhenTodoNotFound() {
        doThrow(new TodoNotFoundException(1L)).when(todoRepository).deleteById(1L);

        assertThrows(TodoNotFoundException.class, () -> {
            todoService.deleteById(1L);
        });
    }
}
