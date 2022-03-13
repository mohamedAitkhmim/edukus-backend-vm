package com.edukus.diabeto.persistence.entity;


import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "ArticleCategory")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "article_category")
public class ArticleCategoryEntity implements Serializable {

  @Id
  @GeneratedValue
  private Long id;
  private String code;
  private String label;
}
