package projectbuildup.mivv.domain.shortsTest;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import projectbuildup.mivv.domain.shorts.dto.ShortsDto;
import projectbuildup.mivv.domain.shorts.entity.Shorts;
import projectbuildup.mivv.domain.shorts.entity.ShortsCategory;
import projectbuildup.mivv.domain.shorts.repository.ShortsRepository;
import projectbuildup.mivv.domain.shorts.service.ShortsService;

import java.util.Iterator;
import java.util.List;

@SpringBootTest
@Slf4j
public class ShortsTest {
    @Autowired
    ShortsService shortsService;
    @Autowired
    ShortsRepository shortsRepository;

    @Test
    @DisplayName("쇼츠를 생성하는 테스트")
//확인완료
    void createTest() {

        ShortsDto.creatRequest shortsRequestDto0 = new ShortsDto.creatRequest();
        shortsRequestDto0.setTitle("shorts title0");
        shortsRequestDto0.setContent("shorts contents0");
        shortsRequestDto0.setVideoUrl("shorts videoUrl0");
        shortsRequestDto0.setCategory(ShortsCategory.SAVING);

        Shorts shorts = new Shorts(shortsRequestDto0);
        Shorts shorts_saved = shortsRepository.save(shorts);

        log.info("생성된 쇼츠는 {}", shorts);
        log.info("저장된 쇼츠는 {}", shorts_saved);


        ShortsDto.creatRequest shortsRequestDto1 = new ShortsDto.creatRequest();
        shortsRequestDto1.setTitle("shorts title1");
        shortsRequestDto1.setContent("shorts contents1");
        shortsRequestDto1.setVideoUrl("shorts videoUrl1");
        shortsRequestDto1.setCategory(ShortsCategory.SAVING);

        shortsService.createShorts(shortsRequestDto1);


    }

    @Test
    @DisplayName("쇼츠 리스트를 조회하는 테스트")
//확인완료
    void getCategoryShortsList() {
        ShortsDto.creatRequest shortsRequestDto1 = new ShortsDto.creatRequest();
        shortsRequestDto1.setTitle("shorts title1");
        shortsRequestDto1.setContent("shorts contents1");
        shortsRequestDto1.setVideoUrl("shorts videoUrl1");
        shortsRequestDto1.setCategory(ShortsCategory.EDUCATION);

        shortsService.createShorts(shortsRequestDto1);

        ShortsDto.creatRequest shortsRequestDto2 = new ShortsDto.creatRequest();
        shortsRequestDto2.setTitle("shorts title2");
        shortsRequestDto2.setContent("shorts contents2");
        shortsRequestDto2.setVideoUrl("shorts videoUrl2");
        shortsRequestDto2.setCategory(ShortsCategory.EDUCATION);

        shortsService.createShorts(shortsRequestDto2);

        ShortsDto.creatRequest shortsRequestDto3 = new ShortsDto.creatRequest();
        shortsRequestDto3.setTitle("shorts title3");
        shortsRequestDto3.setContent("shorts contents3");
        shortsRequestDto3.setVideoUrl("shorts videoUrl3");
        shortsRequestDto3.setCategory(ShortsCategory.SAVING);

        shortsService.createShorts(shortsRequestDto3);

        ShortsDto.creatRequest shortsRequestDto4 = new ShortsDto.creatRequest();
        shortsRequestDto4.setTitle("shorts title4");
        shortsRequestDto4.setContent("shorts contents4");
        shortsRequestDto4.setVideoUrl("shorts videoUrl4");
        shortsRequestDto4.setCategory(ShortsCategory.SAVING);

        shortsService.createShorts(shortsRequestDto4);

        ShortsDto.creatRequest shortsRequestDto5 = new ShortsDto.creatRequest();
        shortsRequestDto5.setTitle("shorts title5");
        shortsRequestDto5.setContent("shorts contents5");
        shortsRequestDto5.setVideoUrl("shorts videoUrl5");
        shortsRequestDto5.setCategory(ShortsCategory.EDUCATION);

        shortsService.createShorts(shortsRequestDto5);

        List<ShortsDto.shortsResponse> savingShortsList = shortsService.getAllSavingShorts();

        for (int i = 0; i < savingShortsList.size(); i++) {
            log.info("쇼츠 {} {}", savingShortsList.get(i).getTitle(), savingShortsList.get(i).getCategory());
        }

        List<ShortsDto.shortsResponse> eduShortsList = shortsService.getAllEduShorts();
        for (int i = 0; i < eduShortsList.size(); i++) {
            log.info("쇼츠 {} {}", eduShortsList.get(i).getTitle(), eduShortsList.get(i).getCategory());
        }
    }

    @Test
    @DisplayName("쇼츠를 단건 조회하는 테스트")
//확인완료
    void getOneShortsTest() {
        ShortsDto.shortsResponse shortsResponse = shortsService.getShorts((long) 1);
        log.info("쇼츠 {} {} {} {}", shortsResponse.getTitle(), shortsResponse.getContent(), shortsResponse.getVideoUrl(), shortsResponse.getCategory());
    }

    @Test
    @DisplayName("쇼츠를 수정하는 테스트")
//확인완료
    void updateShortsTest() {
        ShortsDto.updateRequest requestDto = new ShortsDto.updateRequest();
        requestDto.setId((long) 1);
        requestDto.setTitle("fix title");
        requestDto.setContent("fix content");
        requestDto.setVideoUrl("fix videoUrl");

        shortsService.updateShorts(requestDto);

        ShortsDto.shortsResponse shortsResponse = shortsService.getShorts((long) 1);
        log.info("쇼츠 {} {} {} {}", shortsResponse.getTitle(), shortsResponse.getContent(), shortsResponse.getVideoUrl(), shortsResponse.getCategory());
    }

    @Test
    @DisplayName(value = "쇼츠를 삭제하는 테스트")
//확인완료
    void deleteShortsTest() {
        shortsService.deleteShorts((long) 1);
        Assertions.assertThat(shortsRepository.findById((long) 1)).isNotPresent();
    }
}
