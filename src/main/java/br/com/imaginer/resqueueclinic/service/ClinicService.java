package br.com.imaginer.resqueueclinic.service;

import br.com.imaginer.resqueueclinic.controller.feign.ClinicToVaccineEdge;
import br.com.imaginer.resqueueclinic.domain.form.ClinicForm;
import br.com.imaginer.resqueueclinic.domain.model.Clinic;
import br.com.imaginer.resqueueclinic.domain.model.User;
import br.com.imaginer.resqueueclinic.domain.request.ClinicRequestToVaccineService;
import br.com.imaginer.resqueueclinic.domain.request.UpdateClinic;
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
  private final ClinicToVaccineEdge edge;

  public ClinicService(ClinicRepository clinicRepository, UserRepository userRepository, ClinicToVaccineEdge edge) {
    this.clinicRepository = clinicRepository;
    this.userRepository = userRepository;
    this.edge = edge;
  }

  public List<Clinic> findAllByUserId(UUID userId) {
    try {
      return clinicRepository.findByUserId(userId);
    } catch (Exception e) {
      throw new HandlerException("Usuário não possui uma clinica cadastrada.");
    }
  }

  public Optional<Clinic> findByIdAndUserId(UUID clinicId, UUID userId) {
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


    Clinic savedClinic = clinicRepository.save(newClinic);
    edge.createClinic(
        new ClinicRequestToVaccineService(
            savedClinic.getId(),
            savedClinic.getUser().getId(),
            true
        )
    );

    return savedClinic;
  }

  @Transactional
  public Optional<Clinic> updateClinic(UUID clinicId, UUID userId, UpdateClinic updatedClinic) {

    Optional<Clinic> byIdAndUserId = clinicRepository.findByIdAndUserId(clinicId, userId)
        .map(existingClinic -> {
          existingClinic.setName(updatedClinic.name() == null ? existingClinic.getName() : updatedClinic.name());
          existingClinic.setAddress(updatedClinic.address() == null ? existingClinic.getAddress() : updatedClinic.address());
          existingClinic.setPhone(updatedClinic.phone() == null ? existingClinic.getPhone() : updatedClinic.phone());

          return clinicRepository.save(existingClinic);
        });

    if (byIdAndUserId.isPresent()) {
      return byIdAndUserId;
    }

    throw new HandlerException("Id da clinica não localizado.");
  }

  @Transactional
  public void deleteClinic(UUID clinicId, UUID userId) {
    Optional<Clinic> clinic = clinicRepository.findByIdAndUserId(clinicId, userId);

    if (clinic.isEmpty()) {
      throw new HandlerException("Id da clinica não localizado.");
    }

    edge.deactivateClinic(clinic.get().getId());
    clinicRepository.delete(clinic.get());

  }
}
