package br.com.imaginer.resqueueclinic.service;

import br.com.imaginer.resqueueclinic.domain.model.Clinic;
import br.com.imaginer.resqueueclinic.repository.ClinicRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClinicService {

    private final ClinicRepository clinicRepository;

    public ClinicService(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    public Optional<Clinic> findByIdAndUserId(Long clinicId, UUID userId) {
        return clinicRepository.findByIdAndUserId(clinicId, userId);
    }

    public Clinic save(Clinic clinic) {
        return clinicRepository.save(clinic);
    }

    public void deleteById(Long clinicId) {
        clinicRepository.findById(clinicId).ifPresent(clinicRepository::delete);
    }

    public List<Clinic> findAllByUserId(UUID userId) { return clinicRepository.findByUserId(userId); };
}