package projectbuildup.mivv.domain.inquiry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.w3c.dom.stylesheets.LinkStyle;
import projectbuildup.mivv.domain.inquiry.entity.InquiryEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface InquiryRepository extends JpaRepository<InquiryEntity, Long> {

    int countByUser_idAndAnswer(Long user_id, String answer);

    Optional<InquiryEntity> findById(Long id);

    List<InquiryEntity> findByUser_id(Long user_id);
}
