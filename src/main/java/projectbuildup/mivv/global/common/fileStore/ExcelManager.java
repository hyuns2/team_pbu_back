package projectbuildup.mivv.global.common.fileStore;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.error.exception.CFileNotInputException;
import projectbuildup.mivv.global.error.exception.CIllegalFileExtensionException;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Component
public class ExcelManager {

    @Value("${path.files}")
    String STORE_PATH;
    
    @Value("${path.ipUrl}")
    String ipUrl;

    /**
     * 엑셀을 서버에 저장하고, 그 객체를 반환
     *
     * @param multipartFile 파일 정보
     * @return File 파일 객체
     * @throws CIllegalFileExtensionException 엑셀이 아니면 발생
     */
    public File storeExcelFile(MultipartFile multipartFile) throws IOException {
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
     * @param userList 유저 리스트
     * @param fileName 반환할 엑셀의 이름
     */
    public void writeExcel(HttpServletResponse response, List<User> userList, String fileName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();
        int rowIndex = 0;

        createHeader(sheet, rowIndex);
        createBody(sheet, rowIndex, userList);

        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    private void createHeader(Sheet sheet, int rowIndex) {
        Row headerRow = sheet.createRow(rowIndex++);
        Cell header0 = headerRow.createCell(0);
        header0.setCellValue("이름");
        Cell header1 = headerRow.createCell(1);
        header1.setCellValue("전화번호");
    }

    private void createBody(Sheet sheet, int rowIndex, List<User> userList) {
        for (User user: userList) {
            Row bodyRow = sheet.createRow(rowIndex++);
            Cell bodyCell0 = bodyRow.createCell(0);
            bodyCell0.setCellValue(user.getIdentityVerification().getName());
            Cell bodyCell1 = bodyRow.createCell(1);
            bodyCell1.setCellValue(user.getIdentityVerification().getMobile());
        }
    }

}
