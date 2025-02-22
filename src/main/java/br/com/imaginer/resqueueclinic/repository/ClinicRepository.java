package br.com.imaginer.resqueueclinic.repository;

import br.com.imaginer.resqueueclinic.domain.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    List<Clinic> findByUserId(UUID userId);

    Optional<Clinic> findByIdAndUserId(Long id, UUID userId);
}
