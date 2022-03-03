package com.example.springreactiveweb.Service;

import com.example.springreactiveweb.Model.Todo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

class TodoReactiveServiceImplTest {
    private static WebClient webClient;

    @BeforeAll
    static void init() {
        webClient = WebClient.create("http://localhost:8082/todo");
    }


    @Test
    void getTodo() {
        Todo todo = new Todo(1, "sleep", true);
        Mono<Todo> expectedTodo = Mono.just(todo).log();
        StepVerifier.create(expectedTodo)
                .assertNext(response -> assertThat(response.isComplete())
                        .isEqualTo(true))
                .verifyComplete();
    }


    @Test
    public void createTodo() {
        Todo task = new Todo(1, "sleep", false);
        Mono<Todo> todo =
                webClient
                        .post()
                        .uri("/add")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(task)
                        .exchangeToMono(clientResponse ->
                                clientResponse.bodyToMono(Todo.class)).log();
        StepVerifier.create(todo)
                .assertNext(response -> assertThat(response.getTitle())
                        .isEqualTo("sleep"))
                .verifyComplete();
    }


    @Test
    void getPendingTodoList() {
        var todo = webClient.get().uri("/pending")
                .retrieve()
                .bodyToFlux(Todo.class)
                .flatMap(Flux::just)
                .filter(task -> !task.isComplete()).log();
        StepVerifier.create(todo).expectNextCount(2).verifyComplete();
    }
}