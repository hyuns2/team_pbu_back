package projectbuildup.gasomann.domain.coupon.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.stereotype.Service;
import projectbuildup.gasomann.domain.coupon.dto.CouponDto;
import projectbuildup.gasomann.domain.coupon.entity.Coupon;
import projectbuildup.gasomann.domain.coupon.repository.CouponRepository;
import projectbuildup.gasomann.domain.couponIssuance.repository.CouponIssuanceRepository;
import projectbuildup.gasomann.domain.user.entity.User;
import projectbuildup.gasomann.domain.user.repository.UserRepository;
import projectbuildup.gasomann.domain.worthyConsumption.entity.CheckConditionType;
import projectbuildup.gasomann.domain.worthyConsumption.entity.WorthyConsumption;
import projectbuildup.gasomann.domain.worthyConsumption.repository.WorthyConsumptionRepository;
import projectbuildup.gasomann.global.common.fileStore.ExcelManager;
import projectbuildup.gasomann.global.common.imageStore.Image;
import projectbuildup.gasomann.global.common.imageStore.ImageType;
import projectbuildup.gasomann.global.common.imageStore.ImageUploader;
import projectbuildup.gasomann.global.error.exception.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponService {
    private final CouponRepository couponRepository;
    private final WorthyConsumptionRepository worthyConsumptionRepository;
    private final UserRepository userRepository;
    private final CouponIssuanceRepository couponIssuanceRepository;

    private final ImageUploader imageUploader;
    private final ExcelManager excelManager;

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

    public void retrieveExcelByCouponTypeAndDate(final HttpServletResponse response, final Long couponId, final int year, final int month) throws IOException {

        Optional<Coupon> target = couponRepository.findById(couponId);
        if (target.isEmpty()) {
            throw new CCouponNotFoundException();
        }
        String couponTitle = target.get().getTitle();

        LocalDate startTemp = LocalDate.of(year, month, 1);
        LocalDate endTemp = startTemp.withDayOfMonth(startTemp.lengthOfMonth());

        LocalDateTime start = startTemp.atTime(LocalTime.MIDNIGHT);
        LocalDateTime end = endTemp.atTime(LocalTime.MAX);

        if (start.isAfter(LocalDateTime.now())) {
            throw new CInvalidDateTimeException();
        }

        List<User> userList = couponIssuanceRepository.findUsersByCouponIdAndDate(couponId, start, end);
        List<List<String>> users = new ArrayList<>();
        for (User user: userList)
            users.add(List.of(user.getId().toString(), user.getUsername()));

        Workbook workbook = excelManager.writeExcel(users);
        response.setContentType("ms-vnd/excel");
        response.setHeader("Content-Disposition", "attachment;filename=" + year + "-" + month + "/" + couponTitle + ".xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }
}
