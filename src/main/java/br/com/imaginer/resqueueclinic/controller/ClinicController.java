package br.com.imaginer.resqueueclinic.controller;

import br.com.imaginer.resqueueclinic.domain.form.ClinicForm;
import br.com.imaginer.resqueueclinic.domain.form.ClinicFormSimple;
import br.com.imaginer.resqueueclinic.domain.model.Clinic;
import br.com.imaginer.resqueueclinic.domain.model.User;
import br.com.imaginer.resqueueclinic.service.ClinicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/clinic")
public class ClinicController {

    private final ClinicService clinicService;

    public ClinicController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @GetMapping
    @Operation(description = "Busca todas as clínicas cadastradas pelo usuário especificado.", security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<List<Clinic>> getAllClinics(@AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        return ResponseEntity.ok(clinicService.findAllByUserId(userId));
    }

    /*@GetMapping("/{clinicId}/user/{userId}")
    @Operation(description = "Busca uma clínica específica pelo ID da clínica e pelo ID do usuário.", security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Clinic> getClinicById(@PathVariable Long clinicId, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        return clinicService.findByIdAndUserId(clinicId, userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }*/

    @PostMapping
    @Operation(description = "Cria uma nova clínica associada a um usuário.", security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Clinic> createClinic(@Valid @RequestBody ClinicFormSimple clinicFormSimple, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        ClinicForm clinicForm = new ClinicForm(clinicFormSimple, new User(userId, jwt.getClaim("email")));
        return ResponseEntity.ok(clinicService.createClinic(clinicForm));
    }

    @PutMapping("/{clinicId}")
    @Operation(description = "Atualiza os dados de uma clínica existente.", security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<?> updateClinic(@PathVariable Long clinicId, @AuthenticationPrincipal Jwt jwt, @Valid @RequestBody ClinicForm updatedClinic) {
        UUID userId = UUID.fromString(jwt.getSubject());
        Optional<Clinic> clinic = clinicService.updateClinic(clinicId, userId, updatedClinic);
        return ResponseEntity.ok(clinic);
    }

    @DeleteMapping("/{clinicId}")
    @Operation(description = "Deleta uma clínica pelo ID.", security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Void> deleteClinic(@PathVariable Long clinicId, @AuthenticationPrincipal Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        clinicService.deleteClinic(clinicId, userId);
        return ResponseEntity.noContent().build();

    }
}
