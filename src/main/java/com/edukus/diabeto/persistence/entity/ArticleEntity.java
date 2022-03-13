package com.edukus.diabeto.persistence.entity;


import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "Article")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Article")
public class ArticleEntity {

  @Id
  @GeneratedValue
  private Long id;

  private String author;
  private String title;
  private String description;
  private String urlToImage;
  private LocalDateTime publishedAt;
  private LocalDateTime updatedAt;
  private String email;
  private String category;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name ="category", referencedColumnName = "code" , insertable = false, updatable = false)
  private ArticleCategoryEntity articleCategoryEntity;

}
