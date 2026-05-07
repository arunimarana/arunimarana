package com.example.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/hello")
    public ResponseEntity<Map<String, String>> hello(
            @RequestParam(defaultValue = "World") String name) {
        return ResponseEntity.ok(Map.of(
            "message", "Hello, " + name + "!",
            "timestamp", Instant.now().toString()
        ));
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
            "status", "UP",
            "service", "hello-api",
            "timestamp", Instant.now().toString()
        ));
    }
}
