package projectbuildup.mivv.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.challenge.dto.ChallengeDto;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;
import projectbuildup.mivv.global.common.PagingDto;
import projectbuildup.mivv.global.error.exception.CResourceNotFoundException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;

    /**
     * 챌린지를 생성합니다.
     *
     * @param requestDto 생성할 챌린지 정보
     */
    public void createChallenge(ChallengeDto.CreationRequest requestDto) {
        Challenge challenge = Challenge.of(requestDto);
        challengeRepository.save(challenge);
    }

    /**
     * 전체 챌린지를 조회합니다.
     *
     * @param pageable pageable
     * @return 전체 챌린지 목록
     */
    public PagingDto<ChallengeDto.Response> getChallenges(Pageable pageable) {
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
    public ChallengeDto.Response getChallenge(Long challengeId) {
        Challenge challenge = challengeRepository.findById(challengeId).orElseThrow(CResourceNotFoundException::new);
        return new ChallengeDto.Response(challenge);
    }

    /**
     * 챌린지의 정보를 수정합니다.
     *
     * @param requestDto 수정할 항목
     */
    public void updateChallenge(ChallengeDto.UpdateRequest requestDto) {
        Challenge challenge = challengeRepository.findById(requestDto.getChallengeId()).orElseThrow(CResourceNotFoundException::new);
        challenge.update(requestDto);
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
