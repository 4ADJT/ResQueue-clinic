package br.com.imaginer.resqueueclinic.controller.feign;

import br.com.imaginer.resqueueclinic.domain.request.ClinicRequestToVaccineService;
import br.com.imaginer.resqueueclinic.domain.request.ClinicResponseToVaccineService;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@FeignClient(name = "RESQUEUE-VACCINE")
public interface ClinicToVaccineEdge {
  @PostMapping("/vaccine/clinic/create")
  ResponseEntity<ClinicResponseToVaccineService> createClinic(
      @RequestBody ClinicRequestToVaccineService clinic
  );

  @PutMapping("/vaccine/clinic/deactivate/{clinicId}")
  ResponseEntity<ClinicResponseToVaccineService> deactivateClinic(
      @PathVariable("clinicId") UUID clinicId
  );
}
