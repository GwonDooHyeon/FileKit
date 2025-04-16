package com.helper.filekit.api.controller;

import com.helper.filekit.LocalFileKit;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/local-file")
public class LocalFileController {

    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestParam MultipartFile file,
            @RequestParam String uploadPath) {
        return ResponseEntity.ok(LocalFileKit.upload(file, uploadPath));
    }

}
