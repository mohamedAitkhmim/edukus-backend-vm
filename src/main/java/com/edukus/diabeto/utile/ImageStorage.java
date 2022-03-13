package com.edukus.diabeto.utile;

import static com.edukus.diabeto.service.UserProfileServiceImpl.JPEG;
import static com.edukus.diabeto.service.UserProfileServiceImpl.PATH_SEPARATOR;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ImageStorage {

  @Value("${edukus.image.outputfile}")
  private String imageDirPath;

  public static final String PUBLIC_IMAGES_URL = "/public/images/";

  public String store(String directory, byte[] image) throws IOException {
    String directoryName = imageDirPath.concat(directory);
    String imageName = RandomString.generate(10) + JPEG;
    Files.createDirectories(Paths.get(directoryName));
    Path imagePath = Paths.get(directoryName + PATH_SEPARATOR + imageName);
    Files.write(imagePath, image);
    return imageName;
  }

  public byte[] retrieve(String directory, String fileName) throws IOException {
    String directoryName = imageDirPath.concat(directory);
    return Files.readAllBytes(Paths.get(directoryName + PATH_SEPARATOR + fileName));
  }

  public static String getPublicImagesUrl(String directory, String fileName){
    return PUBLIC_IMAGES_URL + directory + PATH_SEPARATOR + fileName ;
  }

}
