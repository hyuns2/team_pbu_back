package projectbuildup.mivv.global.common.fileStore;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class File {
    // 사용자의 파일명
    private String originalFileName;
    //서버의 파일명
    private String storeFilename;
    //서버의 파일 경로
    private String filePath;
    //파일 url 경로
    private String filePathWithUrl;
}
