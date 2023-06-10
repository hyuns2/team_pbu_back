package projectbuildup.mivv.domain.coupon.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.coupon.dto.CouponDto;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.coupon.repository.CouponRepository;
import projectbuildup.mivv.domain.couponIssuance.repository.CouponIssuanceRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.domain.worthyConsumption.entity.CheckConditionType;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;
import projectbuildup.mivv.domain.worthyConsumption.repository.WorthyConsumptionRepository;
import projectbuildup.mivv.global.common.imageStore.Image;
import projectbuildup.mivv.global.common.imageStore.ImageType;
import projectbuildup.mivv.global.common.imageStore.ImageUploader;
import projectbuildup.mivv.global.error.exception.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponService {
    private final CouponRepository couponRepository;
    private final WorthyConsumptionRepository worthyConsumptionRepository;
    private final UserRepository userRepository;
    private final CouponIssuanceRepository couponIssuanceRepository;

    private final ImageUploader imageUploader;

    /**
     * 가치소비에서 쿠폰을 생성하는 로직입니다.
     * @param
     * @param
     */
    public void createCoupon(Long worthyConsumptionId, CouponDto.Request couponDto) throws IOException {
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionId).orElseThrow(CWorthyConsumptionNotFoundException::new);
        Image image = imageUploader.upload(couponDto.getImage(), ImageType.COUPON);
        Coupon coupon = Coupon.toEntity(couponDto, image.getImagePath());
        worthyConsumption.addCoupon(coupon);
        worthyConsumption.getCondition().checkIssuableCouponStatus(CheckConditionType.AVAILABLE);
        worthyConsumptionRepository.save(worthyConsumption);
    }
    public List<CouponDto.Response> readAllCoupon(){
        return couponRepository.findAll().stream()
                .map(CouponDto.Response::new)
                .toList();
    }
    /**
     * 쿠폰 조회시, 완전한 정보 모두를 포함한 것입니다.
     * @param
     * @return
     */
    public CouponDto.Response readCouponWithWorthyConsumption(Long couponId, Long userId){
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CCouponNotFoundException::new);
        User user = userRepository.findById(userId).orElseThrow(CUserExistException::new);

        if(couponIssuanceRepository.findByUserAndCoupon(user, coupon).isEmpty())
            throw new CBadRequestException("유저가 보유한 쿠폰이 아닙니다.");

        return new CouponDto.Response(coupon);
    }
    public void updateCoupon(Long couponId, CouponDto.Request couponDto) throws IOException {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CCouponNotFoundException::new);
        Image image = imageUploader.upload(couponDto.getImage(), ImageType.COUPON);
        String imagePath = image.getImagePath();
        coupon.update(couponDto, imagePath);
        couponRepository.save(coupon);
    }
    public void deleteCoupon(Long couponId){
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CCouponNotFoundException::new);
        couponIssuanceRepository.deleteAllByCoupon(coupon);
        couponRepository.delete(coupon);
    }

    private void writeExcel(HttpServletResponse response, List<User> userList) throws IOException {
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
        response.setHeader("Content-Disposition", "attachment;filename=" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ".xlsx");

        workbook.write(response.getOutputStream());
        workbook.close();
    }

    public void retrieveExcelByCoupon(final HttpServletResponse response, final Long couponId) throws IOException {

        if (couponRepository.findById(couponId).isEmpty()) {
            throw new CCouponNotFoundException();
        }

        List<User> userList = couponIssuanceRepository.findUsersByCouponId(couponId);
        writeExcel(response, userList);

    }

    public void retrieveExcelByCouponDate(final HttpServletResponse response, final int year, final int month) throws IOException {
        LocalDate startTemp = LocalDate.of(year, month, 1);
        LocalDate endTemp = startTemp.withDayOfMonth(startTemp.lengthOfMonth());

        LocalDateTime start = startTemp.atTime(LocalTime.MIDNIGHT);
        LocalDateTime end = endTemp.atTime(LocalTime.MAX);

        if (start.isAfter(LocalDateTime.now())) {
            throw new CInvalidDateTimeException();
        }

        List<User> userList = couponIssuanceRepository.findUsersByCouponDate(start, end);
        writeExcel(response, userList);
    }
}
