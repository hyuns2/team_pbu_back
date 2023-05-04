package projectbuildup.mivv.global.common.fileUpload;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UploadFile {
    // 사용자의 파일명
    private String uploadFileName;
    //서버의 파일명
    private String storeFilename;
    //서버 저장경로
    private String storeFullPath;
}
