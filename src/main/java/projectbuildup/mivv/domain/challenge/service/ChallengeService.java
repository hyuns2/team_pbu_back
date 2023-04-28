package projectbuildup.mivv.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import projectbuildup.mivv.domain.challenge.dto.ChallengeDto;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;
import projectbuildup.mivv.domain.image.Image;
import projectbuildup.mivv.domain.image.ImageUploader;
import projectbuildup.mivv.global.common.pagination.PageParam;
import projectbuildup.mivv.global.common.pagination.PagingDto;
import projectbuildup.mivv.global.error.exception.CResourceNotFoundException;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final ImageUploader imageUploader;

    /**
     * 챌린지를 생성합니다.
     *
     * @param requestDto 생성할 챌린지 정보
     */
    public void createChallenge(ChallengeDto.CreationRequest requestDto, MultipartFile imageFile) throws IOException {
        Image image = imageUploader.upload(imageFile);
        Challenge challenge = Challenge.from(requestDto, image);
        challengeRepository.save(challenge);
    }

    /**
     * 전체 챌린지를 조회합니다.
     *
     * @param pageParam 페이지네이션
     * @return 전체 챌린지 목록
     */
    public PagingDto<ChallengeDto.Response> getChallenges(PageParam pageParam) {
        Pageable pageable = pageParam.toPageable();
        Page<Challenge> pages = challengeRepository.findAll(pageable);
        List<ChallengeDto.Response> challenges = pages.getContent().stream()
                .map(ChallengeDto.Response::new)
                .toList();
        return new PagingDto<>(pages.getNumber(), pages.getTotalPages(), challenges);
    }


    /**
     * 챌린지 하나를 조회합니다.
     *
     * @param challengeId 챌린지 아이디넘버
     * @return 챌린지 정보
     */
    public ChallengeDto.ShortResponse getChallenge(Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(CResourceNotFoundException::new);
        return new ChallengeDto.ShortResponse(challenge);
    }

    /**
     * 챌린지의 정보를 수정합니다.
     *
     * @param requestDto 수정할 항목
     */
    public void updateChallenge(ChallengeDto.UpdateRequest requestDto, MultipartFile imageFile) throws IOException {
        Challenge challenge = challengeRepository.findById(requestDto.getChallengeId()).orElseThrow(CResourceNotFoundException::new);
        challenge.update(requestDto);
        if (imageFile != null) {
            imageUploader.delete(challenge.getImage());
            Image image = imageUploader.upload(imageFile);
            challenge.updateImage(image);
        }
        challengeRepository.save(challenge);
    }

    /**
     * 챌린지를 삭제합니다.
     *
     * @param challengeId 챌린지 아이디넘버
     */
    public void deleteChallenge(Long challengeId) {
        challengeRepository.deleteById(challengeId);
    }
}
