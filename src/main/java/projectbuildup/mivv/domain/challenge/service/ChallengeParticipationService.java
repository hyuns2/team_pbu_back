package projectbuildup.mivv.domain.challenge.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.challenge.dto.ChallengeDto;
import projectbuildup.mivv.domain.challenge.entity.Challenge;
import projectbuildup.mivv.domain.challenge.repository.ChallengeRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.domain.user.repository.UserRepository;
import projectbuildup.mivv.global.common.pagination.PageParam;
import projectbuildup.mivv.global.common.pagination.PageSortGroup;
import projectbuildup.mivv.global.common.pagination.PagingDto;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChallengeParticipationService {
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;

    /**
     * 참여 중인 챌린지 목록을 조회합니다.
     *
     * @param userId 회원 아이디넘버
     * @return 참여 중인 챌린지 목록
     */
    public PagingDto<ChallengeDto.ShortResponse> getOngoingChallenges(Long userId, PageParam pageParam) {
        Pageable pageable = pageParam.toPageable();
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);

        Page<Challenge> pages = challengeRepository.findOngoingChallenge(user, pageable);
        List<ChallengeDto.ShortResponse> responseData = pages.getContent().stream()
                .map(ChallengeDto.ShortResponse::new)
                .toList();
        return new PagingDto<>(pages.getNumber(), pages.getTotalPages(), responseData);
    }

    /**
     * 참여 가능한 챌린지 목록을 조회합니다.
     *
     * @param userId 사용자 아이디넘버
     * @return 참여 가능한 챌린지 목록
     */
    public PagingDto<ChallengeDto.ShortResponse> getJoinableChallenges(Long userId, PageParam pageParam) {
        Pageable pageable = pageParam.toPageable();
        User user = userRepository.findById(userId).orElseThrow(CUserNotFoundException::new);

        Page<Challenge> pages = challengeRepository.findJoinableChallenge(user, pageable);
        List<ChallengeDto.ShortResponse> responseData = pages.getContent().stream()
                .map(ChallengeDto.ShortResponse::new)
                .toList();
        return new PagingDto<>(pages.getNumber(), pages.getTotalPages(), responseData);
    }
}
