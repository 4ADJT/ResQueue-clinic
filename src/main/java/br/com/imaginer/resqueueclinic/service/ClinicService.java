package br.com.imaginer.resqueueclinic.service;

import br.com.imaginer.resqueueclinic.domain.form.ClinicForm;
import br.com.imaginer.resqueueclinic.domain.model.Clinic;
import br.com.imaginer.resqueueclinic.domain.model.User;
import br.com.imaginer.resqueueclinic.handler.exceptions.HandlerException;
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
        try {
            return clinicRepository.findByUserId(userId);
        } catch (Exception e) {
            throw new HandlerException("Usuário não possui uma clinica cadastrada.");
        }
    }

    public Optional<Clinic> findByIdAndUserId(Long clinicId, UUID userId) {
        return clinicRepository.findByIdAndUserId(clinicId, userId);
    }

    @Transactional
    public Clinic createClinic(ClinicForm clinicForm) {
        UUID id = clinicForm.getUser().getId();
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isPresent()) {
            throw new HandlerException("Esse Usuário já tem uma Clínica cadastrada em seu nome.");
        }

        User savedUser = userRepository.save(clinicForm.getUser());
        Clinic newClinic = new Clinic(clinicForm.getName(), clinicForm.getAddress(), clinicForm.getPhone(), savedUser);

        return clinicRepository.save(newClinic);
    }

    @Transactional
    public Optional<Clinic> updateClinic(Long clinicId, UUID userId, ClinicForm updatedClinic) {

        Optional<Clinic> byIdAndUserId = clinicRepository.findByIdAndUserId(clinicId, userId).map(existingClinic -> {
            existingClinic.setName(updatedClinic.getName());
            existingClinic.setAddress(updatedClinic.getAddress());
            existingClinic.setPhone(updatedClinic.getPhone());
            return clinicRepository.save(existingClinic);
        });

        if (byIdAndUserId.isPresent()) {
            return byIdAndUserId;
        }
        throw new HandlerException("Id da clinica não localizado.");
    }

    @Transactional
    public void deleteClinic(Long clinicId, UUID userId) {
        Optional<Clinic> clinic = clinicRepository.findByIdAndUserId(clinicId, userId);
        if (clinic.isPresent()) {
            clinicRepository.delete(clinic.get());
        } else {
            throw new HandlerException("Id da clinica não localizado.");
        }
    }
}
