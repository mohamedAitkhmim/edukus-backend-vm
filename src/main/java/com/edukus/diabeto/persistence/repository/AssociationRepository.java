package com.edukus.diabeto.persistence.repository;

import com.edukus.diabeto.persistence.entity.Association;
import com.edukus.diabeto.persistence.entity.UserProfile;
import com.edukus.diabeto.presentation.dto.UserDto;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AssociationRepository extends JpaRepository<Association, Long> {

  @Query("select new com.edukus.diabeto.presentation.dto.UserDto(up.user.userId, up.fullName, up.length, up.weight, up.gender, up.diabetesType, up.birthDate, up.infectionDate) from UserProfile up where up.user.email in "
      + "(select a.patient.email  from Association a  where a.doctor.userId = :doctorId )")
  List<UserDto> findPatientsByDoctorId(@Param("doctorId") String doctorId);

  @Query("select up from UserProfile up , Association a  where up.user.email = a.doctor.email and a.patient.email = :id ")
  List<UserProfile> findDoctorsByPatientId(@Param("id") String id);


}
