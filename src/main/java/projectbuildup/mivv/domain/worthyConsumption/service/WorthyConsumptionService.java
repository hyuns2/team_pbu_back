package projectbuildup.mivv.domain.worthyConsumption.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.coupon.entity.Coupon;
import projectbuildup.mivv.domain.coupon.repository.CouponRepository;
import projectbuildup.mivv.domain.couponIssuance.repository.CouponIssuanceRepository;
import projectbuildup.mivv.domain.likes.repository.LikesShortsRepository;
import projectbuildup.mivv.domain.likes.repository.LikesWorthyConsumptionRepository;
import projectbuildup.mivv.domain.shorts.dto.ShortsDto;
import projectbuildup.mivv.domain.shorts.entity.ShortsCategory;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.domain.worthyConsumption.dto.WorthyConsumptionDto;
import projectbuildup.mivv.domain.worthyConsumption.dto.response.WorthyConsumptionResponseDto;
import projectbuildup.mivv.domain.worthyConsumption.entity.Condition;
import projectbuildup.mivv.domain.worthyConsumption.entity.RecommendationReason;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumptionUrl;
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
    public void createWorthyConsumption(WorthyConsumptionDto.Creation worthyConsumptionDto) throws IOException {
        Image logo = imageUploader.upload(worthyConsumptionDto.getLogo(),ImageType.VALUE);
        Image videoThumbNail = imageUploader.upload(worthyConsumptionDto.getVideoThumbNail(),ImageType.VALUE);
        Video video = videoUploader.upload(worthyConsumptionDto.getVideo(), "values");
        Image image = imageUploader.upload(worthyConsumptionDto.getImage(), ImageType.VALUE);
        Image detailImage = imageUploader.upload(worthyConsumptionDto.getDetailImage(), ImageType.VALUE);
        Image detailBackgroundImage = imageUploader.upload(worthyConsumptionDto.getDetailBackgroundImage(), ImageType.VALUE);
        Image placeImage = imageUploader.upload(worthyConsumptionDto.getPlaceImage(), ImageType.VALUE);

        WorthyConsumptionUrl worthyConsumptionUrl = new WorthyConsumptionUrl(logo.getImagePath(), videoThumbNail.getImagePath(), video.getVideoPath(), image.getImagePath(), detailImage.getImagePath(), detailBackgroundImage.getImagePath(), placeImage.getImagePath());
        Condition condition = new Condition(worthyConsumptionDto);
        List<RecommendationReason> recommendationReasons = worthyConsumptionDto.getRecommendationReasons().stream()
                .map(dto -> mapToRecommendationReason(dto.getTitle(), dto.getDescription()))
                .collect(Collectors.toList());
        WorthyConsumption worthyConsumption = worthyConsumptionDto.toEntity(worthyConsumptionUrl, condition, recommendationReasons);
        worthyConsumptionRepository.save(worthyConsumption);
    }
    private RecommendationReason mapToRecommendationReason(String title, String description) {
        return RecommendationReason.builder()
                .title(title)
                .description(description)
                .build();
    }

    /**
     * 가치소비를 조회하는 로직입니다.
     * @param worthyConsumptionId
     * @return
     */
    public WorthyConsumptionResponseDto.ReadSummaryResponse readSummaryWorthyConsumption(Long worthyConsumptionId){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionId).orElseThrow(CWorthyConsumptionNotFoundException:: new);
        Long couponId = getCouponForMonth(worthyConsumption);
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CCouponNotFoundException::new);
        return new WorthyConsumptionResponseDto.ReadSummaryResponse(worthyConsumption, coupon);
    }
    public WorthyConsumptionResponseDto.ReadBasicResponse readBasicWorthyConsumption(Long worthyConsumptionId, Long userId){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionId).orElseThrow(CWorthyConsumptionNotFoundException:: new);
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);

        checkConditionToIssuableCoupon(worthyConsumption);
        Long couponId = getCouponForMonth(worthyConsumption);
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(CCouponNotFoundException::new);

        Boolean isLiked = likesWorthyConsumptionRepository.findByUserAndWorthyConsumption(user, worthyConsumption).isPresent() ? Boolean.TRUE : Boolean.FALSE;
        return new WorthyConsumptionResponseDto.ReadBasicResponse(worthyConsumption, isLiked, coupon);
    }
    public WorthyConsumptionResponseDto.ReadDetailResponse readDetailWorthyConsumption(Long worthyConsumptionId){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionId).orElseThrow(CWorthyConsumptionNotFoundException:: new);
        return new WorthyConsumptionResponseDto.ReadDetailResponse(worthyConsumption);
    }
    public List<WorthyConsumptionResponseDto.ReadBasicResponse> readAllWorthyConsumption(Long userId){
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        return worthyConsumptionRepository.findAll().stream()
                .map(worthyConsumption -> {
                    boolean liked = likesWorthyConsumptionRepository.findByUserAndWorthyConsumption(user, worthyConsumption).isPresent();
                    Long couponId = getCouponForMonth(worthyConsumption);
                    Coupon coupon = couponRepository.findById(couponId).orElseThrow(CCouponNotFoundException::new);
                    return new WorthyConsumptionResponseDto.ReadBasicResponse(worthyConsumption, liked, coupon);
                })
                .collect(Collectors.toList());
    }

    /**
     * 가치소비를 수정하는 로직입니다. Patch 방식으로 수정함
     * @param
     */
    public void updateWorthyConsumption(Long worthyConsumptionId, WorthyConsumptionDto.Update worthyConsumptionDto) throws IOException {
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionId).orElseThrow(CWorthyConsumptionNotFoundException:: new);

        updateUrl(worthyConsumption, worthyConsumptionDto);
        worthyConsumption.getCondition().update(worthyConsumptionDto);
        worthyConsumption.update(worthyConsumptionDto);

        worthyConsumptionRepository.save(worthyConsumption);
    }
    public void updateUrl(WorthyConsumption worthyConsumption, WorthyConsumptionDto.Update worthyConsumptionDto) throws IOException {
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
        worthyConsumptionRepository.delete(worthyConsumption);
    }

    /**
     * 가치소비의 쿠폰이 발급 가능한 상황인지 판단하는 로직입니다.
     * 우선 오늘 날짜와 발급 가능 날짜를 판단하고
     * 발급받은 사람 수를 세어 최종 판단을 합니다.
     * 쿠폰이 여러개 있는 경우에 해당 달에 맞는 쿠폰이 뭔지 파악해야하는데, 이건 기획단에 한번 더 물어보고 짜려고 합니다.(기준 모호)
     */
    public void checkConditionToIssuableCoupon(WorthyConsumption worthyConsumption){
        worthyConsumption.getCondition().checkIssuableCouponStatus(OK);
        checkMaxParticipantsForCoupon(worthyConsumption.getCoupons().get(0).getId(), worthyConsumption);
        checkConventionDateForCoupon(worthyConsumption);
        worthyConsumptionRepository.save(worthyConsumption);
    }
    public void checkConventionDateForCoupon(WorthyConsumption worthyConsumption){
        if(!(worthyConsumption.getCondition().getConventionStartDate().isBefore(LocalDate.now())//테스트시 now 설정 X
                &&worthyConsumption.getCondition().getConventionEndDate().isAfter(LocalDate.now())))
            worthyConsumption.getCondition().checkIssuableCouponStatus(NOT_DATE);
    }
    public void checkMaxParticipantsForCoupon(Long couponId, WorthyConsumption worthyConsumption){
        int nowParticipants = couponIssuanceRepository.countByCouponId(couponId);
        if(nowParticipants>=worthyConsumption.getCondition().getMaxIssuance())
            worthyConsumption.getCondition().checkIssuableCouponStatus(ALREADY_SPEND);
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
}
