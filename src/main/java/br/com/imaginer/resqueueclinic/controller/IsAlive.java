package br.com.imaginer.resqueueclinic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/isAlive")
public class IsAlive {

    @GetMapping
    public boolean isAlive() {
        return true;
    }
}
