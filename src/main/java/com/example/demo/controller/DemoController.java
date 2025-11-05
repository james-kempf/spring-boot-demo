package com.example.demo.controller;

import com.example.demo.model.Greeting;
import com.example.demo.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class DemoController {

    @Autowired
    private KafkaService kafkaService;

    private final AtomicInteger counter = new AtomicInteger();

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(defaultValue = "World") String name) {
        var i = 0;
        System.out.println(i);
        return new Greeting(counter.getAndIncrement(), "Hello " + name);
    }

    @GetMapping("/http-client")
    public ResponseEntity<String> httpClient() {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://jsonplaceholder.typicode.com/todos/1"))
                .GET().build();

        try {
            String body = client.send(request, HttpResponse.BodyHandlers.ofString()).body();
            return ResponseEntity.ok(body);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error");
        }
    }

    @PostMapping("/publish")
    public String postMessage(@RequestParam String message) {
        kafkaService.sendMessage(message);
        return "Message Sent: " + message;
    }

}
