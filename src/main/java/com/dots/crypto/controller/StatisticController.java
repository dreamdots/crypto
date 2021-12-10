package com.dots.crypto.controller;

import com.dots.crypto.model.Process;
import com.dots.crypto.repository.ProcessRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/statistic")
@RequiredArgsConstructor(onConstructor_= @Autowired)
public class StatisticController {
    private final ProcessRepository processRepository;

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }

    @GetMapping("/findAll")
    @PreAuthorize("hasRole('ADMIN')")
    public List<Process<?>> findAll() {
        log.info("Show all process");
        return processRepository.findAll();
    }
}
