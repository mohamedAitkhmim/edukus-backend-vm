package com.edukus.diabeto.persistence.repository;

import com.edukus.diabeto.persistence.entity.UserProfile;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {

  Optional<UserProfile> findByUser_Email(String email);

  @Query("select up from UserProfile up where up.user.role.code =:role and up.user.userId = :id ")
  UserProfile findDoctorByd(@Param("id") String id, @Param("role") String role);


}
