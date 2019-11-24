package com.pjwstk.handler;

import com.fasterxml.jackson.databind.JsonNode;
import com.pjwstk.domain.api.RecipeResponse;
import com.pjwstk.service.parsemanager.FileParserService;
import com.pjwstk.service.parsemanager.ParserService;
import com.pjwstk.service.uploadmanager.FileUploadService;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.Part;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RequestScoped
public class FileDataHandler {

  @Inject
  private FileUploadService fileUploadService;

  @Inject
  private ParserService parserService;

  @EJB
  private FileParserService fileParserService;

  private Logger logger = LoggerFactory.getLogger(getClass().getName());

  public <T> Object dataUploadHandler(Part part) {

    Object outputObject = null;
    try {
      File file = fileUploadService.uploadFile(part);
      JsonNode jsonNode = parserService.getParsingDataFromFile(file);
      List<RecipeResponse> recipeResponseList = (List<RecipeResponse>) parserService
          .parse(jsonNode);
      outputObject = fileParserService.loadDataToDatabase(recipeResponseList);
    } catch (IOException e) {
      logger.error(e.getMessage());
    }
    logger.info("File {} was uploaded", part.toString());
    return outputObject;
  }
}
