package com.helper.filekit.api.service.response;

import lombok.Getter;

@Getter
public class FileResponse {

    private final String fileName;
    private final String filePath;
    private final String fileUrl;

    public FileResponse(String fileName, String filePath, String fileUrl) {
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileUrl = fileUrl;
    }

}
