package br.com.imaginer.resqueueclinic.repository;

import br.com.imaginer.resqueueclinic.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findById(UUID id);
}
