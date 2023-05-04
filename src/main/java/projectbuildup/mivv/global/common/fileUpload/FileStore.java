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
        String storeFullPath = fileDir + "excels/" + storeFileName;

        multipartFile.transferTo(new File(storeFullPath));
        return new UploadFile(uploadFileName, storeFileName, storeFullPath);

    }

    // 이미지만 저장하는 메소드
    public UploadFile storeImageFile(MultipartFile multipartFile) throws IOException {

        if (multipartFile.isEmpty())
            throw new CFileNotInputException();
        String uploadFileName = multipartFile.getOriginalFilename();
        String extension = checkExtension(uploadFileName);

        if (!extension.equals("png") && !extension.equals("jpg") && !extension.equals("jpeg")) {
            throw new CIllegalFileExtensionException();
        }
        String storeFileName = UUID.randomUUID() + "." + extension;
        String storeFullPath = fileDir + "images/" + storeFileName;

        multipartFile.transferTo(new File(storeFullPath));
        return new UploadFile(uploadFileName, storeFileName, storeFullPath);

    }

    // 확장자 뜯기
    private String checkExtension(String uploadFileName) {
        int dotIndex = uploadFileName.lastIndexOf(".");
        return uploadFileName.substring(dotIndex + 1);
    }
}
