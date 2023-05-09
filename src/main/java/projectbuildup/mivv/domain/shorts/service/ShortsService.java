package projectbuildup.mivv.domain.shorts.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.shorts.dto.ShortsDto;
import projectbuildup.mivv.domain.shorts.entity.Shorts;
import projectbuildup.mivv.domain.shorts.entity.ShortsCategory;
import projectbuildup.mivv.domain.shorts.repository.ShortsRepository;
import projectbuildup.mivv.global.common.imageStore.Image;
import projectbuildup.mivv.global.common.imageStore.ImageUploader;
import projectbuildup.mivv.global.error.exception.CShortsNotFoundException;

import java.io.IOException;
import java.util.List;
@Slf4j
@RequiredArgsConstructor
@Service
public class ShortsService {
    private final ShortsRepository shortsRepository;
    private final ImageUploader imageUploader;

    public void createShorts(ShortsDto.creatRequest shortsRequestDto) throws IOException {
        Image image = imageUploader.upload(shortsRequestDto.getImage(), "shorts");
        Shorts shorts = new Shorts(shortsRequestDto, image.getImagePath());
        shortsRepository.save(shorts);
    }
    public ShortsDto.shortsResponse getOneShorts(Long shortsId){
        Shorts shorts = shortsRepository.findById(shortsId).orElseThrow(CShortsNotFoundException::new);
        return new ShortsDto.shortsResponse(shorts);
    }
    public List<ShortsDto.shortsResponse> getAllSavingShorts(){
        return shortsRepository.findAll().stream().filter(shorts -> shorts.getCategory()==ShortsCategory.SAVING).map(ShortsDto.shortsResponse::new).toList();
    }
    public List<ShortsDto.shortsResponse> getAllEduShorts(){
        return shortsRepository.findAll().stream().filter(shorts -> shorts.getCategory()==ShortsCategory.EDUCATION).map(ShortsDto.shortsResponse::new).toList();

    }
    public void updateShorts(Long shortsId, ShortsDto.updateRequest shortsRequestDto) throws IOException {
        Shorts shorts = shortsRepository.findById(shortsId).orElseThrow(CShortsNotFoundException::new);
        Image image = imageUploader.upload(shortsRequestDto.getImage(), "shorts");
        shorts.update(shortsRequestDto, image.getImagePath());
        shortsRepository.save(shorts);
    }
    public void deleteShorts(Long shortsId){
        shortsRepository.deleteById(shortsId);
    }
}
