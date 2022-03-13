package com.edukus.diabeto.persistence.entity;


import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.REMOVE;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "ArticleContent")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "article_content")
public class ArticleContentEntity {

  @Id
  private Long id;
  private String content;

  @OneToOne(fetch = FetchType.LAZY, cascade=ALL)
  @JoinColumn(name = "id")
  @MapsId
  private ArticleEntity article;
}
