package com.edukus.diabeto.persistence.repository;

import com.edukus.diabeto.persistence.entity.Role;
import com.edukus.diabeto.persistence.entity.User;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

  Optional<Role> findByCode(String code);

}
