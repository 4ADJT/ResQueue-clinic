package br.com.imaginer.resqueueclinic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clinic")
public class IsAlive {

    @GetMapping("/isAlive")
    public boolean isAlive() {
        return true;
    }
}
