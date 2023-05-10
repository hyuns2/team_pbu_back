package projectbuildup.mivv.domain.likes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.challenge.dto.ChallengeDto;
import projectbuildup.mivv.domain.likes.entity.LikesShorts;
import projectbuildup.mivv.domain.likes.entity.LikesCategory;
import projectbuildup.mivv.domain.likes.entity.LikesWorthyConsumption;
import projectbuildup.mivv.domain.likes.repository.LikesShortsRepository;
import projectbuildup.mivv.domain.likes.repository.LikesWorthyConsumptionRepository;
import projectbuildup.mivv.domain.shorts.dto.ShortsDto;
import projectbuildup.mivv.domain.shorts.entity.Shorts;
import projectbuildup.mivv.domain.shorts.entity.ShortsCategory;
import projectbuildup.mivv.domain.shorts.repository.ShortsRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.domain.worthyConsumption.dto.response.WorthyConsumptionResponseDto;
import projectbuildup.mivv.domain.worthyConsumption.entity.WorthyConsumption;
import projectbuildup.mivv.domain.worthyConsumption.repository.WorthyConsumptionRepository;
import projectbuildup.mivv.global.common.BaseTimeEntity;
import projectbuildup.mivv.global.error.exception.CShortsNotFoundException;
import projectbuildup.mivv.global.error.exception.CUserExistException;
import projectbuildup.mivv.global.error.exception.CWorthyConsumptionNotFoundException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.collections4.CollectionUtils.collect;

@Service
@RequiredArgsConstructor
public class LikesService extends BaseTimeEntity {

    private final UserRepository userRepository;
    private final ShortsRepository shortsRepository;
    private final WorthyConsumptionRepository worthyConsumptionRepository;

    private final LikesWorthyConsumptionRepository likesWorthyConsumptionRepository;
    private final LikesShortsRepository likesShortsRepository;
    public void addLikesShorts(Long userId, Long shortsId){
        User user = userRepository.findById(userId).orElseThrow(CUserExistException::new);
        Shorts shorts = shortsRepository.findById(shortsId).orElseThrow(CShortsNotFoundException::new);
        LikesCategory likesCategory;

        if(shorts.getCategory().equals(ShortsCategory.SAVING))
            likesCategory = LikesCategory.SHORTS_SAVING;
        else
            likesCategory = LikesCategory.SHORTS_EDU;

        LikesShorts likesShorts = new LikesShorts(user, shorts, likesCategory);
        likesShortsRepository.save(likesShorts);
    }
    public void addLikesWorthyConsumption(Long userId, Long worthyConumptionId){
        User user = userRepository.findById(userId).orElseThrow(CUserExistException::new);
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConumptionId).orElseThrow(CWorthyConsumptionNotFoundException::new);

        LikesWorthyConsumption likesWorthyConsumption = new LikesWorthyConsumption(user, worthyConsumption);
        likesWorthyConsumptionRepository.save(likesWorthyConsumption);
    }


    public void deleteLikesWorthyConsumption(Long userId, Long worthyConsumptionId){
        User user = userRepository.findById(userId).orElseThrow(CUserExistException::new);
        WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById(worthyConsumptionId).orElseThrow(CWorthyConsumptionNotFoundException::new);

        likesWorthyConsumptionRepository.deleteLikesWorthyConsumptionByUserAndWorthyConsumption(user, worthyConsumption);
    }
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
                .map(WorthyConsumptionResponseDto.ReadSummaryResponse::new)
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
