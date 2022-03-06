package com.example.springreactiveweb.Service;

import com.example.springreactiveweb.Model.Todo;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface TodoReactiveService {
    Flux<Todo> getTodoList();

    Flux<Todo> getPendingTodoList();

    Mono<Todo> getTodo(int id);

    Mono<Todo> addTask(Todo task);
}
