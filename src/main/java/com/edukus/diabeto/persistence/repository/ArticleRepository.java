package com.edukus.diabeto.persistence.repository;

import com.edukus.diabeto.persistence.entity.ArticleEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ArticleRepository extends PagingAndSortingRepository<ArticleEntity, Long> {

  Optional<ArticleEntity> findByIdAndEmail(Long id, String email);
  Page<ArticleEntity> findByEmail(String email, Pageable pageable);
  Page<ArticleEntity> findByCategoryIn(List<String> categories, Pageable pageable);



}
