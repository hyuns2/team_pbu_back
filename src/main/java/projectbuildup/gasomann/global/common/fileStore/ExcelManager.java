package projectbuildup.gasomann.global.common.fileStore;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.gasomann.global.error.exception.CFileNotInputException;
import projectbuildup.gasomann.global.error.exception.CIllegalFileExtensionException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Component
public class ExcelManager {

    @Value("${path.files}")
    String STORE_PATH;
    @Value("${path.ipUrl}")
    String ipUrl;

    /**
     * 엑셀을 서버에 저장하고, 첫번째 시트를 반환
     *
     * @param multipartFile 파일 정보
     * @return Sheet 엑셀의 첫번째 시트
     * @throws CIllegalFileExtensionException 엑셀이 아니면 발생
     */
    public Sheet storeExcelAndRetrieveFirstSheet(MultipartFile multipartFile) throws IOException {
        File file = storeExcelFile(multipartFile);
        InputStream inputStream = new FileInputStream(file.getFilePath());
        Workbook workBook = WorkbookFactory.create(inputStream);
        return workBook.getSheetAt(0);
    }

    private File storeExcelFile(MultipartFile multipartFile) throws IOException {
        String uploadFileName = getUploadFileName(multipartFile);
        String extension = checkExtension(uploadFileName);

        String storeFileName = UUID.randomUUID() + "." + extension;
        String storeFullPath = STORE_PATH + "/excels/" + storeFileName;

        multipartFile.transferTo(new java.io.File(storeFullPath));
        return new File(uploadFileName, storeFileName, storeFullPath, ipUrl + storeFullPath);
    }

    private String getUploadFileName(MultipartFile multipartFile) {
        if (multipartFile.isEmpty())
            throw new CFileNotInputException();
        return multipartFile.getOriginalFilename();
    }

    private String checkExtension(String uploadFileName) {
        String extension = getExtension(uploadFileName);
        if (!extension.equals("xls") && !extension.equals("xlsx"))
            throw new CIllegalFileExtensionException();
        return extension;
    }

    private String getExtension(String uploadFileName) {
        int dotIndex = uploadFileName.lastIndexOf(".");
        return uploadFileName.substring(dotIndex + 1);
    }

    /**
     * 엑셀파일 작성 및 반환 (첫 data는 헤더로 간주)
     */
    public Workbook writeExcel(List<List<String>> data) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        createHeader(sheet, data.get(0));
        createBody(sheet, data.subList(1, data.size()));

        return workbook;
    }

    private void createHeader(Sheet sheet, List<String> header) {
        Row row = sheet.createRow(0);
        int cellIndex = 0;
        for (String content: header)
            row.createCell(cellIndex++).setCellValue(content);
    }

    private void createBody(Sheet sheet, List<List<String>> data) {
        int rowIndex = 1;
        for (List<String> body: data) {
            Row row = sheet.createRow(rowIndex++);
            int cellIndex = 0;

            for (String content: body)
                row.createCell(cellIndex++).setCellValue(content);
        }
    }
}
