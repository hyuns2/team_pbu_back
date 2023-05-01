package projectbuildup.mivv.domain.likes.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.likes.entity.Likes;
import projectbuildup.mivv.domain.likes.entity.LikesCategory;
import projectbuildup.mivv.domain.likes.repository.LikesRepository;
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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LikesService extends BaseTimeEntity {
    private final UserRepository userRepository;
    private final ShortsRepository shortsRepository;
    private final WorthyConsumptionRepository worthyConsumptionRepository;
    public void addLikes(Long userId, Long contentId, LikesCategory category){
        User user = userRepository.findById(userId).orElseThrow(CUserExistException::new);
        Likes likes = user.getLikes();

        if(category==LikesCategory.WORTHY_CONSUMPTION)
            likes.getUserLikesWorthyConsumptionList().add(contentId);
        else if(category==LikesCategory.SHORTS_SAVING)
            likes.getUserLikesSavingShortsList().add(contentId);
        else
            likes.getUserLikesEduShortsList().add(contentId);

        userRepository.save(user);

    }
    public void deleteLikes(Long userId, Long contentId, LikesCategory category){
        User user = userRepository.findById(userId).orElseThrow(CUserExistException::new);
        Likes likes = user.getLikes();

        if(category==LikesCategory.WORTHY_CONSUMPTION)
            likes.getUserLikesWorthyConsumptionList().remove(contentId);
        else if(category==LikesCategory.SHORTS_SAVING)
            likes.getUserLikesSavingShortsList().remove(contentId);
        else
            likes.getUserLikesEduShortsList().remove(contentId);

        userRepository.save(user);

    }
    public LikesCategory findShortsCategory(Long shortsId){
        Shorts shorts = shortsRepository.findById(shortsId).orElseThrow(CShortsNotFoundException::new);
        if(shorts.getCategory()== ShortsCategory.SAVING)
            return LikesCategory.SHORTS_SAVING;
        else
            return LikesCategory.SHORTS_EDU;
    }

    public List<ShortsDto.shortsResponse> getUserLikesEduShorts(Long userId){
        User user = userRepository.findById(userId).orElseThrow(CUserExistException::new);
        Iterator iter = user.getLikes().getUserLikesEduShortsList().stream().iterator();
        List<Shorts> eduShortsList = new ArrayList<>();
        while(iter.hasNext()){
            Shorts shorts = shortsRepository.findById((Long)iter.next()).orElseThrow(CShortsNotFoundException::new);
            eduShortsList.add(shorts);
        }
        return eduShortsList.stream().map(ShortsDto.shortsResponse::new).toList();
    }
    public List<ShortsDto.shortsResponse> getUserLikesSavingShorts(Long userId){
        User user = userRepository.findById(userId).orElseThrow(CUserExistException::new);
        Iterator iter = user.getLikes().getUserLikesSavingShortsList().stream().iterator();
        List<Shorts> savingShortsList = new ArrayList<>();
        while(iter.hasNext()){
            Shorts shorts = shortsRepository.findById((Long)iter.next()).orElseThrow(CShortsNotFoundException::new);
            savingShortsList.add(shorts);
        }
        return savingShortsList.stream().map(ShortsDto.shortsResponse::new).toList();
    }
    public List<WorthyConsumptionResponseDto.ReadSummaryResponse> getUserLikesWorthyConsumption(Long userId){
        User user = userRepository.findById(userId).orElseThrow(CUserExistException::new);
        Iterator iter = user.getLikes().getUserLikesWorthyConsumptionList().stream().iterator();
        List<WorthyConsumption> worthyConsumptionList = new ArrayList<>();
        while(iter.hasNext()){
            WorthyConsumption worthyConsumption = worthyConsumptionRepository.findById((Long)iter.next()).orElseThrow(CWorthyConsumptionNotFoundException::new);
            worthyConsumptionList.add(worthyConsumption);
        }
        return worthyConsumptionList.stream().map(WorthyConsumptionResponseDto.ReadSummaryResponse::new).toList();
    }

}
