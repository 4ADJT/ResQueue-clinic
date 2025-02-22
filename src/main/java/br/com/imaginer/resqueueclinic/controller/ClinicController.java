package br.com.imaginer.resqueueclinic.controller;

import br.com.imaginer.resqueueclinic.domain.form.ClinicForm;
import br.com.imaginer.resqueueclinic.domain.model.Clinic;
import br.com.imaginer.resqueueclinic.service.ClinicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/clinics")
public class ClinicController {

    private final ClinicService clinicService;

    public ClinicController(ClinicService clinicService) {
        this.clinicService = clinicService;
    }

    @GetMapping
    @Operation(description = "Busca todas as clínicas cadastradas pelo usuário especificado.", security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<List<Clinic>> getAllClinics(@PathVariable UUID userId) {
        return ResponseEntity.ok(clinicService.findAllByUserId(userId));
    }

    @GetMapping("/{clinicId}/user/{userId}")
    @Operation(description = "Busca uma clínica específica pelo ID da clínica e pelo ID do usuário.", security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Clinic> getClinicById(@PathVariable Long clinicId, @PathVariable UUID userId) {
        return clinicService.findByIdAndUserId(clinicId, userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(description = "Cria uma nova clínica associada a um usuário.", security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Clinic> createClinic(@Valid @RequestBody ClinicForm clinicForm) {
        return ResponseEntity.ok(clinicService.createClinic(clinicForm));
    }

    @PutMapping("/{clinicId}/user/{userId}")
    @Operation(description = "Atualiza os dados de uma clínica existente.", security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Clinic> updateClinic(
            @PathVariable Long clinicId,
            @PathVariable UUID userId,
            @Valid @RequestBody ClinicForm updatedClinic) {

        return clinicService.updateClinic(clinicId, userId, updatedClinic)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{clinicId}/user/{userId}")
    @Operation(description = "Deleta uma clínica pelo ID.", security = { @SecurityRequirement(name = "bearer-key") })
    public ResponseEntity<Void> deleteClinic(@PathVariable Long clinicId, @PathVariable UUID userId) {
        if (clinicService.deleteClinic(clinicId, userId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
