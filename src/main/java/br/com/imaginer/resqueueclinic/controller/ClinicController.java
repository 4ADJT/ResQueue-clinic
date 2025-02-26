package br.com.imaginer.resqueueclinic.controller;

import br.com.imaginer.resqueueclinic.domain.form.ClinicForm;
import br.com.imaginer.resqueueclinic.domain.form.ClinicFormSimple;
import br.com.imaginer.resqueueclinic.domain.model.Clinic;
import br.com.imaginer.resqueueclinic.domain.model.User;
import br.com.imaginer.resqueueclinic.domain.request.UpdateClinic;
import br.com.imaginer.resqueueclinic.service.ClinicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Tag(name = "Clinic Controller")
@RestController
@RequestMapping("/clinic")
public class ClinicController {

    private final ClinicService clinicService;

    public ClinicController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @GetMapping
    @Operation(description = "Busca a clínicas cadastrado pelo usuário especificado.", security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<List<Clinic>> getClinicsByUser(@AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        return ResponseEntity.ok(clinicService.findAllByUserId(userId));
    }

    @PostMapping
    @Operation(description = "Cria uma nova clínica associada a um usuário.", security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Clinic> createClinic(@Valid @RequestBody ClinicFormSimple clinicFormSimple, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        ClinicForm clinicForm = new ClinicForm(clinicFormSimple, new User(userId, jwt.getClaim("email")));
        Clinic newClinic = clinicService.createClinic(clinicForm);
        return ResponseEntity.status(201).body(newClinic);
    }

    @PutMapping("/{clinicId}")
    @Operation(description = "Atualiza os dados de uma clínica existente.", security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Optional<Clinic>> updateClinic(@PathVariable UUID clinicId,
                                                         @AuthenticationPrincipal Jwt jwt,
                                                         @Valid @RequestBody UpdateClinic updatedClinic) {
        UUID userId = UUID.fromString(jwt.getSubject());
        Optional<Clinic> clinic = clinicService.updateClinic(clinicId, userId, updatedClinic);
        return ResponseEntity.ok(clinic);
    }

    @DeleteMapping("/{clinicId}")
    @Operation(description = "Deleta uma clínica pelo ID.", security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Void> deleteClinic(@PathVariable UUID clinicId, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        clinicService.deleteClinic(clinicId, userId);
        return ResponseEntity.noContent().build();

    }
}
