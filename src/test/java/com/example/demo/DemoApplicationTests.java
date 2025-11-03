package com.example.demo;

import com.example.demo.controller.DemoController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DemoApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    DemoController demoController;

    @Test
    void contextLoads() throws Exception {
        assertNotNull(demoController);
    }

    @Test
    void helloShouldReturnDefaultMessage() throws Exception {
        String response = restTemplate.getForObject("http://localhost:" + port + "/hello", String.class);
        assertEquals("Hello World!", response);
    }

    @Test
    void helloShouldReturnGivenMessage() {
        String response = demoController.hello("test");
        assertEquals("Hello test!", response);
    }

}
