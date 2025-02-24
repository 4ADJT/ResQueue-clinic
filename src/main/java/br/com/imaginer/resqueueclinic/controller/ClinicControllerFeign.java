package br.com.imaginer.resqueueclinic.controller;

import br.com.imaginer.resqueueclinic.controller.feign.ResqueueVaccineClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/clinic/api")
@Tag(name = "Test")
public class ClinicControllerFeign {
  private final ResqueueVaccineClient resqueueVaccineClient;

  public ClinicControllerFeign(ResqueueVaccineClient resqueueVaccineClient) {
    this.resqueueVaccineClient = resqueueVaccineClient;
  }

  @Operation(description = "Test Feign", security = {@SecurityRequirement(name = "bearer-key")})
  @GetMapping("/send-message/{messageText}")
  public ResponseEntity<?> callVaccineService(@PathVariable String messageText) {
    ResponseEntity<Void> response = resqueueVaccineClient.sendMessage(messageText);
    return ResponseEntity.status(response.getStatusCode()).build();
  }
}
