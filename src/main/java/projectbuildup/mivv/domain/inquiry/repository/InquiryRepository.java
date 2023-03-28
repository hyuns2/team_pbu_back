package projectbuildup.mivv.domain.inquiry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import projectbuildup.mivv.domain.inquiry.entity.InquiryEntity;

@Repository
public interface InquiryRepository extends JpaRepository<InquiryEntity, Long> {



}
