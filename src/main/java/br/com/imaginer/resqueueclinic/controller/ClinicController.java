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
    public ResponseEntity<List<Clinic>> getAllClinics(@PathVariable UUID userId) {
        return ResponseEntity.ok(clinicService.findAllByUserId(userId));
    }

    @GetMapping("/{id}")
    @Operation(description = "Busca uma clínica específica pelo ID da clinica e pelo ID do usuário.", security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Clinic> getClinicById(@PathVariable Long clinicId, @PathVariable UUID userId) {

        return clinicService.findByIdAndUserId(clinicId, userId)
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

        Clinic saveClinic = new Clinic(clinic.getName(),clinic.getAddress(), clinic.getPhone(), clinic.getUser());
        return ResponseEntity.ok(clinicService.save(saveClinic));
    }

    @PutMapping("/{clinicId}/user/{userId}")
    @Operation(description = "Atualiza os dados de uma clínica existente com base no ID da clínica e do usuário.", security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Clinic> updateClinic(
            @PathVariable Long clinicId,
            @PathVariable UUID userId,
            @Valid @RequestBody ClinicForm updatedClinic) {

        return clinicService.findByIdAndUserId(clinicId, userId)
                .map(existingClinic -> {
                    existingClinic.setName(updatedClinic.getName());
                    existingClinic.setAddress(updatedClinic.getAddress());
                    existingClinic.setPhone(updatedClinic.getPhone());

                    Clinic savedClinic = clinicService.save(existingClinic);
                    return ResponseEntity.ok(savedClinic);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{clinicId}/user/{userId}")
    @Operation(description = "Deleta uma clínica pelo ID, e remove o usuário associado se existir.", security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Object> deleteClinic(@PathVariable Long clinicId, @PathVariable UUID userId) {
        return clinicService.findByIdAndUserId(clinicId, userId)
                .map(clinic -> {
                    clinicService.deleteById(clinicId);
                    return ResponseEntity.noContent().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
