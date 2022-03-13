package com.edukus.diabeto.presentation.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ArticleDto {

  private Long id;
  private String author;
  private String title;
  private String description;
  private String urlToImage;
  private MultipartFile image;
  private LocalDate publishedAt;
  private String category;
  private String content;
}
