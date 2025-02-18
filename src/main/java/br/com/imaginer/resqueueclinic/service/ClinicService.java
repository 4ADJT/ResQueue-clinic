package br.com.imaginer.resqueueclinic.service;

import br.com.imaginer.resqueueclinic.domain.model.Clinic;
import br.com.imaginer.resqueueclinic.repository.ClinicRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClinicService {

    private final ClinicRepository clinicRepository;

    public ClinicService(ClinicRepository clinicRepository) {
        this.clinicRepository = clinicRepository;
    }

    public List<Clinic> findAll() {
        return clinicRepository.findAll();
    }

    public Optional<Clinic> findById(Long id) {
        return clinicRepository.findById(id);
    }

    public Clinic save(Clinic clinic) {
        return clinicRepository.save(clinic);
    }

    public void deleteById(Long id) {
        clinicRepository.deleteById(id);
    }
}