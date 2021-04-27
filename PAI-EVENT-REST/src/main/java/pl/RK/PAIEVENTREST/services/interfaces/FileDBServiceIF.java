package pl.RK.PAIEVENTREST.services.interfaces;

import org.springframework.web.multipart.MultipartFile;
import pl.RK.PAIEVENTREST.models.FileDB;
import pl.RK.PAIEVENTREST.models.enums.TypeOfImage;

import java.io.IOException;
import java.util.stream.Stream;

public interface FileDBServiceIF {


    boolean store(MultipartFile file , String email , TypeOfImage typeOfImage , int eventId) throws IOException;

    FileDB getFile(int id);

    Stream<FileDB> getAllFiles();

    //

    FileDB getAvatar(String email);

    FileDB getBackground(int eventId);

}
