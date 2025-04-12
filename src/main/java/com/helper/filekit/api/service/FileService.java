package com.helper.filekit.api.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    String uploadFile(MultipartFile file) throws IOException;

    String uploadFiles(List<MultipartFile> files);

}
