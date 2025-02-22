package br.com.imaginer.resqueueclinic.controller;

import br.com.imaginer.resqueueclinic.domain.form.ClinicForm;
import br.com.imaginer.resqueueclinic.domain.model.Clinic;
import br.com.imaginer.resqueueclinic.domain.model.User;
import br.com.imaginer.resqueueclinic.service.ClinicService;
import br.com.imaginer.resqueueclinic.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/clinics")
public class ClinicController {

    private final ClinicService clinicService;
    private final UserService userService;

    public ClinicController(ClinicService clinicService, UserService userService) {
        this.clinicService = clinicService;
        this.userService = userService;
    }

    @GetMapping
    @Operation(description = "Busca todas as clínicas cadastradas pelo usuário especificado.", security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<List<Clinic>> getAllClinics(@PathVariable UUID idUser) {

        log.info(idUser.toString());
        return ResponseEntity.ok(clinicService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(description = "Busca uma clínica específica pelo ID e pelo ID do usuário.", security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Clinic> getClinicById(@PathVariable Long id, UUID idUser) {

        log.info(idUser.toString());
        return clinicService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(description = "Cria uma nova clínica associada a um usuário, garantindo que um usuário não tenha mais de uma clínica cadastrada.", security = { @SecurityRequirement(name = "bearer-key") })
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
    @Operation(description = "Atualiza os dados de uma clínica existente com base no ID fornecido.", security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Clinic> updateClinic(@PathVariable Long id, @Valid @RequestBody ClinicForm updatedClinic) {
        Clinic updateClinic = new Clinic(updatedClinic.getName(),updatedClinic.getAddress(), updatedClinic.getPhone());
        return clinicService.findById(id)
                .map(existingClinic -> ResponseEntity.ok(clinicService.save(updateClinic)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Deleta uma clínica pelo ID, se ela existir no sistema.", security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Void> deleteClinic(@PathVariable Long id) {
        if (clinicService.findById(id).isPresent()) {
            clinicService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
