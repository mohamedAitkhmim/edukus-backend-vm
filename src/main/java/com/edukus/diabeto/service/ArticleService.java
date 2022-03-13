package com.edukus.diabeto.service;

import com.edukus.diabeto.presentation.dto.ArticleCategoryDto;
import com.edukus.diabeto.presentation.dto.ArticleDto;
import com.edukus.diabeto.presentation.dto.ArticlePageDto;
import java.io.IOException;
import java.util.List;

public interface ArticleService {

  ArticlePageDto getPaginatedArticles(List<String> types, String email, Integer page, Integer numberOfElements, String sort);

  ArticleDto getArticle(Long id);

  void saveArticle(String preferredUsername, ArticleDto articleDto) throws IOException;

  void updateArticle(String preferredUsername, ArticleDto articleDto) throws IOException;

  byte[] getArticleImage(String directory, String name) throws IOException;

  String saveImage(byte[] bytes) throws IOException;

  void deleteArticle(String preferredUsername, Long id);

  List<ArticleCategoryDto> getArticleCategories();
}
