package projectbuildup.mivv.global.common.fileStore;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import projectbuildup.mivv.domain.user.entity.User;

import java.io.IOException;
import java.util.List;

public class ExcelReturner {

    public static void writeExcel(HttpServletResponse response, List<User> userList, String fileName) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        int rowIndex = 0;

        Row headerRow = sheet.createRow(rowIndex++);
        Cell header0 = headerRow.createCell(0);
        header0.setCellValue("이름");
        Cell header1 = headerRow.createCell(1);
        header1.setCellValue("전화번호");

        for (User user: userList) {
            Row bodyRow = sheet.createRow(rowIndex++);
            Cell bodyCell0 = bodyRow.createCell(0);
            bodyCell0.setCellValue(user.getIdentityVerification().getName());
            Cell bodyCell1 = bodyRow.createCell(1);
            bodyCell1.setCellValue(user.getIdentityVerification().getMobile());
        }

        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

}
