package projectbuildup.mivv.domain.inquiry.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.inquiry.entity.InquiryEntity;
import projectbuildup.mivv.domain.inquiry.repository.InquiryRepository;
import projectbuildup.mivv.global.error.exception.CInquiryNotFoundException;
import projectbuildup.mivv.global.error.exception.CInquiryOverException;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class InquiryService {

    private final InquiryRepository repo;

    public void validate(final InquiryEntity entity) {
        if (entity.getUser() == null) {
            throw new CUserNotFoundException();
        }
    }

    public void registerInquiry(final InquiryEntity entity) {
        validate(entity);

        //사용자가 문의가능한 상태인지 확인(최대 2개)
        if (repo.countByUser_idAndAnswer(entity.getUser().getId(), entity.getAnswer()) > 1) {
            throw new CInquiryOverException();
        }

        repo.save(entity);
    }

    public void registerAnswer(final InquiryEntity entity) {
        validate(entity);

        Optional<InquiryEntity> target = repo.findById(entity.getId());
        if (target.isEmpty()) {
            throw new CInquiryNotFoundException();
        }

        final InquiryEntity result = target.get();
        result.setAnswer(entity.getAnswer());
        repo.save(result);
    }

    public List<InquiryEntity> retrieveForUser(final InquiryEntity entity) {
        validate(entity);

        return repo.findByUser_id(entity.getUser().getId());
    }

    public List<InquiryEntity> retrieveForAdmin() {
        return repo.findAllByOrderByTimeStampDesc();
    }

    public InquiryEntity retrieveDetailsForUser(final InquiryEntity entity) {
        validate(entity);

        List<InquiryEntity> result = repo.findByUser_idAndTitleAndContent(entity.getUser().getId(), entity.getTitle(), entity.getContent());
        if (result.size() != 1) {
            throw new CInquiryNotFoundException();
        }

        return result.get(0);
    }

    public InquiryEntity retrieveDetailsForAdmin(final InquiryEntity entity) {
        validate(entity);

        Optional<InquiryEntity> result= repo.findById(entity.getId());
        if (result.isEmpty()) {
            throw new CInquiryNotFoundException();
        }

        return result.get();
    }

    public void deleteInquiry(final InquiryEntity entity) {
        validate(entity);

        List<InquiryEntity> result = repo.findByUser_idAndTitleAndContent(entity.getUser().getId(), entity.getTitle(), entity.getContent());
        if (result.size() != 1) {
            throw new CInquiryNotFoundException();
        }

        repo.deleteById(result.get(0).getId());
    }

}
