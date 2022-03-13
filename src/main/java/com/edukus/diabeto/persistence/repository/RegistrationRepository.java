package com.edukus.diabeto.persistence.repository;

import com.edukus.diabeto.persistence.entity.Registration;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RegistrationRepository extends CrudRepository<Registration, Long> {

  Optional<Registration> findByUser_UserId(String userId);

  Optional<Registration> findByUser_Email(String email);

}
