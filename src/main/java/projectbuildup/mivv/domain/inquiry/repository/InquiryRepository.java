package projectbuildup.mivv.domain.inquiry.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import projectbuildup.mivv.domain.inquiry.entity.InquiryEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface InquiryRepository extends JpaRepository<InquiryEntity, Long> {

    int countByUser_idAndAnswer(Long user_id, String answer);

    Optional<InquiryEntity> findById(Long id);

    List<InquiryEntity> findByUser_id(Long user_id);

    List<InquiryEntity> findAllByOrderByTimeStampDesc();

    void deleteById(Long id);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update InquiryEntity i set i.answer=?1 where i.id=?2")
    void updateAnswer(String answer, Long id);
}
