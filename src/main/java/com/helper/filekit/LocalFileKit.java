package com.helper.filekit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public abstract class LocalFileKit {

    private LocalFileKit() {
        throw new UnsupportedOperationException("LocalFileKit 클래스는 인스턴스화 할 수 없습니다.");
    }

    /**
     * 로컬 환경에 파일을 업로드 한다.
     *
     * @param file       파일
     * @param uploadPath 업로드 할 파일 경로
     */
    public static String upload(MultipartFile file, String uploadPath) {
        if (file.isEmpty()) {
            return "파일이 비어 있습니다.";
        }

        String originalFilename = file.getOriginalFilename();
        String storeFilename = createStoreFilename(originalFilename);
        long size = file.getSize();
        log.info("size : {}", size);
        createDirectoryIfNotExists(uploadPath);

        String fullPath = uploadPath + File.separator + storeFilename;
        log.info("fullPath: {}", fullPath);
        try {
            file.transferTo(new File(fullPath));
        } catch (IOException e) {
            return "파일 업로드 중 오류 발생: " + e.getMessage();
        }

        return "파일 업로드 성공: " + fullPath;
    }

    private static String createStoreFilename(String originalFilename) {
        String extension = StringUtils.getFilenameExtension(originalFilename);
        // TODO UUID => ULID로 변경
        String uuid = UUID.randomUUID().toString();
        return uuid + "." + extension;
    }

    /**
     * 업로드 할 파일 경로가 존재하지 않으면 새로운 디렉토리 생성
     */
    private static void createDirectoryIfNotExists(String uploadPath) {
        File uploadDirectory = new File(uploadPath);

        try {
            if (!uploadDirectory.exists()) {
                boolean directoryCreated = uploadDirectory.mkdirs();
                if (!directoryCreated) {
                    throw new IllegalArgumentException("디렉토리 생성에 실패했습니다: " + uploadPath);
                }
            }
        } catch (SecurityException e) {
            throw new IllegalArgumentException("디렉토리 생성 권한이 없습니다: " + uploadPath, e);
        }
    }

}
