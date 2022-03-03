package com.example.springreactiveweb.Service;

import com.example.springreactiveweb.Model.Todo;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class TodoReactiveServiceImpl implements TodoReactiveService {

    private final WebClient webClient;

    TodoReactiveServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8081/todo").build();
    }

    @Override
    public Flux<Todo> getTodoList() {
        return webClient.get().uri("/")
                .retrieve()
                .bodyToFlux(Todo.class)
                .delayElements(Duration.ofSeconds(1))
                .log();
    }

    @Override
    public Flux<Todo> getPendingTodoList() {
        return webClient.get().uri("/")
                .retrieve()
                .bodyToFlux(Todo.class)
                .flatMap(Flux::just)
                .filter(todo -> !todo.isComplete())
                .log();
    }

    @Override
    public Mono<Todo> getTodo(String id) {
        return webClient.get().uri("/{id}", id)
                .retrieve()
                .bodyToMono(Todo.class).log();
//                .map((item) -> new ResponseEntity<>(item, HttpStatus.OK))
//                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    public Mono<Todo> addTask(Todo task) {
        return webClient
                .post()
                .uri("/add")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(task)
                .exchangeToMono(clientResponse ->
                        clientResponse.bodyToMono(Todo.class)
                                .onErrorReturn(new Todo(99999, "Error", false))
                                .log());
    }
}
