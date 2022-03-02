package com.example.springreactiveweb.Service;

import com.example.springreactiveweb.Model.Todo;
import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TodoReactiveService {
    Flux<Todo> getTodoList();
    Flux<Todo> getPendingTodoList();
    Mono<ResponseEntity<Todo>> getTodo(String id);
    Mono<Todo> addTask(Todo task);
}
