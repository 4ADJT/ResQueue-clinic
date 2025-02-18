package br.com.imaginer.resqueueclinic.controller;

import br.com.imaginer.resqueueclinic.domain.form.ClinicForm;
import br.com.imaginer.resqueueclinic.domain.model.Clinic;
import br.com.imaginer.resqueueclinic.service.ClinicService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clinics")
public class ClinicController {

    private final ClinicService clinicService;

    public ClinicController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @GetMapping
    public ResponseEntity<List<Clinic>> getAllClinics() {
        return ResponseEntity.ok(clinicService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Clinic> getClinicById(@PathVariable Long id) {
        return clinicService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
//    @PreAuthorize("hasRole('USER')") TODO: Ver com o Rodrigo
    public ResponseEntity<Clinic> createClinic(@Valid @RequestBody ClinicForm clinic) {
        Clinic saveClinic = new Clinic(clinic.getName(),clinic.getAddress(), clinic.getPhone());
        return ResponseEntity.ok(clinicService.save(saveClinic));
    }

    @PutMapping("/{id}")
//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Clinic> updateClinic(@PathVariable Long id, @Valid @RequestBody ClinicForm updatedClinic) {
        Clinic updateClinic = new Clinic(updatedClinic.getName(),updatedClinic.getAddress(), updatedClinic.getPhone());
        return clinicService.findById(id)
                .map(existingClinic -> ResponseEntity.ok(clinicService.save(updateClinic)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
//    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Void> deleteClinic(@PathVariable Long id) {
        if (clinicService.findById(id).isPresent()) {
            clinicService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}