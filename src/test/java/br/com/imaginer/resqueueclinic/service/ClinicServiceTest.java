package br.com.imaginer.resqueueclinic.service;

import br.com.imaginer.resqueueclinic.controller.ClinicController;
import br.com.imaginer.resqueueclinic.domain.form.ClinicForm;
import br.com.imaginer.resqueueclinic.domain.form.ClinicFormSimple;
import br.com.imaginer.resqueueclinic.domain.model.Clinic;
import br.com.imaginer.resqueueclinic.domain.model.User;
import br.com.imaginer.resqueueclinic.domain.request.UpdateClinic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClinicServiceTest {

    @Mock
    private ClinicService clinicService;

    @InjectMocks
    private ClinicController clinicController;

    private Jwt jwt;
    private UUID userId;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        jwt = mock(Jwt.class);
        when(jwt.getSubject()).thenReturn(userId.toString());
        lenient().when(jwt.getClaim("email")).thenReturn("user@example.com");
    }

    @Test
    void testGetClinicsByUser() {
        List<Clinic> clinics = List.of(new Clinic());
        when(clinicService.findAllByUserId(userId)).thenReturn(clinics);

        ResponseEntity<List<Clinic>> response = clinicController.getClinicsByUser(jwt);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(clinics, response.getBody());
    }

//    @Test
//    void testCreateClinic() {
//        ClinicFormSimple clinicFormSimple = new ClinicFormSimple("Sá", "123213","119673");
//        ClinicForm clinicForm = new ClinicForm(clinicFormSimple, new User(userId, "user@example.com"));
//        Clinic clinic = new Clinic();
//        when(clinicService.createClinic(any(ClinicForm.class))).thenReturn(clinic);
//
//        ResponseEntity<Clinic> response = clinicController.createClinic(clinicFormSimple, jwt);
//
//        assertEquals(200, response.getStatusCodeValue());
//        assertEquals(clinic, response.getBody());
//    }

    @Test
    void testUpdateClinic() {
        UUID clinicId = UUID.randomUUID();
        UpdateClinic updateClinic = new UpdateClinic("Sá", "123213","119673");
        Clinic clinic = new Clinic();
        when(clinicService.updateClinic(clinicId, userId, updateClinic)).thenReturn(Optional.of(clinic));

        ResponseEntity<?> response = clinicController.updateClinic(clinicId, jwt, updateClinic);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(Optional.of(clinic), response.getBody());
    }

    @Test
    void testDeleteClinic() {
        UUID clinicId = UUID.randomUUID();
        doNothing().when(clinicService).deleteClinic(clinicId, userId);

        ResponseEntity<Void> response = clinicController.deleteClinic(clinicId, jwt);

        assertEquals(204, response.getStatusCodeValue());
        verify(clinicService, times(1)).deleteClinic(clinicId, userId);
    }

}
