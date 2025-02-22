package br.com.imaginer.resqueueclinic.service;

import br.com.imaginer.resqueueclinic.domain.form.ClinicForm;
import br.com.imaginer.resqueueclinic.domain.model.Clinic;
import br.com.imaginer.resqueueclinic.domain.model.User;
import br.com.imaginer.resqueueclinic.repository.ClinicRepository;
import br.com.imaginer.resqueueclinic.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ClinicService {

    private final ClinicRepository clinicRepository;
    private final UserRepository userRepository;

    public ClinicService(ClinicRepository clinicRepository, UserRepository userRepository) {
        this.clinicRepository = clinicRepository;
        this.userRepository = userRepository;
    }

    public List<Clinic> findAllByUserId(UUID userId) {
        return clinicRepository.findByUserId(userId);
    }

    public Optional<Clinic> findByIdAndUserId(Long clinicId, UUID userId) {
        return clinicRepository.findByIdAndUserId(clinicId, userId);
    }

    @Transactional
    public Clinic createClinic(ClinicForm clinicForm) {
        Optional<User> existingUser = userRepository.findById(clinicForm.getUser().id());

        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Esse Usuário já tem uma Clínica cadastrada em seu nome.");
        }

        User savedUser = userRepository.save(clinicForm.getUser());
        Clinic newClinic = new Clinic(clinicForm.getName(), clinicForm.getAddress(), clinicForm.getPhone(), savedUser);

        return clinicRepository.save(newClinic);
    }

    @Transactional
    public Optional<Clinic> updateClinic(Long clinicId, UUID userId, ClinicForm updatedClinic) {
        return clinicRepository.findByIdAndUserId(clinicId, userId).map(existingClinic -> {
            existingClinic.setName(updatedClinic.getName());
            existingClinic.setAddress(updatedClinic.getAddress());
            existingClinic.setPhone(updatedClinic.getPhone());
            return clinicRepository.save(existingClinic);
        });
    }

    @Transactional
    public boolean deleteClinic(Long clinicId, UUID userId) {
        Optional<Clinic> clinic = clinicRepository.findByIdAndUserId(clinicId, userId);
        if (clinic.isPresent()) {
            clinicRepository.delete(clinic.get());
            return true;
        }
        return false;
    }
}
