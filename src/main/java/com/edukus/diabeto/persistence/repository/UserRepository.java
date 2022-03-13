package com.edukus.diabeto.persistence.repository;

import com.edukus.diabeto.persistence.entity.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, String> {

  Optional<User> findByEmail(String email);

}
