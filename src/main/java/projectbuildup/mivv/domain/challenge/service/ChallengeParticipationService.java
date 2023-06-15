package projectbuildup.mivv.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.challenge.dto.ChallengeDto;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;
import projectbuildup.mivv.domain.participation.entity.Participation;
import projectbuildup.mivv.domain.participation.repository.ParticipationRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.common.pagination.PageParam;
import projectbuildup.mivv.global.common.pagination.PagingDto;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;

@Service
@RequiredArgsConstructor
public class ChallengeParticipationService {
    private final ChallengeRepository challengeRepository;
    private final ChallengeService challengeService;
    private final UserRepository userRepository;
    private final ParticipationRepository participationRepository;

    /**
     * 참여 중인 챌린지 목록을 조회합니다.
     * 종료된 챌린지는 제외합니다.
     *
     * @param userId    회원 아이디넘버
     * @param pageParam 페이지 필드
     * @return 참여 중인 챌린지 목록
     */
    public PagingDto<ChallengeDto.Response> getOngoingChallenges(Long userId, PageParam pageParam) {
        Pageable pageable = pageParam.toPageable();
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        Page<Participation> pages = participationRepository.findAllByUserAndClosedFalse(user, pageable);
        return challengeService.participationsToResponseDto(pages.getNumber(), pages.getTotalPages(), pages.getContent());
    }


    /**
     * 참여 가능한 챌린지 목록을 조회합니다.
     *
     * @param userId    사용자 아이디넘버
     * @param pageParam 페이지 필드
     * @return 참여 가능한 챌린지 목록
     */
    public PagingDto<ChallengeDto.Response> getJoinableChallenges(Long userId, PageParam pageParam) {
        Pageable pageable = pageParam.toPageable();
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        Page<Challenge> pages = challengeRepository.findJoinableChallenge(user, pageable);
        return challengeService.challengesToResponseDto(pages.getNumber(), pages.getTotalPages(), pages.getContent());
    }

    /**
     * 참여했던 챌린지 중, 종료된 것을 조회합니다.
     *
     * @param userId    사용자 아이디넘버
     * @param pageParam 페이지 필드
     * @return 참여했던 챌린지 목록
     */
    public PagingDto<ChallengeDto.Response> getClosedChallenges(Long userId, PageParam pageParam) {
        Pageable pageable = pageParam.toPageable();
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);
        Page<Participation> pages = participationRepository.findAllByUserAndClosedTrue(user, pageable);
        return challengeService.participationsToResponseDto(pages.getNumber(), pages.getTotalPages(), pages.getContent());
    }
}
