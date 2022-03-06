package com.example.springreactiveweb.Controller;

import com.example.springreactiveweb.Model.Todo;
import com.example.springreactiveweb.Service.TodoReactiveServiceImpl;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
public class TodoReactiveController {
    TodoReactiveServiceImpl todoReactiveService;

    TodoReactiveController(TodoReactiveServiceImpl todoReactiveService) {
        this.todoReactiveService = todoReactiveService;
    }

    @GetMapping(value = "/todo/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Todo> getTodoList() {
        return todoReactiveService.getTodoList();
    }

    @GetMapping(value = "/todo/pending", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Todo> getPendingTodo() {
        return todoReactiveService.getPendingTodoList();
    }

    @GetMapping(value = "/todo/{id}")
    public Mono<Todo> getMonoTodo(@PathVariable int id) {
        return todoReactiveService.getTodo(id);
    }

    @PostMapping(value = "/todo/add")
    public Mono<Todo> addTask(@RequestBody Todo task) {
        return todoReactiveService.addTask(task);
    }
}