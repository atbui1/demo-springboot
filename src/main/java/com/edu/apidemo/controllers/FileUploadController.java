package com.edu.apidemo.controllers;

import com.edu.apidemo.models.ResponseObject;
import com.edu.apidemo.services.upload.IStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/v1/fileUpload")
public class FileUploadController {
    @Autowired
    private IStorageService iStorageService;
    @PostMapping("")
    public ResponseEntity<ResponseObject> fileUpload(@RequestParam("file")MultipartFile file) {
        try {
            //save file to a folder --< use a service
            String generatedFileName = iStorageService.storeFile(file);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "upload image file successfully", generatedFileName)
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED).body(
                    new ResponseObject("failed", ex.getMessage(), "")
            );
        }
    }

    // one file
    @GetMapping("/files/{fileName:.+}")
    public ResponseEntity<byte[]> readDetailFile(@PathVariable String fileName) {
        try {
            byte[] bytes = iStorageService.readFileContent(fileName);
            return ResponseEntity
                    .ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(bytes);
        } catch (Exception ex) {
            return ResponseEntity.noContent().build();
        }
    }
    //load all
    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllUploadFiles() {
        try {
            List<String> listFiles = iStorageService.loadAll()
                    .map(path -> {
                        String urlPath = MvcUriComponentsBuilder.fromMethodName(
                                //convert fileName to url(send request "readDetailFile")
                                FileUploadController.class,
                                "readDetailFile",
                                path.getFileName().toString())
                                .build()
                                .toUri()
                                .toString();
                        return urlPath;
                    }).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("ok", "list files successfully", listFiles)
            );
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("failed", "list file failed", new String[]{})
            );
        }
    }
}
