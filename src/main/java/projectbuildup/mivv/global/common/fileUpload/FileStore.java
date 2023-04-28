package projectbuildup.mivv.global.common.fileUpload;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.mivv.global.error.exception.CFileNotInputException;
import projectbuildup.mivv.global.error.exception.CIllegalFileExtensionException;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class FileStore {

    private final String rootPath = System.getProperty("user.dir");
    private final String fileDir = rootPath + "/files/";

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    // 엑셀파일만 저장하는 메소드
    public UploadFile storeExcelFile(MultipartFile multipartFile) throws IOException {

        if (multipartFile.isEmpty())
            throw new CFileNotInputException();
        String uploadFileName = multipartFile.getOriginalFilename();
        String extension = checkExtension(uploadFileName);

        if (!extension.equals("xls") && !extension.equals("xlsx")) {
            throw new CIllegalFileExtensionException();
        }
        String storeFileName = UUID.randomUUID() + "." + extension;

        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new UploadFile(uploadFileName, storeFileName);

    }

    // 확장자 뜯기
    private String checkExtension(String uploadFileName) {
        int dotIndex = uploadFileName.lastIndexOf(".");
        return uploadFileName.substring(dotIndex + 1);
    }
}
