package com.edukus.diabeto.presentation.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PrescriptionDto {

    private Long id;
    private LocalDateTime date;
    private String description;
    private Boolean isActive;
    private String doctorEmail;
    private String patientEmail;

}
