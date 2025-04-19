package com.helper.filekit;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
public abstract class LocalFileKit {

    private LocalFileKit() {
        throw new UnsupportedOperationException("LocalFileKit 클래스는 인스턴스화 할 수 없습니다.");
    }

    /**
     * 로컬 환경에 파일을 업로드 한다.
     *
     * @param file              파일
     * @param uploadPath        업로드 할 파일 경로
     * @param maxFileSizeInMB   최대 파일 크기 (MB 단위)
     * @param allowedExtensions 허용된 확장자 목록
     */
    public static String upload(MultipartFile file, String uploadPath, Long maxFileSizeInMB,
                                List<String> allowedExtensions) {
        if (file.isEmpty()) {
            return "파일이 비어 있습니다.";
        }

        // 파일 크기 검증
        validateFileSize(file.getSize(), maxFileSizeInMB);

        // 파일 확장자 검증
        validateFileExtension(file.getOriginalFilename(), allowedExtensions);

        String originalFilename = file.getOriginalFilename();
        String storeFilename = createStoreFilename(originalFilename);
        createDirectoryIfNotExists(uploadPath);

        String fullPath = uploadPath + File.separator + storeFilename;
        try {
            file.transferTo(new File(fullPath));
        } catch (IOException e) {
            return "파일 업로드 중 오류 발생: " + e.getMessage();
        }

        return "파일 업로드 성공: " + fullPath;
    }

    public static File download(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("파일이 존재하지 않습니다: " + filePath);
        }
        return file;
    }

    private static void validateFileSize(long fileSize, Long maxFileSizeInMB) {
        long maxFileSizeInBytes = maxFileSizeInMB * 1024 * 1024; // MB를 바이트로 변환
        if (fileSize > maxFileSizeInBytes) {
            throw new IllegalArgumentException("파일 크기가 허용된 최대 크기를 초과했습니다. (최대: " + maxFileSizeInMB + " MB)");
        }
    }

    private static void validateFileExtension(String originalFilename, List<String> allowedExtensions) {
        String extension = StringUtils.getFilenameExtension(originalFilename);
        if (extension == null || !allowedExtensions.contains(extension.toLowerCase())) {
            throw new IllegalArgumentException("허용되지 않는 파일 확장자입니다. (허용된 확장자: " + allowedExtensions + ")");
        }
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
