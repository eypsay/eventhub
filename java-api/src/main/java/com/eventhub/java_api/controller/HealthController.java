package com.eventhub.java_api.controller;

import com.eventhub.java_api.dto.HealthResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class HealthController {

@GetMapping("/health")
    public HealthResponseDto  health(){
        return new HealthResponseDto("UP");
    }
}
