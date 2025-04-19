package com.helper.filekit.api.controller;

import com.helper.filekit.LocalFileKit;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/local-file")
public class LocalFileController {

    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestParam MultipartFile file,
            @RequestParam String uploadPath,
            @RequestParam long maxFileSizeInMB,
            @RequestParam List<String> allowedExtensions) {
        return ResponseEntity.ok(LocalFileKit.upload(file, uploadPath, maxFileSizeInMB, allowedExtensions));
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> download(@RequestParam String filePath) {
        FileSystemResource resource = new FileSystemResource(LocalFileKit.download(filePath));

        if (!resource.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"");

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }

}
