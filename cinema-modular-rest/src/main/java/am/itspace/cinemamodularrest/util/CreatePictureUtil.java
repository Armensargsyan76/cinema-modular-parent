package am.itspace.cinemamodularrest.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

@Service
@Slf4j
public class CreatePictureUtil {

    @Value("${cinema.image}")
    private String folderPath;

    public boolean isPictureNotAllowedType(MultipartFile multipartFile) {
        if (!multipartFile.isEmpty() && multipartFile.getSize() > 0) {
            return multipartFile.getContentType() != null && !multipartFile.getContentType().contains("image");
        }
        return false;
    }

    public String creatPicture(MultipartFile multipartFile) {
        String fileName = System.nanoTime() + "_" + multipartFile.getOriginalFilename();
        String fullName = folderPath + File.separator + fileName;
        File file = new File(fullName);
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            log.error("method transferTo in CreatePictureUtil failed", e);
            return null;
        }
        return fileName;
    }

    public byte[] getImage(String fileName) {
        try {
            InputStream inputStream = new FileInputStream(folderPath + File.separator + fileName);
            return IOUtils.toByteArray(inputStream);
        }catch (IOException e){
            log.error("method getImage in CreatePictureUtil failed", e);
            return null;
        }
    }

}
