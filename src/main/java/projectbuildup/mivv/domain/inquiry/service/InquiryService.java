package projectbuildup.mivv.domain.inquiry.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.inquiry.dto.InquiryDto;
import projectbuildup.mivv.domain.inquiry.entity.InquiryEntity;
import projectbuildup.mivv.domain.inquiry.repository.InquiryRepository;
import projectbuildup.mivv.domain.user.entity.User;
import projectbuildup.mivv.global.error.exception.CInquiryNotFoundException;
import projectbuildup.mivv.global.error.exception.CInquiryOverException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class InquiryService {

    private final InquiryRepository repo;

    public void registerInquiry(final InquiryDto.InquiryRequestDto dto, final User user) {
        InquiryEntity entity = InquiryDto.InquiryRequestDto.toEntity(dto, user);

        //사용자가 문의가능한 상태인지 확인(최대 2개)
        if (repo.countByUser_idAndAnswer(entity.getUser().getId(), entity.getAnswer()) > 1) {
            throw new CInquiryOverException();
        }

        repo.save(entity);
    }

    public void registerAnswer(final InquiryDto.AnswerRequestDto dto) {
        Optional<InquiryEntity> target = repo.findById(dto.getId());
        if (target.isEmpty()) {
            throw new CInquiryNotFoundException();
        }

        InquiryEntity result = target.get();
        repo.updateAnswer(dto.getAnswer(), result.getId());
    }

    public List<InquiryDto.InquiryResponseDto> retrieveForUser(final User user) {
        List<InquiryEntity> resultEntity = repo.findByUser_id(user.getId());

        return resultEntity.stream().map(InquiryDto.InquiryResponseDto::new).collect(Collectors.toList());
    }

    public List<InquiryDto.InquiryResponseDto> retrieveForAdmin() {
        List<InquiryEntity> resultEntity = repo.findAllByOrderByTimeStampDesc();

        return resultEntity.stream().map(InquiryDto.InquiryResponseDto::new).collect(Collectors.toList());
    }

    public InquiryDto.InquiryResponseDto retrieveDetails(final Long id) {
        Optional<InquiryEntity> result= repo.findById(id);
        if (result.isEmpty()) {
            throw new CInquiryNotFoundException();
        }

        InquiryEntity resultEntity = result.get();
        return new InquiryDto.InquiryResponseDto(resultEntity);
    }

    public void deleteInquiry(final Long id) {
        Optional<InquiryEntity> target = repo.findById(id);
        if (target.isEmpty()) {
            throw new CInquiryNotFoundException();
        }

        repo.deleteById(id);
    }

    public void deleteAnswer(final Long id) {
        Optional<InquiryEntity> target = repo.findById(id);
        if (target.isEmpty()) {
            throw new CInquiryNotFoundException();
        }

        repo.updateAnswer(null, id);
    }

}
