package com.edukus.diabeto.service.mapper;

import com.edukus.diabeto.persistence.entity.ArticleCategoryEntity;
import com.edukus.diabeto.persistence.entity.ArticleContentEntity;
import com.edukus.diabeto.persistence.entity.ArticleEntity;
import com.edukus.diabeto.presentation.dto.ArticleCategoryDto;
import com.edukus.diabeto.presentation.dto.ArticleDto;
import com.edukus.diabeto.presentation.dto.ArticlePageDto;
import com.edukus.diabeto.utile.ImageStorage;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;

public class ArticleMapper {

  public static ArticlePageDto mapToArticlePage(Page<ArticleEntity> page) {
    return new ArticlePageDto(page.getTotalPages(), page.getNumberOfElements(), mapToArticles(page.getContent()));
  }

  public static List<ArticleDto> mapToArticles(List<ArticleEntity> entities) {
    return CollectionUtils.emptyIfNull(entities).stream().map(ArticleMapper::mapToArticle).collect(Collectors.toList());
  }

  private static ArticleDto mapToArticle(ArticleEntity entity) {
    if(entity == null){
      return ArticleDto.builder().build();
    }
    return new ArticleDto(entity.getId(), entity.getAuthor(), entity.getTitle(), entity.getDescription(), ImageStorage.getPublicImagesUrl(entity.getPublishedAt().toLocalDate().toString(), entity.getUrlToImage()), null,
        entity.getPublishedAt().toLocalDate(), entity.getArticleCategoryEntity().getCode(), null);
  }

  public static ArticleDto mapToArticleContent(ArticleContentEntity entity) {
    return ArticleDto.builder().id(entity.getId()).content(entity.getContent()).build();
  }

  public static ArticleEntity mapToArticleEntity(Long id, ArticleDto articleDto, String imageName, String email, LocalDateTime createdAt, LocalDateTime updatedAt) {
    if(articleDto == null || imageName == null){
      return null;
    }
    return new ArticleEntity(id, articleDto.getAuthor(), articleDto.getTitle(), articleDto.getDescription(), imageName, createdAt, updatedAt, email, articleDto.getCategory(), null);
  }

  public static List<ArticleCategoryDto> mapToArticleCategories(List<ArticleCategoryEntity> entities) {
    return CollectionUtils.emptyIfNull(entities).stream().map(ArticleMapper::mapToArticleCategory).collect(Collectors.toList());
  }

  private static ArticleCategoryDto mapToArticleCategory(ArticleCategoryEntity entity) {
    if(entity == null){
      return ArticleCategoryDto.builder().build();
    }
    return new ArticleCategoryDto(entity.getCode(), entity.getLabel());
  }
}
