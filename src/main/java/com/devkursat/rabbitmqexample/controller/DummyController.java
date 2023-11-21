package com.devkursat.rabbitmqexample.controller;

import com.devkursat.rabbitmqexample.service.DummyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("dummy")
public class DummyController {

    public DummyController(DummyService dummyService) {
        this.dummyService = dummyService;
    }

    private DummyService dummyService;

    @GetMapping("/")
    public ResponseEntity<?> asynchronous() {
        dummyService.requestReceiverService();
        return ResponseEntity.ok().body("Your request has been received.");
    }

    @GetMapping("synchronous")
    public ResponseEntity<?> synchronous() throws InterruptedException {
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            list.add(i);
        }
        Thread.sleep(3000);

        System.out.println("List successfully filled.");
        System.out.println("List length: " + list.size());
        return ResponseEntity.ok().body("Your request has been completed.");
    }

}
