package br.com.imaginer.resqueueclinic.controller.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "RESQUEUE-VACCINE")
public interface ResqueueVaccineClient {
  @GetMapping("/vaccine/message/{messageText}")
  ResponseEntity<Void> sendMessage(@PathVariable("messageText") String messageText);
}
