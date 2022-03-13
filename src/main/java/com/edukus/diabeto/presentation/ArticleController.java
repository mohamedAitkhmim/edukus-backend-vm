package com.edukus.diabeto.presentation;


import static com.edukus.diabeto.utile.ImageStorage.PUBLIC_IMAGES_URL;

import com.edukus.diabeto.presentation.dto.ArticleCategoryDto;
import com.edukus.diabeto.presentation.dto.ArticleDto;
import com.edukus.diabeto.presentation.dto.ArticlePageDto;
import com.edukus.diabeto.security.TokenParserUtil;
import com.edukus.diabeto.service.ArticleService;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class ArticleController {

  private final ArticleService articleService;

  public ArticleController(ArticleService articleService) {
    this.articleService = articleService;
  }

  @GetMapping("/private/articles")
  public ResponseEntity<ArticlePageDto> getArticles(Principal userPrincipal, @RequestParam(value = "categories", required = false) List<String> categories, @RequestParam("page") Integer page, @RequestParam("elements") Integer elements, @RequestParam("sort") String sort) {
    String preferredUsername = TokenParserUtil.extractToken(userPrincipal).getPreferredUsername();
    return ResponseEntity.status(HttpStatus.OK).body(articleService.getPaginatedArticles(categories, preferredUsername, page, elements, sort));
  }

  @GetMapping("/private/articles/categories")
  public ResponseEntity<List<ArticleCategoryDto>> getArticleCategories() {
    return ResponseEntity.status(HttpStatus.OK).body(articleService.getArticleCategories());
  }

  @GetMapping("/private/articles/{id}")
  public ResponseEntity<ArticleDto> getArticle(@PathVariable Long id) {
    return ResponseEntity.status(HttpStatus.OK).body(articleService.getArticle(id));
  }

  @PostMapping(value = "/private/article", consumes = { "multipart/form-data" })
  public ResponseEntity<Void> saveArticle(@ModelAttribute ArticleDto articleDto, Principal userPrincipal) throws IOException {
    String preferredUsername = TokenParserUtil.extractToken(userPrincipal).getPreferredUsername();
    articleService.saveArticle(preferredUsername, articleDto);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }

  @PutMapping(value = "/private/article", consumes = { "multipart/form-data" })
  public ResponseEntity<Void> updateArticle(@ModelAttribute ArticleDto articleDto, Principal userPrincipal) throws IOException {
    String preferredUsername = TokenParserUtil.extractToken(userPrincipal).getPreferredUsername();
    articleService.updateArticle(preferredUsername, articleDto);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }

  @DeleteMapping(value = "/private/article/{id}")
  public ResponseEntity<Void> deleteArticle(Principal userPrincipal, @PathVariable Long id){
    String preferredUsername = TokenParserUtil.extractToken(userPrincipal).getPreferredUsername();
    articleService.deleteArticle(preferredUsername, id);
    return ResponseEntity.status(HttpStatus.OK).body(null);
  }

  @GetMapping(PUBLIC_IMAGES_URL + "{directory}/{name}")
  public ResponseEntity<byte[]> getArticleImage(@PathVariable String directory, @PathVariable String name) throws IOException {

    byte[] imageBytes = articleService.getArticleImage(directory, name);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.IMAGE_JPEG);
    return new ResponseEntity<>(imageBytes, headers, HttpStatus.OK);
  }

  @PostMapping(PUBLIC_IMAGES_URL)
  public ResponseEntity<String> saveImage(@RequestPart("file") MultipartFile image) throws IOException {

    if (image.isEmpty()) {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
    }
    return ResponseEntity.status(HttpStatus.OK).body(articleService.saveImage(image.getBytes()));
  }
}
