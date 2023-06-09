package projectbuildup.mivv.domain.inquiry.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import projectbuildup.mivv.domain.inquiry.dto.InquiryDto;
import projectbuildup.mivv.domain.inquiry.entity.InquiryEntity;
import projectbuildup.mivv.domain.inquiry.repository.InquiryRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.error.exception.CInquiryNotFoundException;
import projectbuildup.mivv.global.error.exception.CInquiryNotMatchException;
import projectbuildup.mivv.global.error.exception.CInquiryOverException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class InquiryService {

    private final InquiryRepository repo;

    /**
     * 사용자가 문의 생성
     *
     * @param dto 문의제목, 문의내용
     * @param user 유저
     * @throws CInquiryOverException 사용자 문의 2개 초과시
     */
    public void createInquiry(final InquiryDto.InquiryRequestDto dto, final User user) {
        InquiryEntity entity = InquiryDto.InquiryRequestDto.toEntity(dto, user);

        if (isUnavailableToInquiry(entity)) {
            throw new CInquiryOverException();
        }

        repo.save(entity);
    }

    private boolean isUnavailableToInquiry(InquiryEntity entity) {
        return (repo.countByUser_idAndAnswer(entity.getUser().getId(), null) > 1);
    }

    /**
     * 관리자가 문의에 답변 입력
     *
     * @param dto 문의 번호, 답변 내용
     * @throws CInquiryOverException 문의 찾기 실패시
     */
    @Transactional
    public void createAnswer(final InquiryDto.AnswerRequestDto dto) {
        Optional<InquiryEntity> target = repo.findById(dto.getId());
        if (target.isEmpty()) {
            throw new CInquiryNotFoundException();
        }

        InquiryEntity result = target.get();
        result.updateAnswer(dto.getAnswer());
    }

    /**
     * 사용자가 사용자의 모든 문의 정보를 조회
     *
     * @param user 유저
     * @return List<InquiryDto.InquiryResponseDto> 사용자 문의들의 정보
     */
    public List<InquiryDto.InquiryResponseDto> retrieveForUser(final User user) {
        List<InquiryEntity> resultEntity = repo.findByUserIdOrderByTimeStampDesc(user.getId());

        return resultEntity.stream().map(InquiryDto.InquiryResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 관리자가 모든 문의 정보를 조회
     *
     * @return List<InquiryDto.InquiryResponseDto> 문의들의 정보
     */
    public List<InquiryDto.InquiryResponseDto> retrieveForAdmin() {
        List<InquiryEntity> resultEntity = repo.findAll(Sort.by(Sort.Direction.DESC, "timeStamp"));

        return resultEntity.stream().map(InquiryDto.InquiryResponseDto::new).collect(Collectors.toList());
    }

    /**
     * 사용자가 문의를 삭제
     *
     * @param id 문의 id
     * @throws CInquiryNotFoundException 문의 찾기 실패시
     */
    public void deleteInquiry(final User user, final Long id) {
        Optional<InquiryEntity> target = repo.findById(id);
        if (target.isEmpty()) {
            throw new CInquiryNotFoundException();
        }

        User inquiryOwner = target.get().getUser();
        if (!inquiryOwner.getId().equals(user.getId())) {
            throw new CInquiryNotMatchException();
        }

        repo.deleteById(id);
    }

    /**
     * 관리자가 문의의 답변을 삭제
     *
     * @param id 문의 id
     * @throws CInquiryNotFoundException 문의 찾기 실패시
     */
    @Transactional
    public void deleteAnswer(final Long id) {
        Optional<InquiryEntity> target = repo.findById(id);
        if (target.isEmpty()) {
            throw new CInquiryNotFoundException();
        }

        InquiryEntity result = target.get();
        result.updateAnswer(null);
    }

}
