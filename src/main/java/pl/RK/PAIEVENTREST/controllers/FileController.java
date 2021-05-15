package pl.RK.PAIEVENTREST.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.RK.PAIEVENTREST.models.FileDB;
import pl.RK.PAIEVENTREST.models.dto.FileDBDto;
import pl.RK.PAIEVENTREST.models.enums.TypeOfImage;
import pl.RK.PAIEVENTREST.services.implementations.FileDBServiceImp;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/file")
@CrossOrigin(origins = "http://localhost:3000")
public class FileController {

    FileDBServiceImp fileDBService;

    @Autowired
    public FileController(FileDBServiceImp fileDBService) {
        this.fileDBService = fileDBService;
    }

    @PostMapping("/")
    public boolean upload(@RequestParam String email,@RequestParam TypeOfImage typeOfImage
            ,@RequestParam Integer eventId ,@RequestPart("file") MultipartFile file) throws IOException {
      return  fileDBService.store(file, email, typeOfImage, eventId);
    }
    @GetMapping("/all")
    public ResponseEntity<List<FileDBDto>> getAll(){
        List<FileDBDto> files = fileDBService.getAllFiles().map(dbFile -> {
            String fileDownloadUri = ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/files/")
                    .path(dbFile.getFileID().toString())
                    .toUriString();

            return new FileDBDto(
                    dbFile.getName(),
                    fileDownloadUri,
                    dbFile.getType(),
                    dbFile.getData().length);
        }).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(files);
    }

    @GetMapping("/")
    public ResponseEntity<byte[]> get(@RequestParam Integer id){
        FileDB fileDB = fileDBService.getFile(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
}

    @GetMapping("/av")
        public ResponseEntity<byte[]> getAvatar(@RequestParam String email) {
        FileDB fileDB =fileDBService.getAvatar(email);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }
    @GetMapping("/bg")
    public ResponseEntity<byte[]> getBackGround(@RequestParam Integer eventId) {
        FileDB fileDB =fileDBService.getBackground(eventId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileDB.getName() + "\"")
                .body(fileDB.getData());
    }


}
