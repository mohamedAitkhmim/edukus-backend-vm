package com.edukus.diabeto.persistence.repository;

import com.edukus.diabeto.persistence.entity.UserFile;
import com.edukus.diabeto.persistence.entity.UserProfile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserFileRepository extends JpaRepository<UserFile, Long> {

  UserFile findByUser_EmailAndActiveTrue(String email);

}
