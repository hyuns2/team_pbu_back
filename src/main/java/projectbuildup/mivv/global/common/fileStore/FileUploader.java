package projectbuildup.mivv.global.common.fileStore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.mivv.global.error.exception.CFileNotInputException;
import projectbuildup.mivv.global.error.exception.CIllegalFileExtensionException;

import java.io.IOException;
import java.util.UUID;

@Component
public class FileUploader {

    @Value("${path.files}")
    String STORE_PATH;
    
    @Value("{path.ipUrl}")
    String ipUrl;

    @Value("{path.ipUrl}")
    String ipUrl;

    // 엑셀파일만 저장하는 메소드
    public File storeExcelFile(MultipartFile multipartFile) throws IOException {

        if (multipartFile.isEmpty())
            throw new CFileNotInputException();
        String uploadFileName = multipartFile.getOriginalFilename();
        String extension = checkExtension(uploadFileName);

        if (!extension.equals("xls") && !extension.equals("xlsx")) {
            throw new CIllegalFileExtensionException();
        }
        String storeFileName = UUID.randomUUID() + "." + extension;
        String storeFullPath = STORE_PATH + "/excels/" + storeFileName;

        multipartFile.transferTo(new java.io.File(storeFullPath));
        return new File(uploadFileName, storeFileName, ipUrl + storeFullPath);

    }

    // 확장자 뜯기
    private String checkExtension(String uploadFileName) {
        int dotIndex = uploadFileName.lastIndexOf(".");
        return uploadFileName.substring(dotIndex + 1);
    }
}
