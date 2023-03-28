package projectbuildup.mivv.domain.inquiry.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import projectbuildup.mivv.domain.inquiry.entity.InquiryEntity;
import projectbuildup.mivv.domain.inquiry.repository.InquiryRepository;
import projectbuildup.mivv.global.error.exception.CUserNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class InquiryService {

    private final InquiryRepository repo;

    public void validate(final InquiryEntity entity) {
        if (entity.getUser() == null) {
            throw new CUserNotFoundException();
        }
    }

    public void register(final InquiryEntity entity) {
        validate(entity);
        repo.save(entity);
    }

}
