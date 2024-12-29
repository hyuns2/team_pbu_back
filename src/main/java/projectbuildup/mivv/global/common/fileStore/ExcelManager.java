package projectbuildup.mivv.global.common.fileStore;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.error.exception.CFileNotInputException;
import projectbuildup.mivv.global.error.exception.CIllegalFileExtensionException;

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
     * 유저들의 정보를 엑셀에 담아서 반환
     *
     * @param response 응답 객체
     * @param stringMap 작성할 데이터맵
     * @param fileName 반환할 엑셀의 이름
     */
    public void writeExcel(HttpServletResponse response, Map<String, String> stringMap, String fileName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        createHeader(sheet);
        createBody(sheet, stringMap);

        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private void createHeader(Sheet sheet) {
        Row headerRow = sheet.createRow(0);
        Cell header0 = headerRow.createCell(0);
        header0.setCellValue("이름");
        Cell header1 = headerRow.createCell(1);
        header1.setCellValue("전화번호");
    }

    private void createBody(Sheet sheet, Map<String, String> stringMap) {
        int rowIndex = 1;
        for (String key: stringMap.keySet()) {
            Row bodyRow = sheet.createRow(rowIndex++);

            Cell bodyCell0 = bodyRow.createCell(0);
            bodyCell0.setCellValue(key);
            Cell bodyCell1 = bodyRow.createCell(1);
            bodyCell1.setCellValue(stringMap.get(key));
        }
    }

}
