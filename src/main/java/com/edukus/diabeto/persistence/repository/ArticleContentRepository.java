package com.edukus.diabeto.persistence.repository;

import com.edukus.diabeto.persistence.entity.ArticleContentEntity;
import org.springframework.data.repository.CrudRepository;

public interface ArticleContentRepository extends CrudRepository<ArticleContentEntity, Long> {

  void deleteByIdAndArticle_Email(Long id, String email);
}
