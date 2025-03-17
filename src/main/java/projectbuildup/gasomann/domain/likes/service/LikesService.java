package projectbuildup.gasomann.domain.likes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectbuildup.gasomann.domain.coupon.entity.Coupon;
import projectbuildup.gasomann.domain.coupon.repository.CouponRepository;
import projectbuildup.gasomann.domain.likes.entity.LikesShorts;
import projectbuildup.gasomann.domain.likes.entity.LikesCategory;
import projectbuildup.gasomann.domain.likes.entity.LikesWorthyConsumption;
import projectbuildup.gasomann.domain.likes.repository.LikesShortsRepository;
import projectbuildup.gasomann.domain.likes.repository.LikesWorthyConsumptionRepository;
import projectbuildup.gasomann.domain.shorts.dto.ShortsDto;
import projectbuildup.gasomann.domain.shorts.entity.Shorts;
import projectbuildup.gasomann.domain.shorts.entity.ShortsCategory;
import projectbuildup.gasomann.domain.shorts.repository.ShortsRepository;
import projectbuildup.gasomann.domain.user.entity.User;
import projectbuildup.gasomann.domain.user.repository.UserRepository;
import projectbuildup.gasomann.domain.worthyConsumption.dto.response.WorthyConsumptionResponseDto;
import projectbuildup.gasomann.domain.worthyConsumption.entity.WorthyConsumption;
import projectbuildup.gasomann.domain.worthyConsumption.repository.WorthyConsumptionRepository;
import projectbuildup.gasomann.domain.worthyConsumption.service.WorthyConsumptionService;
import projectbuildup.gasomann.global.error.exception.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.collections4.CollectionUtils.collect;

@Service
@RequiredArgsConstructor
public class LikesService {

    private final UserRepository userRepository;
    private final ShortsRepository shortsRepository;
    private final WorthyConsumptionRepository worthyConsumptionRepository;
    private final CouponRepository couponRepository;

    private final LikesWorthyConsumptionRepository likesWorthyConsumptionRepository;
    private final LikesShortsRepository likesShortsRepository;

    private final WorthyConsumptionService worthyConsumptionService;
    public void addLikesShorts(Long userId, Long shortsId){
        User user = userRepository.findById(userId).orElseThrow(CUserExistException::new);
        Shorts shorts = shortsRepository.findById(shortsId).orElseThrow(CShortsNotFoundException::new);
        LikesCategory likesCategory;

        if(shorts.getCategory().equals(ShortsCategory.SAVING))
            likesCategory = LikesCategory.SHORTS_SAVING;
        else
            likesCategory = LikesCategory.SHORTS_EDU;

        checkDuplicateShorts(user, shorts);

        LikesShorts likesShorts = new LikesShorts(user, shorts, likesCategory);
        likesShortsRepository.save(likesShorts);
    }
    public void addLikesWorthyConsumption(Long userId, Long worthyConsumptionId){
        User user = userRepository.findById(userId).orElseThrow(CUserExistException::new);
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionId).orElseThrow(CWorthyConsumptionNotFoundException::new);

        checkDuplicateWorthyConsumption(user, worthyConsumption);
        LikesWorthyConsumption likesWorthyConsumption = new LikesWorthyConsumption(user, worthyConsumption);
        likesWorthyConsumptionRepository.save(likesWorthyConsumption);
    }
    public void checkDuplicateShorts(User user, Shorts shorts){
        if(likesShortsRepository.findByUserAndShorts(user, shorts).isPresent())
            throw new CBadRequestException("이미 찜한 쇼츠입니다.");
    }
    public void checkDuplicateWorthyConsumption(User user, WorthyConsumption worthyConsumption){
        if(likesWorthyConsumptionRepository.findByUserAndWorthyConsumption(user, worthyConsumption).isPresent())
            throw new CBadRequestException("이미 찜한 가치소비입니다.");
    }
    @Transactional
    public void deleteLikesWorthyConsumption(Long userId, Long worthyConsumptionId){
        User user = userRepository.findById(userId).orElseThrow(CUserExistException::new);
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionId).orElseThrow(CWorthyConsumptionNotFoundException::new);

        likesWorthyConsumptionRepository.deleteLikesWorthyConsumptionByUserAndWorthyConsumption(user, worthyConsumption);
    }
    @Transactional
    public void deleteLikesShorts(Long userId, Long shortsId){
        User user = userRepository.findById(userId).orElseThrow(CUserExistException::new);
        Shorts shorts = shortsRepository.findById(shortsId).orElseThrow(CShortsNotFoundException::new);

        likesShortsRepository.deleteLikesShortsByUserAndShorts(user, shorts);
    }
    public LikesCategory findShortsCategory(Long shortsId){
        Shorts shorts = shortsRepository.findById(shortsId).orElseThrow(CShortsNotFoundException::new);
        if(shorts.getCategory()== ShortsCategory.SAVING)
            return LikesCategory.SHORTS_SAVING;
        else
            return LikesCategory.SHORTS_EDU;
    }

    public List<WorthyConsumptionResponseDto.ReadSummaryResponse> getAllUserLikesWorthyConsumption(Long userId){
        User user = userRepository.findById(userId).orElseThrow(CUserExistException::new);
        return likesWorthyConsumptionRepository.findAllByUser(user)
                .stream().map(LikesWorthyConsumption::getWorthyConsumption)
                .map(worthyConsumption -> {
                    Long couponId = worthyConsumptionService.getCouponForMonth(worthyConsumption);
                    Coupon coupon = couponRepository.findById(couponId).orElseThrow(CCouponNotFoundException::new);
                    return new WorthyConsumptionResponseDto.ReadSummaryResponse(worthyConsumption, coupon, Boolean.TRUE);
                })
                .toList();
    }
    public List<ShortsDto.shortsResponse> getAllUserLikesShorts(Long userId){
        User user = userRepository.findById(userId).orElseThrow(CUserExistException::new);

        return likesShortsRepository.findAllByUser(user)
                .stream()
                .map(likesShorts -> new ShortsDto.shortsResponse(likesShorts.getShorts(), true))
                .toList();
    }
    public List<ShortsDto.shortsResponse> getAllUserLikesEduShorts(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(CUserExistException::new);

        return likesShortsRepository.findAllByUserAndLikesCategory(user, LikesCategory.SHORTS_EDU)
                .stream()
                .map(likesShorts -> new ShortsDto.shortsResponse(likesShorts.getShorts(), true))
                .collect(Collectors.toList());

    }
    public List<ShortsDto.shortsResponse> getAllUserLikesSavingShorts(Long userId){
        User user = userRepository.findById(userId).orElseThrow(CUserExistException::new);

        return likesShortsRepository.findAllByUserAndLikesCategory(user, LikesCategory.SHORTS_SAVING)
                .stream()
                .map(likesShorts -> new ShortsDto.shortsResponse(likesShorts.getShorts(), true))
                .collect(Collectors.toList());
    }
}
