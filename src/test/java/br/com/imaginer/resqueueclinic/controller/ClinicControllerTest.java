package br.com.imaginer.resqueueclinic.controller;

import br.com.imaginer.resqueueclinic.domain.form.ClinicForm;
import br.com.imaginer.resqueueclinic.domain.model.Clinic;
import br.com.imaginer.resqueueclinic.service.ClinicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClinicControllerTest {

    @Mock
    private ClinicService clinicService;

    @InjectMocks
    private ClinicController clinicController;

    private Clinic clinic;
    private ClinicForm clinicForm;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        clinic = new Clinic("Clínica Saúde", "Rua A, 123", "123456789");

        clinicForm = new ClinicForm("Clínica Saúde", "Rua A, 123", "123456789");
    }

    @Test
    void testGetAllClinics() {
        when(clinicService.findAll()).thenReturn(Arrays.asList(clinic));

        ResponseEntity<List<Clinic>> response = clinicController.getAllClinics();

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Clínica Saúde", response.getBody().getFirst().getName());

        verify(clinicService, times(1)).findAll();
    }

    @Test
    void testGetClinicById_Success() {
        when(clinicService.findById(1L)).thenReturn(Optional.of(clinic));

        ResponseEntity<Clinic> response = clinicController.getClinicById(1L);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Clínica Saúde", Objects.requireNonNull(response.getBody()).getName());

        verify(clinicService, times(1)).findById(1L);
    }

    @Test
    void testGetClinicById_NotFound() {
        when(clinicService.findById(2L)).thenReturn(Optional.empty());

        ResponseEntity<Clinic> response = clinicController.getClinicById(2L);

        assertEquals(404, response.getStatusCodeValue());

        verify(clinicService, times(1)).findById(2L);
    }

    @Test
    void testCreateClinic() {
        when(clinicService.save(any(Clinic.class))).thenReturn(clinic);

        ResponseEntity<Clinic> response = clinicController.createClinic(clinicForm);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Clínica Saúde", response.getBody().getName());

        verify(clinicService, times(1)).save(any(Clinic.class));
    }

    @Test
    void testUpdateClinic_Success() {
        when(clinicService.findById(1L)).thenReturn(Optional.of(clinic));
        when(clinicService.save(any(Clinic.class))).thenReturn(clinic);

        ResponseEntity<Clinic> response = clinicController.updateClinic(1L, clinicForm);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("Clínica Saúde", response.getBody().getName());

        verify(clinicService, times(1)).findById(1L);
        verify(clinicService, times(1)).save(any(Clinic.class));
    }

    @Test
    void testUpdateClinic_NotFound() {
        when(clinicService.findById(2L)).thenReturn(Optional.empty());

        ResponseEntity<Clinic> response = clinicController.updateClinic(2L, clinicForm);

        assertEquals(404, response.getStatusCodeValue());

        verify(clinicService, times(1)).findById(2L);
    }

    @Test
    void testDeleteClinic_Success() {
        when(clinicService.findById(1L)).thenReturn(Optional.of(clinic));
        doNothing().when(clinicService).deleteById(1L);

        ResponseEntity<Void> response = clinicController.deleteClinic(1L);

        assertEquals(204, response.getStatusCodeValue());

        verify(clinicService, times(1)).findById(1L);
        verify(clinicService, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteClinic_NotFound() {
        when(clinicService.findById(2L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = clinicController.deleteClinic(2L);

        assertEquals(404, response.getStatusCodeValue());

        verify(clinicService, times(1)).findById(2L);
    }
}
