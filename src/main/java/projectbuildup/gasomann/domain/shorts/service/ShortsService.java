package projectbuildup.gasomann.domain.shorts.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.gasomann.domain.likes.repository.LikesShortsRepository;
import projectbuildup.gasomann.domain.shorts.dto.ShortsDto;
import projectbuildup.gasomann.domain.shorts.entity.Shorts;
import projectbuildup.gasomann.domain.shorts.entity.ShortsCategory;
import projectbuildup.gasomann.domain.shorts.repository.ShortsRepository;
import projectbuildup.gasomann.domain.user.entity.User;
import projectbuildup.gasomann.domain.user.repository.UserRepository;
import projectbuildup.gasomann.global.common.imageStore.Image;
import projectbuildup.gasomann.global.common.imageStore.ImageType;
import projectbuildup.gasomann.global.common.imageStore.ImageUploader;
import projectbuildup.gasomann.global.common.videoStore.Video;
import projectbuildup.gasomann.global.common.videoStore.VideoUploader;
import projectbuildup.gasomann.global.error.exception.CShortsNotFoundException;
import projectbuildup.gasomann.global.error.exception.CUserExistException;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShortsService {
    private final ShortsRepository shortsRepository;
    private final UserRepository userRepository;
    private final ImageUploader imageUploader;
    private final VideoUploader videoUploader;

    private final LikesShortsRepository likesShortsRepository;

    public void createShorts(ShortsDto.creatRequest shortsRequestDto) throws IOException {
        Video video = videoUploader.upload(shortsRequestDto.getVideo(),"shorts");
        Image image = imageUploader.upload(shortsRequestDto.getImage(), ImageType.SHORTS);

        Shorts shorts = new Shorts(shortsRequestDto, video.getVideoPath(), image.getImagePath());
        shortsRepository.save(shorts);
    }
    public ShortsDto.shortsResponse getOneShorts(Long shortsId, Long userId){
        User user = userRepository.findById(userId).orElseThrow(CUserExistException::new);
        Shorts shorts = shortsRepository.findById(shortsId).orElseThrow(CShortsNotFoundException::new);
        Boolean liked = likesShortsRepository.findByUserAndShorts(user, shorts).isPresent() ? Boolean.TRUE : Boolean.FALSE;
        return new ShortsDto.shortsResponse(shorts, liked);
    }
    public List<ShortsDto.shortsResponse> getAllSavingShorts(Long userId){
        User user = userRepository.findById(userId).orElseThrow(CUserExistException::new);
        return shortsRepository.findAll().stream()
                .filter(shorts -> shorts.getCategory() == ShortsCategory.SAVING)
                .map(shorts -> {
                    boolean liked = likesShortsRepository.findByUserAndShorts(user, shorts).isPresent();
                    return new ShortsDto.shortsResponse(shorts, liked);
                })
                .collect(Collectors.toList());
    }
    public List<ShortsDto.shortsResponse> getAllEduShorts(Long userId){
        User user = userRepository.findById(userId).orElseThrow(CUserExistException::new);
        return shortsRepository.findAll().stream()
                .filter(shorts -> shorts.getCategory() == ShortsCategory.EDUCATION)
                .map(shorts -> {
                    boolean liked = likesShortsRepository.findByUserAndShorts(user, shorts).isPresent();
                    return new ShortsDto.shortsResponse(shorts, liked);
                })
                .collect(Collectors.toList());
    }
    public void updateShorts(Long shortsId, ShortsDto.updateRequest shortsRequestDto) throws IOException {
        Shorts shorts = shortsRepository.findById(shortsId).orElseThrow(CShortsNotFoundException::new);
        Image image = imageUploader.upload(shortsRequestDto.getImage(), ImageType.SHORTS);
        Video video = videoUploader.upload(shortsRequestDto.getVideo(),"shorts");
        shorts.update(shortsRequestDto, image.getImagePath(), video.getVideoPath());

        shortsRepository.save(shorts);
    }
    public void deleteShorts(Long shortsId){
        shortsRepository.deleteById(shortsId);
    }
}
