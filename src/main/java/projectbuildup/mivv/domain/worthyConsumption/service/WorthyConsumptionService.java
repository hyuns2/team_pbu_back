package projectbuildup.mivv.domain.worthyConsumption.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumptionUrl;
import projectbuildup.mivv.domain.worthyConsumption.repository.WorthyConsumptionRepository;
import projectbuildup.mivv.global.common.imageStore.Image;
import projectbuildup.mivv.global.common.imageStore.ImageUploader;
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
    private final UserRepository userRepository;
    private final LikesWorthyConsumptionRepository likesWorthyConsumptionRepository;
    private final LikesShortsRepository likesShortsRepository;

    private final ImageUploader imageUploader;

    /**
     * 가치소비를 생성하는 로직입니다.
     * @param
     */
    public void createWorthyConsumption(WorthyConsumptionDto.Creation worthyConsumptionDto) throws IOException {
        Image image = imageUploader.upload(worthyConsumptionDto.getImage(), "values");
        Image detailImage = imageUploader.upload(worthyConsumptionDto.getDetailImage(), "values");
        Image detailBackgroundImage = imageUploader.upload(worthyConsumptionDto.getDetailBackgroundImage(), "values");
        Image placeImage = imageUploader.upload(worthyConsumptionDto.getPlaceImage(), "values");

        WorthyConsumptionUrl worthyConsumptionUrl = new WorthyConsumptionUrl(worthyConsumptionDto, image.getImagePath(), detailImage.getImagePath(), detailBackgroundImage.getImagePath(), placeImage.getImagePath());
        Condition condition = new Condition(worthyConsumptionDto);
        WorthyConsumption worthyConsumption = worthyConsumptionDto.toEntity(worthyConsumptionUrl, condition);
        worthyConsumptionRepository.save(worthyConsumption);
    }
    /**
     * 가치소비를 조회하는 로직입니다.
     * @param worthyConsumptionId
     * @return
     */
    public WorthyConsumptionResponseDto.ReadSummaryResponse readSummaryWorthyConsumption(Long worthyConsumptionId){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionId).orElseThrow(CWorthyConsumptionNotFoundException:: new);
        return new WorthyConsumptionResponseDto.ReadSummaryResponse(worthyConsumption);
    }
    public WorthyConsumptionResponseDto.ReadBasicResponse readBasicWorthyConsumption(Long worthyConsumptionId, Long userId){
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionId).orElseThrow(CWorthyConsumptionNotFoundException:: new);
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);

        checkConditionToIssuableCoupon(worthyConsumption);

        Boolean isLiked = likesWorthyConsumptionRepository.findByUserAndWorthyConsumption(user, worthyConsumption).isPresent() ? Boolean.TRUE : Boolean.FALSE;
        return new WorthyConsumptionResponseDto.ReadBasicResponse(worthyConsumption, isLiked);
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
                    return new WorthyConsumptionResponseDto.ReadBasicResponse(worthyConsumption, liked);
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
        updateCondition(worthyConsumption, worthyConsumptionDto);
        updateContent(worthyConsumption, worthyConsumptionDto);

        worthyConsumptionRepository.save(worthyConsumption);
    }
    public void updateUrl(WorthyConsumption worthyConsumption, WorthyConsumptionDto.Update worthyConsumptionDto) throws IOException {
        if(worthyConsumptionDto.getImage() != null){
            Image image = imageUploader.upload(worthyConsumptionDto.getImage(), "values");
            worthyConsumption.getWorthyConsumptionUrl().setImagePath(image.getImagePath());
        }
        if(worthyConsumptionDto.getDetailImage() != null && !worthyConsumptionDto.getDetailImage().isEmpty()) {
            Image detailImage = imageUploader.upload(worthyConsumptionDto.getDetailImage(), "values");
            worthyConsumption.getWorthyConsumptionUrl().setDetailImagePath(detailImage.getImagePath());
        }
        if(worthyConsumptionDto.getDetailBackgroundImage() != null && !worthyConsumptionDto.getDetailBackgroundImage().isEmpty()) {
            Image detailBackgroundImage = imageUploader.upload(worthyConsumptionDto.getDetailBackgroundImage(), "values");
            worthyConsumption.getWorthyConsumptionUrl().setDetailBackgroundImagePath(detailBackgroundImage.getImagePath());

        }
        if(worthyConsumptionDto.getPlaceImage() != null && !worthyConsumptionDto.getPlaceImage().isEmpty()) {
            Image placeImage = imageUploader.upload(worthyConsumptionDto.getPlaceImage(), "values");
            worthyConsumption.getWorthyConsumptionUrl().setPlaceImagePath(placeImage.getImagePath());
        }
    }
    public void updateCondition(WorthyConsumption worthyConsumption, WorthyConsumptionDto.Update worthyConsumptionDto) throws IOException {

        if(worthyConsumptionDto.getMaxParticipants() != null)
            worthyConsumption.getCondition().setMaxParticipants(worthyConsumptionDto.getMaxParticipants());
        if(worthyConsumptionDto.getIssuableCouponStartDate() != null)
            worthyConsumption.getCondition().setIssuableCouponStartDate(worthyConsumptionDto.getIssuableCouponStartDate());
        if(worthyConsumptionDto.getIssuableCouponEndDate() != null)
            worthyConsumption.getCondition().setIssuableCouponEndDate(worthyConsumptionDto.getIssuableCouponEndDate());
        if(worthyConsumptionDto.getLastMonthAmount() != null)
            worthyConsumption.getCondition().setLastMonthAmount(worthyConsumptionDto.getLastMonthAmount());

    }
    public void updateContent(WorthyConsumption worthyConsumption, WorthyConsumptionDto.Update worthyConsumptionDto) throws IOException {

        if(worthyConsumptionDto.getTitle() != null)
            worthyConsumption.setTitle(worthyConsumptionDto.getTitle());
        if(worthyConsumptionDto.getHashtags() != null)
            worthyConsumption.setHashtags(worthyConsumptionDto.getHashtags());
        if(worthyConsumptionDto.getOriginalPrice() != null)
            worthyConsumption.setOriginalPrice(worthyConsumptionDto.getOriginalPrice());
        if(worthyConsumptionDto.getSalePrice() != null)
            worthyConsumption.setSalePrice(worthyConsumptionDto.getSalePrice());
        if(worthyConsumptionDto.getRecommendationReason() != null)
            worthyConsumption.setRecommendationReason(worthyConsumptionDto.getRecommendationReason());
        if(worthyConsumptionDto.getAvailablePrice() != null)
            worthyConsumption.setAvailablePrice(worthyConsumptionDto.getAvailablePrice());
        if(worthyConsumptionDto.getAvailablePlace() != null)
            worthyConsumption.setAvailablePlace(worthyConsumptionDto.getAvailablePlace());
        if(worthyConsumptionDto.getSummary() != null)
            worthyConsumption.setSummary(worthyConsumptionDto.getSummary());

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
        checkConditionDateForCoupon(worthyConsumption);
        worthyConsumptionRepository.save(worthyConsumption);
    }
    public void checkConditionDateForCoupon(WorthyConsumption worthyConsumption){
        if(!(worthyConsumption.getCondition().getIssuableCouponStartDate().isBefore(LocalDate.now())//테스트시 now 설정 X
                &&worthyConsumption.getCondition().getIssuableCouponEndDate().isAfter(LocalDate.now())))
            worthyConsumption.getCondition().checkIssuableCouponStatus(NOT_DATE);
    }
    public void checkMaxParticipantsForCoupon(Long couponId, WorthyConsumption worthyConsumption){
        int nowParticipants = couponIssuanceRepository.countByCouponId(couponId);
        if(nowParticipants>=worthyConsumption.getCondition().getMaxParticipants())
            worthyConsumption.getCondition().checkIssuableCouponStatus(ALREADY_SPEND);
    }
}
