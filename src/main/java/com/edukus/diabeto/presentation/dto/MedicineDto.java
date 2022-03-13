package com.edukus.diabeto.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class MedicineDto {

  private Long id;
  private String label;
  private Double qte;
  private String unit;
  private String prandial;
  private List<MedicineTimeDto> times;
  private boolean notification;

  public List<String> getMoments() {
    return CollectionUtils.emptyIfNull(times).stream().map(MedicineTimeDto::getTime).map(this::convertToMoment).collect(Collectors.toList());
  }

  public void setTimes(List<LocalDateTime> times) {
    this.times = CollectionUtils.emptyIfNull(times).stream().map(time -> new MedicineTimeDto(time, notification)).collect(Collectors.toList());
  }


  public List<LocalDateTime> getTimes() {
    return CollectionUtils.emptyIfNull(times).stream().map(MedicineTimeDto::getTime).collect(Collectors.toList());
  }

  public boolean getNotification() {
    return CollectionUtils.emptyIfNull(times).stream().findAny().map(MedicineTimeDto::isNotification).orElse(false);
  }

  private String convertToMoment(LocalDateTime time) {
    if (6 <= time.getHour() && time.getHour() < 12) {
      return "morning";
    } else if (12 <= time.getHour() && time.getHour() < 20) {
      return "evening";
    }
    return "night";
  }
}
