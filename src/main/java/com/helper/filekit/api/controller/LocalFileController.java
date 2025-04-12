package com.helper.filekit.api.controller;

import com.helper.filekit.api.service.LocalFileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/local-file")
public class LocalFileController {

    private final LocalFileService localFileService;

    // request: 파일 경로 - 어디에 올릴 건지
    @PostMapping("/upload")
    public ResponseEntity<String> upload(@RequestParam MultipartFile file) {
        return ResponseEntity.ok(localFileService.uploadFile(file));
    }

}
