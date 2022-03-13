package com.edukus.diabeto.presentation;

import com.edukus.diabeto.service.AssociationService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssociationController {

  private AssociationService associationService;

  public AssociationController(AssociationService associationService) {
    this.associationService = associationService;
  }


}
