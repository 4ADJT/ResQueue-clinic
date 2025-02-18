package br.com.imaginer.resqueueclinic.repository;

import br.com.imaginer.resqueueclinic.domain.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {
}
