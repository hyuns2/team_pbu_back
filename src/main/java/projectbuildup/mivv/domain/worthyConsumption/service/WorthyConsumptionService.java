package projectbuildup.mivv.domain.worthyConsumption.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.coupon.repository.CouponRepository;
import projectbuildup.mivv.domain.couponIssuance.entity.CouponIssuance;
import projectbuildup.mivv.domain.couponIssuance.repository.CouponIssuanceRepository;
import projectbuildup.mivv.domain.likes.repository.LikesShortsRepository;
import projectbuildup.mivv.domain.likes.repository.LikesWorthyConsumptionRepository;
import projectbuildup.mivv.domain.shorts.dto.ShortsDto;
import projectbuildup.mivv.domain.shorts.entity.ShortsCategory;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.domain.worthyConsumption.dto.WorthyConsumptionDto;
import projectbuildup.mivv.domain.worthyConsumption.dto.response.WorthyConsumptionResponseDto;
import projectbuildup.mivv.domain.worthyConsumption.entity.*;
import projectbuildup.mivv.domain.worthyConsumption.repository.WorthyConsumptionRepository;
import projectbuildup.mivv.global.common.imageStore.Image;
import projectbuildup.mivv.global.common.imageStore.ImageType;
import projectbuildup.mivv.global.common.imageStore.ImageUploader;
import projectbuildup.mivv.global.common.videoStore.Video;
import projectbuildup.mivv.global.common.videoStore.VideoUploader;
import projectbuildup.mivv.global.error.exception.CCouponNotFoundException;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;
import projectbuildup.mivv.global.error.exception.CWorthyConsumptionNotFoundException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static projectbuildup.mivv.domain.worthyConsumption.entity.CheckConditionType.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorthyConsumptionService {
    private final WorthyConsumptionRepository worthyConsumptionRepository;
    private final CouponIssuanceRepository couponIssuanceRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final LikesWorthyConsumptionRepository likesWorthyConsumptionRepository;
    private final LikesShortsRepository likesShortsRepository;

    private final ImageUploader imageUploader;
    private final VideoUploader videoUploader;

    /**
     * 가치소비를 생성하는 로직입니다.
     * @param
     */
    public void createWorthyConsumption(WorthyConsumptionDto.Request worthyConsumptionDto) throws IOException {
        log.info("확인1");
        Image logo = imageUploader.upload(worthyConsumptionDto.getLogo(),ImageType.VALUE);
        Image videoThumbNail = imageUploader.upload(worthyConsumptionDto.getVideoThumbNail(),ImageType.VALUE);
        Video video = videoUploader.upload(worthyConsumptionDto.getVideo(), "values");
        Image image = imageUploader.upload(worthyConsumptionDto.getImage(), ImageType.VALUE);
        Image detailImage = imageUploader.upload(worthyConsumptionDto.getDetailImage(), ImageType.VALUE);
        Image detailBackgroundImage = imageUploader.upload(worthyConsumptionDto.getDetailBackgroundImage(), ImageType.VALUE);
        Image placeImage = imageUploader.upload(worthyConsumptionDto.getPlaceImage(), ImageType.VALUE);
        log.info("확인2");

        WorthyConsumptionUrl worthyConsumptionUrl = new WorthyConsumptionUrl(logo.getImagePath(), videoThumbNail.getImagePath(), video.getVideoPath(), image.getImagePath(), detailImage.getImagePath(), detailBackgroundImage.getImagePath(), placeImage.getImagePath());
        Condition condition = new Condition(worthyConsumptionDto);
        WorthyConsumption worthyConsumption = worthyConsumptionDto.toEntity(worthyConsumptionUrl, condition);

        log.info("확인3 {}", worthyConsumptionDto.getRecommendationReasonDtos().size());
//        List<RecommendationReason> recommendationReasons = worthyConsumptionDto.getRecommendationReasons().stream()
//                .map(dto -> mapToRecommendationReason(dto.getTitle(), dto.getDescription(), worthyConsumption))
//                .collect(Collectors.toList());
        List<RecommendationReason> recommendationReasons = worthyConsumptionDto.getRecommendationReasonDtos().stream()
                .map(dto -> mapToRecommendationReason(dto.getTitle(), dto.getDescription(), worthyConsumption))
                .collect(Collectors.toList());
        worthyConsumption.setRecommendationReasons(recommendationReasons);
        worthyConsumptionRepository.save(worthyConsumption);
    }
    public void createWorthyConsumptionRR(WorthyConsumptionDto.RecommendationReasonDto dto, Long worthyConsumptionId){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionId).orElseThrow();
        RecommendationReason recommendationReason = dto.toEntity(worthyConsumption);
        worthyConsumption.addRR(recommendationReason);
        worthyConsumptionRepository.save(worthyConsumption);
    }
    private RecommendationReason mapToRecommendationReason(String title, String description, WorthyConsumption worthyConsumption) {
        return new RecommendationReason(title, description, worthyConsumption);
    }

    /**
     * 가치소비를 조회하는 로직입니다.
     * @param worthyConsumptionId
     * @return
     */
    public WorthyConsumptionResponseDto.ReadSummaryResponse readSummaryWorthyConsumption(Long worthyConsumptionId, Long userId){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionId).orElseThrow(CWorthyConsumptionNotFoundException:: new);
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);

        Boolean isLiked = likesWorthyConsumptionRepository.findByUserAndWorthyConsumption(user, worthyConsumption).isPresent() ? Boolean.TRUE : Boolean.FALSE;

        Long couponId = getCouponForMonth(worthyConsumption);
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CCouponNotFoundException::new);
        return new WorthyConsumptionResponseDto.ReadSummaryResponse(worthyConsumption, coupon, isLiked);
    }
    public WorthyConsumptionResponseDto.ReadBasicResponse readBasicWorthyConsumption(Long worthyConsumptionId, Long userId){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionId).orElseThrow(CWorthyConsumptionNotFoundException:: new);
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);

        checkConditionToIssuableCoupon(user, worthyConsumption);

        Long couponId = getCouponForMonth(worthyConsumption);
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CCouponNotFoundException::new);

        long count = checkUserIssueCount(user, worthyConsumption);

        Boolean isLiked = likesWorthyConsumptionRepository.findByUserAndWorthyConsumption(user, worthyConsumption).isPresent() ? Boolean.TRUE : Boolean.FALSE;
        return new WorthyConsumptionResponseDto.ReadBasicResponse(worthyConsumption, coupon, isLiked, count);
    }
    public WorthyConsumptionResponseDto.ReadDetailResponse readDetailWorthyConsumption(Long worthyConsumptionId){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionId).orElseThrow(CWorthyConsumptionNotFoundException:: new);
        Long couponId = getCouponForMonth(worthyConsumption);
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CCouponNotFoundException::new);
        return new WorthyConsumptionResponseDto.ReadDetailResponse(worthyConsumption, coupon);
    }
    public List<WorthyConsumptionResponseDto.ReadBasicResponse> readAllWorthyConsumption(Long userId){
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        return worthyConsumptionRepository.findAll().stream()
                .map(worthyConsumption -> {
                    boolean liked = likesWorthyConsumptionRepository.findByUserAndWorthyConsumption(user, worthyConsumption).isPresent();
                    Long couponId = getCouponForMonth(worthyConsumption);
                    Coupon coupon = couponRepository.findById(couponId).orElseThrow(CCouponNotFoundException::new);
                    long count = checkUserIssueCount(user, worthyConsumption);
                    return new WorthyConsumptionResponseDto.ReadBasicResponse(worthyConsumption, coupon, liked, count);
                })
                .collect(Collectors.toList());
    }

    /**
     * 가치소비를 수정하는 로직입니다. Patch 방식으로 수정함
     * @param
     */
    public void updateWorthyConsumption(Long worthyConsumptionId, WorthyConsumptionDto.Request worthyConsumptionDto) throws IOException {
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionId).orElseThrow(CWorthyConsumptionNotFoundException:: new);

        updateUrl(worthyConsumption, worthyConsumptionDto);
        worthyConsumption.getCondition().update(worthyConsumptionDto);
        List<RecommendationReason> recommendationReasons = worthyConsumptionDto.getRecommendationReasonDtos().stream()
                .map(dto -> mapToRecommendationReason(dto.getTitle(), dto.getDescription(), worthyConsumption))
                .collect(Collectors.toList());
        worthyConsumption.update(worthyConsumptionDto, recommendationReasons);

        worthyConsumptionRepository.save(worthyConsumption);
    }
    public void updateUrl(WorthyConsumption worthyConsumption, WorthyConsumptionDto.Request worthyConsumptionDto) throws IOException {
        Image logo = imageUploader.upload(worthyConsumptionDto.getLogo(),ImageType.VALUE);
        Image videoThumbNail = imageUploader.upload(worthyConsumptionDto.getVideoThumbNail(),ImageType.VALUE);
        Video video = videoUploader.upload(worthyConsumptionDto.getVideo(), "values");
        Image image = imageUploader.upload(worthyConsumptionDto.getImage(), ImageType.VALUE);
        Image detailImage = imageUploader.upload(worthyConsumptionDto.getDetailImage(), ImageType.VALUE);
        Image detailBackgroundImage = imageUploader.upload(worthyConsumptionDto.getDetailBackgroundImage(), ImageType.VALUE);
        Image placeImage = imageUploader.upload(worthyConsumptionDto.getPlaceImage(), ImageType.VALUE);

        worthyConsumption.getWorthyConsumptionUrl().update(logo.getImagePath(), videoThumbNail.getImagePath(), video.getVideoPath(), image.getImagePath(), detailImage.getImagePath(), detailBackgroundImage.getImagePath(), placeImage.getImagePath());
    }
        /**
         * 가치소비를 삭제하는 로직입니다.
         * @param worthyConsumptionId
         */
    public void deleteWorthyConsumption(Long worthyConsumptionId){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionId).orElseThrow(CWorthyConsumptionNotFoundException:: new);
        likesWorthyConsumptionRepository.deleteAllByWorthyConsumption(worthyConsumption);

        //찜하기에서 사용자가 찜한 가치소비 리스트 삭제해야 됨.
// Remove coupons and coupon issuances
        List<Coupon> coupons = worthyConsumption.getCoupons();
        for (Coupon coupon : coupons) {
            couponIssuanceRepository.deleteAllByCoupon(coupon);
        }
        worthyConsumptionRepository.delete(worthyConsumption);
    }
    public void deleteWorthyConsumptionRR(Long worthyConsumptionId){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionId).orElseThrow(CWorthyConsumptionNotFoundException:: new);
        worthyConsumption.deleteRR();
        worthyConsumptionRepository.save(worthyConsumption);
    }
    /**
     * 가치소비의 쿠폰이 발급 가능한 상황인지 판단하는 로직입니다.
     * 우선 오늘 날짜와 발급 가능 날짜를 판단하고
     * 발급받은 사람 수를 세어 최종 판단을 합니다.
     * 쿠폰이 여러개 있는 경우에 해당 달에 맞는 쿠폰이 뭔지 파악해야하는데, 이건 기획단에 한번 더 물어보고 짜려고 합니다.(기준 모호)
     */
    public void checkConditionToIssuableCoupon(User user, WorthyConsumption worthyConsumption){
        worthyConsumption.getCondition().checkIssuableCouponStatus(AVAILABLE);

        Long couponId = getCouponForMonth(worthyConsumption);
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CCouponNotFoundException::new);

        checkValidDateToIssuableCoupon(worthyConsumption, coupon);
        checkAlreadyIssuedCoupon(user, worthyConsumption, coupon);

        worthyConsumptionRepository.save(worthyConsumption);
    }
    public void checkValidDateToIssuableCoupon(WorthyConsumption worthyConsumption, Coupon coupon){
        if(coupon.getIssuableEndDate().isBefore(LocalDate.now())||coupon.getIssuableStartDate().isAfter(LocalDate.now()))
            worthyConsumption.getCondition().checkIssuableCouponStatus(NOT_INVALID_DATE);

    }
    public void checkAlreadyIssuedCoupon(User user, WorthyConsumption worthyConsumption, Coupon coupon){
        if(couponIssuanceRepository.findByUserAndCoupon(user, coupon).isPresent())
            worthyConsumption.getCondition().checkIssuableCouponStatus(ISSUED);
    }
    /**
     * 해당 월에 맞는 쿠폰을 조회합니다.
     * @param worthyConsumption
     * @return
     */
    public Long getCouponForMonth(WorthyConsumption worthyConsumption){
        List<Coupon> couponList = worthyConsumption.getCoupons().stream().filter(coupon -> checkCouponDate(coupon)==true).toList();

        return couponList.get(0).getId();
    }
    public boolean checkCouponDate(Coupon coupon){
        if(coupon.getLimitStartDate().isBefore(LocalDate.now())&&coupon.getLimitEndDate().isAfter(LocalDate.now()))
            return true;
        return false;
    }
    public long checkUserIssueCount(User user, WorthyConsumption worthyConsumption){

        return couponIssuanceRepository.findAllByUser(user).stream()
                .map(CouponIssuance::getCoupon)
                .filter(coupon -> coupon.getWorthyConsumption().equals(worthyConsumption))
                .count();
    }
}
