package br.com.imaginer.resqueueclinic.controller;

import br.com.imaginer.resqueueclinic.domain.form.ClinicForm;
import br.com.imaginer.resqueueclinic.domain.model.Clinic;
import br.com.imaginer.resqueueclinic.domain.model.User;
import br.com.imaginer.resqueueclinic.service.ClinicService;
import br.com.imaginer.resqueueclinic.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/clinics")
public class ClinicController {

    private final ClinicService clinicService;
    private final UserService userService;

    public ClinicController(ClinicService clinicService, UserService userService) {
        this.clinicService = clinicService;
        this.userService = userService;
    }


//    Buscar as clinicas que apenas o cliente cadastrou
    @GetMapping
    @Operation(description = "#######", security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<List<Clinic>> getAllClinics(@PathVariable UUID idUser) {
        return ResponseEntity.ok(clinicService.findAll());
    }

//    Buscas as clinicas pelo id e com o idUser
    @GetMapping("/{id}")
    @Operation(description = "#######", security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Clinic> getClinicById(@PathVariable Long id, @PathVariable UUID idUser) {
        return clinicService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(description = "#######", security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Clinic> createClinic(@Valid @RequestBody ClinicForm clinic) {
        Optional<User> existUser = userService.findById(clinic.getUser().id());

        if (existUser.isEmpty())
            userService.save(clinic.getUser());
        else
            throw new IllegalArgumentException("Esse Usuário já tem uma Clinica cadastrado em seu nome.");

        Clinic saveClinic = new Clinic(clinic.getName(),clinic.getAddress(), clinic.getPhone());
        return ResponseEntity.ok(clinicService.save(saveClinic));
    }

    @PutMapping("/{id}")
    @Operation(description = "#######", security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Clinic> updateClinic(@PathVariable Long id, @Valid @RequestBody ClinicForm updatedClinic) {
        Clinic updateClinic = new Clinic(updatedClinic.getName(),updatedClinic.getAddress(), updatedClinic.getPhone());
        return clinicService.findById(id)
                .map(existingClinic -> ResponseEntity.ok(clinicService.save(updateClinic)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(description = "#######", security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Void> deleteClinic(@PathVariable Long id) {
        if (clinicService.findById(id).isPresent()) {
            clinicService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}