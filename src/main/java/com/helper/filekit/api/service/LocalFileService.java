package com.helper.filekit.api.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class LocalFileService implements FileService {

    @Override
    public String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        String originalFilename = file.getOriginalFilename();
        String storeFilename = createStoreFilename(originalFilename);

        try {
            file.transferTo(new File(storeFilename));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return "안녕하세요";
    }

    @Override
    public String uploadFiles(List<MultipartFile> files) {
        return "";
    }

    private String createStoreFilename(String originalFilename) {
        String extension = extractExtension(originalFilename);
        String uuid = UUID.randomUUID().toString();
        return uuid + "_" + extension;
    }

    private String extractExtension(String originalFilename) {
        return originalFilename.split("\\.")[1];
    }

}
