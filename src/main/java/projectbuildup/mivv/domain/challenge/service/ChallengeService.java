package projectbuildup.mivv.domain.challenge.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.challenge.dto.ChallengeDto;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.participation.repository.ParticipationRepository;
import projectbuildup.mivv.domain.remittance.entity.Remittance;
import projectbuildup.mivv.global.common.imageStore.Image;
import projectbuildup.mivv.global.common.imageStore.ImageType;
import projectbuildup.mivv.global.common.imageStore.ImageUploader;
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
    private final ParticipationRepository participationRepository;


    /**
     * 챌린지를 생성합니다.
     *
     * @param requestDto 생성할 챌린지 정보
     */
    @Transactional
    public void createChallenge(ChallengeDto.CreationRequest requestDto) throws IOException {
        Image image = imageUploader.upload(requestDto.getImageFile(), ImageType.CHALLENGE);
        Challenge challenge = Challenge.of(requestDto, image);
        challengeRepository.save(challenge);
    }

    /**
     * 전체 챌린지를 조회합니다.
     *
     * @param pageParam 페이지네이션
     * @return 전체 챌린지 목록
     */
    public PagingDto<ChallengeDto.Response> getAllChallenges(PageParam pageParam) {
        Pageable pageable = pageParam.toPageable();
        Page<Challenge> pages = challengeRepository.findAll(pageable);
        return mapToResponseDto(pages);
    }

    /**
     * Page 형식을 응답 DTO로 매핑합니다.
     *
     * @param pages pages
     * @return response dto
     */
    public PagingDto<ChallengeDto.Response> mapToResponseDto(Page<Challenge> pages) {
        List<ChallengeDto.Response> challenges = pages.getContent().stream()
                .map(c -> new ChallengeDto.Response(c, getTotalSavingAmount(c)))
                .toList();
        return new PagingDto<>(pages.getNumber(), pages.getTotalPages(), challenges);
    }

    private long getTotalSavingAmount(Challenge challenge) {
        long sum = 0;
        List<Participation> participations = participationRepository.findAllByChallenge(challenge);
        for (Participation participation : participations) {
            sum += calculateTotal(participation);
        }
        return sum;
    }

    private long calculateTotal(Participation participation) {
        return participation.getRemittanceList().stream()
                .mapToLong(Remittance::getAmount)
                .sum();
    }


    /**
     * 챌린지의 정보를 수정합니다.
     *
     * @param requestDto 수정할 항목
     */
    @Transactional
    public void updateChallenge(ChallengeDto.UpdateRequest requestDto) throws IOException {
        Challenge challenge = challengeRepository.findById(requestDto.getChallengeId()).orElseThrow(CResourceNotFoundException::new);
        challenge.update(requestDto);
        if (requestDto.getImageFile() != null) {
            imageUploader.delete(challenge.getImage());
            Image image = imageUploader.upload(requestDto.getImageFile(), ImageType.CHALLENGE);
            challenge.updateImage(image);
        }
        challengeRepository.save(challenge);
    }

    /**
     * 챌린지를 삭제합니다.
     *
     * @param challengeId 챌린지 아이디넘버
     */
    @Transactional
    public void deleteChallenge(Long challengeId) {
        challengeRepository.deleteById(challengeId);
    }
}
