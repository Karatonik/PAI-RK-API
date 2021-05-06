package pl.RK.PAIEVENTREST.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import pl.RK.PAIEVENTREST.models.EventPAI;
import pl.RK.PAIEVENTREST.models.FileDB;
import pl.RK.PAIEVENTREST.models.UserPAI;
import pl.RK.PAIEVENTREST.models.enums.TypeOfImage;
import pl.RK.PAIEVENTREST.repositorys.EventPaiRepository;
import pl.RK.PAIEVENTREST.repositorys.FileDBRepository;
import pl.RK.PAIEVENTREST.repositorys.UserPaiRepository;
import pl.RK.PAIEVENTREST.services.interfaces.FileDBServiceIF;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class FileDBServiceImp implements FileDBServiceIF {


    FileDBRepository fileDBRepository;
    UserPaiRepository userPaiRepository;
    EventPaiRepository eventPaiRepository;
    @Autowired
    public FileDBServiceImp(FileDBRepository fileDBRepository, UserPaiRepository userPaiRepository, EventPaiRepository eventPaiRepository) {
        this.fileDBRepository = fileDBRepository;
        this.userPaiRepository = userPaiRepository;
        this.eventPaiRepository = eventPaiRepository;
    }

    @Override
    public boolean store(MultipartFile file, String email, TypeOfImage typeOfImage, int eventId) throws IOException {
        Optional<UserPAI> optionalUserPAI = userPaiRepository.findById(email);
        if(optionalUserPAI.isPresent()){

            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            FileDB fileDB;
            if(typeOfImage.equals(TypeOfImage.Avatar)){
                fileDBRepository.findByUserPAIAndTypeOfImage(optionalUserPAI.get(),TypeOfImage.Avatar).ifPresent(db -> fileDBRepository.delete(db));
               fileDB = new FileDB(fileName, file.getContentType(), file.getBytes(),typeOfImage,optionalUserPAI.get());
            }else {
                Optional<EventPAI> optionalEventPAI=eventPaiRepository.findById(eventId);
                if(optionalEventPAI.isPresent()){
                    fileDBRepository.findByEventPAI(optionalEventPAI.get()).ifPresent(db->fileDBRepository.delete(db));
                    fileDB = new FileDB(fileName, file.getContentType(), file.getBytes(),typeOfImage,optionalUserPAI.get(),optionalEventPAI.get());
                }else {
                    return false;
                }

            }
            fileDBRepository.save(fileDB);
            return   true;

        }

        return false;
    }

    @Override
    public FileDB getFile(int id) {
        return fileDBRepository.findById(id).get();
    }

    @Override
    public Stream<FileDB> getAllFiles() {
        return fileDBRepository.findAll().stream();
    }

    @Override
    public FileDB getAvatar(String email) {
        Optional<UserPAI> optionalUserPAI =userPaiRepository.findById(email);
        return optionalUserPAI.map(userPAI -> fileDBRepository.findByUserPAIAndTypeOfImage(userPAI, TypeOfImage.Avatar).get())
                .orElse(null);
    }

    @Override
    public FileDB getBackground(int eventId) {
        Optional<EventPAI>optionalEventPAI = eventPaiRepository.findById(eventId);
        return optionalEventPAI.map(eventPAI -> fileDBRepository.findByEventPAI(eventPAI).get())
                .orElse(null);
    }
}
