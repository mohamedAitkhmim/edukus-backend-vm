package com.edukus.diabeto.service;

import com.edukus.diabeto.persistence.entity.ArticleContentEntity;
import com.edukus.diabeto.persistence.entity.ArticleEntity;
import com.edukus.diabeto.persistence.entity.Role;
import com.edukus.diabeto.persistence.entity.User;
import com.edukus.diabeto.persistence.repository.ArticleCategoryRepository;
import com.edukus.diabeto.persistence.repository.ArticleContentRepository;
import com.edukus.diabeto.persistence.repository.ArticleRepository;
import com.edukus.diabeto.persistence.repository.MeasureTypeRepository;
import com.edukus.diabeto.persistence.repository.UserRepository;
import com.edukus.diabeto.presentation.dto.ArticleCategoryDto;
import com.edukus.diabeto.presentation.dto.ArticleDto;
import com.edukus.diabeto.presentation.dto.ArticlePageDto;
import com.edukus.diabeto.service.mapper.ArticleMapper;
import com.edukus.diabeto.utile.ImageStorage;
import com.edukus.diabeto.utile.RoleType;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ArticleServiceImpl implements ArticleService {

  @Autowired
  ArticleRepository articleRepository;

  @Autowired
  ArticleContentRepository articleContentRepository;

  @Autowired
  ImageStorage imageStorage;

  @Autowired
  MeasureTypeRepository measureTypeRepository;

  @Autowired
  UserRepository userRepository;

  @Autowired
  ArticleCategoryRepository articleCategoryRepository;

  @Override
  public ArticlePageDto getPaginatedArticles(List<String> categories, String email, Integer page, Integer numberOfElements, String sort) {
    Role role = userRepository.findByEmail(email).map(User::getRole).orElseThrow(() -> new RuntimeException("user not found"));
    Pageable pageable = PageRequest.of(page, numberOfElements, Sort.by(sort));
    if(RoleType.ADMIN.getValue().equals(role.getCode()) || RoleType.DOCTOR.getValue().equals(role.getCode())){
      return ArticleMapper.mapToArticlePage(articleRepository.findByEmail(email, pageable));
    }
    return ArticleMapper.mapToArticlePage(articleRepository.findByCategoryIn(categories, pageable));
  }

  @Override
  public ArticleDto getArticle(Long id) {
    return ArticleMapper.mapToArticleContent(articleContentRepository.findById(id).orElseThrow(() -> new RuntimeException("article not found")));
  }

  @Override
  public void saveArticle(String preferredUsername, ArticleDto articleDto) throws IOException {

    String directory = LocalDate.now().toString();
    String imageName = imageStorage.store(directory, articleDto.getImage().getBytes());

    ArticleEntity article = ArticleMapper.mapToArticleEntity(null, articleDto, imageName, preferredUsername, LocalDateTime.now(), LocalDateTime.now());
    ArticleContentEntity content = new ArticleContentEntity(null, articleDto.getContent(), article);
    articleContentRepository.save(content);
  }

  @Override
  public void updateArticle(String preferredUsername, ArticleDto articleDto) throws IOException {

    ArticleEntity articleEntity = articleRepository.findByIdAndEmail(articleDto.getId(), preferredUsername).orElseThrow(() -> new RuntimeException("article not found"));

    String directory = LocalDate.now().toString();
    String imageName = articleDto.getImage() == null ? articleEntity.getUrlToImage() : imageStorage.store(directory, articleDto.getImage().getBytes());

    ArticleEntity article = ArticleMapper.mapToArticleEntity(articleDto.getId(), articleDto, imageName, preferredUsername, articleEntity.getPublishedAt(), LocalDateTime.now());
    ArticleContentEntity content = new ArticleContentEntity(articleDto.getId(), articleDto.getContent(), article);
    articleContentRepository.save(content);
  }

  @Override
  public byte[] getArticleImage(String directory, String name) throws IOException {
    return imageStorage.retrieve(directory, name);
  }

  @Override
  public String saveImage(byte[] bytes) throws IOException {
    String directory = LocalDate.now().toString();
    String imageName = imageStorage.store(directory, bytes);

    return imageStorage.getPublicImagesUrl(directory, imageName);
  }

  @Override
  @Transactional
  public void deleteArticle(String preferredUsername, Long id) {
    articleContentRepository.deleteByIdAndArticle_Email(id, preferredUsername);
  }

  @Override
  public List<ArticleCategoryDto> getArticleCategories() {
    return ArticleMapper.mapToArticleCategories(articleCategoryRepository.findAll());
  }


}
